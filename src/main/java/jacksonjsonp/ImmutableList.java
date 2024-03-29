package jacksonjsonp;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class ImmutableList<E> implements List<E> {
    private List<E> list;

    ImmutableList(List<E> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<>(list.iterator());
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new UnmodifiableListIterator<>(list.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new UnmodifiableListIterator<>(list.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new ImmutableList<>(list.subList(fromIndex, toIndex));
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public Spliterator<E> spliterator() {
        return list.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException("List is immutable.");
    }

    @Override
    public Stream<E> stream() {
        return list.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return list.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        list.forEach(action);
    }
}
