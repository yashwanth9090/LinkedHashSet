package HashSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class HashSet<T> implements OrderedSet<T>{
	
	private Node<T> head;
	private Node<T> tail;
	private	Node<T>[] array;
	private ArrayIndexCalculator indexCalculator;
	private int count = 0;


	/**
	 * Parameterized constructor
	 * @param initialSize - Initial size of the set
	 */
	@SuppressWarnings("unchecked")
	public HashSet(int initialSize){
		array = new Node[initialSize];
		indexCalculator = new ArrayIndexCalculator();
	}
	 
	// Package visible only - for testing purposes
	@SuppressWarnings("unchecked")
	HashSet(int initialSize, ArrayIndexCalculator indexCalculator) {
		array = new Node[initialSize];
		this.indexCalculator = indexCalculator;
	}

	public int getLengthOfArray() {
		return array.length;
	}

	@SuppressWarnings("unchecked")
	private void createNewArray(int length) {
		array = new Node[length];
	}
	public void insert(T value) {
	//	System.out.println("Inserted new element");
		if(value == null){
			throw new IllegalArgumentException();
		}
		if(exists(value)){return;}
		if(count == array.length){
			array = doubleArray(array.length);
		}
		count++;
	//	System.out.println("InitialCount: "+count);
		Node<T> newNode = new Node<T>(value);
		int indexOfString = hashFunction(value);
		if(collision(value)){
			//System.out.println("collision");
			tail = avoidCollision(value,newNode,tail,indexOfString);
			count--;
		//	System.out.println("CollisionCount: "+count);
			return;
		}
		System.out.println("index: "+indexOfString);
//		System.out.println("FinalCount: "+count);
		insertIntoArray(indexOfString, newNode);
		if(head == null){
			head = newNode;
			tail = newNode;
		}else{
			tail.setNext(newNode); 
			//newNode.next = null;
			newNode.setPrev(tail);
			tail = tail.getNext();
			
		}
		
		
	}


	Node<T>[] doubleArray(int length) {
		@SuppressWarnings("unchecked")
		Node<T>[] temp = new Node[length];
		for(int i = 0;i<length;i++){
			temp[i] = array[i];
		}
		createNewArray(2*length);
		@SuppressWarnings("unchecked")
		Node<T>[] hashSetArray = new Node[2*length];
		for(int i =0;i<length;i++){
			hashSetArray[i] = temp[i];
		}
		return hashSetArray;
	}

	Node<T> avoidCollision(T value, Node<T> newNode, Node<T> tail, int indexOfString) {
		Node<T> temp = array[indexOfString];
		//System.out.println("CollisionIndex: "+indexOfString);
		while(temp.getRight()!=null){
			temp = temp.getRight();
		}
		temp.setRight(newNode);
		tail.setNext(newNode);
		newNode.setPrev(tail);
		tail = tail.getNext();
		return tail;
		
	}

	private boolean collision(T value) {

		return array[hashFunction(value)]!=null;
		
	}

	 public boolean exists(T value) {
		if(array[hashFunction(value)]==null){
			return false;
		}else{
			
			Node<T> temp = array[hashFunction(value)];
			while(temp!= null){
				if(temp.getData().equals(value)){
				//	System.out.println("-->"+temp.data);
					return true;
				}
				temp =temp.getRight();
			}
			
			return false;
		}
	}

	private void insertIntoArray(int indexOfString, Node<T> newNode) {

		array[indexOfString] = newNode;
	}

	int hashFunction(T value) {
		return indexCalculator.getIndex(value,array.length);
	}

	public void print() {
	
		Node<T> temp = head;
		if(head == null){
			System.out.println("Empty list!");
			return;
		}else{ 
		while(temp!=null){
			System.out.println(temp.getData());
			temp = temp.getNext();
		}
		}
		
	//	System.out.println(count);
	}
	
	public ArrayList<T> getAsList(){
		Node<T> temp = head;
		
		ArrayList<T> testList = new ArrayList<>();
		if(head == null){
			System.out.println("Empty list!");
			//testList.add(null);
			return testList;
		}else{
			
		while(temp!=null){
			testList.add(temp.getData());
			temp = temp.getNext();
			
		}
		
		}
		return testList;
	}
	
	
	public void delete(T value) {
		if(!exists(value)){
			throw new InputMismatchException();
			
		}else{
			int index = hashFunction(value);
			Node<T> element = array[index];
			if(isCollisionLinkedList(element)){
				Node<T> newNode = deleteLinkedList(element,value);
				array[index] = newNode;
				
			}else{
				array[index] = null;
				deleteOrderingLinkedList(element);
			} 
		}
				 
	}
	

	private Node<T> deleteLinkedList(Node<T> element,T value) {
		if(element.getData().equals(value)){
			deleteOrderingLinkedList(element);
			return element.getNext();
		}else{
			Node<T> previous = element;
			Node<T> current = element;
			while(current!=null){
				if(current.getData().equals(value)){
					deleteOrderingLinkedList(current);
					previous.setRight(current.getRight());
				}
				previous = current;
				current = current.getNext();
			}
			return element;
		}
		
	}

	private void deleteOrderingLinkedList(Node<T> element) {

		if(element.getPrev()!=null){
			if(element.getNext()!=null){ 
				element.getPrev().setNext(element.getNext());
				element.getNext().setPrev(element.getPrev());
			}else{
				element.getPrev().setNext(null);
				tail = element.getPrev();
			}
		}else{
			if(element.getNext()!=null){
				head = element.getNext();
				element.getNext().setPrev(null);
			}else{
				head = null;
				tail = null;
			}
		}
		
	}

	private boolean isCollisionLinkedList(Node<T> element) {
		return element.getRight()!=null;
		
	}

/*	public void printArray() {
		int j = 0;
		String[] testArray  = new String();
		for(int i=0;i<hashSetArray.length;i++){
			if(hashSetArray[i]!=null){
				
			}
		}
	}*/
		
}