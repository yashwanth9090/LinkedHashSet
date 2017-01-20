package HashSet;

/**
  Collection used to store list of elements and retains insertion order.
 **/
public interface OrderedSet<T> {
		
	/**
	 Inserts elements into the collection and @throws 
	 IllegalArgumentException if the input is null.
	 **/
	public void insert(T value);
	
	/**
	 
	Deletes elements from the list and retains insertion order.
	@throws InputMismatchException if the element doesn't exist.
	 
	 **/
	public void delete(T value);
	
	/**
		Checks uniqueness of the list during insertion.
	 **/
	public boolean exists(T value);
	

	/**
	 * Prints all of the elements in the set.
	 */
	public void print();
	
	
}
