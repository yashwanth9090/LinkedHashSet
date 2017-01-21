package HashSet;

public interface Container<T> {
	/**
	 * Creates and returns the Iterator.
	 * @return
	 */
	public MyIterator<T> iterator();
}
