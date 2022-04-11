/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      HashTable.java
 * Description:    This program represents and creates a hash table sorted
 * 				   using a bully algorithm. It consists of an array of base
 * 				   elements which is stored at certain indexes its
 * 				   probe sequence, determined by the hash algorithm used. It
 * 				   also contains insert and lookup methods to place and view
 * 				   the data stored in the element itself.
 */

/**
 * Class:            HashTable
 * Description:      Array of Base objects representing a Hash Table data
 * 					 structure. It stores elements at indexes of the array
 * 					 determined by a hash algorithm, and utilises the bully
 * 					 algorithm to represent the elements accordingly.
 *
 * Fields:           counter  - number of hash tables so far.
 *                   debug  - debug state to display debug messages.
 *                   occupancy - how many elements are in Hash Table.
 * 					 size - size of Hash Table
 * 					 table - the Hash Table itself (array of bases).
 * 					 tableCount - the Hash Table id
 * 					 tracker - to track memory
 * 					 index - last location checked in hash table
 * 					 count - count of the index in probe probe sequence.
 *
 * Public functions: debugOn - turns on debugging for this Hash Table
 *                   debugOff - turns off debugging for this Hash Table
 *                   HashTable - Allocates and initialises memory associated
 * 								 with Hash Table.
 * 					 jettison - jettisons each Base object stored in Hash Table
 * 								and the Tracker object itself.
 * 					 getOccupancy - obtains current occupancy of Hash Table.
 * 					 insert - inserts an element in the Hash Table. Delegates
 * 							  to method below.
 * 					 insert(recursive) - inserts an element in the Hash Table
 * 										 using the Bully algorithm and
 * 										 possibly recursion.
 * 					 locate - locates the index where the functions insert
 * 							  and locate are to be performed.
 * 					 lookup - Looks up the element in the hash table.
 * 					 toString - creates a string representation of the hash
 * 								table.
 */
public class HashTable extends Base {

	// counters, flags and constants 
	private static int counter = 0;         // number of HashTables so far
	private static boolean debug;           // allocation of debug states

	// data fields
	private long occupancy;     // how many elements are in the Hash Table
	private int size;           // size of Hash Table
	private Base table[];       // the Hash Table itself ==> array of Base
	private int tableCount;     // which hash table it is
	private Tracker tracker;    // to track memory

	// initialized by Locate function
	private int index;      // last location checked in hash table
        
	// set in insert/lookup, count of location in probe sequence
        private int count = 0;

	// messages
	private static final String DEBUG_ALLOCATE = " - Allocated]\n";
	private static final String DEBUG_LOCATE = " - Locate]\n";
	private static final String DEBUG_LOOKUP = " - Lookup]\n";
	private static final String AND = " and ";
	private static final String BUMP = "[Bumping To Next Location...]\n";
	private static final String COMPARE = " - Comparing ";
	private static final String FULL = " is full...aborting...]\n";
	private static final String FOUND_SPOT = " - Found Empty Spot]\n";
	private static final String HASH = "[Hash Table ";
	private static final String HASH_VAL = "[Hash Value Is ";
	private static final String INSERT = " - Inserting ";
	private static final String PROCESSING = "[Processing ";
	private static final String TRYING = "[Trying Index ";

	/**
	 * Function name: debugOn
	 * 
	 * Initialises the debug field to true, displaying debug messages
	 * as a result.
	 * 
	 * @param nothing is passed
	 * @return nothing is returned.
	 * 
	 * Side effects: debug field initialised to true.
	 * 
	 */
	public static void debugOn () {
		
		// Initialises debug field to true.
		debug = true;
	}

	/**
	 * Function name: debugOff
	 * 
	 * Initialises the debug field to false to not display debug messages.
	 * 
	 * @param nothing is passed
	 * @return nothing is returned.
	 * 
	 * Side effects: debug field initialised to false.
	 * 
	 */
	public static void debugOff () {
		
		//Initialises debug field to false.
		debug = false;
	}

	/**
	 * Function name: HashTasble
	 * 
	 * Constructs a HashTable object which holds all the 
	 * inner methods used to manipulate a Hash Table.
	 * 
	 * @param sz the size of the hash table (expected to be prime number)
	 * @param caller: String with a class name to help debug memory
	 * 				  issues.
	 * @return nothing is returned.
	 * 
	 * Side effects: Class fields are initialised.
	 * 
	 */
	public HashTable (int sz, String caller) {

		// Assigns occupancy field of a hash table to 0.
		occupancy = 0;

		// Assigns the sz paramter to the size field of the hash table.
		size = sz;

		// Constructs an array of Base objects with a fixed length
		// of the size parameter. Assigns each element of array to null
		// by default of Java.
		table = new Base[sz];

		// Initialises the tableCount field to the number of hash tables
		// currently allocated.
		tableCount = counter + 1;

		// Increments the static counter of how many hash tables are allocated
		// by 1.
		counter++;

		// If debug mode is on, displays debug message for hash table
		// allocation.
		if (debug) {
			System.err.print(HASH + tableCount + DEBUG_ALLOCATE);
		}

		// DO NOT CHANGE THIS PART
		tracker = new Tracker ("HashTable", 
				Size.of (index)
				+ Size.of (occupancy)
				+ Size.of (size)
				+ Size.of (table)
				+ Size.of (tableCount)
				+ Size.of (tracker),
				caller + " calling HashTable Ctor");
	}

