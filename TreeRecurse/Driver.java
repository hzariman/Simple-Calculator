/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Tree.java
 * Description:    This program contains the driver file for the Tree
 * 				   to be used alongside the UCSDStudent class. It handles the
 * 				   main input and functionality when handling a tree of
 * 				   UCSDStudents.
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
 * Public functions: 
 * 					 UCSDStudent - class constructor which initialises and
 * 								   allocates memory.
 * 					 jettison - jettisons the UCSDStudent object.
 * 					 copy - creates a new copy of the UCSDStudent.
 *                   getName - returns the name of the UCSDStudent.
 * 					 equals - checks if the object and another object are
 * 							  equal to one another.
 * 					 isLessThan - compares if the name is alphabetically less
 * 								  than another student.
 * 					 toString - returns a string in the desired format to 
 * 								display student.
 */
class UCSDStudent extends Base {

	private String name;
	private long studentNum;
	private Tracker tracker;

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

		// Creates a tracker object for the student.
		tracker = new Tracker ("UCSDStudent " + nm,  
                nm.length ()  
                + Size.of (sn)  
                + Size.of (tracker),  
                " UCSDStudent constructor");  
  
		// Initialises the name and studentnum fields to the input parameters.
        name = nm; 
        studentNum = sn;
	}

	/**
	 * Function name: jettison
	 * 
	 * Stops tracking the memory for this object,
	 * 
	 * @param nothing is passed.
	 * @return nothing is returned.
	 * 
	 * Side effects: The tracker object is jettisoned.
	 * 
	 */
	public void jettison () {

		// Jettisons the tracker object.
		tracker.jettison ();  
        tracker = null;  
	}

	/**
	 * Function name: copy
	 * 
	 * Creates a copy of the UCSDStudent
	 * 
	 * @param nothing is passed.
	 * @return UCSDStudent a copy of the UCSDStudent is returned.
	 * 
	 */
	public Base copy () {

		// Instantiates a new UCSDStudent using the same name and
		// student number.
		return new UCSDStudent (name, studentNum);
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
	 * Function name: equals
	 * 
	 * To determine if this object and another Object are the same.
	 * 
	 * @param object the other object to be compared to.
	 * @return boolean: true if they refer to the same object.
	 * 					false otherwise.
	 * 
	 */
	public boolean equals (Object object) {

		// If this object is the same as the parameter object,
		// then return true.
		if (this == object)  
				return true;  
		// If the object is not an instance of the UCSDStudent class
		// then return false.
		if (!(object instanceof UCSDStudent))  
				return false;  
		
		UCSDStudent otherStudent = (UCSDStudent) object;	//represents the
															//object as a 
															//UCSDStudent.
		
		// Checks if their name fields are equal to each other, returns
		// true or false.												
		return name.equals (otherStudent.getName ());  
	}

	/**
	 * Function name: isLessThan
	 * 
	 * Compares the names of two Base objects and checks if this
	 * object's name is alphabetically smaller than the other object.
	 * 
	 * @param base Base object of the other object to be compared to.
	 * @return boolean: true if this object has a name that is alphabetically
	 * 					smaller than the other object.
	 * 					false otherwise.
	 */
	public boolean isLessThan (Base base) {

		// Returns true if this objects name field is alphabetically smaller
		// than the other object. False otherwise.
		return (name.compareTo (base.getName ()) < 0) ? true : false; 
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
		return "name:  " + name + " with studentnum:  " + studentNum;
	}
}

public class Driver {
private static final short NULL = 0;
private static final int EOF = -1;

public static void main (String [] args) {

	/* initialize debug states */
	Tree.debugOff ();

	/* check command line options */
	for (int index = 0; index < args.length; ++index) {
		if (args[index].equals ("-x"))
		Tree.debugOn ();
	}


	/* The real start of the code */
	SymTab<UCSDStudent> symtab = 
		new SymTab<UCSDStudent> ("Driver");

	String buffer = null;
	int command;
	long number = 0;
	UCSDStudent stu = null;

	System.out.println ("Initial Symbol Table:\n" + symtab);

	while (true) {
		command = NULL; // reset command each time in loop
		System.out.print ("Please enter a command:\n  "
		+ "(c)heck memory, is(e)mpty, "
		+ "(i)nsert, (l)ookup, "
		+ "(r)emove, (w)rite:  ");

		command = MyLib.getchar ();
		if (command == EOF) 
			break;

		MyLib.clrbuf ((char) command); // get rid of return

		switch (command) {
			case 'c':
				Tracker.checkMemoryLeaks ();
				System.out.println ();
				break;

			case 'e': 
				if (symtab.isEmpty ()) 
					System.out.println ("Tree is empty.");
				else
					System.out.println (
						"Tree is not empty.");
				break;

			case 'i':
				System.out.print
				("Please enter UCSD student name to insert:  ");

				buffer = MyLib.getline ();// formatted input

				System.out.print
					("Please enter UCSD student number:  ");

				number = MyLib.decin ();

				// get rid of return
				MyLib.clrbuf ((char) command); 

				// create student and place in symbol table
				stu = new UCSDStudent (buffer, number);

				symtab.insert (stu);
				stu = null;
				break;

			case 'l':  
				UCSDStudent found;      // whether found or not

				System.out.print
				("Please enter UCSD student name to lookup:  ");
				buffer = MyLib.getline ();// formatted input

				stu = new UCSDStudent (buffer, 0);
				found = symtab.lookup (stu);

				if (found != null) {
					System.out.println ("Student found!!!");
					System.out.println (found);

					found.jettison ();
					found = null;
				}
				else
					System.out.println ("student " + buffer
						+ " not there!");

				stu.jettison ();
				stu = null;
				break;

			case 'r':  
				UCSDStudent removed; // data to be removed

				System.out.print
				("Please enter UCSD student name to remove:  ");

				buffer = MyLib.getline ();

				stu = new UCSDStudent (buffer, 0);

				removed = symtab.remove (stu);

				if (removed != null) {
					System.out.println (
						"Student removed!!!"); 
					System.out.println (removed);

					removed.jettison ();
					removed = null;
				}
				else
					System.out.println ("student "
						+ buffer
						+ " not there!");

				stu.jettison ();
				stu = null;
				break;

			case 'w':
				System.out.print ("The Symbol Table " +
					"contains:\n" + symtab);
			}
		}

		System.out.print ("\nFinal Symbol Table:\n" + symtab);
		symtab.jettison ();

		Tracker.checkMemoryLeaks ();
	}
}

