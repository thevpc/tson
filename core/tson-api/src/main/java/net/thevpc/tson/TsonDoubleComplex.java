package net.thevpc.tson;

public interface TsonDoubleComplex extends TsonNumber {
    double real();

    double imag();

    TsonPrimitiveBuilder builder();
}
