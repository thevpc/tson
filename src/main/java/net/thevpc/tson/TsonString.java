package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String getValue();

    TsonPrimitiveBuilder builder();
}