	/**
	 * Function name: jettison
	 * 
	 * Stops tracking the memory for all Base objects in hash table, and the 
	 * Tracker object itself.
	 * 
	 * @param nothing is passed.
	 * @return nothing is returned.
	 * 
	 * Side effects: All Base objects in hash table array is jettisoned.
	 * 				 The tracker of the hash table is jettisoned.
	 * 				 The static counter decremented by 1.
	 * 
	 */
	public void jettison () {

		// Iterates over all elements in the hash table array
		for (int tableElement = 0; tableElement < size; tableElement++) {

			// If the object at the index is empty, continue the loop
			if (table[tableElement] == null) {
				continue;
			}

			// If the element is not null, jettison it and decrement
			// occupany by 1.
			table[tableElement].jettison();
			occupancy--;
		}

		// After all elements in array are jettisoned, jettison the tracker.
		tracker.jettison();

		// Decrement the static counter by -1.
		counter--;
	}

	/**
	 * Function name: getOccupancy
	 * 
	 * Getter method which returns the value stored in occupancy field
	 * 
	 * @param nothing is passed.
	 * @return occupancy of hash table is returned.
	 * 
	 */
	public long getOccupancy () {
		
		// returns the value stored in the occupancy field.
		return occupancy;
	}

	/**
	 * Performs insertion into the table via delegation to the
	 * private insert method.
	 *
	 * @param   element       The element to insert.
	 * @return  true or false indicating success of insertion
	 */
	public boolean insert (Base element) {
		return insert (element, false);
	}

	/**
	 * Function name: insert
	 * 
	 * Inserts an element into the hash table. Reorganises hash table if
	 * there is a collision using the Bully algorithm and recursion. 
	 * 
	 * @param element the Base object to be inserted/reinserted into the 
	 * 				  hash table.
	 * @param recursiveCall boolean to determine whether or not the count
	 * 						field should be reset to 0.
	 * @return boolean: true if insertion is succesfull
	 * 					false if insertion failed (table is full/nothing
	 * 					could be inserted).
	 * 
	 * Side effects:	index and count fields are changed as a result
	 * 					of the locate fuction call. Elements may be 
	 * 					reinserted at different indexes as a result of
	 * 					bullying.
	 */
	public boolean insert (Base element, boolean recursiveCall) {

		// Displays debug message for insertion if debug mode is on.
		if (debug) {
			System.err.println(HASH + tableCount + INSERT
							 + element.getName() + "]");
		}

		// If it is not a recursive call, then reset the count to 0.
		if (!recursiveCall) {
			count = 0;
		}
		
		// Calls the locate function, possibly changing the index and
		// count fields. Returns an object if element is found or null
		// if element is not found.
		Base elementFromLocate = locate(element);	// Stores the element
													// returned from locate.

		// If an element is returned from locate, it is a duplicate
		// case (element with same name, possibly different value).
		if (elementFromLocate != null) {

			// Jettison old element stored at the index.
			// Store the new Base object at the same index before.
			table[index].jettison();
			table[index] = element;

			// Return true as it was a successful insertion.
			return true;

		// If the element returned from locate is null/ not a duplicate
		// element case.
		} else {

			// If there is an empty space in the hash table at that index.
			if (table[index] == null) {

				// Insert the element into the hash table array at that index.
				// and increment the occupancy.
				table[index] = element;
				occupancy++;

				// Reset the count field to 0 (for future use in other
				// functions). 
				count = 0;

				// Insertion was successful.
				return true;

			// If there is already a Base object in the hash table at index.
			} else {

				// Check if the hash table is full.
				if (getOccupancy() == size) {

					// Display debug message that table is full if debug moode
					if (debug) {
						System.err.print(HASH + tableCount + FULL);
					}

					// Reset the count field to 0 for future use.
					// Jettison the element supposed to be inserted.
					count = 0;
					element.jettison();

					// Nothing can be inserted
					return false;
				}
		
				// If Base object in the table at index is alphabetically
				// smaller than the element to be stored  (Bully algorithm)
				if (table[index].isLessThan(element)) {

					// Display debug message to show bump
					if (debug) {
						System.err.print(BUMP);
					}

					Base bumpedElement = table[index];		// the element
															// to be bumped.
					
					// Insert the 'bigger' element into the table at the index
					table[index] = element;

					// Recursive call, using the bumped element and
					// recursiveCall as true so that count is unchanged.
					return insert(bumpedElement,true);
				}

				// If the element cannot be inserted anywhere (nothing is
				// inserted), return false.
				else {
					return false;
				}
			}
		}


	}

