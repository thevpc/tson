package net.thevpc.tson;

import java.util.List;

public interface TsonArray extends TsonElement, Iterable<TsonElement> {
    TsonElementHeader getHeader();

    boolean isEmpty();

    int size();

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonArrayBuilder builder();

    TsonElement get(int index);
}
