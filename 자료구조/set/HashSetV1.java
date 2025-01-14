package set;

import java.util.Arrays;
import java.util.LinkedList;

public class HashSetV1 {
	static final int DEFAULT_CAPACITY = 16;

	LinkedList<Integer>[] buckets;

	private int size = 0;
	private int capacity = DEFAULT_CAPACITY;

	public HashSetV1() {
		initBuckets();
	}

	public HashSetV1(int capacity) {
		this.capacity = capacity;
		initBuckets();
	}

	private void initBuckets() {
		buckets = new LinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			buckets[i] = new LinkedList<>();
		}
	}

	public boolean add(int value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];

		if(bucket.contains(value)) {
			return false;
		}

		bucket.add(value);
		size++;

		return true;
	}

	private int getHasIndex(int value) {
		return Math.abs(value) % capacity;
	}

	public boolean contains(int value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];
		return bucket.contains(value);
	}

	public boolean remove(int value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];
		boolean result = bucket.remove(Integer.valueOf(value));
		if(result) {
			size--;
			return true;
		}else{
			return false;
		}
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "HashSetV1{" +
			"buckets=" + Arrays.toString(buckets) +
			", size=" + size +
			", capacity=" + capacity +
			'}';
	}
}
