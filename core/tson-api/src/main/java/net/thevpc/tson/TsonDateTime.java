package net.thevpc.tson;

import java.time.Instant;

public interface TsonDateTime extends TsonTemporal {
    Instant getValue();

    TsonPrimitiveBuilder builder();
}
