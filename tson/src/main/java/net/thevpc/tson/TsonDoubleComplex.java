package net.thevpc.tson;

public interface TsonDoubleComplex extends TsonNumber {
    double getReal();

    double getImag();

    TsonPrimitiveBuilder builder();
}
