package net.thevpc.tson;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

public interface TsonReader {
    TsonReader setOption(String name, Object value);

    boolean isSkipHeader();

    TsonReader setSkipHeader(boolean skipHeader);

    boolean isSkipComments();

    TsonReader setSkipComments(boolean skipComments);


    <T> T read(TsonElement  tson, Class<? extends T> clazz);

    <T> T read(InputStream stream, Class<? extends T> clazz) ;

    <T> T read(CharSequence string, Class<? extends T> clazz);

    <T> T read(InputStream stream, String encoding, Class<? extends T> clazz) ;

    <T> T read(Reader reader, Class<? extends T> clazz) ;

    <T> T read(File file, Class<? extends T> clazz) ;

    <T> T read(Path file, Class<? extends T> clazz) ;

    <T> T read(URL url, Class<? extends T> clazz) ;

    TsonElement readElement(InputStream stream) ;

    TsonElement readElement(CharSequence string);

    TsonElement readElement(InputStream stream, String encoding) ;

    TsonElement readElement(Reader reader) ;

    TsonElement readElement(File file) ;

    TsonElement readElement(Path file) ;

    TsonElement readElement(URL url) ;

    TsonDocument readDocument(InputStream stream) ;

    TsonDocument readDocument(CharSequence string);

    TsonDocument readDocument(InputStream stream, String encoding) ;

    TsonDocument readDocument(Reader reader) throws IOException;

    TsonDocument readDocument(File file) throws IOException;

    TsonDocument readDocument(Path file) throws IOException;

    TsonDocument readDocument(URL url) throws IOException;

    void visitElement(InputStream stream, TsonParserVisitor visitor) throws IOException;

    void visitElement(CharSequence string, TsonParserVisitor visitor);

    void visitElement(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException;

    void visitElement(Reader reader, TsonParserVisitor visitor) throws IOException;

    void visitElement(File file, TsonParserVisitor visitor) throws IOException;

    void visitElement(Path file, TsonParserVisitor visitor) throws IOException;

    void visitElement(URL url, TsonParserVisitor visitor) throws IOException;

    void visitDocument(InputStream stream, TsonParserVisitor visitor) throws IOException;

    void visitDocument(CharSequence string, TsonParserVisitor visitor);

    void visitDocument(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException;

    void visitDocument(Reader reader, TsonParserVisitor visitor) throws IOException;

    void visitDocument(File file, TsonParserVisitor visitor) throws IOException;

    void visitDocument(Path file, TsonParserVisitor visitor) throws IOException;

    void visitDocument(URL url, TsonParserVisitor visitor) throws IOException;

    void visitElement(String tsonString,TsonParserVisitor visitor);

    void visitDocument(String tsonString,TsonParserVisitor visitor);
}
