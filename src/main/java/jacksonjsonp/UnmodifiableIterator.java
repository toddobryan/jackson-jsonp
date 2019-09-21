package jacksonjsonp;

import java.util.Iterator;
import java.util.function.Consumer;

public class UnmodifiableIterator<E> implements Iterator<E> {
    private Iterator<E> iter;

    UnmodifiableIterator(Iterator<E> iter) {
        this.iter = iter;
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public E next() {
        return iter.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Iterator cannot modify list.");
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        iter.forEachRemaining(action);
    }
}
