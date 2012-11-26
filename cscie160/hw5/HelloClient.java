package cscie160.hw5;

import java.rmi.*;

public class HelloClient {
    public static void main(String args[]){
		try {
		    Hello obj = (Hello)Naming.lookup("//localhost/HelloServer");
		    System.out.println("Server returned : " + obj.sayHello());
		    System.out.println("Type of HelloServer: " + obj.getClass().getName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
}
