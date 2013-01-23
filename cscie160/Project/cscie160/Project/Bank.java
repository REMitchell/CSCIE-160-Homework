package cscie160.Project;

import java.rmi.RemoteException;
import cscie160.Project.Account;
//REMOTE

/**
 * The bank creates and stores accounts, mapped to their account number.
 * Has getter and setter methods for accounts, which are used by the ATM after verifying that the client
 * has access to those accounts.
 * @author ryanmitchell
 *
 */
public interface Bank extends java.rmi.Remote {
	/**
	 * 
	 */
	public Account getAccount(String accountNum) throws RemoteException;
	public void updateAccountBalance(String accountNum, float newBalance) throws RemoteException;
}
