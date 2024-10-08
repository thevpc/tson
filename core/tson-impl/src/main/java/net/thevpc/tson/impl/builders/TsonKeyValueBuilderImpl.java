package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.impl.elements.TsonPairImpl;
import net.thevpc.tson.impl.util.TsonUtils;

public class TsonKeyValueBuilderImpl extends AbstractTsonElementBuilder<TsonKeyValueBuilder> implements TsonKeyValueBuilder {
    private TsonElement key;
    private TsonElement value;

    @Override
    public TsonElementType type() {
        return TsonElementType.PAIR;
    }

    @Override
    public TsonKeyValueBuilder merge(TsonPair other) {
        key = other.key();
        value = other.value();
        return this;
    }

    @Override
    public TsonKeyValueBuilder reset() {
        key = null;
        value = null;
        return this;
    }

    @Override
    public TsonElement key() {
        return key;
    }

    @Override
    public TsonKeyValueBuilder setKey(TsonElementBase key) {
        this.key = Tson.of(key);
        return this;
    }


    @Override
    public TsonElement value() {
        return value;
    }

    @Override
    public TsonKeyValueBuilder setValue(TsonElementBase value) {
        this.value = Tson.of(value);
        return this;
    }


    @Override
    public TsonElement build() {
        return TsonUtils.decorate(
                new TsonPairImpl(
                        key == null ? TsonNullImpl.INSTANCE : key,
                        value == null ? TsonNullImpl.INSTANCE : value
                ), getComments(), getAnnotations());
    }


}
