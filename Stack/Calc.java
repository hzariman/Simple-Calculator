/**
 * Name:           Hariz Megat Zariman
 * PID:            A16383158
 * USER:           cs12sp21bjd
 * File name:      Calc.java
 * Description:    This program simulates calculator which perfroms advanced 
 *                 commands such as addition, subtractions, multiplication,
 *                 division, exponentiation, and factorial, translating  
 *                 user input to postfix notation for the computer to
 * 				   evalute and display a result.
 */

import java.io.*;      // System.in and System.out

/**
 * Class:            Calc
 * Description:      Holds all the calculator functions and manipulates
 * 					 LongStack objects to hold long values representing
 * 					 numbers in infix and later postfix order. Also processes
 * 					 user input into postfix form which is then evaluated
 * 					 base on operators.
 *
 * Fields:           functions      - holds the operation functions that deal
 * 									  with how certain numbers are evaluated.
 *                   CALCSTACKSIZE  - max size of calculator stack allowed.
 *                   EOF/TRUE/FALSE - constants for eof/true/false values
 * 					 BYTE			- constant for a byte.
 * 					 operators      - array of operator symbols in order
 * 									  of priority.
 * 					 SIGN_BIT		- sign bit for operator
 * 
 * Public functions: eval 			- evaluate mathematical expressions from 
 * 									  "postfix" notation.
 *                   intpost        - converts "infix" mathematical expression
 * 									  entered by user into "postfix" notation.
 */
public class Calc {
	// the calculator operation interface
	interface Operation {
		long operation (long op1, long op2);
	}

	// the calculator operations in priority order
	private static Operation[] functions = new Operation[] {
		null,
		null,
		new Operation () { public long operation (long op1, long op2) 
		{ return add (op1, op2); }},
		new Operation () { public long operation (long op1, long op2) 
		{ return sub (op1, op2); }},
		new Operation () { public long operation (long op1, long op2) 
		{ return mult (op1, op2); }},
		new Operation () { public long operation (long op1, long op2) 
		{ return divide (op1, op2); }},
		new Operation () { public long operation (long op1, long op2) 
		{ return exponent (op1, op2); }},
		null,
		new Operation() { public long operation (long op1, long op2) 
		{ return fact (op1, op2); }}
	};

	// maximum size of a calculator stack
	private static final int CALCSTACKSIZE = 100;

	// constants for EOF and true/false values
	private static final int
		EOF = -1,
		TRUE = 1,
		FALSE = 0,
		BYTE = 8;

	// array of operators, according to their priority
	private static final char[] operators = "()+-*/^ !".toCharArray ();

	// sign bit for the operator when setupword is called
	private static final long SIGN_BIT = 1L << ((Long.BYTES << 3) - 1);

	/**
	 * Getter method to find index of operator in the operators array
	 * 
	 * @param word: the operator word to get the index of
	 * @return long: index of operator in the operators array
	 */
	private static int getIndex (long word) {
		return (int)(word & 0xFF00) >> BYTE;
	}

	/**
	 * Getter method to extract the operator from a word
	 * 
	 * @param word: the word to extract the operator from
	 * @return char: the operator as a char
	 */
	private static char getOperator (long word) {
		return (char) (word & 0xFF);
	}

	/**
	 * Getter method that returns the priority of an operator
	 * 
	 * @param word: the word that contains the operator
	 * @return long: priority of the operator
	 */
	private static long getPriority (long word) {
		return (word & 0xFE00);
	}

	/**
	 * Checks if an item is an operator
	 * 
	 * @param item: the item to check if it is an operator
	 * @return boolean:
	 *      true: item is an operator
	 *      false: item is not an operator
	 */
	private static boolean isOperator (long item) {
		return item < 0;
	}

	/**
	 * Checks if an item is a number
	 * 
	 * @param item: the item to check if it is a number
	 * @return boolean:
	 *      true: item is a number
	 *      false: item is not a number
	 */
	private static boolean isNumber (long item) {
		return item >= 0;
	}

