/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Tree.java
 * Description:    This program holds the Binary Tree data structure that will 
 *                 be used for the calculator program. It manipulates a tree
 *                 by consisiting of a root node and node children, which can
 *                 the user can then insert, remove or lookup the data from 
 * 				   nodes in the tree.
 */

import org.w3c.dom.NameList;

/**
 * Class:            Tree
 * Description:      The main class for creating tree data structure. Contains
 * 					 all relevant methods to manipulate the tree: insert,
 * 					 and lookup. Has fields to represent information about
 * 					 the tree itself.
 *
 * Fields:           root  - the root node of the tree
 *                   occupancy  - how many nodes are in the tree
 *                   treeName - the tree name
 * 					 representation - how to represent the nodes in the tree
 * 					 tracker - to track memory
 *
 * Public functions: Tree - the class constructor 
 *                   jettison - jettisons all the nodes in the tree and the
 * 								tree itself.
 *                   jettisonAllNodes - jettisons all the nodes in the tree.
 * 					 debugOff - turns off debug messages
 * 					 debugOn - turns on debug messages
 * 					 insert - inserts a node into the tree at where it belongs
 * 					 isEmpty - checks if there are any nodes in the tree.
 * 					 remove - Obtains and removes desired node.
 * 					 lookup - Obtains desired node and its data.
 * 					 toString - Displays the tree as a string.
 */
public class Tree<Whatever extends Base> {

	// data fields
	private TNode root;
	private long occupancy; 
	private String treeName;
	private String representation;
	private Tracker tracker;

	// debug flag
	private static boolean debug;

