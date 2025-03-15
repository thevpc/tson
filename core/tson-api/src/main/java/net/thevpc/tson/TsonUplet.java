package net.thevpc.tson;

public interface TsonUplet extends TsonContainer, Iterable<TsonElement> {
    boolean isNamed();

    boolean isBlank();

    int size();

    TsonUpletBuilder builder();
}
