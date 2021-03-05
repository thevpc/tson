package net.thevpc.tson;

public interface TsonFloat extends TsonNumber {
    float getValue();

    TsonPrimitiveBuilder builder();
}
