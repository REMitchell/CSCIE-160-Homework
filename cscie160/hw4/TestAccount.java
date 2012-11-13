package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TestAccount tests the getBalance and setBalance method of Account
 * It does not require any tests to be run before running
 * @author ryanmitchell
 *
 */
public class TestAccount {

	/**
	 * Creates an account with an arbitrary account balance. Checks that
	 * the correct balance is retrieved by getBalance
	 */
	@Test
	public void testGetBalance() {
		float accountBalance = (float) 20.00;
		Account testAccount = new Account(accountBalance);
		assertEquals("GetBalance works", testAccount.getBalance(), accountBalance, 0.00);
	}
	
	
	/**
	 * Creates an account
	 */
	@Test
	public void testSetBalance() {
		float accountBalance = (float) 20.00;
		Account testAccount = new Account(accountBalance);
		float newBalance = (float) 200.00;
		testAccount.setBalance(newBalance);
		assertEquals("SetBalance works", newBalance, testAccount.getBalance(), 0.00);
	}

}
