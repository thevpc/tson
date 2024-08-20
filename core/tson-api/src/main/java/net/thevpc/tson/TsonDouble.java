package net.thevpc.tson;

public interface TsonDouble extends TsonNumber {
    double value();
    TsonPrimitiveBuilder builder();
}