	/**
	 * Function name: eval
	 * 
	 * Utilizes two stacks, evaluates mathematical expression
	 * from "postfix" form. Calls operation functions and 
	 * manipulates values onto stack respectively. 
	 * 
	 * @param stack1 LongStack Object containing expression 
	 * 				 in reverse "postfix" notation order.
	 * @return long:
	 * 				result of calculated mathematical expression
	 * 
	 */
	public static long eval (LongStack stack1) {

		LongStack stack2 = new LongStack(CALCSTACKSIZE,	   //to store operators
								"Second stack for eval");  //in priority order
										
		long stackItem;		// value from top of stack
		long operand1;		// first value to undergo operation
		long operand2;		// second value to undergo operation
		long result;		// result of the operation

		// Pushes all values on stack 1 into stack 2, reversing
		// it's order into ideal "postfix" form.
		while (!stack1.isEmptyStack()) {
			stackItem = stack1.pop();
			stack2.push(stackItem);
		}

		// Iterates over entire stack until all values are processed.
		while (!stack2.isEmptyStack()) {

			// Assigns to value of top of stack
			stackItem = stack2.top();

			// if value at the top is a number, pop from the stack
			// and push onto 1st stack (1st stack holds numbers).
			if (isNumber(stackItem)) {
				stackItem = stack2.pop();
				stack1.push(stackItem);
			}

			// if value is an operator, pop operator value and
			// pop first operand from 1st stack (stack of numbers)
			// to be used.
			if (isOperator(stackItem)) {
				stackItem = stack2.pop();
				operand1 = stack1.pop();
				
				// If the operator value is a factorial, only
				// pop 1 value from the 1st stack, assign second
				// operand to any value (ignored later).
				if (getOperator(stackItem) == '!') {
					operand2 = operand1;

				// Otherwise obtain second operand by popping from
				// first stack.
				} else {
					operand2 = stack1.pop();
				}

				// Call math function based on operator index using operand values,
				// store in result.
				result = functions[getIndex(stackItem)].operation(operand1,operand2);

				// Push result to first stack.
				stack1.push(result);

			}
		}
		
		// Jettison stack of locally created object stack 2, clearing memory.
		stack2.jettisonStack();

		// Stack 1 should only have 1 value: the final result. Pop value and 
		// return the result.
		result = stack1.pop();
		return result;
	}

	/**
	 * Function name: intopost
	 * 
	 * Utilizes two stacks, converts expression entered by
	 * user into "postfix" form. 
	 * 
	 * @param stack1 Output parameter of LongStack Object used to store
	 * 				 expression entered by user into "postfix" notation
	 * 				 (but in reversed order on the stack).
	 * @return int:
	 * 				1 (non-zero value) returned to Main caller, signifying
	 * 				that the function was succesful.
	 * 
	 */
	public static int intopost (LongStack stack1) {
		// TODO: YOUR CODE GOES HERE
		LongStack stack2 = new LongStack(CALCSTACKSIZE,    //to store operators
							"Second stack for intopost");  //in priority order

		int inputChar = MyLib.getchar();	//character from input stream
		long operatorChar;					// value of operator character
		long operatorInStack;				// value of operator in 2nd stack

		// while input stream still has characters
		while (inputChar != '\n' ) {

			// If user enters CTRL-D, break loop and return EOF
			if (inputChar == EOF) {

				// Jettison stack, clearing memory
				stack2.jettisonStack();
				return EOF;
			}

			// If there is an empty space, obtain next char and continue loop
			if (inputChar == ' ') {
				inputChar = MyLib.getchar();
				continue;
			}

			//If the char is a digit:
			if (inputChar>='0' && inputChar <= '9') {

				// Put character back into stream, then call decin() function,
				// turing character digit into long value.
				MyLib.ungetc((char)inputChar);
				long number = MyLib.decin();

				//Push long value into 1st stack (numbers).
				stack1.push(number);

				// if char is open bracket, call setupword using char
				// turning value into long formatted for later use.
			} else if (inputChar == '(') {
				operatorChar = setupword((char)inputChar);

				//push value onto second stack (operators)
				stack2.push(operatorChar);

				//if char is closed bracket, pop from second stack and
				// push onto first stack until matching parentheses is found.
			} else if (inputChar == ')') {

				operatorChar = stack2.pop();

				// Keep popping from second stack and pushing onto 1st until
				// first open bracket is found.
				while (getOperator(operatorChar) != '(') {
					stack1.push(operatorChar);
					operatorChar = stack2.pop();
				}

				// Otherwise, char is an opterator, so process accordingly.
			} else {

				// Format operator into a operator long correctly
				// using setupword.
				operatorChar = setupword((char)inputChar);

				// If the second stack is not empty (there are other operators)
				while (!stack2.isEmptyStack()) {

					// assign value at top of stack 2
					operatorInStack = stack2.top();

					// Check if operator from input char has greater priority
					// than top of stack, if so break the loop.
					if (getPriority(operatorChar) > getPriority(operatorInStack)) {
						break;
					}

					// Otherwise keep popping from stack 2 and pushing
					// to stack 1.
					operatorInStack = stack2.pop();
					stack1.push(operatorInStack);
					
				}

				// Finally, push the operator from input to stack 2
				// to match priority.
				stack2.push(operatorChar);
			}

			// Obtains next value from input stream
			inputChar = MyLib.getchar();
		}

		//Finally, push all values from stack 2 onto stack 1 if not empty.
		while (!stack2.isEmptyStack()) {
			operatorInStack = stack2.pop();

			// if there is a open bracket still in the stack, dont push
			if (getOperator(operatorInStack) == '(') {
				continue;
			}
			stack1.push(operatorInStack);
		}

		// Jettison local stack, clearing memory allocated.
		stack2.jettisonStack();

		// return non-zero value to represent success.
		return 1;

	}

