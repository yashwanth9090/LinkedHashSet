package HashSet;

public interface MyIterator<T> {
	/**
	 * Provides navigation in List and returns true if element exists.
	 * @return boolean
	 */
	public boolean hasNext();
	/**
	 * Returns the element in the set.
	 * @return
	 */
	public Object next();

}
