package net.thevpc.tson;

public interface TsonArray extends TsonContainer, Iterable<TsonElement> {
    boolean isWithArgs();

    boolean isEmpty();

    int size();

    TsonArrayBuilder builder();

    TsonElement get(int index);
}
