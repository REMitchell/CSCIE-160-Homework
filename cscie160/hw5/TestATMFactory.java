package cscie160.hw5;

import static org.junit.Assert.*;
import java.rmi.RemoteException;

import org.junit.Test;

/**
 * Tests the ATMFactoryImpl class
 * @author ryanmitchell
 *
 */

public class TestATMFactory {
	private ATMFactoryImpl myFactory;
	private static ATM myATM;
	
	/**
	 * Tests that ATMFactoryImpl returns an object and does not throw a RemoteException
	 * when creating the ATMFactory or when getting a ATM
	 */
	@Test
	public void testGetATM() {
		try{
			myFactory = new ATMFactoryImpl();
		}catch(RemoteException e){
			fail("Remote Exception when creating Factory");
		}
		
		try{
			myATM = myFactory.getATM();
			assertNotNull(myATM);
		}catch(RemoteException e){
			fail("Remote Exception when getting ATM from Factory");
		}
		assertNotNull(myATM);
	}
}
