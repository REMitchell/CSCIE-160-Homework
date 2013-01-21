package cscie160.Project;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import cscie160.Project.Commands;

public class TransactionNotification implements Serializable{

	/**
	 * @param args
	 */
	private Commands command;
	private float amount;
	private AccountInfo account;
	private AccountInfo toAccount;
	TransactionNotification(Commands myCommand, float myAmount, AccountInfo myFromAccount, AccountInfo myToAccount){
		//Must be a transfer
		command = myCommand;
		amount = myAmount;
		account = myFromAccount;
		toAccount = myToAccount;	
	}
	
	TransactionNotification(Commands myCommand, float myAmount, AccountInfo myAccount){
		command = myCommand;
		account = myAccount;
		amount = myAmount;
		toAccount = null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override public String toString() {
		if(command == Commands.TRANSFER){
			return "<TransactionNotification Message>\n TRANSFER of "+String.valueOf(amount)+" from "+account.getAccountNum()+" to "+toAccount.getAccountNum();
		}
		if(command == Commands.BALANCE){
			return "<TransactionNotification Message>\n BALANCE of "+String.valueOf(account.getAccountNum());
		}
		if(command == Commands.DEPOSIT){
			return "<TransactionNotification Message>\n DEPOSIT "+String.valueOf(amount)+"into account "+account.getAccountNum();
		}
		if(command == Commands.WITHDRAW){
			return "<TransactionNotification Message>\n WITHDRAW "+String.valueOf(amount)+"from account "+account.getAccountNum();
		}
		return "<TransactionNotification Message>\n Error: Invalid command was sent to ATM";
	}

}
