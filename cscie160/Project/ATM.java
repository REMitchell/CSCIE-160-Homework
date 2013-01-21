package cscie160.Project;
import java.rmi.*;

/**
 * ATM interface defines three functions, deposit, withdraw, and getBalance
 * @author ryanmitchell
 *
 */
//REMOTE
public interface ATM extends java.rmi.Remote {

	public void registerListener(ATMListener listener) throws RemoteException;
	public void removeListener(ATMListener listener) throws RemoteException;
	public void notifyListeners(TransactionNotification notification) throws RemoteException;
	public boolean deposit(String accountNum, Integer accountPin, float amount) throws ATMException, RemoteException;
	public boolean withdraw(String accountNum, Integer accountPin, float amount) throws ATMException, RemoteException;
	public float getBalance(AccountInfo account) throws AuthorizationException, RemoteException;
	public boolean transfer(String fromAccountNum, Integer fromAccountPin, String toAccountNum, Integer toAccountPin, float amount) throws ATMException, RemoteException;
}