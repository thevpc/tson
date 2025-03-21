package net.thevpc.tson;

import java.time.LocalTime;

public interface TsonLocalTime extends TsonTemporal {
    LocalTime value();

    TsonPrimitiveBuilder builder();
}
