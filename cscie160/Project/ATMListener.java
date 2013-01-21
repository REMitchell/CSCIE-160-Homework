package cscie160.Project;

import java.rmi.Remote;
import java.rmi.RemoteException;
//ATMListener is the same as client
public interface ATMListener extends Remote{
	//notification handler to receive messages
	public void receiveTransactionNotification(TransactionNotification notification) throws java.io.IOException, RemoteException;
	//public void registerClient() throws RemoteException;
}
