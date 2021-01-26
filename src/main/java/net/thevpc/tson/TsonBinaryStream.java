package net.thevpc.tson;

import java.io.*;

public interface TsonBinaryStream extends TsonElement {
    InputStream getValue();

    Reader getBase64Value();

    Reader getBase64Value(int lineMax);

    TsonPrimitiveBuilder builder();
}
