package HashSet;

public class Node<T> {
	private T data;
	private Node<T> prev;
	private	Node<T> next;
	private	Node<T> right;
	

	public T getData() {
		return data;
	}


	public Node<T> getPrev() {
		return prev;
	}


	public void setPrev(Node<T> prev) {
		this.prev = prev;
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
