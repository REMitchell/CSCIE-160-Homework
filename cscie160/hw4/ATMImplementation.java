package cscie160.hw4;


public class ATMImplementation implements ATM {

	/**
	 * @param args
	 */
	private Account myAccount;
	ATMImplementation(){
		myAccount = new Account(new Float(2000.00));
		//System.out.println("Ryan created an account! Account balance is "+myAccount.getBalance());
	}
	public static void main(String[] args) {

	}
	public void deposit(float amount) throws ATMException{
		float tmpBalance = myAccount.getBalance()+amount;
		myAccount.setBalance(tmpBalance);
	}
	public void withdraw(float amount) throws ATMException{
		myAccount.setBalance(myAccount.getBalance() - amount);
	}
	public Float getBalance() throws ATMException{
		return myAccount.getBalance();
	}
}
