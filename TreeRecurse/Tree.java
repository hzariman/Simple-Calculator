/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Tree.java
 * Description:    This program holds the Binary Tree data structure that will 
 *                 be used for the calculator program. It manipulates a tree
 *                 by consisiting of a root node and TNode children, which can
 *                 the user can then insert, remove or lookup the data from 
 * 				   nodes in the tree. This program implements these concepts
 * 				   via recursion and also rebalances the tree after each
 * 				   method if needed.
 */

 /**
 * Class:            Tree
 * Description:      The main class for creating tree data structure. Contains
 * 					 all relevant methods to manipulate the tree: insert,
 * 					 and lookup. Also holds the TNode class, representing each
 * 					 individual node in the binary tree. Has fields to 
 * 					 represent information about the tree itself.
 *
 * Fields:           root  - the root node of the tree
 *                   occupancy  - how many nodes are in the tree
 *                   treeCount - the number of the current tree (used to ID).
 * 					 representation - how to represent tree as a string.
 * 					 tracker - to track memory
 * 					 treeCounter - how many trees are currently allocated.
 *
 * Public functions: Tree - the class constructor 
 *                   jettison - jettisons all the nodes in the tree and the
 * 								tree itself.
 * 					 debugOff - turns off debug messages
 * 					 debugOn - turns on debug messages
 * 					 isEmpty - checks if the tree is empty.
 * 					 insert - inserts a node into the tree at where it belongs
 * 					 remove - Obtains and removes desired node.
 * 					 lookup - Obtains desired node and its data.
 * 					 toString - Displays the tree as a string.
 */
public class Tree<Whatever extends Base> {

	/* data fields */
	private TNode root;
	private long occupancy; 
	private String representation;
	private long treeCount;
	private Tracker tracker;
	private static long treeCounter;

	/* debug flag */
	private static boolean debug;

	/* debug messages */
	private static final String ALLOCATE = " - Allocating]\n";
	private static final String JETTISON = " - Jettisoning]\n";
	private static final String AND = " and ";
	private static final String CLOSE = "]\n";
	private static final String COMPARE = " - Comparing ";
	private static final String INSERT = " - Inserting ";
	private static final String CHECK = " - Checking ";
	private static final String UPDATE = " - Updating ";
	private static final String REPLACE = " - Replacing ";
	private static final String TREE = "[Tree ";

	private class PointerBox {
		public TNode pointer;

		public PointerBox (TNode pointer) {
			this.pointer = pointer;
		}
	}

	/**
	 * Function name: Tree
	 * 
	 * Constructs a Tree object which holds all the 
	 * inner methods used to manipulate a binary tree data structure.
	 * 
	 * @param name the name of the tree
	 * @param caller: String with a class name to help debug memory
	 * 				  issues.
	 * @return nothing is returned.
	 * 
	 * Side effects: Class fields are initialised.
	 * 
	 */
	public Tree (String caller) {
		tracker = new Tracker ("Tree", Size.of (root)
		+ Size.of (occupancy)
		+ Size.of (representation)
		+ Size.of (treeCount)
		+ Size.of (treeCounter)
		+ Size.of (tracker),
		caller + " calling Tree CTor");

		// DO NOT CHANGE THIS PART ABOVE

		// Initialises class fields to their default values.
		root = null;
		occupancy = 0;
		representation = "";
		treeCount = treeCounter +1;
		treeCounter++;
		
		// Displays debug messages if debug mode is on.
		if (debug) {
			System.err.print(TREE + treeCount + ALLOCATE);
		}

	}

	/**
	 * Function name: jettison
	 * 
	 * Stops tracking the memory for all node objects in binary tree, and the 
	 * Tree tracker object itself.
	 * 
	 * @param nothing is passed.
	 * @return nothing is returned.
	 * 
	 * Side effects: All nodes beggining at the root of the tree are
	 * 				 jettisoned. 
	 * 				 The tracker object of the tree itself is jettisoned.
	 * 
	 */
	public void jettison () {

		// Displays debug messages if debug mode is on.
		if (debug) {
			System.err.print(TREE + treeCount + JETTISON);
		}

		// If the tree is not empty, then first jettison all the nodes
		// starting at the root node.
		if (!isEmpty()) {
			root.jettisonAllTNodes();
		}

		//Jettison the Tree tracker object itself.
		tracker.jettison();

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

		// Sets the debug field to false.
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

		// Sets the debug field to true.
		debug = true;

	}

