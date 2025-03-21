package net.thevpc.tson;

public interface TsonFloatComplex extends TsonNumber {
    float real();

    float imag();

    TsonPrimitiveBuilder builder();
}
