package cscie160.hw6;

/**
 * Account represents a bank account, and is essentially a wrapper around the account balance (float balance)
 * and contains methods deposit, withdraw, getBalance, and setBalance for interacting with that balance
 * @param args
 */
public class Account {


	private static float balance;

	Account(float startingBalance){
		balance = startingBalance;
	}
	public synchronized static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * Is called by ATMImplementation. Takes a float amount to be deposited into the account.
	 * @param amount
	 */
	public void deposit(float amount){
		balance = balance + amount;
	}
	
	/**
	 * Is called by ATMImplementation. Takes a float amount to be withdrawn from the account, and tests to make
	 * sure the account has sufficient funds. This method is locked to prevent threads from interfering with
	 * each other while testing for sufficient funds and then withdrawing from the account.
	 * @param amount
	 * @throws ATMException
	 */
	public void withdraw(float amount) throws ATMException{
		synchronized(this){
			if(balance >= amount){		
				balance = balance - amount;
			}else{
				throw new ATMException("Insufficient funds in account");
			}
		}
	}
	
	/**
	 * Accessor method for bank account balance
	 * @return balance, bank account balance
	 */
	public float getBalance(){
		return balance;
	}
	
	/**
	 * Setter method for bank account balance (deprecated)
	 * @param amount The amount the balance should be set to
	 */
	public void setBalance(float amount){
		balance = amount;
	}
}
