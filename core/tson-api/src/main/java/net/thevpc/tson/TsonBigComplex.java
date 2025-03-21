package net.thevpc.tson;

import java.math.BigDecimal;

public interface TsonBigComplex extends TsonNumber {
    BigDecimal real();

    BigDecimal imag();

    TsonPrimitiveBuilder builder();
}
