package net.thevpc.tson;

public interface TsonInt extends TsonNumber {
    int getValue();
    TsonPrimitiveBuilder builder();
}
