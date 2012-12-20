package cscie160.Project;
import java.rmi.*;

/**
 * ATM interface defines three functions, deposit, withdraw, and getBalance
 * @author ryanmitchell
 *
 */
//REMOTE
public interface ATM extends java.rmi.Remote 
{
	//Note: Do I need this?
	public boolean authenticate()
	public boolean deposit(AccountInfo account, float amount) throws ATMException, RemoteException;
	public boolean withdraw(AccountInfo account, float amount) throws ATMException, RemoteException;
	public float getBalance(AccountInfo account) throws ATMException, RemoteException;
	public boolean transfer(AccountInfo fromAccount, AccountInfo toAccount) throws ATMException, RemoteException;
}