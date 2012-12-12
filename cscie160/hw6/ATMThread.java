package cscie160.hw6;
import java.util.ArrayList;

/**
 * ATMThread is created by Server and runs when a new request is added to requestQ
 *
 */
public class ATMThread extends Thread{
	//Has reference to the ArrayList requestQueue created by the server
	public ArrayList<ATMRunnable> requestQ;

	/**
	 * Initializes requestQ based on the requestQ passed to it by the server, announces
	 * the creation of a new ATMThread
	 */
	ATMThread(ArrayList<ATMRunnable> myRequestQ){
		requestQ = myRequestQ;
		System.out.println("Creating an ATMThread "+this.getName());
	}
	
	/**
	 * The main run thread for ATMThread. Checks for a new ATMRunnable in requestQ every 100 milliseconds
	 * Calls removeRunnable (which removes and executes the command) if one is found
	 */
	public void run(){
		System.out.println("Running ATMThread "+this.getName());
		while(true){
			synchronized(requestQ){
				if(requestQ.size() != 0){
					removeRunnable(requestQ);
				}
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * It is extremely important that threads aren't trying to grab the same ATMRunnables. 
	 * removeRunnable is used to remove, and run, the runnables in a synchronized way
	 * @param requestQ
	 */
	private static synchronized void removeRunnable(ArrayList<ATMRunnable> requestQ){
			try{
				requestQ.remove(0).run();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
}
