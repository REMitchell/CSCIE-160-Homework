package cscie160.hw4;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAccount {

	
	@Test
	public void testGetBalance() {
		float accountBalance = (float) 20.00;
		Account testAccount = new Account(accountBalance);
		assertEquals("GetBalance works", testAccount.getBalance(), accountBalance, 0.00);
	}
	
	

	@Test
	public void testSetBalance() {
		float accountBalance = (float) 20.00;
		Account testAccount = new Account(accountBalance);
		float newBalance = (float) 20.00;
		testAccount.setBalance(newBalance);
		assertEquals("SetBalance works", newBalance, testAccount.getBalance(), 0.00);
	}

}
