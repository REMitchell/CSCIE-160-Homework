package cscie160.hw1;
/**
 * Represents an elevator with a certain carrying capacity, 
 * Currently uses a sweeping algorithm, traveling up until it reaches the top
 * of the building, and back down, carrying passengers and dropping them off
 * along the way, with no modification based on passenger destination
 * @author ryanmitchell
 * @version 2.0 Oct 25, 2012
 */
public class Elevator {
	private static final int CAPACITY = 10;
	private static final int NUMBFLOORS = 7;
	public int currentFloor = 1;
	public enum Direction {UP, DOWN};
	Direction direction = Direction.UP;
	private int passengers = 0;
	private int[] stopsArray = {0,0,0,0,0,0,0,0,0,0,0};

	/**
	 * Used to run the elevator through a trivial test, where three passengers
	 * are added who want to get off at various floors, and the elevator moves
	 * 20 times, printing out its state before each move.
	 * @param args
	 */
	
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
	 * The elevator moves first and, if it reaches the top or bottom
	 * of the building, it will change direction and move in the opposite
	 * direction on its next move. It checks to see if passengers need
	 * to be let out after moving
	 * @param direction 
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
	 * Simply prints a "stop" message, then prints the current state of the elevator
	 **/
	public void stop(){
		System.out.println("Stopping on floor "+currentFloor+", about to let passengers off");
		System.out.println(toString());
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