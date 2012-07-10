
package emi.basics.utils;

/**
 * @author Emilien Dutang
 */

public class IntList
{
	public Node firstNode = null;
	
	// can be used as an iterator over the linked list
	public Node current = null;
	
	public IntList()
	{
	}
	
	public IntList(int[] values)
	{
		if (values.length == 0)
			return;
		
		// we have to watch out for order, as nodes are pushed in from last to first we have to create the last in first
		Node newNode = new Node(null, values[values.length - 1]);
		
		for (int i = 1; i < values.length; i++)
			newNode = new Node(newNode, values[values.length - 1 - i]);
		firstNode = newNode;
		current = newNode;
	}
	
	/**
	 * I dont see any use for a recursive method as :
	 * - stack use memory and can overflow
	 * - complexity is the same
	 * - method is harder to read + debug
	 */
	public IntList getReverse()
	{
		IntList newList = new IntList();
		
		Node read = firstNode;
		if (read == null)
			return newList;
		
		Node newNode = null;
		while (read != null)
		{
			newNode = new Node(newNode, read.value);
			read = read.next;
		}

		// last from reversed is now the new first node
		newList.firstNode = newNode;
		return newList;
	}
	
	public long count()
	{
		long nbNode = 0;
		Node currentNode = firstNode;
		
		while (currentNode != null)
		{
			nbNode++;
			currentNode = currentNode.next;
		}
		
		return nbNode;
	}
	
	public void addAsFirstNode(int value)
	{
		Node newFirst = new Node(firstNode, value);
		firstNode = newFirst;
		resetIterator();
	}
	
	public void addAsLastNode(int value)
	{
		Node currentNode = firstNode;
		
		while (true)
		{
			if (currentNode.next != null)
				currentNode = currentNode.next;
			else
				break;
		}
		
		Node newLastNode = new Node(null, value);
		
		// currentNode is now the old last, we just have to update its next
		currentNode.next = newLastNode;
		resetIterator();
	}
	
	/*
	 * iterations & getter methods
	 */
	public int get(int i)
	{
		if (i < 0) // unexpected value
			return -1;
		
		Node tempCurrent = firstNode;
		while (tempCurrent != null && (i > 0))
		{
			tempCurrent = tempCurrent.next;
			i--;
		}
		
		if (tempCurrent == null) // we reached the end of the arrayList
			return -1; // out of bounds
		else
			return tempCurrent.value;
	}
	
	public int getCurrentValue()
	{
		if (current != null)
			return current.value;
		else
			return -1;
	}
	
	public int getFirstValue()
	{
		if (firstNode != null)
			return firstNode.value;
		else
			return -1;
	}
	
	public int getLastValue()
	{
		if (firstNode == null)
			return -1;
		
		Node tempCurrent = firstNode;
		while (tempCurrent.next != null)
			tempCurrent = tempCurrent.next;
	
		return tempCurrent.value;
	}
	
	public void resetIterator()
	{
		current = firstNode;
	}
	
	public void next()
	{
		if (current != null)
			current = current.next;
	}
	
	// inner Node class
	private class Node
	{
		Node next = null;
		int value = 0;
		
		private Node(Node nextArg, int valueArg)
		{
			next = nextArg;
			value = valueArg;
		}
	}
}
