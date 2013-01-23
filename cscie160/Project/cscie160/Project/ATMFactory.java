package cscie160.Project;

import java.rmi.RemoteException;

/**
 * Remote interface for ATMFactory
 * @author rmitchell
 *
 */
public interface ATMFactory extends java.rmi.Remote {
	public ATM getATM() throws RemoteException;
}
