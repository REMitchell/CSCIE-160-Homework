package cscie160.Project;
/**
 * The Account is a remote object that provides methods for
 *  adding and subtracting funds from the Account. 
 *  It should verify the appropriate funds are available in the Account.
 * @param args
 */
//REMOTE
public class Account {

	private float balance;
	//private int accountNum;

	Account(float startingBalance){
		balance = startingBalance;
	}
	
	/**
	 * Accessor method for bank account balance
	 * @return balance, bank account balance
	 */
	public float getBalance(){
		return balance;
	}

	public boolean deposit(float amount){
		balance = balance + amount;
		return true;
	}
	
	/**
	 * Setter method for bank account balance
	 * @param amount The amount the balance should be set to
	 */
	public boolean withdraw(float amount){
		if(balance - amount >= (float) 0.00){
			balance = balance - amount;
			return true;
		}else{
			return false;
		}
	}
}
