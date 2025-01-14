package collection;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class MyArrayList<E> implements MyList<E> {

	public static final int DEFAULT_CAPACITY = 5;

	private Object[] elements;
	private int size = 0;

	public MyArrayList() {
		elements = new Object[DEFAULT_CAPACITY];
	}

	public MyArrayList(int capacity) {
		elements = new Object[capacity];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(E object) {

		if (size == elements.length) {
			grow();
		}

		elements[size] = object;
		size++;
	}

	@Override
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

	@Override
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

	@Override
	public E get(int index) {
		return (E)elements[index];
	}

	@Override
	public E set(int index, E object) {
		E oldValue = get(index);
		elements[index] = object;
		return oldValue;
	}

	@Override
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
