package net.thevpc.tson;

import java.math.BigDecimal;

public interface TsonBigDecimal extends TsonNumber {
    BigDecimal value();

    TsonPrimitiveBuilder builder();
}
