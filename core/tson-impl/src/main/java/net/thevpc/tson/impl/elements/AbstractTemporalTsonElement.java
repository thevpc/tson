package net.thevpc.tson.impl.elements;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonString;

public abstract class AbstractTemporalTsonElement extends AbstractPrimitiveTsonElement{
    public AbstractTemporalTsonElement(TsonElementType type) {
        super(type);
    }
    @Override
    public TsonString toStr() {
        return (TsonString) Tson.of(String.valueOf(temporalValue()));
    }
}
