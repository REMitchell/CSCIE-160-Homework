package cscie160.Project;
import java.rmi.*;

/**
 * Interface for ATM
 * @author ryanmitchell
 *
 */
public interface ATM extends java.rmi.Remote {
	public void registerListener(ATMListener listener) throws RemoteException;
	public void removeListener(ATMListener listener) throws RemoteException;
	public void notifyListeners(TransactionNotification notification) throws RemoteException;
	public boolean deposit(AccountInfo account, float amount) throws ATMException, RemoteException;
	public boolean withdraw(AccountInfo account, float amount) throws ATMException, RemoteException;
	public float getBalance(AccountInfo account) throws AuthorizationException, RemoteException;
	public boolean transfer(AccountInfo fromAccount, AccountInfo toAccount, float amount) throws ATMException, RemoteException;
}