	/**
	 * Function name: isEmpty
	 * 
	 * Checks if the tree is empty or not.
	 * 
	 * @param nothing is passed in.
	 * @return boolean: true if tree is empty,
	 * 					false otherwise.
	 * 
	 */
	public boolean isEmpty () {

		// If occupancy is 0, return true. Otherwise false.
		return occupancy == 0;
	}

	/**
	 * Function name: insert
	 * 
	 * Inserts the element into the binary tree.
	 * 
	 * @param element the Whatever to be inserted into the tree.
	 * @return boolean: True if insertion is successful (always true).
	 * 
	 * Side effects: root node is potentially changed as a result
	 * 				 of restructuring the tree, if balance is too big
	 * 				 (explained later in setHeightAndBalance).
	 * 
	 */
	public boolean insert (Whatever element) {

		// If there are no nodes in the tree, then create
		// a node of Whatever element and set that to the root.
		if (root == null) {
			root = new TNode(element, "Tree.insert");

		// Otherwise, call the insert function at the root node.
		// Change the root to the new pointer as a result of the node's insert
		} else {

			// Put the root pointer into a box.
			PointerBox rootBox = new PointerBox(root); // Boxed pointer
													   // to be used in insert
			// Calling insert at the root.
			root.insert(element, rootBox);

			// Update the root by the resulted pointer
			root = rootBox.pointer;
		}

		// Returns true when insert is successful
		// (Should always be able to insert)
		return true;

	}

	/**
	 * Function name: lookup
	 * 
	 * Looks up the matching data in the binary tree.
	 * 
	 * @param element to be found.
	 * @return Whatever: Returns a copy of the data found
	 * 					 (to prevent tree corruption).
	 * 
	 */
	public Whatever lookup (Whatever element) {

		// If the tree is empty, then return null.
		if (isEmpty()) {
			return null;
		
		// If the tree is not empty, call TNode's lookup
		// at the root and return the data found as a result
		// of the delegated method's recursion.
		} else {
			return root.lookup(element);
		}
	}

	/**
	 * Function name: remove
	 * 
	 * Removes the matching data from the binary tree by delegating
	 * to TNode's remove.
	 * 
	 * @param element to be found and removed.
	 * @return Whatever: Returns the data if found,
	 * 					null otherwise.
	 * 
	 * Side effects: Removes a node from the tree if found.
	 * 
	 */
	public Whatever remove (Whatever element) {

		// If the tree is empty, then return null.
		if (isEmpty()) {
			return null;

		// If the tree is not empty, call TNode's lookup
		// at the root and return the data found as a result
		// of the delegated method's recursion.
		} else {

			// Put the root pointer into a box.
			PointerBox rootBox = new PointerBox(root); // Boxed pointer
													  // to be used in insert.
			
			// Calling remove at the root and storing the result.
			Whatever result = root.remove(element, rootBox, false); // to store
																	// result

			// Update the root by the resulted pointer
			root = rootBox.pointer;

			// Return the result obtained from TNode's remove
			return result;
		}
	}

	/**
	* Creates a string representation of this tree. This method first
	* adds the general information of this tree, then calls the
	* recursive TNode function to add all nodes to the return string 
	*
	* @return  String representation of this tree 
	*/
	public String toString () {

		representation = "Tree " + treeCount + ":\n"
			+ "occupancy is " + occupancy + " elements.\n";

		if (root != null)
		root.writeAllTNodes();

		return representation;
	}

