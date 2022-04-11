/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Driver.java
 * Description:    This program contains the driver file for the Heap
 * 				   to be used alongside the UCSDStudent class. It handles the
 * 				   main input and functionality when handling a Heap of
 * 				   UCSDStudents.
 */

import java.io.*;
import java.util.Scanner;

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
 *                   getName - returns the name of the UCSDStudent.
 * 					 equals - checks if the object and another object are
 * 							  equal to one another.
 * 					 isLessThan - compares if the name is alphabetically less
 * 								  than another student.
 * 					 toString - returns a string in the desired format to 
 * 								display student.
 */
class UCSDStudent extends Base {

	public String name;
	public long studentnum;
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
	public UCSDStudent (String nm, long sn, String caller) {  

		// Initialises the name and studentnum fields to the input parameters.
		name = nm;  
		studentnum = sn;  

		// Creates a tracker object for the student.
		tracker = new Tracker("UCSDStudent",   
				Size.of (name) + Size.of (studentnum) + Size.of (tracker),  
				caller + " calling UCSDStudent ctor");  
		
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
	public void jettison() {  
		
		// Jettisons the tracker object.
		tracker.jettison();  
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

		// Returns the string representation of the student.
		return "name:  " + name + " with studentNum:  " + studentnum;
	}
}

/**
 * Class:            Driver
 * Description:      This class holds the main method that will be used to
 * 					 create and manipulate a heap of UCSDStudents.
 *
 * Fields:           NULL  - the short representation of null
 *
 * Public functions: 
 * 					 main - the main method used to create and manipulate
 * 							the heap data structure from Heap.java.

 */
public class Driver {
	private static final short NULL = 0;

	/**
	 * Function name: Main
	 * 
	 * The main method of the Driver. Creates and manipulates
	 * the heap obtained from Heap.java.
	 * 
	 * @param args the input arguments used when first executing Driver
	 * @return Nothing is returned
	 * 
	 * Note: No arguments are used as debug is handled 
	 * 		 in case d of the main code.
	 * 
	 */
	public static void main (String [] args) {

	// The real start of the code
		String buffer = null;
		char command;		// The command to be used for cases.
		long number = 0;	// the long to be used when obtaining a number.
		int heapSize;		// The maximum size of the heap.

		System.out.print("\nPlease enter the number of"
		+ " objects to be able to store:  ");
		
		// Obtain the size of the intended heap from the user.
		try {
			heapSize = (int)MyLib.decin();

			// get rid of extra characters
			MyLib.clrbuf ((char) 0);

		// Catch EOFException and stop the program if so.
		} catch (EOFException eof) {
			return;
		}
		
		// Create a heap of UCSDStudents.
		Heap<UCSDStudent> mainHeap = new Heap<UCSDStudent>(heapSize, "main");

		// Display the newly-created heap.
		System.out.print ("Initial Heap:\n" + mainHeap);

		// Loop until EOF is reached.
		while (true) {
			command = NULL; // reset command each time in loop

			// String messages to print when certain conditions are met.
			String DEBUG = ", (d)ebug on ";
			String REMOVE = "";
			String INSERT = "(i)nsert";

			// If the debug field is true, then change the debug
			// message to be 'debug off' prompted to the user.
			if (mainHeap.getDebug()) {
				 DEBUG = ", (d)ebug off ";
			}

			// If the heap is not empty, then display the remove
			// option in the menu options.
			if (!mainHeap.isEmpty()) {
				REMOVE = ", (r)emove,";
		   }

		   // If the heap is full, then dont show the Insert prompt.
		   if (mainHeap.isFull()) {
			   INSERT = "";
		   }

		   // Display the commands that the user can enter depending 
		   // on the conditions of the heap mentioned above.
			System.out.print ("Please enter a command:" 
			+ "  (c)heck memory" + DEBUG + INSERT + REMOVE + " (w)rite:  ");
		
			// Obtain the command inputted by the user. 
			try {
				command = MyLib.getchar ();
				MyLib.clrbuf (command); // get rid of return

				switch (command) {

				// If case c then display memory information.
				case 'c':
					Tracker.checkMemoryLeaks();
					System.out.println();

					break;

				// If case D then change the debug field of the mainHeap.
				case 'd':
					if (mainHeap.getDebug()) {
						mainHeap.debugOff();
					} else {
						mainHeap.debugOn();
					}

					break;
				
				// If case is i:
				case 'i':

					// If the heap is full, then nothing can be inserted
					// so break this case.
					if (mainHeap.isFull()) {
						break;
					}

					// If the heap is not empty, prompt the user with an
					// input.
					System.out.print ("Please enter UCSD student"
					+ " name to insert:  ");

					buffer = MyLib.getline (); // formatted input

					System.out.print ("Please enter UCSD student"
					+ " number:  ");

					number = MyLib.decin();
					// get rid of return
					MyLib.clrbuf ((char) command); 

					// create student and place in heap.
					if(!mainHeap.insert (
						new UCSDStudent (buffer, number, "main"))) {
						System.out.println ("Couldn't insert " 
									+ "student!!!"); 
					}
					break;

				// If case is r:
				case 'r':

					// If the heap is empty, then nothing can be removed
					// so break this case.
					if (mainHeap.isEmpty()) {
						break;
					}

					UCSDStudent removed; // data to be removed
					// Store the removed result from the mainHeap method.
					removed = mainHeap.remove ();

					// Displays the removed UCSDStudent if remove was 
					// successful.
					if (removed != null) {
						System.out.println ("Student removed!"); 
						System.out.println (removed);
					}
					
					break;

				// If case is w, then display the mainHeap.
				case 'w':
					System.out.print (mainHeap);
				}

			}
			
			// Catch EOFException
			catch (EOFException eof) {
				break;
			}
		}

		// Display the final heap, jettison it and display any memory leaks.
		System.out.print ("\nFinal Heap:\n" + mainHeap);
		mainHeap.jettisonHeap ();
		Tracker.checkMemoryLeaks ();
	}
}
