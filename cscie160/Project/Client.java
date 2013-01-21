package cscie160.Project;

import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ATMListener{



	/**
	 * @param args
	 */
	public static ATM atm;
	
	public static void main(String[] args){
		System.out.println("Hey there! I'm the main method!");

	}
	
	public Client() throws RemoteException{
		super();
		System.out.println("Hey there! I'm a client!");
	      //atm = null;
	      try {
	         ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
	         atm = factory.getATM();
	    	 System.out.println("Getting the factory");
	    	 if(this == null){
	    		 System.out.println("Why is this null?");
	    	 }else{
	    		 System.out.println("About to register the listener, which is not null");
	    		 System.out.println(this);
	    	 }
	         atm.registerListener(this);
	      } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	      } catch (NotBoundException nbe) {
	         nbe.printStackTrace();
	      }  catch (RemoteException re) {
	         re.printStackTrace();
	      }
		testATM();
	}
	
	/*public void registerClient() throws RemoteException{
		System.out.println("Hey there! I'm a client!");
	      atm = null;
	      try {
	         ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
	         atm = factory.getATM();
	    	 System.out.println("Getting the factory");
	    	 if(this == null){
	    		 System.out.println("Why is this null?");
	    	 }
	         atm.registerListener(this);
	      } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	      } catch (NotBoundException nbe) {
	         nbe.printStackTrace();
	      }  catch (RemoteException re) {
	         re.printStackTrace();
	      }
		testATM(atm);
	}*/
	
	
	public void receiveTransactionNotification(TransactionNotification notification) throws RemoteException {
		System.out.println("Receiving notification!");
		System.out.println(notification);
		// TODO Auto-generated method stub
		/*System.out.println("Prepared to receive notifications!");
		Socket clientConnection = serverSocket.accept();
		
		//Arrange to read the input from the Socket
		InputStream inputStream = clientConnection.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		System.out.println("Client acquired!");
		
		try{
			String transactionNotification;
			while((transactionNotification = bufferedReader.readLine()) != null){
				System.out.println(transactionNotification);
			}
		}catch(SocketException e){
			//Client has stopped sending commands. Exit gracefully
			System.out.println("done!");
		}*/
	}
	
	/*   public static void testATM(ATM atm) {
		   
		      if (atm!=null) {
		         printBalances(atm);
		         performTestOne(atm);
		         performTestTwo(atm);
		         performTestThree(atm);
		         performTestFour(atm);
		         performTestFive(atm);
		         performTestSix(atm);
		         performTestSeven(atm);
		         performTestEight(atm);
		         performTestNine(atm);
		         printBalances(atm);
		      }
		}  */
	
	   public static void testATM() {
		   
		      if (atm !=null) {
		         printBalances();
		        System.out.println("About to perform test One!");
		         performTestOne();
		         System.out.println("About to perform test Two!");
		         printBalances();

		         performTestTwo();
		         System.out.println("About to perform test Three!");
		         printBalances();

		         performTestThree();
		         System.out.println("About to perform test Four!");
		         printBalances();

		         performTestFour();
		         System.out.println("About to perform test Five!");
		         printBalances();

		         performTestFive();
		         printBalances();

		         System.out.println("About to perform test Six!");
		         printBalances();

		         performTestSix();
		         System.out.println("About to perform test Seven!");
		         printBalances();

		         performTestSeven();
		         System.out.println("About to perform test Eight!");
		         printBalances();

		         performTestEight();
		         System.out.println("About to perform test Nine!");
		         System.out.println("About to print balances again!");
		         printBalances();
		         performTestNine();
		         System.out.println("About to print balances again!");
		         printBalances();
		      }
		} 
		   public static void printBalances() {        
		      try {
		    	  System.out.println("About to get balance from ATM");
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
		         atm.withdraw("0000002", 2345, 500);
		      } catch (Exception e) {
		         System.out.println("Failed as expected: "+e);
		      }
		   }
		   public static void performTestThree() {        
		      try {
		         atm.withdraw("0000001", 1234, 50);
		      } catch (Exception e) {
		         System.out.println("Failed as expected: "+e);
		      }
		   }
		   public static void performTestFour() {         
		      try {
		         atm.deposit("0000001", 1234, 500);
		         System.out.println("Balance(0000001): "+atm.getBalance(new AccountInfo("0000001", 1234)));

		      } catch (Exception e) {
		         System.out.println("Unexpected error: "+e);
		      }
		      
		   }
		   public static void performTestFive() {         
		      try {
		         atm.deposit("0000002", 2345, 100);
		      } catch (Exception e) {
		         System.out.println("Unexpected error: "+e);
		      }
		   }
		   public static void performTestSix() {       
		      try {
		         atm.withdraw("0000001", 1234, 100);
		      } catch (Exception e) {
		         System.out.println("Unexpected error: "+e);
		      }
		   }
		   public static void performTestSeven() {        
		      try {
		         atm.withdraw("0000003", 3456, 300);
		      } catch (Exception e) {
		         System.out.println("Unexpected error: "+e);
		      }
		   }
		   public static void performTestEight() {        
		      try {
		         atm.withdraw("0000001", 1234, 200);
		      } catch (Exception e) {
		         System.out.println("Failed as expected: "+e);
		      }
		   }
		   public static void performTestNine() {        
		      try {
		         atm.transfer("0000001", 1234, "0000002", 2345, 100);
		      } catch (Exception e) {
		         System.out.println("Unexpected error: "+e);
		      }
		   }
		   
		   //Helper method, wraps account number and pin into AccountInfo object
		   public static AccountInfo getAccountInfo(String accountNum, Integer pin){
			   AccountInfo myAccountInfo = null;
			   myAccountInfo = new AccountInfo(accountNum, pin);

			   return myAccountInfo;
		   }

}
