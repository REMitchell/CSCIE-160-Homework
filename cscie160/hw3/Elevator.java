package cscie160.hw3;

import java.util.ArrayList;
import java.util.HashSet;

import cscie160.hw3.Elevator;
import cscie160.hw3.ElevatorFullException;
import cscie160.hw3.Floor;

/**
 * @author ryanmitchell
 *
 */
public class Elevator {
	
	/**
	 * @param capacity the maximum capacity of passengers in the elevator
	 * @param numbFloors the number of floors in the building
	 * @param currentFloor the floor the elevator is on at any given time
	 * @param direction The direction the elevator is going in, 1 for up, -1 for down
	 * @param passengers The number of passengers on the elevator at any given time
	 * @param Floors an array of floor objects, one for each floor in the building, plus a placeholder floor at the 0 index, unused
	 * @param stopsArray the index represents the floor number, the value, how many passengers have that floor as a destination
	 * @param waitingQueue the index represents the floor number, indicates that a passenger is waiting to board the elevator at that floor
	 */
	int capacity = 10;
	static int numbFloors = 7;
	int currentFloor = 1;
	int direction = 1;

	private static HashSet<Passenger> passengers = new HashSet<Passenger>();
	private static HashSet<Passenger> unqueuedPassengers = new HashSet<Passenger>();

	//create array lists and hash sets
	for(int i = 0; i <= numbFloors; i++){
		System.out.println("Heyo!");
	}
	

	//stopsArray represents the buttons on the elevator
	boolean[] stopsArray = {false, false, false, false, false, false, false, false};
	
	//upQueue and downQueue represent the up/down buttons on each floor, outside the elevator
	boolean[] upQueue = {false, false, false, false, false, false, false, false};
	boolean[] downQueue = {false, false, false, false, false, false, false, false};
	public static void main(String[] args) {
		Elevator myElevator = new Elevator();
		
		for(int i = 0; i < 40; i++){
			System.out.println(myElevator.toString());
			myElevator.move();
		}
	}
	
	/**
	 * This method is called to move the elevator a single floor
	 * If the elevator reaches the top or bottom of the building, 
	 * it will change direction
	 **/
	public void move(){
		if(currentFloor == numbFloors && direction == 1 || currentFloor == 1 && direction == -1){
			//Cannot move any more. Change direction and continue
			direction = -direction;
		}
		currentFloor = currentFloor+direction;
		//If passengers need to get off here, or it is heading in a direction that a passenger wants to go
		boolean stopHere = direction == 1 ? upQueue[currentFloor] : downQueue[currentFloor];
		if((stopsArray[currentFloor]) || stopHere){
			stop();
		}
	}
	
	/**
	 * Overrides the toString() method
	 **/
	public String toString(){
		String passengerString = "";
		for(Passenger passenger in this.passengers){
			passengerString = passengerString+"\n"+passenger.toString();
		}
		return "****\nCurrently "+passengers+" passengers onboardCurrent Floor: "+currentFloor+"\n";
	}
	
	/**
	 * Prints a line that announces the elevator is stopping
	 * Any passengers destined for the current floor are unloaded
	 * Control is passed to Floor.unloadPassengers, where any passengers waiting on 
	 * the current floor are loaded on the elevator, space permitting
	 **/
	
	public void stop(){
		System.out.println("Stopping on floor "+currentFloor);
		//Create a set of passengers who want to get off on that floor
		//Each passenger who is getting off resets their current floor
		ArrayList<Passenger> gettingOff;
		for(Passenger passenger : passengers){
			if(passenger.arrive(this)){
				gettingOff.add(passenger);
			}
		}
		//Elevator sets the waiting queue to false for that floor
		//(If the capacity limit is hit, floor will set to true)
		waitingQueue[currentFloor] = false;
		//Goes to floor to board waiting passengers on the elevator
		floors[currentFloor].unloadPassengers(this, gettingOff);
	}
	/**
	 * 
	 * @param floor indicates the floor destination floor of the boarding passenger
	 * @return
	 * @throws ElevatorFullException if the capacity has been reached already
	 */
	public boolean boardPassenger() throws ElevatorFullException{
		if(direction == 1){
			//Board passengers heading up
			floors[currentFloor].upPassengers;
			for(Passenger passWaiting : floors[currentFloor].upPassengers){
				System.out.pringln(passWaiting);
			}
		}else{
			//Board passengers heading down
		}
	}
	
	/**
	 * Analagous to pushing the button outside the elevator
	 * @param floor indicates the floor the passenger is registering the request from
	 */
	public void registerRequest(int floor, int direction){
		if(direction == 1){
			upQueue[floor] = true;
		}else{
			downQueue[floor] = true;
		}
	}
	
	public int totalPassengers(){
		return passengers.size();
	}
	
	
}