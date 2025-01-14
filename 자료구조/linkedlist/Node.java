package linkedlist;

public class Node<E> {

	E data;
	Node<E> next;

	public Node(E data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<E> x = this;
		sb.append("[");
		while (x != null) {
			sb.append(x.data);
			if (x.next != null) {
				sb.append("->");
			}
			x = x.next;
		}
		sb.append("]");
		return sb.toString();
	}
}
