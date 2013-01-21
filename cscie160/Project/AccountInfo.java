package cscie160.Project;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//import java.io.Serializable;

public class AccountInfo implements Serializable{

	private final String accountNum;
	private final Integer pin;
	public AccountInfo(String myAccountNum, Integer myPin){
		// TODO Auto-generated constructor stub
		accountNum = myAccountNum;
		pin = myPin;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public Integer getPin(){
		return pin;
	}
	public String getAccountNum(){
		return accountNum;
	}

}
