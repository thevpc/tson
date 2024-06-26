package net.thevpc.tson;

import java.io.Reader;

public interface TsonParserFactory {
    String id();
    TsonStreamParser fromReader(Reader reader, String parser, Object source);
}
