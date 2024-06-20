package net.thevpc.tson;

import java.io.InputStream;
import java.io.Reader;

public interface TsonBinaryStreamBuilder {
    TsonBinaryStream build();

    void writeBase64(String b64);
}
