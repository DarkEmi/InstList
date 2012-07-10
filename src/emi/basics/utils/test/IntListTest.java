package emi.basics.utils.test;

import java.util.ArrayList;

import emi.basics.utils.IntList;
import junit.framework.TestCase;

/**
 * @author Emilien Dutang
 */

public class IntListTest extends TestCase 
{
	public void testAll()
	{
		// null test
		int[] t1 = {};
		IntList testList1 = new IntList(t1);

		assertTrue (testList1.firstNode == null);
		assertTrue (testList1.getReverse().firstNode == null);
		assertTrue (testList1.getFirstValue() == -1);
		assertTrue (testList1.getLastValue() == -1);
		
		// basic tests + insertion tests
		int[] t2 = {1, 2, 3};
		int[] t3 = {0, 1, 2, 3};
		int[] t4 = {1, 2, 3, 4};
		
		IntList testList2 = new IntList(t2);
		
		assertTrue (testList2.getFirstValue() == 1);
		assertTrue (testList2.getLastValue() == 3);
		
		IntList t2first = new IntList(t2);
		t2first.addAsFirstNode(0);
		IntList testList3 = new IntList(t3);
		compare(t2first, testList3);
		assertTrue(t2first.count() == testList3.count());
		
		IntList t2last = new IntList(t2);
		t2last.addAsLastNode(4);
		IntList testList4 = new IntList(t4);
		compare(t2last, testList4);
		assertTrue(t2last.count() == testList4.count());
		
		// basic revert tests - no reason it should bug because of scaling 
		int[] t = {0};
		revertTest(t);
		
		int[] t7 = {-1, 1, 6, 2, 7};
		revertTest(t7);
		
		
		System.out.println("Temps en ms pour revert une list de 100k éléments : " + revertComplexityTime(100000));
		System.out.println("Temps en ms pour revert une list de 1m éléments : " + 	revertComplexityTime(1000000));
		System.out.println("Temps en ms pour revert une list de 10m éléments: " + 	revertComplexityTime(10000000));
		System.out.println("Temps en ms pour revert une list de 20m éléments: " + 	revertComplexityTime(20000000));
		System.out.println("Temps en ms pour revert une list de 30m éléments: " + 	revertComplexityTime(30000000));
		System.out.println("Temps en ms pour revert une list de 40m éléments: " + 	revertComplexityTime(40000000));
		
		// assert complexity (expected is 20* greater, <20 is allowed for random java issues (n² should be 100 times))
		//assertTrue((longRevert / 20) < smallRevert);
		
		// comparing java time like this seems to be completly broken, it keeps making weird results, seems like all the times is coming from malloc ?
		
		// yes it seems like memory allocation is strangely O(n²) after some point, cause it is exactly n² and I dont see in anyway where my algorithm is n²
		// => I should test this with a huge memory allocation before test
		
		/*
		 * results
		 * 	Temps en ms pour revert une list de 100k éléments : 0
			Temps en ms pour revert une list de 1m éléments : 10
			Temps en ms pour revert une list de 10m éléments: 1160
			Temps en ms pour revert une list de 20m éléments: 4040
			Temps en ms pour revert une list de 30m éléments: 12219

			avec 2 go de heap :
			
			Temps en ms pour revert une list de 100k éléments : 3
			Temps en ms pour revert une list de 1m éléments : 11
			Temps en ms pour revert une list de 10m éléments: 46
			Temps en ms pour revert une list de 20m éléments: 90
			Temps en ms pour revert une list de 30m éléments: 6020
			 */
	}
	
	private void compare(IntList l1, IntList l2)
	{
		// pointers should be different
		assertTrue (l1 != l2);
		
		// values shouldBe the same, pointers still different
		while (l1.current != null)
		{
			assertTrue(l1.current != l2.current);
			assertTrue(l1.getCurrentValue() == l2.getCurrentValue());
			l1.next();
			l2.next();
		}
	}
	
	/**
	 * not computationally efficient test, should be used for small lists only
	 */
	private void revertTest(int[] t) 
	{
		IntList origin = new IntList(t);
		IntList reverted = origin.getReverse();
		
		// values should be equal, array is used for easyness
		ArrayList<Integer> revertValues = new ArrayList<Integer>();
		
		// used to compare node memory handles references
		ArrayList<Object> revertHandles = new ArrayList<Object>();
		
		while (reverted.current != null)
		{
			revertValues.add(reverted.getCurrentValue());
			revertHandles.add(reverted.current);
			reverted.next();
		}
		
		// comparaisons
		assertTrue(reverted.count() == origin.count());
		for (int i = 0; i < revertValues.size(); i++, origin.next())
		{
			// les derniers de revert = la value des premiers d'origin
			assertTrue(revertValues.get(revertValues.size() - 1 - i) == origin.getCurrentValue());
			
			// les handles doivent évidement etre différent
			assertTrue(revertHandles.get(revertHandles.size() - 1 - i) == origin.current);
		}
	}
	
	/**
	 * Computational complexity test, O(n) expected
	 */
	private long revertComplexityTime(int size)
	{
		int[] t = new int[size];
		
		IntList origin = new IntList(t);
			
		// mesure and compare complexity with times, O(n) expected
		System.gc(); // hopefully it will prevents unwanted GC in test
		long start = System.currentTimeMillis();
		origin.getReverse();
		long revertTime = System.currentTimeMillis() - start;
		
		return revertTime;
	}
}
