package cscie160.Project;

import java.rmi.Naming;

public class ATMServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ATMFactoryImpl atmFactory = new ATMFactoryImpl();
			Naming.rebind("//localhost/atmfactory", atmFactory);
			System.out.println("myATMFactory is bound in the registry!");
		} catch(Exception e){
			System.out.println("Error binding factory in the registry");
			e.printStackTrace();
		}

	}

}
