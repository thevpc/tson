package net.thevpc.tson;

public interface TsonLong extends TsonNumber {
    long getValue();

    TsonPrimitiveBuilder builder();
}
