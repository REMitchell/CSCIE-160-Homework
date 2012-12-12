package cscie160.hw5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implements ATMFactory, has a single method that returns a remote reference
 * to an ATM when called
 */
public class ATMFactoryImpl  extends UnicastRemoteObject implements ATMFactory{

	private static final long serialVersionUID = 1L;

	protected ATMFactoryImpl() throws RemoteException {

		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns a remote reference to an ATM
	 */
	public ATM getATM() throws RemoteException {
		// TODO Auto-generated method stub
		return new ATMImpl();
	}

}
