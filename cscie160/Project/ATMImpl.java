package cscie160.Project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import cscie160.Project.ATMException;
import java.util.ArrayList;

/**
 *First, notify each of the ATMListeners with a TransactionNotification indicating that it is about to process the operation
 *Then, requires an AccountInfo object as a parameter and authenticates with the remote Security service
 */
/**
 * [notify all registered listeners of the transaction]
   [use security service to authenticate account info]
   [use security service to authorize operation on account]
   [use bank to obtain account reference(s)]
   [use account reference(s) to perform transaction]
 * @author ryanmitchell
 *
 */
public class ATMImpl extends UnicastRemoteObject implements ATM{

	private Bank bank;
	private Security security;
	private float cashOnHand;
	private static ArrayList<ATMListener> clients;
    public ATMImpl() throws RemoteException {
    	System.out.println("In the constructor for ATMImpl!");
    	clients = new ArrayList<ATMListener>();
    	try {
			security = (Security) Naming.lookup("//localhost/security");
			bank = (Bank) Naming.lookup("//localhost/bank");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(RemoteException e){
			System.out.println("Error getting ATM Factory");
			e.printStackTrace();
		}
    	cashOnHand = (float) 500.00;
    }
	
    public boolean authenticate(AccountInfo accountInfo){
    	boolean authenticate = false;
    	try{
    		authenticate = security.authenticatePin(accountInfo.getAccountNum(), accountInfo.getPin());
    	}catch(AuthorizationException e){
    		System.out.println("Incorrect pin!");
    		return false;
    	}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
    	return authenticate;
    }
    
    //Client uses this method to register itself for notifications 
    //when the ATM is processing events on the client's behalf
    public void registerListener(ATMListener clientListener) throws RemoteException{
    	System.out.println("Registering listener!");
    	if(clientListener == null){
    		System.out.println("Client listener is null");
    	}
    	System.out.println(clientListener);
    	clients.add(clientListener);
    }
    public void removeListener(ATMListener clientListener) throws RemoteException{
    	clients.remove(clientListener);
    }
    
    public void notifyListeners(TransactionNotification notification){
    	//This needs to print a transaction notification to the reader
    	for(ATMListener client : clients){
    		System.out.println("About to send the notification: "+notification);
    		try{
    			client.receiveTransactionNotification(notification);
    		}catch(IOException e){
    			System.out.println("IOException");
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
     * Creates a new ATMImpl, called myATM, and binds it in the registry 
     * so that the client can find it
     * @param args
     */
	public static void main(String[] args) {
		try{
			ATMImpl obj = new ATMImpl();
			Naming.rebind("//localhost/myATM", obj);
			System.out.println("myATM is bound in registry");
		} catch (Exception e) {
			System.out.println("ATMImpl error: "+e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Deposits a given amount into the an Account corresponding to the provided Integer accountNum
	 */
	
	public boolean transfer(String fromAccountNum, Integer fromAccountPin, String toAccountNum, Integer toAccountPin, float amount) throws ATMException, RemoteException{
		notifyListeners(new TransactionNotification(Commands.TRANSFER, amount, new AccountInfo(toAccountNum, toAccountPin), new AccountInfo(fromAccountNum, fromAccountPin)));
		boolean fromAuthentication = false;
		boolean toAuthentication = false;
		try{
			fromAuthentication = security.authenticatePin(fromAccountNum, fromAccountPin);
			toAuthentication = security.authenticatePin(toAccountNum, toAccountPin);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(fromAuthentication && toAuthentication){
			try{
			security.authorizeTransfer(fromAccountNum, toAccountNum);
			}catch(AuthorizationException e){
				System.out.println("Transfer not allowed");
				return false;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}
		}

		Account fromAcctObj = bank.getAccount(fromAccountNum);
		Account toAcctObj = bank.getAccount(toAccountNum);
		try{
			float oldFromBalance = fromAcctObj.getBalance();
			if(amount > oldFromBalance){
				throw new ATMException("Insufficient funds to complete transfer");
			}
			float newFromBalance = oldFromBalance - amount;
			System.out.println("The old from balance is : "+oldFromBalance+". Setting the from bank balance to "+newFromBalance);
			bank.updateAccountBalance(fromAccountNum, newFromBalance);
			
			float oldToBalance = toAcctObj.getBalance();
			float newToBalance = oldToBalance + amount;
			System.out.println("The old to balance is : "+oldToBalance+" Setting the to bank balance to "+newToBalance);
			bank.updateAccountBalance(toAccountNum, newToBalance);
		}catch(RemoteException e){
			throw new RemoteException("Error setting account balance");
		}
		return true;
	}
	
	public boolean deposit(String accountNum, Integer accountPin, float amount) throws RemoteException{
		notifyListeners(new TransactionNotification(Commands.DEPOSIT, amount, new AccountInfo(accountNum, accountPin)));
		System.out.println("making a deposit!");
		boolean authentication = false;
		try{
			authentication = security.authenticatePin(accountNum, accountPin);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(authentication){
			try{
				security.authorizeDeposit(accountNum);
			}catch(AuthorizationException e){
				System.out.println("Deposit not allowed");
				return false;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}
		}
		System.out.println("Authorization succeeded!");
		Account acctObj = null;
		try{
			acctObj = bank.getAccount(accountNum);
			System.out.println("Got account! It is: "+acctObj);
		}catch(RemoteException e){
			System.out.println("System error: Cannot access account");
			return false;
		}

		float newBalance = acctObj.getBalance()+amount;
		bank.updateAccountBalance(accountNum, newBalance);
		return true;
	}
	
	/**
	 * Withdraws a given amount from the Account corresponding to the provided Integer accountNum
	 */
	public boolean withdraw(String accountNum, Integer accountPin, float amount) throws ATMException{
		notifyListeners(new TransactionNotification(Commands.WITHDRAW, amount, new AccountInfo(accountNum, accountPin)));
		//Consider synchronizing on cash on hand
		if(amount > cashOnHand){
			System.out.println("Amount is greater than cash on hand. Unable to perform transaction");
			return false;
		}

		boolean authentication = false;
		try{
			authentication = security.authenticatePin(accountNum, accountPin);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(authentication){
			try{
				security.authorizeWithdraw(accountNum);
			}catch(AuthorizationException e){
				System.out.println("Withdrawal not allowed");
				return false;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}
		}
		Account acctObj = null;
		
		try{
			acctObj = bank.getAccount(accountNum);
			float acctBalance = acctObj.getBalance();
			if(acctBalance < amount){
				throw new ATMException("Insufficient funds in account for that withdrawal.");
			}
		}catch(RemoteException e){
			System.out.println("System error: Cannot access account");
		}
		/*try{
			acctObj.withdraw(amount);
		}catch(ATMException e){
			System.out.println("Unable to withdraw from account");
			return false;
		}*/
		float newBalance = (float) 0.00;
		try{
			newBalance = acctObj.getBalance() - amount;
			cashOnHand = cashOnHand - amount;
			bank.updateAccountBalance(accountNum, newBalance);
		}catch(RemoteException e){
			System.out.println("Error setting account balance");
			return false;
		}
		return true;
	}
	
	
	public float getBalance(AccountInfo account) throws RemoteException{
		System.out.println("Getting balance");
		notifyListeners(new TransactionNotification(Commands.BALANCE, (float) 0.00, account));
		boolean authentication = false;
		try{
			System.out.println("Authorizing balance");
			authentication = security.authenticatePin(account.getAccountNum(), account.getPin());
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return (float) -1.00;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		
		if(authentication){
			try{
				security.authorizeBalance(account.getAccountNum());
			}catch(AuthorizationException e){
				System.out.println("Balance not allowed");
				return (float) -1.00;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}

			Account acctObj = null;
			try{
				System.out.println("About to retrieve account object "+account.getAccountNum());
				acctObj = bank.getAccount(account.getAccountNum());
			}catch(RemoteException e){
				System.out.println("System error: Cannot access balance of account "+account.getAccountNum());
				return (float) -1.00;
			}
			float balance = (float) -1.00;
			balance = acctObj.getBalance();
			return balance;
		}
		return (float) -1.00;
	}
	/**
	 * Returns the balance of an Account corresponding to the provided Integer accountNum*/
}
