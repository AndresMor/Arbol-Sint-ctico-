/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treesyntax;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Visitante
 */
public class RegEx {
    
	/** Operators precedence map. */
	private static final Map<Character, Integer> precedenceMap;
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('(', 1);
		map.put('|', 2);
		map.put('.', 3); // explicit concatenation operator
		map.put('?', 4);
		map.put('*', 4);
		map.put('+', 4);
		map.put('^', 5);
		precedenceMap = Collections.unmodifiableMap(map);
	};

	/**
	 * Get character precedence.
	 * 
	 * @param c character
	 * @return corresponding precedence
	 */
	private static Integer getPrecedence(Character c) {
		Integer precedence = precedenceMap.get(c);
		return precedence == null ? 6 : precedence;
	}

	/**
	 * Transform regular expression by inserting a '.' as explicit concatenation
	 * operator.
	 */
	private static String formatRegEx(String regex) {
		String res = new String();
		List<Character> allOperators = Arrays.asList('|', '?', '+', '*', '^');
		List<Character> binaryOperators = Arrays.asList('^', '|');

		for (int i = 0; i < regex.length(); i++) {
			Character c1 = regex.charAt(i);

			if (i + 1 < regex.length()) {
				Character c2 = regex.charAt(i + 1);

				res += c1;

				if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
					res += '.';
				}
			}
		}
		res += regex.charAt(regex.length() - 1);

		return res;
	}

	/**
	 * Convert regular expression from infix to postfix notation using
	 * Shunting-yard algorithm.
	 * 
	 * @param regex infix notation
	 * @return postfix notation
	 */
	public static String infixToPostfix(String regex) {
		String postfix = new String();

		Stack<Character> stack = new Stack<Character>();

		String formattedRegEx = formatRegEx(regex);
                System.out.println("formattedRegEx: " + formattedRegEx);
		for (Character c : formattedRegEx.toCharArray()) {
			switch (c) {
				case '(':
					stack.push(c);
					break;

				case ')':
					while (!stack.peek().equals('(')) {
						postfix += stack.pop();
					}
					stack.pop();
					break;

				default:
					while (stack.size() > 0) {
						Character peekedChar = stack.peek();

						Integer peekedCharPrecedence = getPrecedence(peekedChar);
						Integer currentCharPrecedence = getPrecedence(c);

						if (peekedCharPrecedence >= currentCharPrecedence) {
							postfix += stack.pop();
						} else {
							break;
						}
					}
					stack.push(c);
					break;
			}

		}
		while (stack.size() > 0)
			postfix += stack.pop();

		return postfix;
	}
        
}
