package net.thevpc.tson;

import java.time.LocalTime;

public interface TsonTime extends TsonTemporal {
    LocalTime value();

    TsonPrimitiveBuilder builder();
}
