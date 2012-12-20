package cscie160.Project;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * @param args
	 */
	private Map<Integer, Account> accounts;
	private int cashOnHand;
    public ATMImpl() throws RemoteException {
    	cashOnHand = 500;
    	accounts = new HashMap<Integer, Account>();
		//Create some accounts, store them in a map
		accounts.put(new Integer(0000001), new Account((float) 0.00));
		accounts.put(new Integer(0000002), new Account((float) 100.00));
		accounts.put(new Integer(0000003), new Account((float) 500.00));
    }
	
    /**
     * Creates a new ATMImpl, called myATM, and binds it in the registry 
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
	public boolean deposit(Integer accountNum, float amount) {
		if(amount < 0.00){
			return false;
		}
		Account myAccount = accounts.get(accountNum);
		float startBalance = myAccount.getBalance();
		myAccount.setBalance(startBalance+amount);
		return true;
	}
	
	/**
	 * Withdraws a given amount from the Account corresponding to the provided Integer accountNum
	 */
	public boolean withdraw(Account account, float amount){
		if(amount < 0.00 || amount > account.getBalance()){
			return false;
		}
		if(amount > cashOnHand){
			return false;
		}
		Account myAccount = accounts.get(accountNum);
		float startBalance = myAccount.getBalance();
		//If the account will not be overdrawn
		if(startBalance - amount >= 0.00){
			balance = balance-amount;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Returns the balance of an Account corresponding to the provided Integer accountNum
	 */
	public float getBalance(Integer accountNum) throws ATMException{
		Account myAccount = accounts.get(accountNum);
		return myAccount.getBalance();
	}
}
