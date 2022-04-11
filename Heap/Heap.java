/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Heap.java
 * Description:    This program holds holds the array-based Heap data
 * 				   structure. It is a polymorphic generic container 
 * 				   that behaves in the same way as a Heap.
 */

/**
 * Class:            Heap
 * Description:      The main class for creating heap data structure. Contains
 * 					 all relevant methods to manipulate the heap: insert
 * 					 and remove. Has fields to represent information about
 * 					 the heap itself.
 *
 * Fields:           tracker  - to track memory
 *                   heap  - The array of Whatever elements in the heap
 *                   size - the size of the heap array
 * 					 occupancy - the number of elements currently in the heap.
 * 					 tracker - to track memory
 * 					 debug - determines whether to display debug messages.
 *
 * Public functions: Heap - the class constructor 
 *                   jettisonHeap - jettisons all the elements in the heap
 * 									and the heap itself.
 * 					 debugOff - turns off debug messages
 * 					 debugOn - turns on debug messages
 * 					 getDebug - returns the state of the debug field
 * 					 isFull - Checks if the heap is full
 * 					 isEmpty - checks if the heap is empty.
 * 					 insert - inserts an element to heap.
 * 					 remove - removes an element from the heap.
 * 					 toString - Displays the tree as a string.
 */
public class Heap <Whatever extends Base> {
	// data fields
	private Tracker tracker;	// to track memory
    private Whatever[] heap;	// array to store Whatever elements
    private int size;			// the size of the array allowed
	private int occupancy;		// the number of elements in the heap
	private int rootIndex;		// the index of the root element (0)

	// debug flag
	private static boolean debug;	// determines whether to display debug messages.
	// debug messages
	private static final String WITH = " with ";
	private static final String CLOSE = "]\n";
	private static final String INSERT = "[Inserting ";
	private static final String REMOVE = "[Removing ";
	private static final String REHEAP_UP = "[Reheaping up at child index: ";
	private static final String REHEAP_DOWN = "[Reheaping down]\n";
	private static final String COMPARE = "[Comparing parent ";
	private static final String SWAP = "[Swapping ";
	private static final String JETTISON = "[Jettisoning Heap]";

	/**
	 * Function name: Heap
	 * 
	 * Constructs a Heap object which holds all the 
	 * inner methods used to manipulate a Heap structure.
	 * 
	 * @param heapSize the maximum size of the heap array allowed.
	 * @param caller: String with a class name to help debug memory
	 * 				  issues.
	 * @return nothing is returned.
	 * 
	 * Side effects: Class fields are initialised.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Heap (int heapSize, String caller) {
		tracker = new Tracker ("Heap", Size.of (0) 
			+ Size.of (size) 
			+ Size.of (heap) 
			+ Size.of (tracker),
			caller + " calling Heap Ctor");
		// --------- DO NOT CHANGE ABOVE ---------
		
		// Initialises class fields to their default values.
		size = heapSize;
        heap = (Whatever[])new Base[size];
		occupancy = 0;
		rootIndex = 0;
		debug = false;
		
	}

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
	 * Function name: getDebug
	 * 
	 * Obtains the debug state
	 * 
	 * @param nothing is passed
	 * @return boolean: true if debug is on, false otherwise
	 * 
	 * Side effects: debug state is returned.
	 * 
	 */
	public boolean getDebug () {
		
		// returns the debug state
		return debug;
	}

	/**
	 * Function name: isFull
	 * 
	 * Checks if the heap is full.
	 * 
	 * @param nothing is passed.
	 * @return boolean: true if heap is full, false otherwise.
	 * 
	 * 
	 */
	public boolean isFull() {
		// True if the number of elements = max size, false otherwise
		return occupancy == size;
	}

	/**
	 * Function name: isEmpty
	 * 
	 * Checks if the heap is empty.
	 * 
	 * @param nothing is passed.
	 * @return boolean: true if heap is empty, false otherwise.
	 * 
	 * 
	 */
	public boolean isEmpty() {
		// if occupancy is 0, there are no elements.
		return occupancy ==0;
	}


