package cscie160.hw2;

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
	int numbFloors = 7;
	int currentFloor = 1;
	int direction = 1;
	//how many passengers are waiting on the floor to board the elevator
	int passengers = 0;
	//Four passengers (one on floor 2, one on floor 3, two on floor 4) all
	//Destined for floor 7
	static Floor[] floors = {new Floor(0, 0), new Floor(0, 1), new Floor(1, 2), 
		new Floor(1, 3), new Floor(2, 4), new Floor(0, 5), new Floor(0, 6), 
		new Floor(0, 7)};
	int[] stopsArray = {0, 0, 0, 0, 0, 0, 0, 0};

	boolean[] waitingQueue = {false, false, true, true, true, false, false, false};
	
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
		//If there are passengers that need to get off here, let them off, reset things
		if((stopsArray[currentFloor] != 0) || waitingQueue[currentFloor]){
			stop();
		}
	}
	
	/**
	 * Overrides the toString() method
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
	 * 
	 * @param floor indicates the floor destination floor of the boarding passenger
	 * @return
	 * @throws ElevatorFullException if the capacity has been reached already
	 */
	public boolean boardPassenger(int floor) throws ElevatorFullException{
		passengers++;
		stopsArray[floor]++;
		if (passengers >= capacity) {
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