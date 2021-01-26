package net.thevpc.tson;

public interface TsonDouble extends TsonNumber {
    double getValue();
    TsonPrimitiveBuilder builder();
}