	/**
	 * Class:            TNode
	 * Description:      Implements the node of the binary search tree data 
	 *                   structure.Each node containts two children, the left 
	 *                   child which contains data "less than" the data of the 
	 *                   current node, and the right child which contains data 
	 *                   "greater than" the data of the current node. Also
	 * 					 holds a reference to its parent node.
	 *
	 * Fields:           data  - holds the data stored in the current node
	 *                   left  - the left child
	 *                   right - the right child
	 * 					 balance - the node's balance value on the tree.
	 * 					 height - the node's height value on the tree.
	 * 					 tracker - to track memory.
	 * 					 THRESHOLD - if the balance is greater than this,
	 * 							   - then the tree must be restructured.
	 *
	 * Public functions: TNode - class constructor. Initialises fields
	 *                         	 and allocates memory.
	 *                   toString - To display the node as a string.
	 */
	private class TNode {                
		private Whatever data;
		private TNode left, right;
		private Tracker tracker;

		/* left child's height - right child's height */
		private long balance;
		/* 1 + height of tallest child, or 0 for leaf */
		private long height;

		private static final long THRESHOLD = 2;

		/**
		 * Function name: TNode
		 * 
		 * Constructs a TNode object which represents a node to be
		 * placed inside a binary tree.
		 * 
		 * @param element Whatever to be stored in the node.
		 * @param caller: String with a class name to help debug memory
		 * 				  issues.
		 * @return nothing is returned.
		 * 
		 * Side effects: Class fields are initialised.
		 * 
		 */
		public TNode (Whatever element, String caller) {
			tracker = new Tracker ("TNode", Size.of (data)
			+ Size.of (left)
			+ Size.of (right)
			+ Size.of (height)
			+ Size.of (balance)
			+ Size.of (tracker),
			caller + " calling Tree CTor");
			// DO NOT CHANGE THIS PART ABOVE

			// Initialises class fields to default values.
			data = element;
			left = null;
			right = null;
			balance = 0;
			height = 0;
			// Increments the tree occupancy by 1.
			occupancy++;

			// Displays debug message if debug flag is true.
			if (debug) {
				System.err.print(TREE + treeCount + INSERT 
					+ data.getName() + CLOSE);
			}
		}

		/**
		 * Function name: jettisonTNodeOnly
		 * 
		 * Stops tracking the memory for this node object ONLY.
		 *  Data stored in the node is unchanged.
		 * 
		 * @param nothing is passed.
		 * @return nothing is returned.
		 * 
		 * Side effects: The tracker itself is jettisoned only.
		 * 
		 */
		private void jettisonTNodeOnly () {

			// Calls tracker method to remove Node from memory
			tracker.jettison();
			// Decrenebts the occupancy in the tree by -1.
			occupancy--;
		}

		/**
		 * Function name: jettisonTNodeAndData
		 * 
		 * Stops tracking the memory for this node object and
		 *  the data stored in the node.
		 * 
		 * @param nothing is passed.
		 * @return nothing is returned.
		 * 
		 * Side effects: The data and tracker itself is jettisoned.
		 * 
		 */
		private void jettisonTNodeAndData () {

			// Calls tracker method to remove Node from memory
			tracker.jettison();

			// Calls data method to remove Base object from memory.
			data.jettison();

			//Decrement the occupancy in the tree by -1.
			occupancy--;
		}

		/**
		 * Function name: jettisonAllTNodes
		 * 
		 * Performs a post-order traversal through the Tree, 
		 * jettisoning each TNode and the data it holds using recursion.
		 * 
		 * @param nothing is passed.
		 * @return nothing is returned.
		 * 
		 * Side effects: Jettisons the data and tracker of all nodes
		 * 				 in post-order traversal.
		 * 
		 */
		private void jettisonAllTNodes () {

			// Base case: If the node no children, then jettison this node
			// and return.
			if (left == null && right == null) {
				jettisonTNodeAndData();
				return;
			}

			// If there is a left child, traverse to the left child node
			// and recurse.
			if (left != null) {
				left.jettisonAllTNodes();
			}

			// If there are no nodes to the left, then jettison this node.
			jettisonTNodeAndData();

			// If there is a right child, traverse to it and recurse.
			if (right != null) {
				right.jettisonAllTNodes();
			}
		}

