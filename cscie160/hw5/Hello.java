package cscie160.hw5;


//Similar to ATMFactory
public interface Hello extends java.rmi.Remote {
	String sayHello() throws java.rmi.RemoteException;
}
