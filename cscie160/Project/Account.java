package cscie160.Project;

import java.io.Serializable;

/**
 * The Account is a remote object that provides methods for
 *  adding and subtracting funds from the Account. 
 *  It should verify the appropriate funds are available in the Account.
 * @param args
 */

public class Account implements Serializable{

	private float balance;
	//private int accountNum;

	public Account(float startingBalance){
		System.out.println("Hey there! In ur account!");
		balance = startingBalance;
	}

	/**
	 * Accessor method for bank account balance
	 * @return balance, bank account balance
	 */
	public float getBalance(){
		return balance;
	}
	
	public void setBalance(float newBalance){
		balance = newBalance;
	}
/*
	public boolean deposit(float amount) throws ATMException{
		if(amount <= (float) 0.00){
			throw new ATMException("Deposit amount cannot be negative or zero");
		}
		balance = balance + amount;
		System.out.println("Inside account object. Balance is now "+balance);
		return true;
	}
	
	/**
	 * Setter method for bank account balance
	 * @param amount The amount the balance should be set to
	 */
	/*
	public boolean withdraw(float amount) throws ATMException{
		if(amount <= (float)0.00){
			throw new ATMException("Withdrawal amount cannot be negative or zero");
		}
		if(balance - amount >= (float) 0.00){
			balance = balance - amount;
			return true;
		}else{
			throw new ATMException("Insufficient funds in account");
		}
	}
	*/
	@Override public String toString() {
		return "Info: "+String.valueOf(balance);
	}
}
