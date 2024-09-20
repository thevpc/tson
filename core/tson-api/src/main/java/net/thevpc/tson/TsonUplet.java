package net.thevpc.tson;

public interface TsonUplet extends TsonContainer, Iterable<TsonElement> {
    boolean isEmpty();

    int size();

    TsonUpletBuilder builder();
}
