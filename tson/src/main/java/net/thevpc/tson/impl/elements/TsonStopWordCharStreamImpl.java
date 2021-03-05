package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonCharStream;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;
import net.thevpc.tson.impl.util.AppendableWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Objects;

public class TsonStopWordCharStreamImpl extends AbstractPrimitiveTsonElement implements TsonCharStream {

    private TsonCharStreamSource value;
    private String stopWord;

    public TsonStopWordCharStreamImpl(TsonCharStreamSource value, String stopWord) {
        super(TsonElementType.CHAR_STREAM);
        this.value = value;
        if (stopWord == null || stopWord.isEmpty()) {
            throw new IllegalArgumentException("Illegal empty stop word");
        }
        for (char c : stopWord.toCharArray()) {
            switch (c) {
                case '{':
                case '}':
                case '[':
                case ']':
                case '(':
                case ')':
                case '\"':
                case '\'': {
                    throw new IllegalArgumentException("Illegal stop word : " + stopWord);
                }
                default: {
                    if (Character.isWhitespace(c)) {
                        throw new IllegalArgumentException("Illegal stop word : " + stopWord);
                    }
                }
            }
        }
        this.stopWord = stopWord;
    }

    @Override
    public String getStreamType() {
        return stopWord;
    }

    @Override
    public Reader getValue() {
        return value.open();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TsonStopWordCharStreamImpl that = (TsonStopWordCharStreamImpl) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return this == o ? 1 : 0;
    }

    @Override
    public TsonCharStream toCharStream() {
        return this;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        try (AppendableWriter w = AppendableWriter.of(sb)) {
            try (Reader r = getValue()) {
                char[] b = new char[1024];
                int c;
                while ((c = r.read(b)) > 0) {
                    w.write(b, 0, c);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return sb.toString();
    }
}
