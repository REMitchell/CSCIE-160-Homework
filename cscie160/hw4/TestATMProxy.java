package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The ability of TestATMProxy to test the validity of ATMProxy depends on the successful completion
 * of TestAccount and TestATMImplementation. It should be run third. 
 * @author ryanmitchell
 *
 */
public class TestATMProxy {
	static float balanceBalance;
	static float withdrawalStartBalance;
	static float withdrawalEndBalance;
	static float depositStartBalance;
	static float depositEndBalance;
	static float withdrawalAmount;
	static float depositAmount;
	ATMProxy myATMProxy;

	
/**
 * Creates a new ATMProxy on localhost, port 7777. Client should be started by hand, and should
 * also be on port 7777.
 * depositAmount and withdrawalAmount are arbitrary, but withdrawal amount should be less than the
 * current bank balance (although this is not currently checked -- balances may be negative)
 * @throws ATMException
 * @throws java.io.IOException
 * @throws java.lang.NullPointerException
 */
	public TestATMProxy() throws ATMException, java.io.IOException, java.lang.NullPointerException{
		myATMProxy = new ATMProxy("localhost", 7777);
		depositAmount = (float)100.00;
		withdrawalAmount = (float)100.00;
	}
	/**
	 * Performs all calculations for tests. If calculations are done and sent to the same 
	 * client from different methods, the client hangs, so the calculations must be done
	 * all at once, in the same method
	 */
	@Test
	public void startUp(){
		try{
			//Balance function
			balanceBalance = myATMProxy.getBalance();
			
			//deposit functions
			depositStartBalance = myATMProxy.getBalance();
			myATMProxy.deposit(depositAmount);
			depositEndBalance = myATMProxy.getBalance();
			
			//withdraw functions
			withdrawalStartBalance = myATMProxy.getBalance();
			myATMProxy.withdraw(withdrawalAmount);
			withdrawalEndBalance = myATMProxy.getBalance();
			
		}catch (ATMException e){
            e.printStackTrace();
			fail("ATMException while passing commands to ATMProxy");
		}
		
	}

	/**
	 * Tests the getBalance method in ATMProxy
	 */
	@Test
	public void testGetBalance(){
		assertEquals("Balance test",  (float) 2000.00, balanceBalance, (float) 0.00);
	}
	/**
	 * Tests the deposit method in ATMProxy
	 */
	@Test
	public void testDeposit(){
		//assertEquals("Testing test :) ", 1, 1, 0);
		assertEquals("Deposit test", depositEndBalance, depositStartBalance+depositAmount, (float)0.00);
	}

	/**
	 * Tests the withdraw method in ATMProxy
	 */
	@Test
	public void testWithdraw(){
		assertEquals("Withdraw Test", withdrawalEndBalance, depositEndBalance-withdrawalAmount, (float) 0.00);
	}
}
