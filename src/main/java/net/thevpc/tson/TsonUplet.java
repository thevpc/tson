package net.thevpc.tson;

import java.util.List;

public interface TsonUplet extends TsonElement, Iterable<TsonElement> {
    boolean isEmpty();

    int size();

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonUpletBuilder builder();
}
