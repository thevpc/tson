package net.thevpc.tson;

import java.io.Reader;

public interface TsonCharStream extends TsonElement {
    String getStreamType();

    Reader value();

    TsonPrimitiveBuilder builder();
}