	/**
	 * Function name: locate
	 * 
	 * Locates the index in the table where the function 'insert' and 
	 * 'lookup' are to be performed. Returns a Base object if the objects
	 * are equal to one another (the same) or returns null otherwise.
	 * 
	 * @param element the Base object to be hashed and compared to different
	 * 				  indexes in the table.
	 * @return Base object: an existing Base object at index of table if 
	 * 						the object is the same as the parameter.
	 * 						Null otherwise.
	 * 
	 * Side effects:	index and count fields are changed as a result
	 * 					of searching for objects at different indexes.
	 */
	private Base locate (Base element) {

		// Displays debug locate message if debug mode is on.
		if (debug) {
			System.err.print(HASH + tableCount + DEBUG_LOCATE);
			System.err.println(PROCESSING + element.getName() + "]");
		}

		// Obtains the hash value of the Base object parameter.
		int hashValue = element.hashCode();

		// Displys the hash value if debug mode is on.
		if (debug) {
			System.err.println(HASH_VAL + hashValue + "]");
		}

		// Calculates the increment the index should be increased by
		// for its probe sequence.
		int increment = (hashValue % (size-1))+1;

		// If the count in the probe sequence is 0, calculate the index
		// normally.
		if (count == 0) {
			index = hashValue % size;

		// If the count is not 0, calculate the new index to begin with.
		} else {
			index = (index + increment) % size;
		}
		
		// Iterates over the probe sequence, starting where the count value
		// reached up to. (psIndex = probe sequence index).
		for (int psIndex = count; psIndex < size; psIndex++) {

			// Displays index messages if debug mode is on.
			if (debug) {
				System.err.println(TRYING + index + "]");
			}

			// If the object at the index of hash table is empty, break the 
			// loop. (If an empty space in the table is found).
			if (table[index] == null) {

				// Display debug message for empty spot found if debug on.
				if (debug) {
					System.err.print(HASH + tableCount + FOUND_SPOT);
				}

				break;
			}
			
			// Displays a debug message to show comparison between objects.
			if (debug) {
				System.err.println(HASH + tableCount + COMPARE
								 + element.getName() + AND 
								 + table[index].getName()+ "]");
			}

			// If the object at the table index is equal to the parameter 
			// element, return it.
			if (table[index].equals(element)) {
				return table[index];
			}

			// If the object at the table index is alphabetically less than
			// the paramter object, increment count and break (bullying later).
			if (table[index].isLessThan(element)) {
				count++;
				break;
			}

			// If all other conditions are not met (object at table index is
			// 'bigger' than the parameter object), continue loop at next
			// index using increment and increment count in probe sequnce.
			index = (index + increment) % size;
			count++;
		}
		
		// If looping through entire probe sequence doesn't find a
		// empty/possible bully location, return null
		return null;
	}

	/**
	 * Function name: lookup
	 * 
	 * Looks up the element in the hash table.
	 * 
	 * @param element the Base object to be looked for
	 * @return Base object: reference to the element if found.
	 * 						Null otherwise.
	 * 
	 * Side effects:	index and count fields are changed as a result
	 * 					of searching for objects at different indexes.
	 */
	public Base lookup (Base element) {
		
		// Displays debug messages if debug mode is on.
		if (debug) {
			System.err.print(HASH + tableCount + DEBUG_LOOKUP);
		}

		Base elementFromLocate = locate(element);	// Element that is
													// returned from locate.

		// Reset count field for future lookups.
		count = 0;

		//Returns base object or null if found/not found.
		return elementFromLocate;
	}


	/**
	 * Creates a string representation of the hash table. The method 
	 * traverses the entire table, adding elements one by one ordered
	 * according to their index in the table. 
	 *
	 * @return  String representation of hash table
	 */
	public String toString () {
		String string = "Hash Table " + tableCount + ":\n";
		string += "size is " + size + " elements, "; 
		string += "occupancy is " + occupancy + " elements.\n";

		/* go through all table elements */
		for (int index = 0; index < size; index++) {

			if (table[index] != null) {
				string += "at index " + index + ": ";
				string += "" + table[index];
				string += "\n";
			}
		}

		string += "\n";

		if(debug)
			System.err.println(tracker);

		return string;
	}
}
