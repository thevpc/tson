package net.thevpc.tson.impl.elements;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonFormat;

public abstract class AbstractTsonElementBase implements TsonElement {

    @Override
    public String toString() {
        return Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(boolean compact) {
        return compact ? Tson.COMPACT_FORMAT.format(this) : Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(TsonFormat format) {
        return format == null ? Tson.DEFAULT_FORMAT.format(this) : format.format(this);
    }

    @Override
    public boolean isContainer() {
        return type().isContainer();
    }

    @Override
    public boolean isNumber() {
        return type().isNumber();
    }

    @Override
    public boolean isPrimitive() {
        return type().isPrimitive();
    }

    @Override
    public boolean isTemporal() {
        return type().isTemporal();
    }
}
