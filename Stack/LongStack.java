/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      LongStack.java
 * Description:    This program mimics the idea of a stack by using the
 *                 functions pop, push and top to manipulate the stack
 *                 of long values.
 */
 
import java.io.*;

/**
 * Class:            LongStack
 * Description:      An outer layer that holds the actual stack along with
 *                   public methods that makes calls methods of its inner
 *                   class LongStackEngine, essentially delegating functions
 * 					 to this class and providing validity checks.
 *
 * Fields:           debug  - determines if debug messages should be displayed
 *                   stackCounter  - how many stacks are created
 *
 * Public functions: LongStack     - constructor that delegates to instantiate
 * 								     a LongStackEngine object using given 
 *                                   parameters.
 *                   debugOn       - sets debug variable to true, ultimately 
 * 								     turning on debug mode.
 * 					 debugOff	   - sets debug varaible to false, hiding
 * 								     debug messages
 * 
 *                   jettisonStack - jettisons a stack, delegates to method in
 * 									 LongStackEngine. Returns false if stack
 * 									 doesn't exist. Otherwise sets stackEngine
 * 									 to null.
 *
 * 					 emptyStack    - delegates to method in LongStackEngine.
 * 									 Displays error message if stack doesn't
 * 									 exist.
 * 					 exists		   - returns false if stackEngine is a null
 * 									 object. Returns true otherwise.
 * 					 getCapacity   - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 getOccupancy  - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 isEmptyStack  - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 isFullStack   - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 pop           - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 push   	   - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 top           - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 writeStack    - delegates to method in LongStackEngine.
 * 									 displays error message if stack doesn't
 * 									 exist.
 * 					 
 */
public class LongStack {

	private static boolean debug; // debug option
	private static int stackCounter = 0; // number of stacks allocated so far

 /**
  * Class:            LongStackEngine
  * Description:      An inner layer that holds the proper methods
  *                   used to manipulate a stack of long objects.
  *                   Also displays debug messages when debug mode is
  * 				  is  activated in outer layer.
  *
  * Fields:           stack  - the array that holds long values in stack
  * 						   order
  *                   stackPointer - index of last space used on the stack
  * 				  stackSize - the size of the stack
  * 				  stackID - the stack being used at the time
  * 				  tracker - Tracker object to keep track of memory
  *
  * Public functions: LongStackEngine - constructor that instantiates a
  * 								 	LongStackEngine object using given.
  * 									Assigns fields appropriately.
  * 
  *                   jettisonStackEngine - jettisons a stack, removing it
  * 										from memory. Decrements stack
  * 										counter by 1.
  * 
  * 					 emptyStack    - resets stack pointer to -1.
  * 					 getCapacity   - returns the number of elements the 
  * 									 LongStackEngine can store.
  * 					 getOccupancy  - returns the number of elements in 
  * 									 LongStackEngine
  * 					 isEmptyStack  - returns true if LongStackEngine is
  * 									 empty, false otherwise.
  * 					 isFullStack   - returns true if longStackEngine is
  * 									 full, false otherwise.
  * 					 pop           - removes an item from top of the stack
  * 									 and returns the element. Decrements
  * 								     stack pointer by 1. Returns null
  * 								     if method fails.
  * 					 push   	   - Adds item to the top of the stack.
  * 					 top           - Obtains the item on the top of the
  * 								     stack.
  * 					 
  */
	private class LongStackEngine {

		// catastrophic error messages
		static final String 
			POP_EMPTY = "Popping from an empty stack!!!\n",
			PUSH_FULL = "Pushing to a full stack!!!\n",
			TOP_EMPTY = "Topping from an empty stack!!!\n",
			WRITE_NONEXIST_STREAM 
				= "Attempt to write using non-existent"
				+ " stream!!!\n";
		
		// Debug messags
		// HEX messages are used for negative values, used in hw4
		static final String
			ALLOCATE = "[Stack %d has been allocated]\n",
			JETTISON = "[Stack %d has been jettisoned]\n",
			HEXPOP = "[Stack %d - Popping 0x%x]\n",
			HEXPUSH = "[Stack %d - Pushing 0x%x]\n",
			HEXTOP = "[Stack %d - Topping 0x%x]\n",
			POP = "[Stack %d - Popping %d]\n",
			PUSH = "[Stack %d - Pushing %d]\n",
			TOP = "[Stack %d - Topping %d]\n",
			EMPTY = "[Stack %d - Emptied]\n";

