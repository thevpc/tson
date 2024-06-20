package net.thevpc.tson.impl.parser;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.StringBuilderReader;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class TsonReaderImpl implements TsonReader {
    private static Map<String, TsonParserFactory> factories = new HashMap<>();

    static {
        ServiceLoader<TsonParserFactory> lo = ServiceLoader.load(TsonParserFactory.class);
        for (TsonParserFactory f : lo) {
            String id = f.id();
            TsonParserFactory old = factories.get(id);
            if (old == null) {
                factories.put(id, f);
            } else {
                throw new IllegalArgumentException("unable to register duplicate TsonParserFactory : " + id + " " + f.getClass() + ", " + old.getClass());
            }
        }
    }

    private TsonStreamParserConfig config = new TsonStreamParserConfig();
    private TsonSerializer marshaller;
    private String defaultParser = "javacc";
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
//                        case "jflex":
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
        return read(clazz, _fromReader(new InputStreamReader(stream), "stream"));
    }

    @Override
    public <T> T read(CharSequence string, Class<? extends T> clazz) {
        try (Reader r = createStringReader(string)) {
            return read(clazz, _fromReader(r, "stream"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public <T> T read(InputStream stream, String encoding, Class<? extends T> clazz) throws IOException {
        return read(clazz, _fromReader(new InputStreamReader(stream, encoding == null ? "UTF-8" : encoding), "stream"));
    }

    @Override
    public <T> T read(Reader reader, Class<? extends T> clazz) throws IOException {
        return read(clazz, _fromReader(reader, String.valueOf(reader)));
    }

    @Override
    public <T> T read(File file, Class<? extends T> clazz) throws IOException {
        try (Reader is = new FileReader(file)) {
            return read(clazz, _fromReader(is, file));
        }
    }

    @Override
    public <T> T read(Path file, Class<? extends T> clazz) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return read(clazz, _fromReader(is, file));
        }
    }

    @Override
    public <T> T read(URL url, Class<? extends T> clazz) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return read(clazz, _fromReader(is, url));
        }
    }

    /////////////////////////////////
    @Override
    public TsonElement readElement(InputStream stream) {
        return readElement(_fromReader(new InputStreamReader(stream), "stream"));
    }

    @Override
    public TsonElement readElement(CharSequence string) {
        try (Reader r = createStringReader(string)) {
            return readElement(_fromReader(r, "stream"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public TsonElement readElement(InputStream stream, String encoding) throws IOException {
        return readElement(_fromReader(new InputStreamReader(stream, encoding == null ? "UTF-8" : encoding), "stream"));
    }

    @Override
    public TsonElement readElement(Reader reader) {
        return readElement(_fromReader(reader, "stream"));
    }

    @Override
    public TsonElement readElement(File file) throws IOException {
        try (Reader is = new FileReader(file)) {
            return readElement(_fromReader(is, file));
        }
    }

    @Override
    public TsonElement readElement(Path file) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return readElement(_fromReader(is, file));
        }
    }

    @Override
    public TsonElement readElement(URL url) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return readElement(_fromReader(is, url));
        }
    }
    /////////////////////////////////

    @Override
    public TsonDocument readDocument(InputStream stream) {
        return readDocument(_fromReader(new InputStreamReader(stream), "stream"));
    }

    @Override
    public TsonDocument readDocument(CharSequence string) {
        try {
            try (Reader r = createStringReader(string)) {
                return readDocument(_fromReader(r, "stream"));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Reader createStringReader(CharSequence string) {
        return (string instanceof StringBuilder) ? new StringBuilderReader((StringBuilder) string) : new StringReader(string.toString());
    }

    @Override
    public TsonDocument readDocument(InputStream stream, String encoding) throws IOException {
        return readDocument(_fromReader(new InputStreamReader(stream, encoding == null ? "UTF-8" : encoding), "stream"));
    }

    @Override
    public TsonDocument readDocument(Reader reader) {
        return readDocument(_fromReader(reader, "stream"));
    }

    @Override
    public TsonDocument readDocument(File file) throws IOException {
        try (Reader is = new FileReader(file)) {
            return readDocument(_fromReader(is, file));
        }
    }

    @Override
    public TsonDocument readDocument(Path file) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            return readDocument(_fromReader(is, file));
        }
    }

    @Override
    public TsonDocument readDocument(URL url) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            return readDocument(_fromReader(is, url));
        }
    }

    /////////////////////////////////
    @Override
    public void visitElement(InputStream stream, TsonParserVisitor visitor) {
        visitElement(visitor, _fromReader(new InputStreamReader(stream), "stream"));
    }

    @Override
    public void visitElement(CharSequence string, TsonParserVisitor visitor) {
        try (Reader r = createStringReader(string)) {
            visitElement(visitor, _fromReader(r, "stream"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visitElement(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException {
        visitElement(visitor, _fromReader(new InputStreamReader(stream, encoding == null ? "UTF-8" : encoding), "stream"));
    }

    @Override
    public void visitElement(Reader reader, TsonParserVisitor visitor) {
        visitElement(visitor, _fromReader(reader, "stream"));
    }

    @Override
    public void visitElement(File file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new FileReader(file)) {
            visitElement(visitor, _fromReader(is, file));
        }
    }

    @Override
    public void visitElement(Path file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            visitElement(visitor, _fromReader(is, file));
        }
    }

    @Override
    public void visitElement(URL url, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            visitElement(visitor, _fromReader(is, url));
        }
    }

    /////////////////////////////////
    @Override
    public void visitDocument(InputStream stream, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(new InputStreamReader(stream), "stream"));
    }

    @Override
    public void visitDocument(CharSequence string, TsonParserVisitor visitor) {
        try (Reader r = createStringReader(string)) {
            visitDocument(visitor, _fromReader(r, "stream"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visitDocument(InputStream stream, String encoding, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(new InputStreamReader(stream, encoding == null ? "UTF-8" : encoding), "stream"));
    }

    @Override
    public void visitDocument(Reader reader, TsonParserVisitor visitor) throws IOException {
        visitDocument(visitor, _fromReader(reader, "stream"));
    }

    @Override
    public void visitDocument(File file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new FileReader(file)) {
            visitDocument(visitor, _fromReader(is, file));
        }
    }

    @Override
    public void visitDocument(Path file, TsonParserVisitor visitor) throws IOException {
        try (Reader is = Files.newBufferedReader(file)) {
            visitDocument(visitor, _fromReader(is, file));
        }
    }

    @Override
    public void visitDocument(URL url, TsonParserVisitor visitor) throws IOException {
        try (Reader is = new InputStreamReader(url.openStream())) {
            visitDocument(visitor, _fromReader(is, url));
        }
    }

    @Override
    public void visitElement(String tsonString, TsonParserVisitor visitor) {
        try (StringReader is = new StringReader(tsonString)) {
            visitElement(visitor, _fromReader(is, "string"));
        }
    }

    @Override
    public void visitDocument(String tsonString, TsonParserVisitor visitor) {
        try (StringReader is = new StringReader(tsonString)) {
            visitDocument(visitor, _fromReader(is, "string"));
        }
    }

    @Override
    public <T> T read(TsonElement tson, Class<? extends T> clazz) {
        return marshaller.deserialize(tson, clazz);
    }

    private <T> T read(Class<? extends T> clazz, TsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        switch (clazz.getName()) {
            case "net.thevpc.tson.TsonElement": {
                try {
                    source.parseElement();
                } catch (Exception e) {
                    throw new TsonParseException(e, source.source());
                }
                return (T) r.getElement();
            }
            case "net.thevpc.tson.TsonDocument": {
                try {
                    source.parseDocument();
                } catch (Exception e) {
                    throw new TsonParseException(e, source.source());
                }
                return (T) r.getDocument();
            }
        }
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e, source.source());
        }
        TsonElement elem = r.getElement();
        return marshaller.deserialize(elem, clazz);
    }

    private TsonElement readElement(TsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e, source.source());
        }
        return r.getElement();
    }

    private TsonDocument readDocument(TsonStreamParser source) {
        ElementBuilderTsonParserVisitor r = new ElementBuilderTsonParserVisitor();
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseDocument();
        } catch (Exception e) {
            throw new TsonParseException(e, source.source());
        }
        return r.getDocument();
    }

    private void visitElement(TsonParserVisitor r, TsonStreamParser source) {
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseElement();
        } catch (Exception e) {
            throw new TsonParseException(e, source.source());
        }
    }

    private void visitDocument(TsonParserVisitor r, TsonStreamParser source) {
        config.setVisitor(r);
        source.setConfig(config);
        try {
            source.parseDocument();
        } catch (Exception e) {
            throw new TsonParseException(e, source.source());
        }
    }

    private TsonStreamParser _fromReader(Reader reader, Object source) {
        return _fromReader(reader, parser, source);
    }

    private TsonStreamParser _fromReader(Reader reader, String parser, Object source) {
        if (parser == null || parser.equals("") || parser.equals("default")) {
            for (Map.Entry<String, TsonParserFactory> r : factories.entrySet()) {
                return r.getValue().fromReader(reader, parser, source);
            }
        }
        TsonParserFactory f = factories.get(parser);
        if (f != null) {
            return f.fromReader(reader, parser, source);
        }
        throw new IllegalArgumentException("Unsupported parser " + parser);
    }
}
