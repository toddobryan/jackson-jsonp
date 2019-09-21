package jacksonjsonp;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ImmutableCollection<E> implements Collection<E> {
    private Collection<E> coll;

    ImmutableCollection(Collection<E> coll) {
        this.coll = coll;
    }

    @Override
    public int size() {
        return coll.size();
    }

    @Override
    public boolean isEmpty() {
        return coll.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return coll.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new UnmodifiableIterator<>(coll.iterator());
    }

    @Override
    public Object[] toArray() {
        return coll.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return coll.toArray(a);
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return coll.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException("Collection is immutable");
    }

    @Override
    public Spliterator<E> spliterator() {
        return coll.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return coll.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return coll.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        coll.forEach(action);
    }
}