		long[] stack;		// array to hold the data in stack order
		int stackPointer;	// index of the last occupied space
		int stackSize;		// size of the stack
		int stackID;		// which stack we are using
		Tracker tracker;	// to keep track of memory usage

		/*---------------------------------------------------------------------
		Function Name:                LongStackEngine
		Purpose:                      This function is the constructor for the
									  LongStackEngine class.
		Description:                  This function assigns parameters and
									  assigns values to their respective field
		Input:                        stackSize: the size of the stack array
									  Caller: the name of the stack object 
									  called
		Output:                       None.
		Result:                       LongStackEngine object instantiated.
									  Nothing returned.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		LongStackEngine (int stackSize, String caller) {

			// allocate a new array to represent the stack
			stack = new long[stackSize]; 

			// hold the memory size of the LongStackEngine object
			long size = Size.of (stackPointer) 
					+ Size.of (stack)
					+ Size.of (stackID)
					+ Size.of (stackSize)
					+ Size.of (tracker);
			tracker = new Tracker ("LongStackEngine", size, caller);

			// Assigns stack pointer to start of stack
			stackPointer = -1;

			// assigns the stackSize parameter to field
			this.stackSize = stackSize;

			// Sets the stackID to its position on the stack counter
			stackID = stackCounter + 1;

			// increments the stack counter
			stackCounter++;

			// debug messages displayed if debug mode is on.
			if (debug)
				System.err.print (String.format (ALLOCATE, 
								stackID));

		
		}

		/*---------------------------------------------------------------------
		Function Name:                jettisonStackEngine
		Purpose:                      This function removes a stack and its
									  allocated memory stored within the 
									  tracker object.
		Description:                  This function calls the jettison method
									  from the tracker object that removes the
									  memory allocated to the stack.
		Input:                        None.
		Output:                       Debug messages if in debug mode.
		Result:                       Stack is removed. Stack counter
									  decremented. Nothing returned.
		---------------------------------------------------------------------*/
		void jettisonStackEngine () {

			// Displays debug messages if debug mode is on.
			if (debug)
				System.err.print (String.format (JETTISON, 
								stackID));
			
			// Calls tracker method to remove stack from memory.
			tracker.jettison();

			//Decrements stack counter by 1.
			stackCounter--;
		}

