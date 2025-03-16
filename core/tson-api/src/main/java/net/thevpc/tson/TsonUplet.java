package net.thevpc.tson;

public interface TsonUplet extends TsonContainer, Iterable<TsonElement> {
    boolean isNamed();

    boolean isBlank();

    TsonElement param(int index);

    int size();

    TsonUpletBuilder builder();
}
