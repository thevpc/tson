package net.thevpc.tson;

import java.time.LocalDate;

public interface TsonDate extends TsonTemporal {
    LocalDate value();

    TsonPrimitiveBuilder builder();
}
