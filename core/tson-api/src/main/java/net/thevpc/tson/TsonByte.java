package net.thevpc.tson;

public interface TsonByte extends TsonNumber {
    byte value();

    TsonPrimitiveBuilder builder();
}
