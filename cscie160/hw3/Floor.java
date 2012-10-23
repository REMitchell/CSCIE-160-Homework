package cscie160.hw3;
import java.util.HashSet;
import java.util.ArrayList;
import cscie160.hw3.Elevator;
import cscie160.hw3.ElevatorFullException;

public class Floor {

	/**
	 * @param passengers Number of passengers waiting on the floor to board the elevator
	 * @param floorNumber The floor level (frequently used as a reference by the elevator)
	 */

	int passengers;
	int floorNumber;
	
	//Should be a hashset?
	private static HashSet<Passenger> unqueuedPassengers = new HashSet<Passenger>();
	
	//Should be arraylists
	static ArrayList<Passenger> upPassengers = new ArrayList<Passenger>();
	static ArrayList<Passenger> downPassengers = new ArrayList<Passenger>();
	public Floor(int passengers, int floorNumber){
		this.passengers = passengers;
		this.floorNumber = floorNumber;
	}
	
	public static void main(String[] args) {

	}
	
	/**
	 * Unloads all passengers onto the elevator, space permitting
	 * @param anElevator the elevator object the passenger is using
	 * @param destFloor the floor the passenger wants to get off on
	 * @return boolean successful in loading all passengers or not. Not currently used
	 */
	//TODO: Overhaul to accept a collection of passengers from elevator
	public boolean unloadPassengers(Elevator anElevator, ArrayList<Passenger> newPassengers){
		//All passengers bound for this floor exit
		for(Passenger newPassenger : newPassengers){
			unqueuedPassengers.add(newPassenger);
		}
		
		//Boards a passenger, resets floors passengers if successful
		while(passengers > 0){
			try{
				anElevator.boardPassenger(destFloor);
			} catch(ElevatorFullException e){
				//Elevator is full. Floor holds any remaining passengers
				//and waits for elevator to return
				System.out.println("Elevator is full! Leaving "+String.valueOf(passengers)+" behind");
				//Registers request so that the elevator will be sure to stop there next time
				anElevator.registerRequest(floorNumber);
				//Lets the door close, elevator continues
				return false;
			}
			//On success...
			passengers--;
			System.out.println("Passenger boarding elevator. There are "+String.valueOf(anElevator.passengers)+" passengers on the elevator and "+String.valueOf(passengers)+" on floor");
		}
		//Passengers should be equal to zero at this point
		//Elevator no longer needs to stop on this floor
		//Lets the door close, elevator continues
		return true;
	}
	
	/**
	 * represents a passenger walking up the elevator and wanting to board
	 * @param anElevator the elevator object the passenger is being loaded onto
	 */
	public void addPassenger(Elevator anElevator){
		passengers++;
		anElevator.registerRequest(floorNumber);
	}
	public Passenger[] getUpPassengers(){
		return upPassengers;
	}
	public Passenger[] getDownPassengers(){
		return downPassengers;
	}
}
