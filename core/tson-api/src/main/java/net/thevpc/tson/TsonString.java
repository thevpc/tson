package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String getValue();

    String raw();

    TsonStringLayout layout();

    TsonPrimitiveBuilder builder();
}
