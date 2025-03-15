package net.thevpc.tson;

public interface TsonObject extends TsonContainer, Iterable<TsonElement> {
    boolean isNamed();
    boolean isWithArgs();

    TsonElement get(String name);

    TsonElement get(TsonElement element);

    int size();

    TsonObjectBuilder builder();
}
