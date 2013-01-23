package cscie160.Project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ATMListener represents 
 * @author rmitchell
 *
 */
public interface ATMListener extends Remote{
	public void receiveTransactionNotification(TransactionNotification notification) throws java.io.IOException, RemoteException;
}
