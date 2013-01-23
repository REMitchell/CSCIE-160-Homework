package cscie160.Project;

import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Client is responsible for sending hardcoded requests to the ATM for various accounts.
 * It is required to provide the correct authentication information (account number, pin) and 
 * prints notifications from the ATM
 * @author rmitchell
 */
public class Client extends UnicastRemoteObject implements ATMListener{

	private static final long serialVersionUID = 1L;
	public static ATM atm;
	
	public static void main(String[] args){
		try{
			Client client = new Client();
		}catch(RemoteException e){
			System.out.println("Problem occurred initializing client");
		}
	}
	
	public Client() throws RemoteException{
		super();
		System.out.println("Initializing client");
	      //atm = null;
	      try {
	    	 System.out.println("Getting the ATMFactory");
	         ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
	         atm = factory.getATM();
	         atm.registerListener(this);
	 		testATM();
	 		atm.removeListener(this);
	      } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	      } catch (NotBoundException nbe) {
	         nbe.printStackTrace();
	      }  catch (RemoteException re) {
	         re.printStackTrace();
	      }
	}
	
	/**
	 * Receives a TransactionNotification from the client and prints it
	 */
	public void receiveTransactionNotification(TransactionNotification notification) throws RemoteException {
		System.out.println(notification);
	}
	

	/**
	 * Calls all ATM tests in order. This is the main testing method for the Client, and is called after all
	 * servers are running and connected to each other appropriately
	 */
	   public static void testATM() {
		      if (atm !=null) {
		         printBalances();
		         performTestOne();
		         performTestTwo();
		         performTestThree();
		         performTestFour();
		         performTestFive();
		         performTestSix();
		         performTestSeven();
		         performTestEight();
		         performTestNine();
		         printBalances();
		      }
		}
	   
	   /**
	    * Prints the balance for all accounts
	    */
	   public static void printBalances() {        
	      try {
	         System.out.println("Balance(0000001): "+atm.getBalance(new AccountInfo("0000001", 1234)));
	         System.out.println("Balance(0000002): "+atm.getBalance(new AccountInfo("0000002", 2345)));
	         System.out.println("Balance(0000003): "+atm.getBalance(new AccountInfo("0000003", 3456)));
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	   public static void performTestOne() { 
		  System.out.println("Test One!");
	      try {
	         atm.getBalance(new AccountInfo("0000001", 5555));
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestTwo() {       
	      try {
	         atm.withdraw(new AccountInfo("0000002", 2345), 500);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestThree() {        
	      try {
	         atm.withdraw(new AccountInfo("0000001", 1234), 50);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestFour() {         
	      try {
	         atm.deposit(new AccountInfo("0000001", 1234), 500);
	         System.out.println("Balance(0000001): "+atm.getBalance(new AccountInfo("0000001", 1234)));

	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	      
	   }
	   public static void performTestFive() {         
	      try {
	         atm.deposit(new AccountInfo("0000002", 2345), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestSix() {       
	      try {
	         atm.withdraw(new AccountInfo("0000001", 1234), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestSeven() {        
	      try {
	         atm.withdraw(new AccountInfo("0000003", 3456), 300);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
	   public static void performTestEight() {        
	      try {
	         atm.withdraw(new AccountInfo("0000001", 1234), 200);
	      } catch (Exception e) {
	         System.out.println("Failed as expected: "+e);
	      }
	   }
	   public static void performTestNine() {        
	      try {
	         atm.transfer(new AccountInfo("0000001", 1234), new AccountInfo("0000002", 2345), 100);
	      } catch (Exception e) {
	         System.out.println("Unexpected error: "+e);
	      }
	   }
}