		/*---------------------------------------------------------------------
		Function Name:                emptyStack
		Purpose:                      This function empties the stack.
		Description:                  This function empties the stack by
									  resetting the stackPointer to -1.
		Input:                        None.
		Output:                       Debug messages if in debug mode.
		Result:                       Sets stackPointer to -1.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		void emptyStack () {
			// Displays debug messages if debug mode is on.
			if (debug)
				System.err.print (String.format (EMPTY, 
								stackID));
			
			// Resets stack pointer to -1, the beggining of a stack.
			stackPointer = -1;
		}

		/*---------------------------------------------------------------------
		Function Name:                getCapacity
		Purpose:                      This function returns the number of
									  elements available to be stored in the
									  stack.
		Description:                  This function determines how many
									  elements can be stored in the stack by
								      finding the difference between the stack
									  size and the stack pointer - 1.
		Input:                        None.
		Output:                       None.
		Result:                       Returns integer number of elements left
									  available to be stored.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		Integer getCapacity () {
			
			// Returns the space available for additional elements to be stored
			return stackSize - stackPointer - 1;
		}

		/*---------------------------------------------------------------------
		Function Name:                getOccupancy
		Purpose:                      This function returns the number of
									  currently in the stack.
		Input:                        None.
		Output:                       None.
		Result:                       Returns integer number of elements in
									  the stack.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		Integer getOccupancy () {

			// Returns stack Pointer + 1, resembling number of elements in stack.
			return stackPointer + 1;
		}

		/*---------------------------------------------------------------------
		Function Name:                isEmptyStack
		Purpose:                      This function checks if the stack is
									  empty.
		Input:                        None.
		Output:                       None.
		Result:                       Returns true if the stack pointer is -1.
									  Returns false otherwise.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		boolean isEmptyStack () {

			// Returns boolean if stack pointer = -1 (start of stack)
			return stackPointer == -1;
		}

		/*---------------------------------------------------------------------
		Function Name:                isFullStack
		Purpose:                      This function checks if the stack is
									  full.
		Input:                        None.
		Output:                       None.
		Result:                       Returns true if the stack pointer is
									  equal to the size of the stack.
									  Returns false otherwise.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		boolean isFullStack () {

			// Returns boolena if stack pointer = the size of the stack.
			return stackPointer == stackSize -1;
		}

		/*---------------------------------------------------------------------
		Function Name:                pop
		Purpose:                      This function removes an item from the
									  top of the stack and returns the value.
		Description:                  This function first checks if the stack
									  is empty and returns null if so.
									  Otherwise, the function obtains the
									  element at the top of the stack and 
									  decrements the stack pointer.
		Input:                        None.
		Output:                       Error message is stack is empty.
									  Debug messages if debug mode is on.
		Result:                       Returns element at the top of the stack.
									  Returns null if stack is empty.
		Side Effects:                 Decrements stack pointer by 1.
		---------------------------------------------------------------------*/
		Long pop () {
			
			// Checks if stack is empty, displays error message if so
			// and returns null.
			if (isEmptyStack()) {
				System.err.print(POP_EMPTY);
				return null;
			}

			// Decrements the stack pointer by 1
			stackPointer--;

			// Displays debug messages if debug mode is on.
			if (debug) {

				if (stack[stackPointer+1] < 0) {
					System.err.print (String.format (HEXPOP, 
									stackID, stack[stackPointer+1]));
				} else {
					System.err.print (String.format (POP, 
									stackID, stack[stackPointer+1]));
				}
			}

			// Returns the element at the top of the stack.
			return stack[stackPointer+1];
		}

		/*---------------------------------------------------------------------
		Function Name:                push
		Purpose:                      This function adds an item to the top of
									  the stack.
		Description:                  This function first checks if the stack
									  is full and returns false if so.
									  Otherwise, the function increments the
									  stack pointer by 1 and inserts the
									  element to to the array at that index.
		Input:                        item : the element to be added to the top
                                  	  of the stack.
		Output:                       Error message is stack is full.
									  Debug messages if debug mode is on.
		Result:                       Returns true if element successfully
									  added to the top of stack. Returns
									  false otherwise.
		Side Effects:                 Increments stack pointer by 1.
		---------------------------------------------------------------------*/
		boolean push (long item) {

			// Checks if stack is full, displays error message and returns
			// false if so.
			if (isFullStack()) {
				System.err.print(PUSH_FULL);
				return false;
			}

			// Increments the stack pointer by 1
			stackPointer++;
			
			// Assigns element to index at the top of the stack.
			stack[stackPointer] = item;

			// Displays debug messages if debug mode is on.
			if (debug) {

				if (item < 0) {
					System.err.print (String.format (HEXPUSH, 
									stackID, item));
				} else {
					System.err.print (String.format (PUSH, 
									stackID, item));
				}
			}

			// Returns true when everything else is completed.
			return true;

		}

		/*---------------------------------------------------------------------
		Function Name:                top
		Purpose:                      This function returns the element at the
									  top of the stack.
		Description:                  This function first checks if the stack
									  is full and returns null if so.
									  Otherwise, the function obtains the
									  element at the top of the stack.
		Input:                        None.
		Output:                       Error message is stack is full.
									  Debug messages if debug mode is on.
		Result:                       Returns element at the top of the stack.
		Side Effects:                 None.
		---------------------------------------------------------------------*/
		Long top () {
			
			// Checks if stack is empty, displays error message and returns
			// null if so.
			if (isEmptyStack()) {
				System.err.print(TOP_EMPTY);
				return null;
			}

			// Displays debug messages if debug mode is on.
			// Displays debug messages if debug mode is on.
			if (debug) {

				if (stack[stackPointer] < 0) {
					System.err.print (String.format (HEXTOP, 
									stackID, stack[stackPointer]));
				} else {
					System.err.print (String.format (TOP, 
									stackID, stack[stackPointer]));
				}
			}
			// Returns the value at the top of the stack.
			return stack[stackPointer];
		}

