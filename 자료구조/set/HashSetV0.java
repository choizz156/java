package set;

import java.util.Arrays;

public class HashSetV0 {

	private int[] elements = new int[10];
	private int size = 0;

	public boolean add(int value) {
		if (contains(value)) {
			return false;
		}
		elements[size] = value;
		size++;
		return true;
	}

	private boolean contains(int value) {
		for (int element : elements) {
			if (element == value) {
				return true;
			}
		}
		return false;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "HashSetV0{" +
			"elements=" + Arrays.toString(Arrays.copyOf(elements, size)) +
			", size=" + size +
			'}';
	}
}
