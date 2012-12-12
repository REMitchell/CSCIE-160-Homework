package cscie160.Project;
/**
 * Defines the ATMFactory, whose job it is to return a remote reference to an ATM
 * @author ryanmitchell
 *
 */
public interface ATMFactory extends java.rmi.Remote {
	public ATM getATM() throws java.rmi.RemoteException;
}