		/**
		 * Function name: insert
		 * 
		 * Inserts an element into the binary tree recursively.
		 * Returns true or false indicating success of insertion.
		 * 
		 * @param element Whatever element to be inserted into the tree.
		 * @param pointerInParentBox PointerBox object that holds reference
		 * 							 to parent TNode that was used to get to 
		 * 							 the current TNode.
		 * @return boolean: true if insertion is succesful, false otherwise.
		 * 					(should always be able to insert)
		 * 
		 * Side effects: Node parent and children pointers may be changed as 
		 * 				 a result of pointerInParentBox being altered
		 * 				 due to recursion. ANew node is also 
		 * 				 inserted into the tree.
		 * 
		 */
		private boolean insert (Whatever element,
			PointerBox pointerInParentBox) {

			// If debug flag is true, then display debug messages.
			if (debug) {
				System.err.print(TREE + treeCount + COMPARE 
					+ element.getName() + AND + data.getName()
					 + CLOSE);
			}

			// Duplicate insertion case: if element to be inserted
			// is equal to the data stored in the node, then jettison current
			// node and set the data field to the element.
			if (element.equals(data)) {
				data.jettison();
				data = element;

			// If the element is less than the data stored in this node:
			} else if (element.isLessThan(data)) {

				// If there is no left child, then insert the node in the left 
				// child and update the balance and height values of the tree.
				if (left == null) {

					// create a new node, and set the left child of this node
					// to the one just created.
					left = new TNode(element, "Tree.TNode.insert");

					// Update the height/balance values on the tree, and
					// return true to indicate success.
					setHeightAndBalance(pointerInParentBox);
					return true;
				}

				// If there exists a left child: 
				// Put the child pointer into a box
				PointerBox leftBox = new PointerBox (left);  // Boxed pointer
				// Calling insert on the left
				left.insert (element, leftBox);
				// Update your left by the resulted pointer
				left = leftBox.pointer;

			// If the element to be inserted is greater than data stored in
			// this node.
			} else {
				
				// If there is no right child, create a node and set the right
				// child to it. Update the node's value on the tree and
				// return true.
				if (right == null) {
					right = new TNode(element, "Tree.TNode.insert");
					setHeightAndBalance(pointerInParentBox);
					return true;
				}

				// If there exists a right child: 
				// Put the child pointer into a box
				PointerBox rightBox = new PointerBox (right); // Boxed pointer
	
				// Calling insert on the left
				right.insert (element, rightBox);

				// Update your left by the resulted pointer
				right = rightBox.pointer;
			}

			// Update this node's height and balance on the tree.
			setHeightAndBalance(pointerInParentBox);

			// Return true to indicate success.
			return true;
		}

		/**
		 * Function name: lookup
		 * 
		 * Looks up the matching data in the binary tree recursively.
		 * 
		 * @param element to be found.
		 * @return Whatever: Returns the data if found,
		 * 					null otherwise.
		 * 
		 */
		@SuppressWarnings("unchecked")
		private Whatever lookup (Whatever element) {

			// If debug flag is true, then display debug messages.
			if (debug) {
				System.err.print(TREE + treeCount + COMPARE 
					+ element.getName() + AND + data.getName()
					 + CLOSE);
			}

			// Found case: if the element to be found is 
			// equal to this node's data, then return
			// a deep copy of the data.
			if (element.equals(data)) {
				return (Whatever)data.copy();

			// If the element is less than the data stored in this node:
			} else if (element.isLessThan(data)) {

				// If there is no left child, then the element
				// is not in the tree so return null.
				if (left == null) {
					return null;
				}

				// Otherwise, traverse to the left child and recurse.
				return left.lookup(element);

			// // If the element is greater than the data stored in this node:
			} else {
				
				// If there is no right child, then the element
				// is not in the tree so return null.
				if (right == null) {
					return null;
				}

				//Otherwise, traverse to the right child and recurse.
				return right.lookup(element);
			}

		}

