package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonCharStream;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;
import net.thevpc.tson.impl.parser.CharStreamCodeSupports;
import net.thevpc.tson.impl.util.AppendableWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Objects;

public class TsonCharStreamImpl extends AbstractPrimitiveTsonElement implements TsonCharStream {

    private TsonCharStreamSource value;
    private String streamType;

    public TsonCharStreamImpl(TsonCharStreamSource value, String language) {
        super(TsonElementType.CHAR_STREAM);
        this.value = value;
        CharStreamCodeSupports.of(language);
        this.streamType = language == null ? "" : "(" + language + ")";
    }

    @Override
    public String getStreamType() {
        return streamType;
    }

    @Override
    public Reader value() {
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
        TsonCharStreamImpl that = (TsonCharStreamImpl) o;
        return Objects.equals(value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value());
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().copyFrom(this);
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
    public String stringValue() {
        StringBuilder sb = new StringBuilder();
        try (AppendableWriter w = AppendableWriter.of(sb)) {
            try (Reader r = value()) {
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
