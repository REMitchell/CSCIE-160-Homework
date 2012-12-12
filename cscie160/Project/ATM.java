package cscie160.Project;
import java.rmi.*;

//Modify the ATM interface to be an RMI remote interface
//Add the int account parameter to each of the method signatures
/**
 * ATM interface defines three functions, deposit, withdraw, and getBalance
 * @author ryanmitchell
 *
 */
public interface ATM extends java.rmi.Remote 
{
	public boolean deposit(Integer accountNum, float amount) throws ATMException, RemoteException;
	public boolean withdraw(Integer accountNum, float amount) throws ATMException, RemoteException;
	public float getBalance(Integer accountNum) throws ATMException, RemoteException;
}