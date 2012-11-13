package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.net.*;
import java.io.*;

public class TestATMProxy {
	public static float balanceBalance;
	public static float withdrawalStartBalance;
	public static float withdrawalEndBalance;
	public static float depositStartBalance;
	public static float depositEndBalance;
	public static float withdrawalAmount;
	public static float depositAmount;
	public ATMProxy myATMProxy;

	//Start the server by hand, run the test against that server

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
	 * This method tests the 
	 */
	@Test
	public void testGetBalance(){
		assertEquals("Balance test",  (float) 2000.00, balanceBalance, (float) 0.00);
	}
	
	@Test
	public void testDeposit(){
		//assertEquals("Testing test :) ", 1, 1, 0);
		assertEquals("Deposit test", depositEndBalance, depositStartBalance+depositAmount, (float)0.00);
	}

	@Test
	public void testWithdraw(){
		assertEquals("Withdraw Test", withdrawalEndBalance, depositEndBalance-withdrawalAmount, (float) 0.00);
	}
}
