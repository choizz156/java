package linkedlist;

public class LinkedListV3<E> {

	private Node<E> first;
	private int size = 0;

	public void add(E e) {

		Node<E> newNode = new Node<>(e);

		if (first == null) {
			first = newNode;
		} else {
			Node<E> lastNode = getLastNode();
			lastNode.next = newNode;
		}

		size++;
	}

	public void add(int index, E e) {
		Node<E> newNode = new Node(e);

		if (index == 0) {
			newNode.next = first;
			first = newNode;
		} else {
			Node<E> prev = getNode(index - 1);
			newNode.next = prev.next;
			prev.next = newNode;
		}
		size++;
	}

	public E remove(int index) {
		Node<E> removeNode = getNode(index);
		Object removedData = removeNode.data;
		if (index == 0) {
			first = removeNode.next;
		} else {
			Node<E> prev = getNode(index - 1);
			prev.next = removeNode.next;
		}

		removeNode.data = null;
		removeNode.next = null;

		size--;

		return (E)removedData;
	}

	private Node<E> getLastNode() {
		Node<E> x = first;

		while (x.next != null) {
			x = x.next;
		}

		return x;
	}

	public E set(int index, E e) {
		Node<E> x = getNode(index);
		E oldData = x.data;
		x.data = e;
		return oldData;
	}

	public E get(int index) {
		Node<E> node = getNode(index);
		assert node != null;
		return node.data;
	}

	private Node<E> getNode(int index) {
		Node<E> x = first;
		for (int i = 0; i < index; i++) {
			x = x.next;
		}
		return x;
	}

	public int indexOf(E e) {
		int index = 0;
		for (Node<E> x = first; x != null; x = x.next) {
			if (e.equals(x.data)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return "LinkedListV1{" +
			"first=" + first +
			", size=" + size +
			'}';
	}
}
