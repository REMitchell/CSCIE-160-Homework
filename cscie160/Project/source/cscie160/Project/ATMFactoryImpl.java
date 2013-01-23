package cscie160.Project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implements ATMFactory, has a single method that returns a remote reference
 * to an ATM when called
 * @author rmitchell
 */
public class ATMFactoryImpl  extends UnicastRemoteObject implements ATMFactory{

	protected ATMFactoryImpl() throws RemoteException {

	}

	/**
	 * Returns a remote reference to an ATM
	 */
	public ATM getATM() throws RemoteException {
		// TODO Auto-generated method stub
		return new ATMImpl();
	}

}
