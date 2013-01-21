package cscie160.Project;

import java.rmi.Naming;


public class BankServer {

	//Creates a bank instance and a security instance
	//Starts both up and registers both in the registry to make
	//both accessible by remote lookup
	/**
	 * @param args
	 */

	private static BankImpl bank;
	private static SecurityImpl security;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			bank = new BankImpl();
			Naming.bind("//localhost/bank", bank);
		}catch(Exception e){
			System.out.println("Error binding the bank");
			e.printStackTrace();
		}
		try{
			security = new SecurityImpl();
			Naming.bind("//localhost/security", security);
		}catch(Exception e){
			System.out.println("Error binding the security handler");
			e.printStackTrace();
		}
		System.out.println("Both bank and security are bound in registry");	
	}
}
