package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonDocumentHeaderImpl;
import net.thevpc.tson.impl.elements.TsonDocumentImpl;
import net.thevpc.tson.*;

public class TsonDocumentBuilderImpl implements TsonDocumentBuilder {
    private TsonDocumentHeader header;
    private TsonElement value;

    @Override
    public TsonDocumentHeader getHeader() {
        return header;
    }

    @Override
    public TsonDocumentBuilderImpl setHeader(TsonDocumentHeader header) {
        this.header = header;
        return this;
    }

    @Override
    public TsonElement getValue() {
        return value;
    }

    @Override
    public TsonDocumentBuilderImpl setValue(TsonElementBase value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonDocumentHeader header() {
        return getHeader();
    }

    @Override
    public TsonDocumentBuilder header(TsonDocumentHeader header) {
        return setHeader(header);
    }

    @Override
    public TsonDocumentBuilder content(TsonElementBase value) {
        return setValue(value);
    }

    @Override
    public TsonElement content() {
        return getValue();
    }

    @Override
    public TsonDocument build() {
        return new TsonDocumentImpl(header == null ? new TsonDocumentHeaderImpl() : header, value == null ? Tson.nullElem() : value);
    }

    @Override
    public String toString() {
        return build().toString();
    }

    @Override
    public String toString(boolean compact) {
        return build().toString(compact);
    }

    @Override
    public String toString(TsonFormat format) {
        return build().toString(format);
    }

}
