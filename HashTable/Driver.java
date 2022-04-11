/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Driver.java
 * Description:    This program contains the driver file for the Hash Table
 * 				   to be used alongside the delcared UCSDStudent class. 
 */
import java.io.*;

/**
 * Class:            UCSDStudent
 * Description:      This class extends the Base class to represent a UCSD
 * 					 student by using their name and student number as fields.
 * 					 It also has similar function that are tailored to 
 * 					 a UCSD student, such as a toString method that returns
 * 					 a string suitable to a student.
 *
 * Fields:           name  - the name of the student
 *                   studentnum  - the student number
 *                   tracker - to track memory
 *
 * Public functions: jettison - jettisons the UCSDStudent object.
 *                   equals - checks if the object and another object are
 * 							  equal to one another.
 *                   getName - returns the name of the UCSDStudent.
 * 					 hashCode - Calculates the hash value of the student
 * 								using their name.
 * 					 isLessThan - compares if the name is alphabetically less
 * 								  than another student.
 * 					 toString - returns a string in the desired format to 
 * 								display student.
 */
class UCSDStudent extends Base {
	private String name;	// the name of the student
	private long studentNum;	// the student number
	private Tracker tracker;	// to track memory

	/**
	 * Function name: UCSDStudent
	 * 
	 * Constructs a HashTable object which holds all the 
	 * inner methods used to manipulate a Hash Table.
	 * 
	 * @param nm a string of the student name
	 * @param sn: a long of the student number
	 * @return nothing is returned.
	 * 
	 * Side effects: Class fields are initialised.
	 * 
	 */
	public UCSDStudent (String nm, long sn) {

		// Initialises the name and studentnum fields to the input parameters.
		this.name = nm;
		this.studentNum = sn;
		
		// DO NOT CHANGE THIS PART
		tracker = new Tracker ("UCSDStudent", 
				Size.of (name)
				+ Size.of (studentNum)
				+ Size.of (tracker),
				" calling UCSDStudent Ctor");

	}

	/**
	 * Function name: jettison
	 * 
	 * Stops tracking the memory for this object,
	 * 
	 * @param nothing is passed.
	 * @return nothing is returned.
	 * 
	 * Side effects: The tracker object is jettisoned, and the field is
	 *  			 set to null.
	 * 
	 */
	public void jettison () {

		// Jettisons the Tracker object and sets it to null.
		tracker.jettison ();
		tracker = null;
	}

	/**
	 * Function name: equals
	 * 
	 * To determine if this object and another Object are the same.
	 * 
	 * @param Object the other object to be compared to.
	 * @return boolean: true if they refer to the same object.
	 * 					false otherwise.
	 * 
	 */
	public boolean equals (Object object) {
		
		// If this object is the same as the parameter object,
		// then return true.
		if (this == object) {
			return true;
		}

		// If the object is not an instance of the UCSDStudent class
		// then return false.
		if (!(object instanceof UCSDStudent)) {
			return false;
		}

		UCSDStudent otherStudent = (UCSDStudent) object;	// represent the
															// object as a 
															// UCSD student.

		// Checks if their name fields are equal to each other, returns
		// true or false.
		return name.equals(otherStudent.getName());
	}

	/**
	 * Function name: getName
	 * 
	 * Getter method to obtain the name field of a UCSDStudent.
	 * 
	 * @param nothing is passed.
	 * @return String name field is returned.
	 * 
	 */
	public String getName () {

		// Returns the name field of the student.
		return name;
	}

	/**
	 * Function name: hashCode
	 * 
	 * Calculates the hash code of the student and returns
	 * its value.
	 * 
	 * @param nothing is passed.
	 * @return int: the hash code value
	 * 
	 * 
	 */
	public int hashCode () {
		
		int retval = 0;  // The cumulative value of the hash code
		int index = 0;	// the index of the character of the name

		// Iterates over each character to the name, obtains each ASCII
		// character value and adds them cumulatively to retval.
		while (index != name.length ()) {  
				retval += name.charAt (index);  
				index ++;  
		}  

		// Returns the sum of all the ASCII values added together.
		return retval;  
	}

