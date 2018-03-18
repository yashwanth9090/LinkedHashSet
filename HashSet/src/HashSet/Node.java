package HashSet;
/**
 * Collection uses Doubly LinkedList.
 * data - To store the element.
 * previous - Pointer to the previous node.
 * next - Pointer to the next node.
 * right - Pointer to the node in collision list.
 * @author Uttam
 *
 * @param <T>
 */
public class Node<T> {
	private T data;
	private Node<T> previous;
	private	Node<T> next;
	private	Node<T> right;
	
	// POJO
	public T getData() {
		return data;
	}


	public Node<T> getPrevious() {
		return previous;
	}


	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}


	public Node<T> getNext() {
		return next;
	}


	public void setNext(Node<T> next) {
		this.next = next;
	}


	public Node<T> getRight() {
		return right;
	}


	public void setRight(Node<T> right) {
		this.right = right;
	}


	public void setData(T data) {
		this.data = data;
	}


	Node(T data){
		this.data = data ;
	}
	

}
