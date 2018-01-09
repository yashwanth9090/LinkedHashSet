package HashSet;
import java.util.InputMismatchException;
public class HashSet<T> implements OrderedSet<T>, Container<T>{

	private	Node<T>[] array;
	private ArrayIndexCalculator indexCalculator;
	private LinkedList<T> linkedList;
	private int count = 0;


	/**
	 * Parameterized constructor
	 * @param initialSize - Initial size of the set
	 */
	@SuppressWarnings("unchecked")
	public HashSet(int initialSize){
		array = new Node[initialSize];
		indexCalculator = new ArrayIndexCalculator();
		linkedList = new LinkedList<T>();
	}
	 
	// Package visible only - for testing purposes
	@SuppressWarnings("unchecked")
	HashSet(int initialSize, ArrayIndexCalculator indexCalculator) {
		array = new Node[initialSize];
		this.indexCalculator = indexCalculator;
		linkedList = new LinkedList<T>();
	}
	// Returns the size of the Array.
	public int getLengthOfArray() {
		return array.length;
	}

	@SuppressWarnings("unchecked")
	private void createNewArray(int length) {
		array = new Node[length];
	}
	
	//Iterator
	private class LinkedHashSetIterator implements MyIterator<T>{
		private Node<T> temp ;
		
		public LinkedHashSetIterator(){
		temp = null;
		}
		@Override
		public boolean hasNext() {
			return temp!=linkedList.getTail();
		}

		@Override
		public Object next() {
			if(temp == null){
				temp = linkedList.getHead() ;
				return temp.getData();
			}
			temp = temp.getNext();
			if(this.hasNext()){
				return temp.getData();
			}
			return temp.getData();
		}
		
	}
	
	// returns iterator object
	@Override
	public MyIterator<T> iterator() {
		return new LinkedHashSetIterator();
	}
	
	public void insert(T value) {
		if(value == null){
			throw new IllegalArgumentException();
		}
		//Checks uniqueness of the set.
		if(exists(value)){return;}
		//Doubles the size of Array if it is full.
		if(count == array.length){
			array = doubleArray(array.length);
		}
		count++;
		Node<T> newNode = new Node<T>(value);
		//Using the Hash Function the index of the node is determined.
		int indexOfString = hashFunction(value);
		//If the index is not null, collision is avoided using LinkedList. 
		if(collision(value)){
			linkedList.setTail(avoidCollision(value,newNode,linkedList.getTail(),indexOfString));
			count--;
			return;
		}
		insertIntoArray(indexOfString, newNode);
		linkedList.insert(newNode);
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
		while(temp.getRight()!=null){
			temp = temp.getRight();
		}
		temp.setRight(newNode);
		linkedList.getTail().setNext(newNode);
		newNode.setPrevious(tail);
		linkedList.setTail(linkedList.getTail().getNext());
		return linkedList.getTail();
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
		Node<T> temp = linkedList.getHead();
		if(linkedList.getHead() == null){
			System.out.println("Empty list!");
			return;
		}else{ 
		while(temp!=null){
			System.out.println(temp.getData());
			temp = temp.getNext();
		}
		}
	}
   //Returns true if set is empty
	boolean isEmpty(){
		return linkedList.getHead()==null;
	}
	/**
	 * Removes specified element from the set if it is present.
	 */
	public void delete(T value) {
		/**
		 * If the element doesn't exists 
		 * @throw InputMisMatchException().
		 */
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
				linkedList.delete(element);
			} 
		}
				 
	}

	private Node<T> deleteLinkedList(Node<T> element,T value) {
		if(element.getData().equals(value)){
			linkedList.delete(element);
			return element.getNext();
		}else{
			Node<T> previous = element;
			Node<T> current = element;
			while(current!=null){
				if(current.getData().equals(value)){
					 linkedList.delete(current);
					previous.setRight(current.getRight());
				}
				previous = current;
				current = current.getNext();
			}
			return element;
		}
		
	}

	//Checks if the element is in collision list.
	private boolean isCollisionLinkedList(Node<T> element) {
		return element.getRight()!=null;
		
	}

	
	
}
