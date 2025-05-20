package net.thevpc.tson;

import java.time.Instant;

public interface TsonInstant extends TsonTemporal {
    Instant value();

    TsonPrimitiveBuilder builder();
}
