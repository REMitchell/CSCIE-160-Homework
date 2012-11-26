package cscie160.hw3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;
import cscie160.hw3.Elevator;
import cscie160.hw3.ElevatorFullException;
import cscie160.hw3.Floor;

/**
 * Represents an elevator with a certain carrying capacity, 
 * Currently uses a sweeping algorithm, traveling up until it reaches the top
 * of the building, and back down, carrying passengers and dropping them off
 * along the way, with no modification based on passenger destination
 * @author ryanmitchell
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
	private static final int CAPACITY = 10;
	private static final int NUMBFLOORS = 7;
	public int currentFloor = 1;
	public enum Direction {UP, DOWN};
	public Direction direction = Direction.UP;
	private HashSet<Passenger> passengers = new HashSet<Passenger>();
	private static LinkedList<Floor> floors = new LinkedList<Floor>();
	//stopsArray represents the buttons on the elevator
	private boolean[] stopsArray = {false, false, false, false, false, false, false, false};
	//upQueue and downQueue represent the up/down buttons on each floor, outside the elevator
	public boolean[] upQueue = {false, false, false, false, false, false, false, false};
	public boolean[] downQueue = {false, false, false, false, false, false, false, false};

	
	/**
	 * Main elevator method, goes for 20 iterations, prints elevator status before moving one floor
	 */
	
	public static void main(String[] args) {
		Elevator myElevator = new Elevator();
		
		for(int i = 0; i < 40; i++){
			System.out.println(myElevator.toString());
			myElevator.move();
		}
	}
	
	/**
	 * Populates the floors array, creates an empty list of upPassengers, downPassengers, and unQueued passengers
	 * for each floor, as well as assigning a floor number.
	 * After floors are created, creates several unqueued passengers with desired destination floors
	 * and calls the "startWaiting" method for each passenger, which places them in up and down queues as appropriate
	 */
	Elevator(){
		Random generator = new Random();
		int numPassOnFloor;
		int destFloor;
		//Temporary arraylist to hold passengers while we populate the floors
		ArrayList<Passenger> tmpPassengers = new ArrayList<Passenger>(); 
		for(int i = 0; i <= NUMBFLOORS; i++){
			//Add a floor with empty passenger lists
			floors.add(new Floor(i, new ArrayList<Passenger>(), new ArrayList<Passenger>(), new HashSet<Passenger>()));
			
			//Add some number of passengers to the floor
			numPassOnFloor = generator.nextInt(CAPACITY);
			for(int j = 0; j < numPassOnFloor; j++){
				//Destination floor of the passenger to be created
				destFloor = generator.nextInt(NUMBFLOORS)+1;
				tmpPassengers.add(new Passenger(i, destFloor));
				//The last passenger that was just added to tmpPassengers
				floors.get(i).acceptPassenger(tmpPassengers.get(tmpPassengers.size()-1));

				if(destFloor != i){
					//Passenger needs to go places!
					floors.get(i).startWaiting(this, tmpPassengers.get(tmpPassengers.size()-1));
				}
			}
		}
		//To test the limit of our elevator's capacity, add new passengers on floor 5,
		//heading to floor 2
		for(int j = 0; j <= CAPACITY; j++ ){
			tmpPassengers.add(new Passenger(5, 2));
			floors.get(5).acceptPassenger(tmpPassengers.get(tmpPassengers.size()-1));
			floors.get(5).startWaiting(this, tmpPassengers.get(tmpPassengers.size()-1));
		}
	}
	
	
	/**
	 * This method is called to move the elevator a single floor
	 * If the elevator reaches the top or bottom of the building, 
	 * it will change direction
	 **/
	public void move(){
		if(direction == Direction.DOWN){
			currentFloor--;
		}else{
			currentFloor++;
		}
		if(currentFloor == NUMBFLOORS){
			direction = Direction.DOWN;
		}
		else if(currentFloor == 1){
			direction = Direction.UP;
		}
		//If passengers need to get off here, or it is heading in a direction that a passenger wants to go,
		//This will be set to true. 
		boolean stopHere = direction == Direction.UP ? upQueue[currentFloor] : downQueue[currentFloor];

		if((stopsArray[currentFloor]) || stopHere){
			stop();
		}
	}
	
	/**
	 * Overrides the toString() method
	 **/
	public String toString(){
		String passengerString = "";
		for(Passenger passenger : passengers){
			passengerString = passengerString+"\n"+passenger.toString();
		}
		String returnString = "Currently "+String.valueOf(passengers.size())+
				" passengers onboard\nCurrent Floor: "+currentFloor+
				"\nNumber of passengers waiting on this floor: "+
				floors.get(currentFloor).getNumWaiting();
		return returnString;
	}
	
	/**
	 * Prints a line that announces the elevator is stopping
	 * Any passengers destined for the current floor are unloaded
	 * Control is passed to Floor.unloadPassengers, where any passengers waiting on 
	 * the current floor are loaded on the elevator, space permitting
	 **/
	
	public void stop(){
		System.out.println("*****\nStopping on floor "+currentFloor);
		//Create a set of passengers who want to get off on that floor
		//Each passenger who is getting off resets their current floor
		HashSet<Passenger> gettingOff = new HashSet<Passenger>();
		Passenger passenger;
		Iterator<Passenger> it = passengers.iterator();
		while(it.hasNext()){
			passenger = it.next();
			if(passenger.arrive(this)){
				gettingOff.add(passenger);
				//Remove passenger from elevator passengers
				it.remove();
			}
		}
		//All passengers wanting to get off on this floor are able to, so stopsArray
		//is set to false
		stopsArray[currentFloor] = false;
		//Goes to floor to board waiting passengers on the elevator
		floors.get(currentFloor).acceptPassengers(gettingOff);
		floors.get(currentFloor).unloadPassengers(this);
	}
	/**
	 * Boards a single Passenger. If the elevator capacity is full, throws an ElevatorFullException
	 * If passenger is boarded successfully, returns true
	 * @param floor indicates the floor destination floor of the boarding passenger
	 * @return
	 * @throws ElevatorFullException if the capacity has been reached already
	 */
	public boolean boardPassenger(Passenger boardingPassenger) throws ElevatorFullException{
		if(passengers.size() >= CAPACITY){
				throw new ElevatorFullException();
		}
		//If passenger can board successfully...
		System.out.println("Boarding: "+boardingPassenger);
		stopsArray[boardingPassenger.getDestination()] = true;
		passengers.add(boardingPassenger);
		return true;
	}
	
	/**
	 * Used by Floor to modify the upQueue and downQueue arrays when a passenger wishes to board
	 * Analagous to pushing the up or down button outside the elevator
	 * @param floor indicates the floor the passenger is registering the request from
	 */
	public void registerRequest(int passengerFloor, Direction requestDirection){
		if(requestDirection == Direction.UP){
			upQueue[passengerFloor] = true;
		}else{
			downQueue[passengerFloor] = true;
		}
	}
	
	/**
	 * Getter method for passengers, returns only the size of the array
	 * @return number of total passengers
	 */
	public int totalPassengers(){
		return passengers.size();
	}
}