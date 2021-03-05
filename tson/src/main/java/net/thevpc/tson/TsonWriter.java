package net.thevpc.tson;

import java.io.*;
import java.nio.file.Path;

public interface TsonWriter{
    TsonWriter setOptionCompact(boolean configValue);

    TsonWriter setOption(String configName, Object configValue);

    void write(Appendable sb, Object any) throws IOException;

    void write(Path file, Object any) throws IOException;

    void write(File file, Object any) throws IOException;

    void write(OutputStream stream, Object any) throws IOException;

    void write(OutputStream stream, String encoding, Object any) throws IOException;

    void write(Writer writer, Object any) throws IOException;

    void writeDocument(Appendable sb, Object any) throws IOException;

    void writeDocument(Path file, Object any) throws IOException;

    void writeDocument(File file, Object any) throws IOException;

    void writeDocument(OutputStream stream, Object any) throws IOException;

    void writeDocument(OutputStream stream, String encoding, Object any) throws IOException;

    void writeDocument(Writer writer, Object any) throws IOException;

    void write(Appendable sb, TsonElement any) throws IOException;

    void write(Path file, TsonElement any) throws IOException;

    void write(File file, TsonElement any) throws IOException;

    void write(OutputStream stream, TsonElement any) throws IOException;

    void write(OutputStream stream, String encoding, TsonElement any) throws IOException;

    void write(Writer writer, TsonElement any) throws IOException;

    void writeDocument(Appendable sb, TsonDocument any) throws IOException;

    void writeDocument(Path file, TsonDocument any) throws IOException;

    void writeDocument(File file, TsonDocument any) throws IOException;

    void writeDocument(OutputStream stream, TsonDocument any) throws IOException;

    void writeDocument(OutputStream stream, String encoding, TsonDocument any) throws IOException;

    void writeDocument(Writer writer, TsonDocument any) throws IOException;
}
