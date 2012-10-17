/**
 * @author Ryan E. Mitchell for CSIE 160
 * 
 */

package cscie160.hw1;

public class Elevator {
	int capacity = 10;
	int numbFloors = 7;
	int currentFloor = 1;
	int direction = 1;
	int passengers = 0;
	int[] stopsArray = {0,0,0,0,0,0,0,0,0,0,0};
	
	public static void main(String[] args) {
		Elevator myElevator = new Elevator();
		myElevator.boardPassenger(2);
		myElevator.boardPassenger(2);
		myElevator.boardPassenger(3);
		for(int i = 1; i <= 20; i++){
			System.out.println(myElevator);
			myElevator.move();
		}
	}
	
	/**
	 * move called to move the elevator a single floor
	 * If the elevator reaches the top or bottom of the building, it will change direction
	 */
	public void move(){
		if(currentFloor == numbFloors && direction == 1 || currentFloor == 1 && direction == -1){
			//Cannot move any more. Change direction and continue
			direction = -direction;
		}
		currentFloor = currentFloor+direction;
		//If there are passengers that need to get off here, let them off, reset things
		if(stopsArray[currentFloor] != 0){
			stop();
			passengers = passengers - stopsArray[currentFloor];
			stopsArray[currentFloor] = 0;
		}
	}
	/**
	 * Overrides the toString() method
	 * @returns string containing relevant information about the elevator
	 **/
	public String toString(){
		return "Currently "+passengers+" passengers onboard\nCurrent Floor: "+currentFloor+"\n";
	}
	/**
	 * Represents the elevator stopping. Currently called only when a passenger is let off
	 * Simply prints a "stop" message.
	 **/
	public void stop(){
		System.out.println("Stopping on floor "+currentFloor);
	}
	
	/**
	 * Takes the floor as an argument to increment the number of passengers
	 * that want to get off the elevator on that floor. Increments the total number
	 * of passengers as well as the number of passengers in the array of floors.
	 * @argument Floor the boarding passenger wishes to stop at
	 **/
	public void boardPassenger(int floor){
		passengers++;
		stopsArray[floor]++;
	}
}