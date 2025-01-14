package set;

import java.util.Arrays;
import java.util.LinkedList;

public class HashSetV2 {
	static final int DEFAULT_CAPACITY = 16;

	LinkedList<Object>[] buckets;

	private int size = 0;
	private int capacity = DEFAULT_CAPACITY;

	public HashSetV2() {
		initBuckets();
	}

	public HashSetV2(int capacity) {
		this.capacity = capacity;
		initBuckets();
	}

	private void initBuckets() {
		buckets = new LinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			buckets[i] = new LinkedList<>();
		}
	}

	public boolean add(Object value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];

		if(bucket.contains(value)) {
			return false;
		}

		bucket.add(value);
		size++;

		return true;
	}

	private int getHasIndex(Object value) {
		return Math.abs(value.hashCode()) % capacity;
	}

	public boolean contains(Object value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];
		return bucket.contains(value);
	}

	public boolean remove(Object value) {
		int hasIndex = getHasIndex(value);
		var bucket = buckets[hasIndex];
		boolean result = bucket.remove(value);
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
