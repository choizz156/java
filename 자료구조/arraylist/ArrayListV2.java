package arraylist;

import java.util.Arrays;

public class ArrayListV2 {
	public static final int DEFAULT_CAPACITY = 5;

	private Object[] elements;
	private int size = 0;

	public ArrayListV2() {
		elements = new Object[DEFAULT_CAPACITY];
	}

	public ArrayListV2(int capacity) {
		elements = new Object[capacity];
	}

	public int size() {
		return size;
	}

	public void add(Object object) {

		if (size == elements.length) {
			grow();
		}

		elements[size] = object;
		size++;
	}

	private void grow() {
		int oldCapacity = elements.length;
		int newCapacity = oldCapacity * 2;
		elements = Arrays.copyOf(elements, newCapacity);
	}

	public Object get(int index) {
		return elements[index];
	}

	public Object set(int index, Object object) {
		Object oldValue = get(index);
		elements[index] = object;
		return oldValue;
	}

	public int indexOf(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "ArrayListV1{" +
			"elements=" + Arrays.toString(Arrays.copyOf(elements, size)) +
			", size=" + size +
			'}';
	}
}