	/**
	 * Function name: isLessThan
	 * 
	 * Compares the names of two Base objects and checks if this
	 * object's name is alphabetically smaller than the other object.
	 * 
	 * @param bbb Base object of the other object to be compared to.
	 * @return boolean: true if this object has a name that is alphabetically
	 * 					smaller than the other object.
	 * 					false otherwise.
	 */
	public boolean isLessThan (Base bbb) {
		
		// Returns true if this objects name field is alphabetically smaller
		// than the other object. False otherwise.
		return (name.compareTo (bbb.getName ()) < 0) ? true : false;
	}

	/**
	 * Function name: toString
	 * 
	 * Returns the string representation of a UCSDStudent
	 * 
	 * @param nothing is passed.
	 * @return string: formatted to make it easier for user to read
	 * 				   and understand that the object is a UCSDStudent.
	 * 
	 */
	public String toString () {
		
		// Returns a string in the ideal format to be displayed in 
		// other functions.
		return "name:  " + name + " with studentNum:  " + studentNum;
	}
}

public class Driver {
	private static final int EOF = -1;
	private static final int HASH_TABLE_SIZE = 5;

	public static void main (String [] args) {

		/* initialize debug states */
		HashTable.debugOff();

		/* check command line options */
		for (int index = 0; index < args.length; ++index) {
			if (args[index].equals("-x"))
				HashTable.debugOn();
		}

		/* The real start of the code */
		SymTab symtab = new SymTab (HASH_TABLE_SIZE, "Driver");
		String buffer = null;
		int command;
		long number = 0;

		System.out.print ("Initial Symbol Table:\n" + symtab);

		while (true) {
			command = 0;    // reset command each time in loop
			System.out.print ("Please enter a command:\n"
					 + "(c)heck memory, "
					 + "(i)nsert, (l)ookup, "
					 + "(o)ccupancy, (w)rite:  ");

			command = MyLib.getchar ();
			if (command == EOF) 
				break;
			MyLib.clrbuf ((char) command); // get rid of return

			switch (command) {			

			case 'c':	// check memory leaks
				Tracker.checkMemoryLeaks ();
				System.out.println ();
				break;

			case 'i':
				System.out.print (
				"Please enter UCSD Student name to insert:  ");
				buffer = MyLib.getline ();// formatted input

				System.out.print (
					"Please enter UCSD Student number:  ");

				number = MyLib.decin ();

				// remove extra char if there is any
				MyLib.clrbuf ((char) command);

				// create Student and place in symbol table
				if(!symtab.insert (
					new UCSDStudent (buffer, number))) {

					System.out.println ("Couldn't insert " 
							    + "student!!!"); 
				}
				break;

			case 'l':
				Base found;     // whether found or not

				System.out.print (
				"Please enter UCSD Student name to lookup:  ");

				buffer = MyLib.getline ();// formatted input

				UCSDStudent stu = new UCSDStudent (buffer, 0);
				found = symtab.lookup (stu);

				if (found != null) {
					System.out.println ("Student found!!!");
					System.out.println (found);
				}
				else
					System.out.println ("Student " 
						+ buffer
						+ " not there!");

				stu.jettison ();
				break;

			case 'o':	// occupancy
				System.out.println ("The occupancy of"
						    + " the hash table is "
						    + symtab.getOccupancy ());
				break;

			case 'w':
				System.out.print (
				"The Symbol Table contains:\n" + symtab);
			}
		}

		/* DON'T CHANGE THE CODE BELOW THIS LINE */
		System.out.print ("\nFinal Symbol Table:\n" + symtab);

		symtab.jettison ();
		Tracker.checkMemoryLeaks ();
	}
}
