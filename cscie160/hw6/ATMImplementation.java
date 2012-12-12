package cscie160.hw6;

/**
 * ATMImplementation is called by ATMRunnable to modify the account balance
 * @author ryanmitchell
 *
 */

public class ATMImplementation implements ATM {

	/**
	 * @param args
	 */
	private Account myAccount;
	ATMImplementation(){
		myAccount = new Account(new Float(2000.00));
	}
	

	/**
	 * Deposit adds the given float amount to account
	 */
	public void deposit(float amount) throws ATMException{
		myAccount.deposit(amount);
	}
	
	/**
	 * Withdraw subtracts the given float amount from the account,
	 * provided that there are sufficient funds in the account
	 */
	public void withdraw(float amount) throws ATMException{
		try{
			myAccount.withdraw(amount);
		}catch(ATMException e){
			throw new ATMException("Insufficient funds in account");
		}
	}
	/**
	 * getBalance returns the account balance
	 */
	public float getBalance() throws ATMException{
		return myAccount.getBalance();
	}
}
