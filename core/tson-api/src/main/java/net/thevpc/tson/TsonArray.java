package net.thevpc.tson;

public interface TsonArray extends TsonContainer, Iterable<TsonElement> {
    TsonElementHeader header();

    boolean isEmpty();

    int size();

    TsonArrayBuilder builder();

    TsonElement get(int index);
}
