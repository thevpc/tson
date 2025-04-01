package net.thevpc.tson;

public interface TsonString extends TsonElement {
    String value();

    String raw();

    String literalString();

    TsonPrimitiveBuilder builder();
}
