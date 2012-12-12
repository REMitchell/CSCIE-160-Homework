package cscie160.hw5;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

/**
 * TestATMImpl is analogous to TestATMImplementation in HW4. It tests the three main methods,
 * withdraw, deposit, and getBalance, in ATM. It depends on a valid account with account number
 * 0000002, and an initial deposit of 100.00. Will catch a null pointer exception if account does
 * not exist, and will fail the asserEquals in all three methods if the balance is incorrect
 * @author ryanmitchell
 *
 */
public class TestATMImpl {
	float atmImpBalance;
	float startingBalance;
	float endBalance;
	Integer accountNum;
	ATMImpl myATMImpl;

	/**
	 * Tests the balance method of ATMImpl
	 */
	@Test
	public void testGetBalance() {
		accountNum = 0000002;
		try{
			myATMImpl = new ATMImpl();
		}catch(RemoteException e){
			fail("Remote Exception creating ATMImpl");
		}

		try{
			atmImpBalance = myATMImpl.getBalance(accountNum);
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}catch(NullPointerException d){
			fail("NullPointerException, it is likely that account number does not exist");
		}
		
		assertEquals("getBalance retrieves the correct balance", 100.00, atmImpBalance, 0.0);
	}
	/**
	 * Tests the deposit method of ATMImpl
	 */
	@Test
	public void testDeposit() {
		accountNum = 0000002;
		
		try{
			myATMImpl = new ATMImpl();
		}catch(RemoteException e){
			fail("Remote Exception creating ATMImpl");
		}
		
		float depositAmount = (float) 100.00;
		//Get starting balance
		try{
			startingBalance = myATMImpl.getBalance(accountNum);
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}catch(NullPointerException d){
			fail("NullPointerException, it is likely that account number does not exist");
		}
		
		//Make deposit
		myATMImpl.deposit(accountNum, depositAmount);

		//Get final balance
		try{
			endBalance = myATMImpl.getBalance(accountNum);
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		
		assertEquals("Deposit added money to account correctly", endBalance, startingBalance+depositAmount, 200.0);
	}

	/**
	 * Tests the withdraw method of ATMImpl
	 */
	@Test
	public void testWithdraw() {
		accountNum = 0000002;
		try{
			myATMImpl = new ATMImpl();
		}catch(RemoteException e){
			fail("Remote Exception creating ATMImpl");
		}

		float withdrawalAmount = (float) 50.00;
		//Get starting balance
		try{
			startingBalance = myATMImpl.getBalance(accountNum);
			System.out.println("Starting balance is: " + startingBalance);
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}catch(NullPointerException d){
			fail("NullPointerException, it is likely that account number does not exist");
		}
		
		myATMImpl.withdraw(accountNum, withdrawalAmount);
		//Get final balance
		try{
			endBalance = myATMImpl.getBalance(accountNum);
		}catch (ATMException e){
			fail("ATMException thrown while getting balance");
		}
		assertEquals("Withdrawal removed money from account correctly", endBalance, startingBalance-withdrawalAmount, 50.0);
	}

}
