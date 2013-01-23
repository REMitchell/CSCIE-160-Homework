package cscie160.Project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import cscie160.Project.ATMException;
import java.util.ArrayList;

/**
 * Implements ATM
 * Gets security and bank objects in order to authenticate and handle client requests for withdrawals,
 * deposits, transfers, and balance inquiries. 
 * Sends a TransactionNotifiation to Client to acknowledge receipt of commands
 * @author rmitchell
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
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}catch(RemoteException e){
			System.out.println("Error getting ATM Factory");
			e.printStackTrace();
		}
    	cashOnHand = (float) 500.00;
    }
	
    /**
     * The Client uses this method to register itself for notificaitons when the ATM
     * is processing requests on the clients behalf. 
     */
    public void registerListener(ATMListener clientListener) throws RemoteException{
    	if(clientListener == null){
    		System.out.println("Client listener is null");
    	}
    	clients.add(clientListener);
    	System.out.println("Added new Client listener");
    }
    
    /**
     * The Client uses this method to remve itself from the ATM's list of listeners
     */
    public void removeListener(ATMListener clientListener) throws RemoteException{
    	System.out.println("Removing Client listener");
    	clients.remove(clientListener);
    }
    
    /**
     * Sends a TransactionNotification to all registered clients on the receipt of a command
     */
    public void notifyListeners(TransactionNotification notification){
    	for(ATMListener client : clients){
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
	 * Authenticates both fromAccount and toAccount, and verifies that withdrawals are allowed on fromAccount and 
	 * deposits are allowed on toAccount. Then, transfers a given amount from fromAccount to toAccount.
	 */
	
	public boolean transfer(AccountInfo fromAccount, AccountInfo toAccount, float amount) throws ATMException, RemoteException{
		notifyListeners(new TransactionNotification(Commands.TRANSFER, amount, toAccount, fromAccount));
		boolean fromAuthentication = false;
		boolean toAuthentication = false;
		try{
			fromAuthentication = security.authenticatePin(fromAccount);
			toAuthentication = security.authenticatePin(toAccount);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(fromAuthentication && toAuthentication){
			try{
			security.authorizeTransfer(fromAccount.getAccountNum(), toAccount.getAccountNum());
			}catch(AuthorizationException e){
				System.out.println("Transfer not allowed");
				return false;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}
		}

		Account fromAcctObj = bank.getAccount(fromAccount.getAccountNum());
		Account toAcctObj = bank.getAccount(toAccount.getAccountNum());
		try{
			float oldFromBalance = fromAcctObj.getBalance();
			if(amount > oldFromBalance){
				throw new ATMException("Insufficient funds to complete transfer");
			}
			float newFromBalance = oldFromBalance - amount;
			System.out.println("The old from balance is : "+oldFromBalance+". Setting the from bank balance to "+newFromBalance);
			bank.updateAccountBalance(fromAccount.getAccountNum(), newFromBalance);
			
			float oldToBalance = toAcctObj.getBalance();
			float newToBalance = oldToBalance + amount;
			System.out.println("The old to balance is : "+oldToBalance+" Setting the to bank balance to "+newToBalance);
			bank.updateAccountBalance(toAccount.getAccountNum(), newToBalance);
		}catch(RemoteException e){
			throw new RemoteException("Error setting account balance");
		}
		return true;
	}
	
	/**
	 * Authenticates AccountInfo provided, and verifies that deposits can be performed on the account.
	 * Then deposits the given amount into the account
	 */
	public boolean deposit(AccountInfo account, float amount) throws RemoteException{
		notifyListeners(new TransactionNotification(Commands.DEPOSIT, amount, account));
		System.out.println("making a deposit!");
		boolean authentication = false;
		try{
			authentication = security.authenticatePin(account);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(authentication){
			try{
				security.authorizeDeposit(account.getAccountNum());
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
			acctObj = bank.getAccount(account.getAccountNum());
			System.out.println("Got account! It is: "+acctObj);
		}catch(RemoteException e){
			System.out.println("System error: Cannot access account");
			return false;
		}

		float newBalance = acctObj.getBalance()+amount;
		bank.updateAccountBalance(account.getAccountNum(), newBalance);
		return true;
	}
	
	/**
	 * Authenticates AccountInfo provided, and verifies that withdrawals can be performed on the account.
	 * Then withdraws the given amount from the account
	 */
	public boolean withdraw(AccountInfo account, float amount) throws ATMException{
		TransactionNotification transaction = null;
		try{
			transaction = new TransactionNotification(Commands.WITHDRAW, amount, account);
		}catch(RemoteException e){
			throw new ATMException("Unable to create transaction notification");
		}
		notifyListeners(transaction);
		if(amount > cashOnHand){
			System.out.println("Amount is greater than cash on hand. Unable to perform transaction");
			return false;
		}

		boolean authentication = false;
		try{
			authentication = security.authenticatePin(account);
		}catch(AuthorizationException e){
			System.out.println("Invalid PIN or invalid account number");
			return false;
		}catch(RemoteException re){
			System.out.println("Remote Exception during withdraw");
		}
		if(authentication){
			try{
				security.authorizeWithdraw(account.getAccountNum());
			}catch(AuthorizationException e){
				System.out.println("Withdrawal not allowed");
				return false;
			}catch(RemoteException re){
				System.out.println("Remote Exception during withdraw");
			}
		}
		Account acctObj = null;
		
		try{
			acctObj = bank.getAccount(account.getAccountNum());
			float acctBalance = acctObj.getBalance();
			if(acctBalance < amount){
				throw new ATMException("Insufficient funds in account for that withdrawal.");
			}
		}catch(RemoteException e){
			System.out.println("System error: Cannot access account");
		}

		float newBalance = (float) 0.00;
		try{
			newBalance = acctObj.getBalance() - amount;
			bank.updateAccountBalance(account.getAccountNum(), newBalance);
			//Cash on hand is updated immediately after the bank updates account balance, 
			//in case there is an error updating balance
			cashOnHand = cashOnHand - amount;
		}catch(RemoteException e){
			System.out.println("Error setting account balance");
			return false;
		}
		return true;
	}
	
	/**
	 * Authenticates AccountInfo provided, and verifies that the balance can be gotten from the account.
	 * Then retrieves the balance from the account.
	 */
	public float getBalance(AccountInfo account) throws RemoteException{
		System.out.println("Getting balance");
		notifyListeners(new TransactionNotification(Commands.BALANCE, account));
		boolean authentication = false;
		try{
			System.out.println("Authorizing balance");
			authentication = security.authenticatePin(account);
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
}
