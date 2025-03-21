package net.thevpc.tson;

import java.time.LocalDate;

public interface TsonLocalDate extends TsonTemporal {
    LocalDate value();

    TsonPrimitiveBuilder builder();
}
