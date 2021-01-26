package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonBoolean;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonBooleanImpl extends AbstractPrimitiveTsonElement implements TsonBoolean {
    public static final TsonBoolean TRUE = new TsonBooleanImpl(true);
    public static final TsonBoolean FALSE = new TsonBooleanImpl(false);
    private boolean value;

    public static final TsonBoolean valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    private TsonBooleanImpl(boolean value) {
        super(TsonElementType.BOOLEAN);
        this.value = value;
    }

    @Override
    public TsonBoolean toBoolean() {
        return this;
    }

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public boolean getBoolean() {
        return getValue();
    }

    @Override
    public Boolean getBooleanObject() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonBooleanImpl that = (TsonBooleanImpl) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return Boolean.compare(value, o.toBoolean().getValue());
    }
}
