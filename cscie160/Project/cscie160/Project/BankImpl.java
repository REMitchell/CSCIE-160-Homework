package cscie160.Project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * implements Bank
 * The bank creates and stores accounts, mapped to their account number.
 * Has getter and setter methods for accounts, which are used by the ATM after verifying that the client
 * has access to those accounts.
 * @author rmitchell
 *
 */
public class BankImpl extends UnicastRemoteObject implements Bank{
	private static final long serialVersionUID = 1L;
	private Map<String, Account> accounts;
	
	/**
	 * Creates three new accounts with starting balances, stores them in a HashMap with account numbers
	 * as keys
	 */
	public BankImpl() throws RemoteException{
		accounts = new HashMap<String, Account>();
		//Create some accounts, store them in a map
		accounts.put("0000001", new Account((float)0.00));
		accounts.put("0000002", new Account((float)100.00));
		accounts.put("0000003", new Account((float)500.00));
	}

	
	/**
	 * Accessor method for accounts -- returns Account object based on the key account number. 
	 * Is used by ATM after verifying that the client has access to the account. 
	 */
	public Account getAccount(String accountNum) throws RemoteException {
		Account accountWanted = accounts.get(accountNum);
		if(accountWanted == null){
			throw new RemoteException("Account "+accountNum+" not found");
		}
		return accountWanted;
	}

	
	/**
	 * Because the Account is a remote object to the ATM, it cannot update the balance without explicitly sending
	 * it back to the Bank across the RMI. This method updates an account balance based on an account number and balance
	 */
	public void updateAccountBalance(String accountNum, float newBalance) throws RemoteException{
		Account modifiedAcct = accounts.get(accountNum);
		modifiedAcct.setBalance(newBalance);
	}
	
}
