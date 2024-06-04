package net.thevpc.tson;

import java.util.List;

public interface TsonUplet extends TsonContainer, Iterable<TsonElement> {
    boolean isEmpty();

    int size();

    List<TsonElement> all();

    TsonUpletBuilder builder();
}
