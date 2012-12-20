package cscie160.hw5;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a remote ATM object with three accounts stored in a Map<int, Account>, representing
 * an account number and a corresponding account object
 * @author ryanmitchell
 *
 */

/**

 *
 */
public class ATMImpl extends UnicastRemoteObject implements ATM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private Map<Integer, Account> accounts;

    public ATMImpl() throws RemoteException {
    	super();
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
	public boolean withdraw(Integer accountNum, float amount){
		if(amount < 0.00){
			return false;
		}
		Account myAccount = accounts.get(accountNum);
		float startBalance = myAccount.getBalance();
		//If the account will not be overdrawn
		if(startBalance - amount >= 0.00){
			myAccount.setBalance(startBalance-amount);
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
