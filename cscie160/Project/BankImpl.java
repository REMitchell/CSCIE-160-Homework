package cscie160.Project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BankImpl extends UnicastRemoteObject implements Bank{
	private static final long serialVersionUID = 1L;
	private Map<String, Account> accounts;
	private final Security security;
	public BankImpl() throws RemoteException{
		accounts = new HashMap<String, Account>();
		// TODO Auto-generated constructor stub
		//Define accounts here
		//Create some accounts, store them in a map
		accounts.put("0000001", new Account((float)0.00));
		accounts.put("0000002", new Account((float)100.00));
		accounts.put("0000003", new Account((float)500.00));
		security = new SecurityImpl();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	//method to obtain a reference to a remote Account
	//Literally returns an account object
	//NEEDS TO RETURN REFERENCE, NOT ACTUAL OBJECT!!!!
	public Account getAccount(String accountNum) throws RemoteException {
		System.out.println("In bank.getaccount! Trying to get account "+accountNum);
		Account accountWanted = accounts.get(accountNum);
		System.out.println("Getting the account! The account num "+accountNum+" is: ");
		System.out.println(accountWanted);
		if(accountWanted == null){
			throw new RemoteException("Account "+accountNum+" not found");
		}
		System.out.println("The account was not null! Returning it now!");
		return accountWanted;
	}
	/*private Integer formValidAcctNum(Integer acctNum){
		String acctString = acctNum.toString();
		int leadingZeros = 7 - acctString.length();
		String leadingString = "";
		for(int i = 0; i <= leadingZeros; i++){
			leadingString = leadingString+"0";
		}
		return Integer.parseInt(leadingString+acctString);
	}*/
	//Unfortunately, because this is using RMI and none of these classes are local...
	public void updateAccountBalance(String accountNum, float newBalance) throws RemoteException{
		Account modifiedAcct = accounts.get(accountNum);
		modifiedAcct.setBalance(newBalance);
	}
	
}
