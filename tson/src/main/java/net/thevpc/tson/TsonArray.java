package net.thevpc.tson;

import java.util.List;

public interface TsonArray extends TsonContainer, Iterable<TsonElement> {
    TsonElementHeader header();

    boolean isEmpty();

    int size();


    List<TsonElement> all();

    TsonArrayBuilder builder();

    TsonElement get(int index);
}
