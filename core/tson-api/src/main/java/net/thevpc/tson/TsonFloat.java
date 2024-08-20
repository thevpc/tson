package net.thevpc.tson;

public interface TsonFloat extends TsonNumber {
    float value();

    TsonPrimitiveBuilder builder();
}
