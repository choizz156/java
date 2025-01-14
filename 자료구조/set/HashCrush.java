package set;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HashCrush {
	static final int CAPACITY = 10;

	public static void main(String[] args) {
		List<Integer>[] buckets = new LinkedList[CAPACITY];

		for (int i = 0; i < CAPACITY; i++) {
			buckets[i] = new LinkedList<>();
		}

		add(buckets, 1);
		add(buckets, 2);
		add(buckets, 5);
		add(buckets, 8);
		add(buckets, 14);
		add(buckets, 99);
		add(buckets, 9); //중복
		System.out.println("buckets = " + Arrays.toString(buckets));

		int searchValue = 9;
		boolean contains = contains(buckets, searchValue);

		System.out.println("buckets.contains(" + searchValue + ") = " + contains);
	}

	private static void add(List<Integer>[] buckets, int searchValue) {
		int hashIndex = hashIndex(searchValue);
		var bucket = buckets[hashIndex];
		if(!bucket.contains(searchValue)) {
			bucket.add(searchValue);
		}
	}

	private static boolean contains(List<Integer>[] buckets, int i) {
		int hashIndex = hashIndex(i);
		List<Integer> bucket = buckets[hashIndex];
		return bucket.contains(i);
	}

	private static int hashIndex(int i) {
		return i % CAPACITY;
	}
}
