package collection;

public interface MyList<E> {
	int size();
	void add(E e);
	E get(int index);
	E remove(int index);
	E set(int index, E e);
	void add(int index, E e);
	int indexOf(E e);
}
