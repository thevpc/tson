package net.thevpc.tson;

public interface TsonUplet extends TsonListContainer, Iterable<TsonElement> {
    boolean isNamed();

    boolean isBlank();

    TsonElement param(int index);

    int size();

    TsonUpletBuilder builder();
}
