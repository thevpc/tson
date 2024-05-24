package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String getValue();
    TsonStringLayout layout();

    TsonPrimitiveBuilder builder();
}
