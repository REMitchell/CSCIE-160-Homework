package cscie160.hw5;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ATMFactoryImpl  extends UnicastRemoteObject implements ATMFactory{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ATMFactoryImpl() throws RemoteException {

		// TODO Auto-generated constructor stub
	}

	@Override
	/*
	 * Create the RMI ready implementation of the factory interface. 
	 * It should return a remote reference to an ATM instance.(non-Javadoc)
	 * @see cscie160.hw5.ATMFactory#getATM()
	 */
	public ATM getATM() throws RemoteException {
		// TODO Auto-generated method stub
		return new ATMImpl();

	}

}
