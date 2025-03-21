package net.thevpc.tson;

import java.time.LocalDateTime;

public interface TsonLocalDateTime extends TsonTemporal {
    LocalDateTime value();

    TsonPrimitiveBuilder builder();
}