		/**
		 * Function name: remove
		 * 
		 * Removes the matching data from the binary tree recursively.
		 * 
		 * @param element to be found and removed.
		 * @param pointerInParentBox PointerBox object that holds reference
		 * 							 to parent TNode that was used to get to 
		 * 							 the current TNode.
		 * @param fromSHB boolean to represent if called from SHB.
		 * @return Whatever: Returns the data if found,
		 * 					null otherwise.
		 * 
		 * Side effects: Removes a node from the tree if found. Changes
		 * 				 pointerInParentBox references as a result of
		 * 				 recursion.
		 * 
		 */
		private Whatever remove (Whatever element, 
		PointerBox pointerInParentBox,
		boolean fromSHB) {

			Whatever result = null;		// the data to be returned
										// from removing the node.
			
			// If debug flag is true, then display debug messages.							
			if (debug) {
				System.err.print(TREE + treeCount + COMPARE 
					+ element.getName() + AND + data.getName()
					 + CLOSE);
			}
			
			// Found case: if the element to be found is 
			// equal to this node's data, then do the following:
			if (element.equals(data)) {

				// If either the left or right child of this node is null
				if (left == null || right == null) {

					// If the left child is null
					if (left == null) {

						// If the right child is null
						if (right == null) {
							// Then set the parent node's reference
							// to this node to null
							pointerInParentBox.pointer = null;
						} else {
							// Otherwise, set the parent's reference to 
							// this node's right child.
							pointerInParentBox.pointer = right;
						}
					
					// If the right child is null
					} else {
						// set the pointerInParentBox's pointer to the
						// left child.
						pointerInParentBox.pointer = left;
					}
				
				// jetison this node, and return the data.
				jettisonTNodeOnly();
				return data;

				// If the node has 2 children,
				} else {

					// set the result to the data in this node.
					result = data;

					// Put the child pointer into a box
					PointerBox rightBox = new PointerBox (right); // Boxed 
																  // pointer

					// Call RARM, starting at the right child of this node.
					right.replaceAndRemoveMin(this, rightBox);

					// Update your right by the resulted pointer
					right = rightBox.pointer;

					
				}

			// If the element is less than the data stored in this node:
			} else if (element.isLessThan(data)) {

				// If there is no left child, then the element
				// is not in the tree so return null.
				if (left == null) {
					return null;
				}
				// Put the child pointer into a box
				PointerBox leftBox = new PointerBox (left); // Boxed pointer
				// Calling remove on the left
				result = left.remove (element, leftBox, false);
				// Update your left by the resulted pointer
				left = leftBox.pointer;

			// If the element is greater than the data stored in this node:
			} else {
				// If there is no right child, then the element
				// is not in the tree so return null.
				if (right == null) {
					return null;
				}

				// Put the child pointer into a box
				PointerBox rightBox = new PointerBox (right); // Boxed pointer
				// Calling insert on the right
				result = right.remove(element, rightBox, false);
				// Update your right by the resulted pointer
				right = rightBox.pointer;

			}

			// If the function is not called from SHB and something
			// has been removed, then call SHB for this node.
			if (!fromSHB && result != null) {
				setHeightAndBalance(pointerInParentBox);
			}
			
			// Return Whatever result obtained from remove.
			return result;
		}

