package net.thevpc.tson;

public interface TsonLong extends TsonNumber {
    long value();

    TsonPrimitiveBuilder builder();
}
