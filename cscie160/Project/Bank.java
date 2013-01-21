package cscie160.Project;

import java.rmi.RemoteException;
import cscie160.Project.Account;
//REMOTE

/**
 * The Bank is a remote object that hosts remote Account objects. 
 * It should provide a method to obtain a reference to a remote Account.
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
