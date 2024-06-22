package net.thevpc.tson;

import java.util.List;

public interface TsonObject extends TsonContainer, Iterable<TsonElement> {
    TsonElementHeader header();

    List<TsonElement> all();

    TsonElementList body();

    TsonElement get(String name);

    TsonElement get(TsonElement element);

    int size();

    TsonObjectBuilder builder();
}
