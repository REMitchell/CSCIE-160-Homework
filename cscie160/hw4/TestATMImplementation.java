package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TestATMImplementation depends on the successful completion of TestAccount to determine the
 * validity of ATMImplementation. 
 * @author ryanmitchell
 *
 */
public class TestATMImplementation {
	float atmImpBalance;
	float startingBalance;
	float endBalance;


	/**
	 * Tests the balance method of ATMImplementation
	 */
	@Test
	public void testGetBalance() {
		ATMImplementation myATMImplementation = new ATMImplementation();
		try{
			atmImpBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		
		assertEquals("getBalance retrieves the correct balance", 2000.00, atmImpBalance, 0.0);
	}
	/**
	 * Tests the deposit method of ATMImplementation
	 */
	@Test
	public void testDeposit() {
		ATMImplementation myATMImplementation = new ATMImplementation();
		
		float depositAmount = (float) 100.00;
		//Get starting balance
		try{
			startingBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		//Make deposit
		try{
			myATMImplementation.deposit(depositAmount);
		}catch (ATMException e){
			fail("ATMException thrown on deposit");
		}
		//Get final balance
		try{
			endBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		
		assertEquals("Deposit added money to account correctly", endBalance, startingBalance+depositAmount, 0.0);
	}

	/**
	 * Tests the withdraw method of ATMImplementation
	 */
	@Test
	public void testWithdraw() {
		ATMImplementation myATMImplementation = new ATMImplementation();

		float withdrawalAmount = (float) 100.00;
		//Get starting balance
		try{
			startingBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		//Make withdrawal
		try{
			myATMImplementation.withdraw(withdrawalAmount);
		}catch (ATMException e){
			fail("ATMException thrown on deposit");
		}
		//Get final balance
		try{
			endBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		
		assertEquals("Deposit added money to account correctly", endBalance, startingBalance-withdrawalAmount, 0.0);
	
	}



}