	/**
	 * Add two operands together
	 * 
	 * @param augend: the first operand
	 * @param addend: the second operand
	 * 
	 * @return long: the result of the adding augend and addend
	 */
	private static long add (long augend, long addend) {
		return augend + addend;
	}

	/**
	 * Divide the divisor by the dividend
	 * 
	 * @param divisor: the long to be divided
	 * @param dividend: the long that divides the divisor
	 * 
	 * @return long: the result of dividing the divisor by the dividend
	 */
	private static long divide (long divisor, long dividend) {
		return dividend / divisor;
	}

	/**
	 * Function name: exponent
	 * 
	 * Performs mathematical exponential (^) function
	 *  via recursion. 
	 * 
	 * @param power first operand obtained from stack 1 in eval. 
	 * @param base second operand obtained from stack 1 in eval.
	 * @return long:
	 * 				base multiplied by function again, but with
	 * 				power input parameter -1. 
	 * 				OR 1 when base case is reached (power is 0).
	 * 
	 * Limitations : only works for positive power and base values.
	 */
	private static long exponent (long power, long base) {
		
		// if the power is not zero.
		if (power >=1) {

			// multiply the base by the function with the base 1 less
			// (recursion).
			return base * exponent(power -1, base);
		
			// until the power is zero, then return 1.
		} else {
			return 1;
		}
	}

	/**
	 * Function name: fact
	 * 
	 * Performs mathematical factorial (!) function
	 *  via recursion. 
	 * 
	 * @param power first operand obtained from stack 1 in eval. 
	 * @param ignored ignored value (in this case, the first operand again)
	 * @return long:
	 * 				xxx multiplied by function again, but with
	 * 				xxx input parameter -1. (basically multiplied by itself -1).
	 * 				OR 1 when base case is reached (xxx is 0).
	 * 
	 * Limitations : only works for positive integer xxx.
	 */
	private static long fact (long xxx, long ignored) {
		// TODO: YOUR CODE GOES HERE
		if (xxx >=1) {
			return xxx* fact(xxx-1, ignored);
		} else {
			return 1;
		}
	}

	/**
	 * Multiply two numbers together
	 * 
	 * @param multiplier: the num by which the multiplicand is multiplied
	 * @param factory: the num to be multiplied
	 * 
	 * @return long: the result of the multiplication
	 */
	private static long mult (long multiplier, long multiplicand) {
		return multiplier * multiplicand;
	}

	/**
	 * Subtract the minuend from the subtrahend
	 * 
	 * @param subtrahend: the number to subtract from
	 * @param minuend: the number to subtract from subtrahend
	 * 
	 * @return long: value of subtracting the minuend from subtrahend
	 */
	private static long sub (long subtrahend, long minuend) {
		return minuend - subtrahend;
	}

	/**
	 * Function name: setupword
	 * 
	 * Constructs a long that represents an operator to be stored
	 *  on the stack.
	 * 
	 * @param char character of operator
	 * @return long:
	 * 				value that represents an operator, containing
	 * 				its priority and ascii value along with sign
	 * 				bit. 
	 * 
	 * Notes: obtained algorithm from Discussion 5
	 */
	private static long setupword (char character) {

		int index = 0;		//to track which operator

		// Always good input, the priority of the operator
		// of char input will always be found.
		while (true) {

			// Obtains index of the operator char position
			// in operators array, breaks when found.
			if (character == operators[index]) {
				break;
			}

			index++;
		}

		// Returns long in specified format (negative to signify operator).
		return SIGN_BIT | index << BYTE | character;
	}
}
