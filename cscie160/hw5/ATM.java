package cscie160.hw5;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

//Modify the ATM interface to be an RMI remote interface
//Add the int account parameter to each of the method signatures
public interface ATM extends java.rmi.Remote 
{
	public void deposit(Integer accountNum, float amount) throws ATMException, RemoteException;
	public void withdraw(Integer accountNum, float amount) throws ATMException, RemoteException;
	public float getBalance(Integer accountNum) throws ATMException, RemoteException;
}