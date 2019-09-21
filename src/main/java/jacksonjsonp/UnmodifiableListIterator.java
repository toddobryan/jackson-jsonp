package jacksonjsonp;

import java.util.ListIterator;

class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
    ListIterator<E> iter;

    UnmodifiableListIterator(ListIterator<E> iter) {
        super(iter);
        this.iter = iter;
    }

    @Override
    public boolean hasPrevious() {
        return iter.hasPrevious();
    }

    @Override
    public E previous() {
        return iter.previous();
    }

    @Override
    public int nextIndex() {
        return iter.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iter.previousIndex();
    }

    @Override
    public void set(E e) {
        throw new UnsupportedOperationException("Iterator cannot modify list.");
    }

    @Override
    public void add(E e) {
        throw new UnsupportedOperationException("Iterator cannot modify list.");
    }
}

