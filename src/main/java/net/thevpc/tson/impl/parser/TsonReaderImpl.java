package net.thevpc.tson.impl.parser;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.parser.javacc.TsonStreamParserImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.StringBuilderReader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class TsonReaderImpl implements TsonReader {
    private TsonStreamParserImplConfig config = new TsonStreamParserImplConfig();
    private TsonSerializer marshaller;
    private String parser = "default";

    public TsonReaderImpl(TsonSerializer marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public TsonReader setOption(String name, Object value) {
        if (name != null) {
            switch (name) {
                case "parser": {
                    switch (String.valueOf(value)) {
                        case "custom-javacc":
                        case "javacc":
                        case "jflex":
                        case "default": {
                            parser = String.valueOf(value);
                            break;
                        }
                        case "":
                        case "null": {
                            parser = "default";
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException("Unsupported parser " + value);
                        }
                    }
                    break;
                }
                case "skipHeader": {
                    config.setSkipHeader(Boolean.valueOf(String.valueOf(value)));
                    break;
                }
                case "skipComments": {
                    config.setSkipComments(Boolean.valueOf(String.valueOf(value)));
                    break;
                }
                case "rawComments": {
                    config.setRawComments(Boolean.valueOf(String.valueOf(value)));
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public boolean isSkipHeader() {
        return config.isSkipHeader();
    }

    @Override
    public TsonReader setSkipHeader(boolean skipHeader) {
        this.config.setSkipHeader(skipHeader);
        return this;
    }

    @Override
    public boolean isSkipComments() {
        return this.config.isSkipComments();
    }

    @Override
    public TsonReader setSkipComments(boolean skipComments) {
        this.config.setSkipComments(skipComments);
        return this;
    }

    /////////////////////////////////

    @Override
    public <T> T read(InputStream stream, Class<? extends T> clazz) throws IOException {
        return read(clazz, _fromReader(new InputStreamReader(stream)));
    }

    @Override
    public <T> T read(CharSequence string, Class<? extends T> clazz) {
        try (Reader r = createStringReader(string)) {
            return read(clazz, _fromReader(r));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Override
    public <T> T read(InputStream stream, String encoding, Class<? extends T> clazz) throws IOException {
        return read(clazz, _fromReader(new InputStreamReader(stream, encoding==null? "UTF-8":encoding)));
    }

    @Override
    public <T> T read(Reader reader, Class<? extends T> clazz) throws IOException {
        return read(clazz, _fromReader(reader));
    }

    @Override
    public <T> T read(File file, Class<? extends T> clazz) throws IOException {
        try (Reader is = new FileReader(file)) {
            return read(clazz, _fromReader(is));
        }
    }

    @Override
    public <T> T read(Path file, Class<? extends T> clazz) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return read(clazz, _fromReader(is));
        }
    }

    @Override
    public <T> T read(URL url, Class<? extends T> clazz) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return read(clazz, _fromReader(is));
        }
    }

    /////////////////////////////////

    @Override
    public TsonElement readElement(InputStream stream) {
        return readElement(_fromReader(new InputStreamReader(stream)));
    }

    @Override
    public TsonElement readElement(CharSequence string) {
        try (Reader r = createStringReader(string)) {
            return readElement(_fromReader(r));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public TsonElement readElement(InputStream stream, String encoding) throws IOException{
        return readElement(_fromReader(new InputStreamReader(stream, encoding==null? "UTF-8":encoding)));
    }

    @Override
    public TsonElement readElement(Reader reader) {
        return readElement(_fromReader(reader));
    }

    @Override
    public TsonElement readElement(File file) throws IOException {
        try (Reader is = new FileReader(file)) {
            return readElement(_fromReader(is));
        }
    }

    @Override
    public TsonElement readElement(Path file) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return readElement(_fromReader(is));
        }
    }

    @Override
    public TsonElement readElement(URL url) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return readElement(_fromReader(is));
        }
    }
    /////////////////////////////////

    @Override
    public TsonDocument readDocument(InputStream stream) {
        return readDocument(_fromReader(new InputStreamReader(stream)));
    }

    @Override
    public TsonDocument readDocument(CharSequence string) {
        try {
            try (Reader r = createStringReader(string)) {
                return readDocument(_fromReader(r));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Reader createStringReader(CharSequence string) {
        return (string instanceof StringBuilder) ? new StringBuilderReader((StringBuilder) string) : new StringReader(string.toString());
    }

    @Override
    public TsonDocument readDocument(InputStream stream, String encoding) throws IOException{
        return readDocument(_fromReader(new InputStreamReader(stream, encoding==null? "UTF-8":encoding)));
    }

    @Override
    public TsonDocument readDocument(Reader reader) {
        return readDocument(_fromReader(reader));
    }

    @Override
    public TsonDocument readDocument(File file) throws IOException {
        try (Reader is = new FileReader(file)) {
            return readDocument(_fromReader(is));
        }
    }

    @Override
    public TsonDocument readDocument(Path file) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return readDocument(_fromReader(is));
        }
    }

    @Override
    public TsonDocument readDocument(URL url) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return readDocument(_fromReader(is));
        }
    }

    /////////////////////////////////

    @Override
    public void visitElement(InputStream stream, TsonParserVisitor visitor) {
        visitElement(visitor, _fromReader(new InputStreamReader(stream)));
    }

    @Override
    public void visitElement(CharSequence string, TsonParserVisitor visitor) {
        try (Reader r = createStringReader(string)) {
            visitElement(visitor, _fromReader(r));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visitElement(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException{
        visitElement(visitor, _fromReader(new InputStreamReader(stream, encoding==null? "UTF-8":encoding)));
    }

    @Override
    public void visitElement(Reader reader, TsonParserVisitor visitor) {
        visitElement(visitor, _fromReader(reader));
    }

    @Override
    public void visitElement(File file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new FileReader(file)) {
            visitElement(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitElement(Path file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            visitElement(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitElement(URL url, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            visitElement(visitor, _fromReader(is));
        }
    }

    /////////////////////////////////

    @Override
    public void visitDocument(InputStream stream, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(new InputStreamReader(stream)));
    }

    @Override
    public void visitDocument(CharSequence string, TsonParserVisitor visitor) {
        try (Reader r = createStringReader(string)) {
            visitDocument(visitor, _fromReader(r));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visitDocument(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(new InputStreamReader(stream, encoding==null? "UTF-8":encoding)));
    }


    @Override
    public void visitDocument(Reader reader, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(reader));
    }

    @Override
    public void visitDocument(File file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new FileReader(file)) {
            visitDocument(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitDocument(Path file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            visitDocument(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitDocument(URL url, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            visitDocument(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitElement(String tsonString, TsonParserVisitor visitor) {
        try (StringReader is = new StringReader(tsonString)) {
            visitElement(visitor, _fromReader(is));
        }
    }

    @Override
    public void visitDocument(String tsonString, TsonParserVisitor visitor) {
        try (StringReader is = new StringReader(tsonString)) {
            visitDocument(visitor, _fromReader(is));
        }
    }

    @Override
    public <T> T read(TsonElement tson, Class<? extends T> clazz) {
        return marshaller.deserialize(tson, clazz);
    }

    private <T> T read(Class<? extends T> clazz, ITsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        switch (clazz.getName()) {
            case "net.thevpc.tson.TsonElement": {
                try {
                    source.parseElement();
                } catch (Exception e) {
                    throw new TsonParseException(e);
                }
                return (T) r.getElement();
            }
            case "net.thevpc.tson.TsonDocument": {
                try {
                    source.parseDocument();
                } catch (Exception e) {
                    throw new TsonParseException(e);
                }
                return (T) r.getDocument();
            }
        }
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e);
        }
        TsonElement elem = r.getElement();
        return marshaller.deserialize(elem, clazz);
    }

    private TsonElement readElement(ITsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e);
        }
        return r.getElement();
    }

    private TsonDocument readDocument(ITsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseDocument();
        } catch (Exception e) {
            throw new TsonParseException(e);
        }
        return r.getDocument();
    }

    private void visitElement(TsonParserVisitor r, ITsonStreamParser source) {
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e);
        }
    }

    private void visitDocument(TsonParserVisitor r, ITsonStreamParser source) {
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseDocument();
        } catch (Exception e) {
            throw new TsonParseException(e);
        }
    }

    private ITsonStreamParser _fromReader(Reader reader) {
        switch (parser){
            case "default":
            case "jflex":{
                return new CustomJFlexTsonStreamParserImpl(reader);
            }
            case "javacc":{
                return new TsonStreamParserImpl(reader);
            }
            case "custom-javacc":{
                return new CustomJavaccTsonStreamParserImpl(reader);
            }
            default: {
                throw new IllegalArgumentException("Unsupported parser " + parser);
            }
        }
    }
}
