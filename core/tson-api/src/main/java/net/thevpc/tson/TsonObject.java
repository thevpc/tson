package net.thevpc.tson;

public interface TsonObject extends TsonContainer, Iterable<TsonElement> {
    TsonElementHeader header();

    TsonElement get(String name);

    TsonElement get(TsonElement element);

    int size();

    TsonObjectBuilder builder();
}
