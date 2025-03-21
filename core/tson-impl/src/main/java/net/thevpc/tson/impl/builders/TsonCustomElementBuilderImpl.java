package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonCustomImpl;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.impl.util.TsonUtils;

public class TsonCustomElementBuilderImpl extends AbstractTsonElementBuilder<TsonCustomBuilder> implements TsonCustomBuilder {
    private Object value = null;

    @Override
    public TsonElementType type() {
        return TsonElementType.CUSTOM;
    }

    @Override
    public TsonCustomBuilder setCustom(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public Object getCustom() {
        return value;
    }

    @Override
    public TsonElement build() {
        return TsonUtils.decorate(
                (value == null ? TsonNullImpl.INSTANCE : new TsonCustomImpl(value))
                , comments(), annotations())
                ;
    }

}
