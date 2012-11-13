package cscie160.hw2;
import cscie160.hw1.Elevator.Direction;

/**
 * Represents an elevator with a certain carrying capacity, 
 * Currently uses a sweeping algorithm, traveling up until it reaches the top
 * of the building, and back down, carrying passengers and dropping them off
 * along the way, with no modification based on passenger destination
 * @author ryanmitchell
 * @version 2.0 Oct 25, 2012
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
	Direction direction = Direction.UP;
	public int passengers = 0;
	//Four passengers (one on floor 2, one on floor 3, two on floor 4) all
	//Destined for floor 7
	public static Floor[] floors = {new Floor(0, 0), new Floor(0, 1), new Floor(1, 2), 
		new Floor(1, 3), new Floor(2, 4), new Floor(0, 5), new Floor(0, 6), 
		new Floor(0, 7)};
	public int[] stopsArray = {0, 0, 0, 0, 0, 0, 0, 0};

	boolean[] waitingQueue = {false, false, true, true, true, false, false, false};
	
	/**
	 * Provides a simple test for the elevator, moves 40 times, board and let off
	 * existing passengers, board 12 passengers on the 7th floor, after 12 moves
	 * to test elevator capacity and error handling
	 * @param args
	 */
	
	public static void main(String[] args) {
		Elevator myElevator = new Elevator();
		for(int i = 0; i < 40; i++){
			System.out.println(myElevator.toString());
			myElevator.move();
			if(myElevator.currentFloor == 1){
				//Temporary measure -- we're assuming all passengers want to
				//Go down to floor 1. Soon, passengers will be their own class
				//With a "destination floor" property, but this works for now
				myElevator.passengers = 0;
			}
			if(i == 12){
				//On the 12th move, as a test of elevator capacity
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
				floors[7].addPassenger(myElevator);
			}
		}
	}
	
	/**
	 * move called to move the elevator a single floor
	 * The elevator moves first and, if it reaches the top or bottom
	 * of the building, it will change direction and move in the opposite
	 * direction on its next move. It checks to see if passengers need
	 * to be let out, or if passengers are waiting to board after moving
	 */

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
		if((stopsArray[currentFloor] != 0) || waitingQueue[currentFloor]){
			stop();
		}		
	}
	
	/**
	 * Overrides the toString() method
	 * @param String returns the current state of the elevator
	 **/
	public String toString(){
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
		System.out.println(toString());
		//Let out passengers who want to go to that floor
		passengers = passengers - stopsArray[currentFloor];
		stopsArray[currentFloor] = 0;
		//Elevator sets the waiting queue to false for that floor
		//(If the capacity limit is hit, floor will set to true)
		waitingQueue[currentFloor] = false;
		//Goes to floor to board waiting passengers on the elevator
		floors[currentFloor].unloadPassengers(this, 1);
	}
	/**
	 * Boards a passenger onto the elevator, throws ElevatorFullException()
	 * if the capacity of the elevator has been reached
	 * @param floor indicates the floor destination floor of the boarding passenger
	 * @return true on success
	 * @throws ElevatorFullException if the capacity has been reached already
	 */
	public boolean boardPassenger(int floor) throws ElevatorFullException{
		passengers++;
		stopsArray[floor]++;
		if (passengers >= CAPACITY) {
	        throw new ElevatorFullException();
		}
	    	return true;
	}
	
	/**
	 * Analagous to pushing the button outside the elevator
	 * @param floor indicates the floor the passenger is registering the request from
	 */
	public void registerRequest(int floor){
		waitingQueue[floor] = true;
	}
}