		void writeStack (PrintStream stream) {

			int index = 0;	// index into the stack

			if (stream == null) {
				System.err.print (WRITE_NONEXIST_STREAM);
				return;
			}

			int stackOccupancy = getOccupancy ();

			if (stream.equals (System.err)) {
				stream.print (
				"\nStack " + stackID + ":\n"
				+ "Stack's capacity is " + stackSize + ".\n"
				+ "Stack has "
				+ stackOccupancy + " item(s) in it.\n"
				+ "Stack can store "
				+ (stackSize - stackOccupancy) 
				+ " more item(s).\n");
				Tracker.checkMemoryLeaks ();
			}

			for (index = 0; index < stackOccupancy; index++) {
				if (stream.equals (System.err))
					stream.print (String.format (
						"Value on stack is |0x%x|\n", 
						stack[index]));
				else {
					if (stack[index] < 0)
						stream.print (String.format (
							"%c ", 
							(byte) stack[index]));
					else
						stream.print (String.format (
							"%d ", stack[index]));
				}
			}
		}
	}

	// -------------------- DO NOT EDIT BELOW THIS LINE -------------------- 
	// PROVIDED INFRASTRUCTURE BELOW, YOUR CODE SHOULD GO ABOVE
	// CHANGING THE CODE BELOW WILL RESULT IN POINT DEDUCTIONS

	// catastrophic error messages
	private static final String 
	CAPACITY_NONEXIST = "Capacity check from a non-existent stack!!!\n",
	EMPTY_NONEXIST = "Emptying a non-existent stack!!!\n",
	ISEMPTY_NONEXIST = "Isempty check from a non-existent stack!!!\n",
	ISFULL_NONEXIST = "Isfull check from a non-existent stack!!!\n",
	OCCUPANCY_NONEXIST = "Occupancy check from a non-existent stack!!!\n",
	POP_NONEXIST = "Popping from a non-existent stack!!!\n",
	PUSH_NONEXIST = "Pushing to a non-existent stack!!!\n",
	TOP_NONEXIST = "Topping from a non-existent stack!!!\n",
	WRITE_NONEXIST_STACK = "Writing to a non-existent stack!!!\n";

	private LongStackEngine stackEngine; // the object that holds the data

	private boolean exists () {
		return !(stackEngine == null);
	}

	public LongStack (int stackSize, String caller) {
		stackEngine = new LongStackEngine (stackSize, caller);
	}

	// Debug state methods
	public static void debugOn () {
		debug = true;
	}

	public static void debugOff () {
		debug = false;
	}

	public boolean jettisonStack () {

		// ensure stack exists
		if (!exists ())
			return false;

		stackEngine.jettisonStackEngine ();
		stackEngine = null;
		return true;
	}

	public void emptyStack () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (EMPTY_NONEXIST);
			return;
		}
		
		stackEngine.emptyStack ();
	}
	
	public Integer getCapacity () {
			
		// ensure stack exists
		if (!exists ()) {
			System.err.print (CAPACITY_NONEXIST);
			return null;
		}
		
		return stackEngine.getCapacity ();
	}

	public Integer getOccupancy () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (OCCUPANCY_NONEXIST);
			return null;
		}

		return stackEngine.getOccupancy ();
	}

	public boolean isEmptyStack () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (ISEMPTY_NONEXIST);
			return false;
		}

		return stackEngine.isEmptyStack ();
	}

	public boolean isFullStack () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (ISFULL_NONEXIST);
			return false;
		}

		return stackEngine.isFullStack ();
	}

	public Long pop () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (POP_NONEXIST);
			return null;
		}

		return stackEngine.pop ();
	}

	public boolean push (long item) {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (PUSH_NONEXIST);
			return false;
		}

		return stackEngine.push (item);
	}

	public Long top () {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (TOP_NONEXIST);
			return null;
		}

		return stackEngine.top ();
	}

	public void writeStack (PrintStream stream) {

		// ensure stack exists
		if (!exists ()) {
			System.err.print (WRITE_NONEXIST_STACK);
		return;
		}

		stackEngine.writeStack (stream);
	}
}