	/**
	 * Function name: getParent
	 * 
	 * Returns the parent element.
	 * 
	 * @param currentIndex The index of the current element
	 * @return Whatever : parent of the current element.
	 * 
	 */
	private Whatever getParent(int currentIndex) {
		return heap[(currentIndex-1) / 2];
	}

	/**
	 * Function name: getLeft
	 * 
	 * Returns the left element.
	 * 
	 * @param currentIndex The index of the current element
	 * @return Whatever : left of the current element.
	 * 
	 */
	private Whatever getLeft(int currentIndex) {
		return heap[(currentIndex*2) + 1];
	}

	/**
	 * Function name: getRight
	 * 
	 * Returns the right element.
	 * 
	 * @param currentIndex The index of the current element
	 * @return Whatever : right of the current element.
	 * 
	 */
	private Whatever getRight(int currentIndex) {
		return heap[(currentIndex*2) + 2];
	}

	/**
	 * Function name: getRight
	 * 
	 * Returns the right element.
	 * 
	 * @param currentIndex The index of the current element
	 * @return Whatever : right of the current element.
	 * 
	 */
	private Whatever getElement(int currentIndex) {
		return heap[currentIndex];
	}

	/**
	 * Function name: getParentIndex
	 * 
	 * Returns the parent index at the current index
	 * 
	 * @param currentIndex The index of the current element
	 * @return int : parent index of the current index.
	 * 
	 */
	private int getParentIndex(int currentIndex) {
		return (currentIndex-1) / 2;
	}

	/**
	 * Function name: getLeftIndex
	 * 
	 * Returns the left index at the current index
	 * 
	 * @param currentIndex The index of the current element
	 * @return int : left index of the current index.
	 * 
	 */
	private int getLeftIndex(int currentIndex) {
		return (currentIndex*2) + 1;
	}

	/**
	 * Function name: getRightIndex
	 * 
	 * Returns the Right index at the current index
	 * 
	 * @param currentIndex The index of the current element
	 * @return int : right index of the current index.
	 * 
	 */
	private int getRightIndex(int currentIndex) {
		return (currentIndex*2) + 2;
	}

	/**
	 * Function name: assignToHeap
	 * 
	 * Assigns an element to the heap at a certain index.
	 * 
	 * @param index The index of the current element
	 * @param elememt : The element to be assigned to the heap
	 * @return nothing is returned
	 * 
	 * Side effects: Element at the index is jettisoned (if it exists)
	 */
	private void assignToHeap(int index, Whatever element) {

		// If there is already an element at the index, jettison it
		if (heap[index]!= null) {
			heap[index].jettison();
		}

		// Assign the element to the heap at the index.
		heap[index] = element;
	}


	/**
	 * Function name: insert
	 * 
	 * Inserts an element to where it belongs on the heap.
	 * 
	 * @param element : The element to be inserted to the heap
	 * @return boolean: True if succesful insertion, false otherwise.
	 * 
	 * Side effects: Heap may be restructured as a result of reheapUp.
	 */
    public boolean insert(Whatever element) {

		// If the heap is full, nothing can be inserted so 
		// return false (Note: Driver already makes it impossible for
		// element to be added when heap is full.)
		if (isFull()) {
			return false;

		// Heap is not empty, so do the following:
		} else {

			// Display debug message if debug is true.
			if (debug) {
				System.err.print(INSERT+ element.getName() + CLOSE);
			}

			// The index to perfrom the insertion is at the final leaf,
			// which is at the next available index on heap array (occupancy).
			int indexToInsert = occupancy;

			// Assign the element to the heap at this index.
			assignToHeap(indexToInsert, element);
			
			// Swaps elements until heap structure property is restored.
			// (i.e parent is less than the child).
			reheapUp(indexToInsert);
			
			// Increment the occupancy and return true to represent
			// successful insertion.
			occupancy++;
			return true;
		}


    }

	
	/**
	 * Function name: remove
	 * 
	 * Removes the root element from the heap.
	 * 
	 * @param Nothing is passed in.
	 * @return Whatever: the element removed (root element).
	 * 
	 * Side effects: Final leaf becomes the root, and heap is further
	 * 				 restructured to maintain heap property.
	 */
    public Whatever remove() {

		// If the heap is empty, nothing can be removed so 
		// return null (Note: Driver already makes it impossible for
		// element to be removed when heap is empty.)
        if (isEmpty()) {
			return null;

		// If the heap is not empty:
		} else {

			// Display debug messages if debug mode is on.
			if (debug) {
				System.err.print(REMOVE+ heap[rootIndex].getName() + CLOSE);
			}

			// The current final leaf is at the occupancy-1 index.
			int finalLeafIndex = occupancy -1;

			Whatever elementRemoved = getElement(rootIndex);	// element at
																// root node.

			Whatever finalLeaf = getElement(finalLeafIndex);	// element at
																// final leaf.
			// Assign whatever element at the final leaf to the root node
			assignToHeap(rootIndex, finalLeaf);

			// Set the element at the final leaf index to null
			assignToHeap(finalLeafIndex, null);

			// Swaps elements until heap structure property is restored.
			// (i.e parent is less than the child).
			reheapDown(rootIndex);

			// Decrement occupancy by 1 and return the element at the root.
			occupancy--;
			return elementRemoved;
		}
    }

