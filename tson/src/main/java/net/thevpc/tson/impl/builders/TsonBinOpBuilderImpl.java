package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonBinOpImpl;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.impl.util.TsonUtils;

public class TsonBinOpBuilderImpl extends AbstractTsonElementBuilder<TsonBinOpBuilder> implements TsonBinOpBuilder {
    private String op;
    private TsonElement key;
    private TsonElement value;

    @Override
    public TsonElementType type() {
        return TsonElementType.BINOP;
    }

    @Override
    public TsonBinOpBuilderImpl merge(TsonPair other) {
        key = other.getKey();
        value = other.getValue();
        return this;
    }

    @Override
    public TsonBinOpBuilder reset() {
        key = null;
        value = null;
        return this;
    }

    @Override
    public TsonElement getFirst() {
        return key;
    }

    @Override
    public TsonBinOpBuilder setFirst(TsonElementBase key) {
        this.key = Tson.of(key);
        return this;
    }


    @Override
    public TsonElement getSecond() {
        return value;
    }

    @Override
    public TsonBinOpBuilder setSecond(TsonElementBase value) {
        this.value = Tson.of(value);
        return this;
    }


    @Override
    public TsonElement build() {
        return TsonUtils.decorate(
                new TsonBinOpImpl(
                        op,
                        key == null ? TsonNullImpl.INSTANCE : key,
                        value == null ? TsonNullImpl.INSTANCE : value
                ), getComments(), getAnnotations());
    }


}
