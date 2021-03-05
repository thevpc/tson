package net.thevpc.tson;

public interface TsonByte extends TsonNumber {
    byte getValue();

    TsonPrimitiveBuilder builder();
}
