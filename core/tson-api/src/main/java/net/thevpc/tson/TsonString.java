package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String getValue();

    String raw();

    String quoted();

    TsonStringLayout layout();

    TsonPrimitiveBuilder builder();
}
