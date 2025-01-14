package arraylist;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class ArrayListV4<E> {
	public static final int DEFAULT_CAPACITY = 5;

	private Object[] elements;
	private int size = 0;

	public ArrayListV4() {
		elements = new Object[DEFAULT_CAPACITY];
	}

	public ArrayListV4(int capacity) {
		elements = new Object[capacity];
	}

	public int size() {
		return size;
	}

	public void add(E object) {

		if (size == elements.length) {
			grow();
		}

		elements[size] = object;
		size++;
	}

	public void add(int index, E object) {

		if (size == elements.length) {
			grow();
		}

		shiftRightFrom(index);

		elements[index] = object;
		size++;
	}

	private void shiftRightFrom(int index) {
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
	}

	private void grow() {
		int oldCapacity = elements.length;
		int newCapacity = oldCapacity * 2;
		elements = Arrays.copyOf(elements, newCapacity);
	}

	public E remove(int index) {
		E oldValue = get(index);
		shiftLeftFrom(index);

		size--;
		elements[index] = null;
		return oldValue;
	}

	private void shiftLeftFrom(int index) {
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
	}

	public E get(int index) {
		return (E)elements[index];
	}

	public E set(int index, E object) {
		E oldValue = get(index);
		elements[index] = object;
		return oldValue;
	}

	public int indexOf(E object) {
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
