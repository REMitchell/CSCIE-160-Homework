package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestATMImplementation {
	float atmImpBalance;
	float startingBalance;
	float endBalance;
	@Test
	public void testGetBalance() {
		ATMImplementation myATMImplementation = new ATMImplementation();
		try{
			atmImpBalance = myATMImplementation.getBalance();
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		
		assertEquals("getBalance retrieves the correct balance", atmImpBalance, 2000.00, 0.0);
	}
	
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
		
		assertEquals("Deposit added money to account correctly", startingBalance+depositAmount, endBalance, 0.0);
	}

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
		
		assertEquals("Deposit added money to account correctly", startingBalance-withdrawalAmount, endBalance, 0.0);
	
	}



}
