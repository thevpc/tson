package net.thevpc.tson;

public interface TsonArray extends TsonListContainer, Iterable<TsonElement> {

    boolean isEmpty();

    int size();

    TsonArrayBuilder builder();

    TsonElement get(int index);
}
