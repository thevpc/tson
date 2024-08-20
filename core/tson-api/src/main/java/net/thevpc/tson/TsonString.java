package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String value();

    String raw();

    String quoted();

    TsonStringLayout layout();

    TsonPrimitiveBuilder builder();
}
