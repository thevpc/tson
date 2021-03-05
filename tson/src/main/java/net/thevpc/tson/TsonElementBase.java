package net.thevpc.tson;

public interface TsonElementBase {
    TsonElementType getType();

    TsonElement build();

    String toString();

    String toString(boolean compact);

    String toString(TsonFormat format);
}
