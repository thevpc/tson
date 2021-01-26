package net.thevpc.tson;

import java.math.BigInteger;

public interface TsonBigInt extends TsonNumber {
    BigInteger getValue();

    TsonPrimitiveBuilder builder();
}
