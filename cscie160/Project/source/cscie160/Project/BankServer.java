package cscie160.Project;

import java.rmi.Naming;

/**
 * Creates both a BankServer and Security in the RMI, binds them, and prints a line
 * announcing that they are bound successfully
 * @author rmitchell
 */
public class BankServer {

	private static BankImpl bank;
	private static SecurityImpl security;
	
	public static void main(String[] args) {
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
