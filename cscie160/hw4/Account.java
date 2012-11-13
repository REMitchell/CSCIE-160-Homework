package cscie160.hw4;

public class Account {

	/**
	 * Account represents a bank account. Has two methods to get and set account balance
	 * @param args
	 */
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
	//Classic beginner mistake (from teaching session)
	/*public void deposit(float amount){
		try{
			if(amount > 0){
				balance += amount;
			}
		}else{
			//Do a thing
		}
	}*/

}
