package cscie160.hw5;
//"ATMImpl is an instance of ATM. Factory returns an Instance"
public interface ATMFactory extends java.rmi.Remote {
	public ATM getATM() throws java.rmi.RemoteException;
	//The only job of the ATMFactory is to return an account object
}
