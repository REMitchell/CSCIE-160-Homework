package cscie160.Project;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * AccountInfo is an object designed to pass Account authorization information (account number, pin)
 * remotely between the Client and the ATM, and then between the ATM and Security
 * @author rmitchell
 */
public class AccountInfo implements Serializable{

	//accountNum and pin are private to prevent unauthorized modification and access
	//(although there are accessor methods, they are easier to add security to)
	private final String accountNum;
	private final Integer pin;
	public AccountInfo(String myAccountNum, Integer myPin){
		accountNum = myAccountNum;
		pin = myPin;
	}

	/**
	 * Accessor method for the account PIN
	 */
	public Integer getPin(){
		return pin;
	}
	
	/**
	 * Accessor method for the Account number
	 */
	public String getAccountNum(){
		return accountNum;
	}
}
