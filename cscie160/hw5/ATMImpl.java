package cscie160.hw5;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

//Create an RMI remote implementation ATMImpl that 
//implements the remote ATM interface. You will need 
//to leverage java.rmi.server.UnicastRemoteObject or the 
//activation framework to make your implementation remotely 
//accessible.
public class ATMImpl extends UnicastRemoteObject implements ATM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	Map<Integer, Account> accounts;

    public ATMImpl() throws RemoteException {
    	super();
    	//Don't think I actually need to register this -- ATMFactory returns an ATMImpl for me
    	//And ATMFactory is the one that actually registers itself with the RMI
    	accounts = new HashMap<Integer, Account>();
		//Create some accounts, store them in a collection
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
	
	public void deposit(Integer accountNum, float amount) throws ATMException{
		Account myAccount = accounts.get(accountNum);
		float startBalance = myAccount.getBalance();
		myAccount.setBalance(startBalance+amount);
	}
	
	public void withdraw(Integer accountNum, float amount) throws ATMException{
		Account myAccount = accounts.get(accountNum);
		float startBalance = myAccount.getBalance();
		myAccount.setBalance(startBalance-amount);
	}
	
	public float getBalance(Integer accountNum) throws ATMException{
		Account myAccount = accounts.get(accountNum);
		return myAccount.getBalance();
	}


}