	/**
	 * Function name: reheapUp
	 * 
	 * Called from Insert. Moves the element at the final leaf
	 * up the heap until it satisfies the heap property, swapping
	 * places with its parent.
	 * Called recursively.
	 * 
	 * @param currentIndex: the index of the current element.
	 * @return Nothing is returned.
	 * 
	 * Side effects: Heap elements are swapped until heap property
	 * 				 is satisfied.
	 */
	private void reheapUp(int currentIndex) {
		
		// Base case: if the index is at the root, 
		// then return.
		if (currentIndex == rootIndex) {
			return;

		// If the element at the current index is not at the root.
		} else {

			// Display debug messages if debug on
			if (debug) {
				System.err.print(REHEAP_UP+ currentIndex + CLOSE);
			}

			int parentIndex = getParentIndex(currentIndex); //get parent index

			Whatever parent = getParent(currentIndex);  // parent of element
														// at this index

			Whatever current = getElement(currentIndex);  // element at 
														  // current index.

			// Display debug message if debug on.
			if (debug) {
				System.err.print(COMPARE+ parent.getName() + WITH 
								+ current.getName() + CLOSE);
			}

			// if the current element is more important than the parent,
			// switch the parent and current element's positions
			// on the heap
			if (current.isLessThan(parent)) {
				swapElement(currentIndex, parentIndex);

				// Recurse, using the parent index.
				reheapUp(parentIndex);

			// If the parent is more important than the current element,
			// do nothing and return.
			} else {
				return;
			}
		}
	}

	/**
	 * Function name: reheapDown
	 * 
	 * Called from Remove. Moves the element at root down the
	 * heap until it satisfies the heap property, swapping
	 * places with its child.
	 * Called recursively.
	 * 
	 * @param currentIndex: the index of the current element.
	 * @return Nothing is returned.
	 * 
	 * Side effects: Heap elements are swapped until heap property
	 * 				 is satisfied.
	 */
	private void reheapDown(int currentIndex) {

		// Base case, if the current index reaches the occupancy,
		// then there are no more elements in the heap and therefore return.
		if (currentIndex == occupancy) {
			return;

		// If the current index has an element != null
		} else {

			// Display debug message if debug on.
			if (debug) {
				System.err.print(REHEAP_DOWN);
			}

			// Find the smallest child's index at the current index.
			// Store in local variable below. (-1 if there is none)
			int smallestChildIndex = findSmallestChildIndex(currentIndex);

			Whatever smallestChild; 	// to store the smallest child.
			Whatever current = getElement(currentIndex);  // the element at
														  // current index.

			// if FSCI (abreviation) returns a valid index, then 
			// obtain the element at the index and store it.
			if (smallestChildIndex != -1) {
				smallestChild = getElement(smallestChildIndex);
			
			// If there is no smallest child (both left and right child indexes)
			// have no element stored.
			} else {
				smallestChild = null;
			} 

			// If either smallest child or the current element has no element
			// stored at their respective indexes, return.
			if (smallestChild == null || current == null) {
				return;
			}

			// Display debug message if debug mode on.
			if (debug) {
				System.err.print(COMPARE+ current.getName() + WITH + smallestChild.getName() + CLOSE);
			}

			// If the smallest child is more important than the current
			// element
			if (smallestChild.isLessThan(current)) {
				// Switch the elements on the heap.
				swapElement(smallestChildIndex, currentIndex);

				// Recurse, using the smallestChildIndex calculated earlier.
				reheapDown(smallestChildIndex);

			}
		}
	}