	// debug messages
	private static final String ALLOCATE = " - Allocating]\n";
	private static final String AND = " and ";
	private static final String CLOSE = "]\n";
	private static final String COMPARE = " - Comparing ";
	private static final String INSERT = " - Inserting ";
	private static final String TREE = "[Tree ";

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
	public Tree (String name, String caller) {

		tracker = new Tracker ("Tree", Size.of (root) 
			+ Size.of (occupancy) 
			+ Size.of (treeName) 
			+ Size.of (representation) 
			+ Size.of (tracker),
			caller + " calling Tree Ctor");
		// --------- DO NOT CHANGE ABOVE ---------
		
		// Initialises class fields to their default values.
		root = null;
		occupancy = 0;
		treeName = name;
		representation = "";
		
		// Displays debug messages if debug mode is on.
		if (debug) {
			System.err.print(TREE + treeName + ALLOCATE);
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
		
		// Jettisons all the children nodes, starting from the root
		// of the tree. Then jettisons the tracker object of the tree itself.
		jettisonAllNodes(root);
		tracker.jettison();
	}

	/**
	 * Function name: jettisonAllNodes
	 * 
	 * Stops tracking the memory for children all node objects in binary tree,
	 * starting at the node passed in the parameter.This function uses an
	 * in-order traversal.
	 * 
	 * @param root the node to be jettisoned along with all it's children.
	 * @return nothing is returned.
	 * 
	 * Side effects: All nodes starting at the parameter node of the tree
	 * 				 are jettisoned.
	 * 
	 */
	public void jettisonAllNodes (TNode root) {
		
		// Base case of recursion, if the node is null.
		if (root == null) {
			return;
		}

		// If the node to the left of the 'root' is not empty,
		// do a recursive call, passing the node in.
		if (root.left != null)
			jettisonAllNodes (root.left);

		// When there are nodes to the left of the node, then
		// jettison it. 
		root.jettison(); 
		
		// If the node to the right of the 'root' is not empty,
		// do a recursive call, passing the node in.
		if (root.right != null)
			jettisonAllNodes (root.right);
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
	 * Function name: insert
	 * 
	 * Inserts the element into the binary tree.
	 * 
	 * @param element the Whatever to be inserted into the tree.
	 * @return boolean: True if insertion is successful,
	 * 					False otherwise.
	 * 
	 * Side effects: Updates node parent and left/right fields
	 * 				 to integrate new node into the tree. Also
	 * 				 updates balance and height fields of all 
	 * 				 other nodes as a result of insertion.
	 * 
	 */
	public boolean insert (Whatever element) {
		
		// If the tree is initially empty (empty root), then create a node
		// using the element and insert at the root, returning true.
		if (root == null) {

			if (debug) {
				System.err.print(TREE + treeName + INSERT 
								+ element.getName() + CLOSE);
			}

			root = new TNode(element, "Tree.insert");
			return true;

		// If there exists a node at the root of the tree.
		} else {

			TNode currentNode = root;		// The node to track the
											// current node we are looking at.

			TNode nodeInserted = root;		// To track which node is inserted.

			// Going down the tree, while the node we are looking at
			// is not empty.
			while (currentNode != null) {

				// Diplays debug message to compare nodes if debug mode on.
				if (debug) {
					System.err.print(TREE + treeName + COMPARE 
						+ element.getName() + AND + currentNode.data.getName()
					 	+ CLOSE);
				}

				// If it is a duplicate case, then jettison the old data and
				// replace it with the 'element' data. Return true when done.
				if (element.equals(currentNode.data)) {
					currentNode.data.jettison();
					currentNode.data = element;

					// If the node has been previously removed, set
					// the field to false.
					if (currentNode.hasBeenDeleted) {
						currentNode.hasBeenDeleted = false;
					}

					// Displays debug message for insertion if debug mode on.
					if (debug) {
						System.err.print(TREE + treeName + INSERT 
										+ element.getName() + CLOSE);
					}

					// Return true for success, exiting the function.
					return true;

				// By comparison if the element is less than the data stored
				// in the current node.
				} else if (element.isLessThan(currentNode.data)) {

					// If there is an empty spot to the left child of the
					// current node, then insert it into the tree.
					if (currentNode.left == null) {
						currentNode.left = new TNode(element, "Tree.insert");
						nodeInserted = currentNode.left;
						break;
					}

					// If there is already a node, then set the current node
					// to it.
					currentNode = currentNode.left;

				// If the element is greater than the data stored in the
				// current node.
				} else {

					// If there is an empty spot to the right child of the
					// current node, then insert it into the tree.
					if (currentNode.right == null) {
						currentNode.right = new TNode(element, "Tree.insert");
						nodeInserted = currentNode.right;
						break;
					}

					// If there is already a node, then set the current node
					// to it.
					currentNode = currentNode.right;
				}
			}

			// Diplay debug message of insertion if debug mode on.
			if (debug) {
				System.err.print(TREE + treeName + INSERT + element.getName() 
								+ CLOSE);
			}

			// Updates the parent field of the inserted node to the node
			// we were looking at.
			nodeInserted.parent = currentNode;
			

			// Loop to update the base and height fields of each node
			// after insertion, going up the tree.
			while (currentNode != null) {

				long tallestChildHeight;	// to store the tallest height
				long nonExistantChildHeight = -1;	// the height of a node
													// that doesn't exist.

				// If there is no left child of the current node, 
				// update base and height accordingly.
				if (currentNode.left == null) {
					tallestChildHeight = currentNode.right.height;
					currentNode.balance = nonExistantChildHeight 
											- currentNode.right.height;
				
				// If there is no right child of the current node, 
				// update base and height accordingly.
				} else if (currentNode.right == null) {
					tallestChildHeight = currentNode.left.height;
					currentNode.balance = currentNode.left.height 
											- nonExistantChildHeight;

				// If there is both a left and right child, find the tallest
				// child height.
				} else {
					if (currentNode.left.height > currentNode.right.height) {
						tallestChildHeight = currentNode.left.height;
					} else {
						tallestChildHeight = currentNode.right.height;
					}

					// Calculate the balance of left - right and store it.
					currentNode.balance = currentNode.left.height 
											- currentNode.right.height;
				}

				// Update the height to be 1 + the tallest height.
				currentNode.height = tallestChildHeight + 1;

				
				// Update currentNode to be the parent, going up the tree.
				currentNode = currentNode.parent; 
			}

		}

		// Return True when successfull.
		return true;
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
	 * Function name: remove
	 * 
	 * Removes the matching data from the binary tree via lazy remove.
	 * 
	 * @param element to be found and removed..
	 * @return Whatever: Returns a pointer to the data if found,
	 * 					null otherwise.
	 * 
	 * Side effects: Removes a node from the tree if found.
	 * 
	 */
	public Whatever remove (Whatever element) {
		
		// If the root is null (tree empty), then return null.
		if (root == null) {
			return null;

		// If the has a node at the root.
		} else {

			TNode currentNode = root;		// to track the current node
											// we are lokking at.

			// Goes down the tree until the node is not found.						
			while (currentNode != null) {

				// Displays debug compare message if debug mode on.
				if (debug) {
					System.err.print(TREE + treeName + COMPARE 
									+ element.getName() + AND 
									+ currentNode.data.getName() + CLOSE);
				}

				// If the element is found at the current node and
				// not deleted, then change the field to true, decrement
				// occupancy and return it.
				if (element.equals(currentNode.data) 
					&& !currentNode.hasBeenDeleted) {

					currentNode.hasBeenDeleted = true;
					occupancy--;
					return currentNode.data;

				// If the element is less than the data in the node, go
				// down the left child.
				} else if (element.isLessThan(currentNode.data)) {

					// if left child is null, then the element DNE.
					if (currentNode.left == null) {
						return null;
					}
					
					// Update current node to the left child.
					currentNode = currentNode.left;

				// If the element is greater than the data in the node, go
				// down the right child.
				} else {

					// if right child is null, then the element DNE.
					if (currentNode.right == null) {
						return null;
					}

					// Update current node to the right child.
					currentNode = currentNode.right;
				}

				
			}
		
		// Return null if the element has not been found after loop.
		return null;

		}
	}

	/**
	 * Function name: lookup
	 * 
	 * Looks up the matching data in the binary tree.
	 * 
	 * @param element to be found.
	 * @return Whatever: Returns a pointer to the data if found,
	 * 					null otherwise.
	 * 
	 * 
	 */
	public Whatever lookup (Whatever element) {
		
		// If the root is null (tree empty), then return null.
		if (root == null) {
			return null;

		// If the has a node at the root.
		} else {

			TNode currentNode = root;	// to track the current node
										// we are lokking at.

			// Goes down the tree until the node is not found.						
			while (currentNode != null) {

				// Displays debug compare message if debug mode on.
				if (debug) {
					System.err.print(TREE + treeName + COMPARE 
									+ element.getName() + AND 
									+ currentNode.data.getName() + CLOSE);
				}

				// If the element is found at the current node and
				// not deleted, then return the data.
				if (element.equals(currentNode.data) 
					&& !currentNode.hasBeenDeleted) {

					return currentNode.data;

				// If the element is less than the data in the node, go
				// down the left child.
				} else if (element.isLessThan(currentNode.data)) {

					// if left child is null, then the element DNE.
					if (currentNode.left == null) {
						return null;
					}

					// Update current node to the left child.
					currentNode = currentNode.left;

				// If the element is greater than the data in the node, go
				// down the right child.
				} else {

					// if right child is null, then the element DNE.
					if (currentNode.right == null) {
						return null;
					}

					// Update current node to the right child.
					currentNode = currentNode.right;
				}

				
			}
		
		// Return null if the element has not been found after loop.
		return null;

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

		representation = "Tree " + treeName + ":\noccupancy is ";
		representation += occupancy + " elements.";

		if (root != null)
			root.writeAllTNodes ();

		if (debug)
			System.err.println (tracker);
		
		return representation;
	}


	/**
	 * Class:            TNode
	 * Description:      Implements the node of the binary search tree data 
	 *                   structure.Each node containts two children, the left 
	 *                   child which contains data "less than" the data of the 
	 *                   current node, and the right child which contains data 
	 *                   "greater than" the data of the current node. Also holds
	 * 					 a reference to its parent node.
	 *
	 * Fields:           data  - holds the data stored in the current node
	 *                   left  - the left child
	 *                   right - the right child
	 * 					 parent - the parent node
	 * 					 hasBeenDeleted - if the node has been removed.
	 * 					 tracker - to track memory.
	 *
	 * Public functions: TNode - class constructor. Initialises fields
	 *                         	 and allocates memory.
	 *                   toString - To display the node as a string.
	 *                   writeAllTNodes - writes all TNodes to the
	 * 									  the string representation field.
	 */
	private class TNode {

		public Whatever data;
		public TNode left, right, parent;
		public boolean hasBeenDeleted;
		private Tracker tracker;

		// left child's height - right child's height
		public long balance;
		// 1 + height of tallest child, or 0 for leaf
		public long height;

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
				+ Size.of (left) + Size.of (right) 
				+ Size.of (parent) 
				+ Size.of (balance) + Size.of (height),
				caller + " calling TNode Ctor");

			// --------- DO NOT CHANGE ABOVE ---------
		
			// Initialises class fields to default values.
			data = element;
			left = null;
			right = null;
			parent = null;
			hasBeenDeleted = false;
			balance = 0;
			height = 0;

			// Increments the tree occupancy by 1.
			occupancy++;

		}

		/**
		 * Function name: jettison
		 * 
		 * Stops tracking the memory for this node object.
		 * 
		 * @param nothing is passed.
		 * @return nothing is returned.
		 * 
		 * Side effects: The data stored in the node and the tracker itself
		 * 				 is jettisoned.
		 * 
		 */
		public void jettison () {
			
			// Jettisons the data and tracker of the node.
			data.jettison();
			tracker.jettison();

			// Sets the deleted field to true.
			hasBeenDeleted = true;

			// Decrements the occupancy by -1.
			occupancy--;
		}

		/**
		* Creates a string representation of this node. Information
		* to be printed includes this node's height, its balance,
		* and the data its storing.
		*
		* @return  String representation of this node 
		*/

		public String toString () {
			return "at height:  " + height + "  with balance:  " +
				balance + "  " + data;
		}

		/**
		* Writes all TNodes to the String representation field. 
		* This recursive method performs an in-order
		* traversal of the entire tree to print all nodes in
		* sorted order, as determined by the keys stored in each
		* node. To print itself, the current node will append to
		* tree's String field.
		*/
		public void writeAllTNodes () {
			if (left != null) 
				left.writeAllTNodes ();
			if (!hasBeenDeleted) 
				representation += "\n" + this;          
			if (right != null)
				right.writeAllTNodes ();
		}
	}
}
