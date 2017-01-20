package HashSet;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashSet;



import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;


import HashSet.HashSet;

public class LinkedHashSetTest {

	private HashSet<String> actualList;
	private LinkedHashSet<String> expectedList;
	
	@Before
	public void setUp() {
		actualList = Mockito.spy(new HashSet<>(4));
		expectedList = new LinkedHashSet<>();
	}
	
	@Test
	public void testInsertionOrder(){
		insertIntoActualList("yashu","uttam","samatha");
		InOrder inOrder = Mockito.inOrder(actualList);
		inOrder.verify(actualList).insert("yashu");
		inOrder.verify(actualList).insert("uttam");
		inOrder.verify(actualList).insert("samatha");
	}
	

	@Test
	public void testDuplicates(){
		
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(2);

		insertIntoActualList("yashu","yashu","samatha");
		
		Mockito.verify(actualList,Mockito.times(5)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		insertIntoExpectedList("yashu","yashu","samatha");
		verifyLists(expectedList,actualList);
	}

	@Test
	public void testEmptyList(){
		assertTrue(actualList.isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull(){
		actualList.insert(null);
	}
	
	
	@Test
	public void testDeletingAllElements(){
		insertIntoActualList("yashu","uttam","sam");
		deleteElementsFromActualList("sam","uttam","yashu");
		assertTrue(actualList.isEmpty());
	}
	
	@Test(expected = InputMismatchException.class)
	public void testDeletionOfNonexistentElement(){
		insertIntoActualList("yashu","yash");
		actualList.delete("uttam"); 
		
	}
	@Test
	public void testInsert(){
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		actualList.insert("yashu");
		Mockito.verify(actualList).insert(arg.capture());
		assertEquals("yashu",arg.getValue());
	}
	@Test
	public void testDeletionFromCollsionListTail(){
		
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(2);
		
		insertIntoActualList("yashu","uttam","samatha");
				
		Mockito.verify(actualList, Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList, Mockito.times(4)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList, Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		
		
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);	
	}
	
	
	@Test
	public void testDeletionFromCollsionListTail2(){
		ArrayIndexCalculator indexCalculator = Mockito.mock(ArrayIndexCalculator.class);
		actualList = new HashSet<>(4, indexCalculator);
		
		when(indexCalculator.getIndex("yashu", 4)).thenReturn(1);
		when(indexCalculator.getIndex("uttam", 4)).thenReturn(1);
		when(indexCalculator.getIndex("samatha", 4)).thenReturn(2);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex("yashu",4);
		Mockito.verify(indexCalculator,Mockito.times(4)).getIndex("uttam",4);
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex("samatha",4);
		
		
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);	
	}
	
	@Test
	public void testDeletionFromCollsionListMiddle(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(1);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq("samatha"));
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromCollsionListHead(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(1);
		when(actualList.hashFunction("samatha")).thenReturn(1);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq("samatha"));
		
		actualList.delete("yashu");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListTail(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		actualList.delete("samatha");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("samatha");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListMiddle(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		actualList.delete("uttam");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("uttam");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListHead(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		actualList.delete("yashu");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletingOrderingLinkedListElements(){
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		
		insertIntoActualList("yashu","uttam","samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("yashu"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("uttam"));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq("samatha"));
		
		deleteElementsFromActualList("yashu","uttam","samatha");
		insertIntoExpectedList("yashu","uttam","samatha");
		expectedList.remove("yashu");		
		expectedList.remove("uttam");
		expectedList.remove("samatha");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testParameterType(){
		actualList.insert("yashu");
		Mockito.verify(actualList).insert(Mockito.anyString());
	}
	
	@Test
	public void testDoubleArraySize(){
		when(actualList.hashFunction("cherry")).thenReturn(0);
		when(actualList.hashFunction("yashu")).thenReturn(1);
		when(actualList.hashFunction("uttam")).thenReturn(2);
		when(actualList.hashFunction("samatha")).thenReturn(3);
		insertIntoActualList("yashu","uttam","samatha","cherry","divya");
		Mockito.verify(actualList).doubleArray(4);
		assertEquals(8,actualList.getLengthOfArray());
		
	}

	
	
	private void verifyLists(Collection<String> expected, HashSet<String> actual) {
	//	assertEquals(expected.size(), actual.size());
		Iterator<String> expectedIterator = expected.iterator();
		MyIterator<String> actualIterator = actual.iterator();
		
		while(expectedIterator.hasNext()) {
			assertEquals(expectedIterator.next(), actualIterator.next());			
		}
	}

	private void insertIntoActualList(String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			actualList.insert(strings[i]);
		}
	}
	
	private void deleteElementsFromActualList(String ...string){
		for(int i=0;i<string.length;i++){
			actualList.delete(string[i]);
		}
	}
	

	private void insertIntoExpectedList(String ... strings) {
		for(int i = 0; i < strings.length; i++) {
			expectedList.add(strings[i]);
		}
	}
	
}