		/**
		 * Function name: replaceAndRemoveMin
		 * 
		 * Called when removing a TNode with 2 children, replaces that TNode 
		 * with the minimum TNode in its right subtree to maintain the Tree 
		 * structure. Is called recursively.
		 * 
		 * @param targetTNode reference to the TNode to remove that 
		 * 					  has 2 children.
		 * @param pointerInParentBox PointerBox object that holds reference
		 * 							 to parent TNode that was used to get to 
		 * 							 the current TNode.
		 * @return none: nothing is returned.
		 * 
		 * Side effects: Removes a node from the tree when there is no
		 * 				 left child. Replaces targetTNode's data and 
		 * 				 changes pointerInParentBox's reference.
		 */
		private void replaceAndRemoveMin (TNode targetTNode,
		PointerBox pointerInParentBox) {

			// If debug flag is true, then display debug messages.
			if (debug) {
				System.err.print(TREE + treeCount + CHECK 
					+ data.getName() + CLOSE);
			}

			// Base case: If there is no more left child, then this node
			// is the successor node.
			if (left == null) {

				// If debug flag is true, then display debug messages.
				if (debug) {
					System.err.print(TREE + treeCount + REPLACE 
						+ data.getName() + CLOSE);
				}

				// Overwrite targetTNode's data to this node's data.
				targetTNode.data = data;

				// Overwrite parent pointer reference to 
				// this node's right child (null/not null)
				pointerInParentBox.pointer = right;

				// Jetison this node.
				jettisonTNodeOnly();
				// Return to stop the recursion.
				return;
			}

			
			// Put the child pointer into a box
			PointerBox leftBox = new PointerBox(left); // Boxed pointer

			// Call RARM with this node's left child.
			left.replaceAndRemoveMin(targetTNode, leftBox);
			// Update your left by the resulted pointer
			left = leftBox.pointer;

			// Update this node's height and balance on the tree.
			setHeightAndBalance(pointerInParentBox);


		}

		/**
		 * Function name: setHeightAndBalance
		 * 
		 * Updates the height and balance of the current TNode, and checks if
		 * the balance of the TNode exceeds the threshold. If it does, 
		 * balances the tree using remove and insert.
		 * 
		 * @param pointerInParentBox PointerBox object that holds reference
		 * 							 to parent TNode that was used to get to 
		 * 							 the current TNode.
		 * 
		 * @return none: nothing is returned.
		 * 
		 * Side effects: the node's height and balance is updated.
		 * 				 The node's position on the tree may change as a result
		 * 				 of the tree restructuring. 
		 */
		private void setHeightAndBalance
			(PointerBox pointerInParentBox) {

				// If debug flag is true, then display debug messages.
				if (debug) {
					System.err.print(TREE + treeCount + UPDATE 
						+ data.getName() + CLOSE);
				}

				// Creating two local variables to represent children height,
				// assuming each child doesn't exist.
				long leftHeight = -1; 	// the left child's default height
				long rightHeight = -1;	// the right child's default height

				// If there is a left child, set the left height to it's height
				if (left != null) {
					leftHeight = left.height;
				}

				// If there is a right child, set the right height to the
				// right child's height.
				if (right != null) {
					rightHeight = right.height;
				}

				// Calculate the balance by the following formula.
				balance = leftHeight - rightHeight;

				// Calculate the node's height: the largest child's height + 1.
				if (leftHeight > rightHeight) {
					height = leftHeight + 1;
				} else {
					height = rightHeight + 1;
				}

				// If the absolute value of the balance is greater than 
				// the threshold
				if (Math.abs(balance) > THRESHOLD) {

					// Remove this node, calling remove from SHB hence 
					// fromSHB is true, storing the result.
					Whatever nodeData = remove(data, pointerInParentBox,true);

					//Reinsert the node with the data just removed.
					pointerInParentBox.pointer.insert(nodeData, 
														pointerInParentBox);

				}


		}

		/**
		* Creates a string representation of this node. Information
		* to be printed includes this node's height, its balance,
		* and the data its storing.
		*
		* @return  String representation of this node 
		*/

		public String toString () {

			return "at height:  " + height + " with balance:  "
				+ balance + "  " + data + "\n";
		}

		/**
		* Writes all TNodes to the String representation field. 
		* This recursive method performs an in-order
		* traversal of the entire tree to print all nodes in
		* sorted order, as determined by the keys stored in each
		* node. To print itself, the current node will append to
		* tree's String field.
		*/
		private void writeAllTNodes () {
			if (left != null)
				left.writeAllTNodes ();

			representation += this;

			if (right != null)
				right.writeAllTNodes ();
		}
	}
}

