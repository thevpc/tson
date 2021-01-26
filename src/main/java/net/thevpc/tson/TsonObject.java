package net.thevpc.tson;

import java.util.List;

public interface TsonObject extends TsonElement, Iterable<TsonElement> {
    TsonElementHeader getHeader();

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonElement get(String name);

    TsonElement get(TsonElement element);

    int size();

    TsonObjectBuilder builder();
}
