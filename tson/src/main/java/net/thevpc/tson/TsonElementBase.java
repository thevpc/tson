package net.thevpc.tson;

public interface TsonElementBase {
    TsonElementType type();

    TsonElement build();

    String toString();

    String toString(boolean compact);

    String toString(TsonFormat format);
}
