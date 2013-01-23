package cscie160.Project;

import java.rmi.RemoteException;

/**
 * Security is a remote object that has methods for authenticating AccountInfo objects and for authorizing 
 * specific operations on individual Accounts.
 * @author ryanmitchell
 */
public interface Security extends java.rmi.Remote {
	public boolean authenticatePin(AccountInfo account) throws AuthorizationException, RemoteException;
	public boolean authorizeDeposit(String accountNum) throws AuthorizationException,RemoteException ;
	public boolean authorizeWithdraw(String accountNum) throws AuthorizationException, RemoteException;
	public boolean authorizeBalance(String accountNum) throws AuthorizationException, RemoteException;
	public boolean authorizeTransfer(String fromAccountNum, String toAccountNum) throws AuthorizationException, RemoteException;

}
