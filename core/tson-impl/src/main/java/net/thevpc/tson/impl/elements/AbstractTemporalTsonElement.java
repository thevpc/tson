package net.thevpc.tson.impl.elements;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElement;
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

    @Override
    public int compareTo(TsonElement o) {
        if (o.type().isTemporal()) {
            switch (o.type()) {
                case LOCAL_DATETIME: {
                    int i = localDateTimeValue().compareTo(o.localDateTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case LOCAL_DATE: {
                    int i = localDateValue().compareTo(o.localDateValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case LOCAL_TIME: {
                    int i = localTimeValue().compareTo(o.localTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case INSTANT: {
                    int i = instantValue().compareTo(o.instantValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
