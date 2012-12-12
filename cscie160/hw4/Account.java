package cscie160.hw4;

/**
 * Account represents a bank account, and is essentially a wrapper around a float representing the
 * account balance. 
 * Has two methods to get and set account balance
 * @param args
 */
public class Account {


	private float balance;

	Account(float startingBalance){
		balance = startingBalance;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Accessor method for bank account balance
	 * @return balance, bank account balance
	 */
	public float getBalance(){
		return balance;
	}
	
	/**
	 * Setter method for bank account balance
	 * @param amount The amount the balance should be set to
	 */
	public void setBalance(float amount){
		balance = amount;
	}

}
