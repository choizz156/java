package linkedlist;

public class LinkedListV2 {

	private Node first;
	private int size = 0;

	public void add(Object e) {
		Node newNode = new Node(e);
		if (first == null) {
			first = newNode;
		} else {
			Node lastNode = getLastNode();
			lastNode.next = newNode;
		}
		size++;
	}

	public void add(int index, Object e) {
		Node newNode = new Node(e);
		if(index == 0){
			newNode.next = first;
			first = newNode;
		}else{
			Node prev = getNode(index - 1);
			newNode.next = prev.next;
			prev.next = newNode;
		}
		size++;
	}

	public Object remove(int index) {
		Node removeNode = getNode(index);
		Object removedData = removeNode.data;
		if (index == 0) {
			first = removeNode.next;
		} else {
			Node prev = getNode(index - 1);
			prev.next = removeNode.next;
		}

		removeNode.data = null;
		removeNode.next = null;

		size--;
		return removedData;
	}

	private Node getLastNode() {
		Node x = first;
		while (x.next != null) {
			x = x.next;
		}
		return x;
	}

	public Object set(int index, Object e) {
		Node x = getNode(index);
		Object oldData = x.data;
		x.data = e;
		return oldData;
	}

	public Object get(int index) {
		Node node = getNode(index);
		assert node != null;
		return node.data;
	}

	private Node getNode(int index) {
		Node x = first;
		for (int i = 0; i < index; i++) {
			x = x.next;
		}
		return x;
	}

	public int indexOf(Object e) {
		int index = 0;
		for(Node x = first; x != null; x = x.next) {
			if(e.equals(x.data)) {
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
