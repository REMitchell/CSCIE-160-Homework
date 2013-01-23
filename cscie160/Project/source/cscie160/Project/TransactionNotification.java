package cscie160.Project;

import java.io.Serializable;
import java.rmi.RemoteException;
import cscie160.Project.Commands;

/**
 * TransactionNotification is used to send messages from the ATM to the client, letting the client know that
 * the ATM is processing the Client's request. 
 * @author rmitchell
 */
public class TransactionNotification implements Serializable{

	private static final long serialVersionUID = 1L;
	private Commands command;
	private float amount;
	private AccountInfo account;
	private AccountInfo toAccount;
	
	/**
	 * Constructor for notifications about transfer operations. Contains a "from account" and a "to account"
	 */
	TransactionNotification(Commands myCommand, float myAmount, AccountInfo myFromAccount, AccountInfo myToAccount) throws RemoteException{
		//Must be a transfer
		command = myCommand;
		amount = myAmount;
		account = myFromAccount;
		toAccount = myToAccount;
		if(command != Commands.TRANSFER){
			throw new RemoteException("Invalid command was sent to ATM. Two accounts given, expecting transfer");
		}
	}
	
	/**
	 * Constructor for notifications about withdraw and deposit operations. Contains one account and an amount
	 */
	TransactionNotification(Commands myCommand, float myAmount, AccountInfo myAccount) throws RemoteException{
		command = myCommand;
		account = myAccount;
		amount = myAmount;
		toAccount = null;
		if(command != Commands.DEPOSIT && command != Commands.WITHDRAW ){
			throw new RemoteException("Invalid command was sent to ATM. One account and amount given, expecing Deposit or Withdraw");
		}
	}
	/**
	 * Constructor for notifications about balance operations.
	 */
	TransactionNotification(Commands myCommand, AccountInfo myAccount) throws RemoteException{
		command = myCommand;
		account = myAccount;
		if(command != Commands.BALANCE){
			throw new RemoteException("Invalid command was sent to ATM. No amount given, expecing Balance");
		}
	}
	
	/**
	 * Overrides toString. Returns a string representing the TransactionNotification depending on the command. 
	 * Returns an error message if the command is unrecognized
	 */
	@Override public String toString() {
		if(command == Commands.TRANSFER){
			return "<TransactionNotification Message>\n TRANSFER of "+String.valueOf(amount)+" from "+account.getAccountNum()+" to "+toAccount.getAccountNum()+"\n";
		}
		if(command == Commands.BALANCE){
			return "<TransactionNotification Message>\n BALANCE of "+String.valueOf(account.getAccountNum())+"\n";
		}
		if(command == Commands.DEPOSIT){
			return "<TransactionNotification Message>\n DEPOSIT "+String.valueOf(amount)+" into account "+account.getAccountNum()+"\n";
		}
		if(command == Commands.WITHDRAW){
			return "<TransactionNotification Message>\n WITHDRAW "+String.valueOf(amount)+" from account "+account.getAccountNum()+"\n";
		}
		//Theoretically, should not be reachable
		return "<TransactionNotification Message>\n Error: Invalid command was sent to ATM\n";
	}

}
