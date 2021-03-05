package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonBinaryStream;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;
import net.thevpc.tson.impl.util.AppendableWriter;
import net.thevpc.tson.impl.util.Base64EncoderAdapter;

import java.io.*;
import java.util.Objects;

public class TsonBinaryStreamImpl extends AbstractPrimitiveTsonElement implements TsonBinaryStream {

    private TsonBinaryStreamSource value;

    public TsonBinaryStreamImpl(TsonBinaryStreamSource value) {
        super(TsonElementType.BINARY_STREAM);
        this.value = value;
    }

    @Override
    public InputStream getValue() {
        return value.open();
    }

    @Override
    public Reader getBase64Value() {
        return getBase64Value(80);
    }

    @Override
    public Reader getBase64Value(int lineMax) {
        return new Base64EncoderAdapter(getValue(), lineMax);
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
        TsonBinaryStreamImpl that = (TsonBinaryStreamImpl) o;
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
    public TsonBinaryStream toBinaryStream() {
        return this;
    }

    @Override
    public String getString() {
        StringBuilder sb = new StringBuilder();
        try (AppendableWriter w = AppendableWriter.of(sb)) {
            try(Reader r=getBase64Value()){
                char[] b=new char[1024];
                int c;
                while((c=r.read(b))>0){
                    w.write(b,0,c);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return sb.toString();
    }
}
