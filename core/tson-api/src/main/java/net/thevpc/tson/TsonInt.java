package net.thevpc.tson;

public interface TsonInt extends TsonNumber {
    int value();
    TsonPrimitiveBuilder builder();
}