	/**
	 * Function name: findSmallestChildIndex
	 * Abbreviation : FSCI
	 * 
	 * Called from reheapDown. Finds the index
	 * of the smallest child and returns it.
	 * 
	 * @param currentIndex: the index of the current element.
	 * @return int: the index of the smallest child.
	 * 				Returns -1 if there is none.
	 * 
	 */
	private int findSmallestChildIndex(int currentIndex) {
		
		int leftChildIndex = getLeftIndex(currentIndex);	// index of left
		int rightChildIndex = getRightIndex(currentIndex);	// index of right

		Whatever leftChild = null;		// element at left child
		Whatever rightChild = null;		// element of right child

		// Check if index does not exceed the size of the array
		// (to prevent null pointer exception)
		if (leftChildIndex < size) {

			// If left index is valid, then get the left element
			// and store it.
			leftChild = getLeft(currentIndex);
		}

		// Check if index does not exceed the size of the array
		// (to prevent null pointer exception)
		if (rightChildIndex < size) {

			// If right index is valid, then get the right element
			// and store it.
			rightChild = getRight(currentIndex);
		}
			
		// If both children are not null:
		if (leftChild!= null && rightChild!= null) {
			
			// Return the index of the smaller child.
			if (leftChild.isLessThan(rightChild)) {
				return leftChildIndex;
			} else {
				return rightChildIndex;
			}

		// If either children are null:
		// If both are null, there is no smallest child, return -1.
		// Otherwise, if one is null, return the index of the other.
		} else {
			if (leftChild == null) {
				if (rightChild == null) {
					return -1;
				}
				return rightChildIndex;
			}
			return leftChildIndex;
		}
	}


	/**
	 * Function name: swapElement
	 * 
	 * Swaps any two elements on the heap.
	 * 
	 * @param first: the index of the first element.
	 * @param second: the index of the second element.
	 * @return Nothing is returned.
	 * 
	 */
	private void swapElement(int firstIndex, int secondIndex) {
		Whatever firstElement = getElement(firstIndex);  	// first element
		Whatever secondElement = getElement(secondIndex);	// Second element
		
		// Display debug message if debug mode on.
		if (debug) {
			System.err.print(SWAP+ secondElement.getName() + WITH + firstElement.getName() + CLOSE);
		}

		// Reassigns elements, essentially 'swapping' them around.
		heap[secondIndex] = firstElement;
		heap[firstIndex] = secondElement;
	}

	/**
	 * Function name: jettisonHeap
	 * 
	 * Jettisons all memeory associated to the heap and its elements
	 * and the tracker object itself.
	 * 
	 * @param Nothing is passed in.
	 * @return Nothing is returned.
	 * 
	 */
	public void jettisonHeap() {

		// Display debug message if debug mode on.
		if (debug) {
			System.err.print(JETTISON);
		}

        // Iterates over all elements in the heap array
		for (int heapElement = 0; heapElement < size; heapElement++) {
			// If the element at the index is empty, continue the loop
			if (heap[heapElement] == null) {
				continue;
			}
			// If the element is not null, jettison it and decrement
			// occupany by 1.
			heap[heapElement].jettison();
			occupancy--;
		}
		// After all elements in array are jettisoned, jettison the tracker.
		tracker.jettison();
    }

	/**
	 * Function name: toString
	 * 
	 * Create a string representation of this heap.
	 * 
	 * @param Nothing is passed in.
	 * @return Nothing is returned.
	 * 
	 */
	public String toString () {
		String string = "The Heap has " + occupancy + " items:\n";
		/* go through all heap elements */
		for (int index = 0; index < size; index++) {
			if (heap[index] != null) {
				string += "At index " + index + ":  ";
				string += "" + heap[index];
				string += ".\n";
			}
		}

		return string;
	}
	
}
