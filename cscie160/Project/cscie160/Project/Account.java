package cscie160.Project;

import java.io.Serializable;

/**
 * The Account is a remote object that provides methods for safely adding and subtracting funds from the Account. 
 * @param args
 */
public class Account implements Serializable{

	private float balance;

	public Account(float startingBalance){
		balance = startingBalance;
	}

	/**
	 * Accessor method for account balance
	 */
	public float getBalance(){
		return balance;
	}
	
	/**
	 * Used by the bank to set the account balance after withdrawals, deposits, and transfers
	 */
	public void setBalance(float newBalance){
		balance = newBalance;
	}

	/**
	 * toString method for Account, prints the account balance
	 */
	@Override public String toString() {
		return "Account Balance: "+String.valueOf(balance);
	}
}
