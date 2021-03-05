package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonIntImpl extends AbstractNumberTsonElement implements TsonInt {
    private int value;

    public TsonIntImpl(int value) {
        super(TsonElementType.INT);
        this.value = value;
    }

    @Override
    public Number getNumber() {
        return getValue();
    }

    @Override
    public TsonInt toInt() {
        return this;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonIntImpl tsonInt = (TsonIntImpl) o;
        return value == tsonInt.value;
    }

    @Override
    public TsonByte toByte() {
        return Tson.elem((byte) getValue()).toByte();
    }

    @Override
    public TsonShort toShort() {
        return Tson.elem((short) getValue()).toShort();
    }

    @Override
    public TsonLong toLong() {
        return Tson.elem((long) getValue()).toLong();
    }

    @Override
    public TsonFloat toFloat() {
        return Tson.elem((float) getValue()).toFloat();
    }

    @Override
    public TsonDouble toDouble() {
        return Tson.elem((double) getValue()).toDouble();
    }

    @Override
    public Byte getByteObject() {
        return (byte) getValue();
    }

    @Override
    public Long getLongObject() {
        return (long)getValue();
    }

    @Override
    public Integer getIntObject() {
        return (int)getValue();
    }

    @Override
    public Short getShortObject() {
        return (short)getValue();
    }

    @Override
    public Float getFloatObject() {
        return (float)getValue();
    }

    @Override
    public Double getDoubleObject() {
        return (double) getValue();
    }


    @Override
    public byte getByte() {
        return ((byte) getValue());
    }

    @Override
    public short getShort() {
        return ((short) getValue());
    }

    @Override
    public int getInt() {
        return ((int) getValue());
    }

    @Override
    public long getLong() {
        return ((long) getValue());
    }

    @Override
    public float getFloat() {
        return ((float) getValue());
    }

    @Override
    public double getDouble() {
        return (double) getValue();
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
        return Integer.compare(value, o.toInt().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.getType().isNumber()) {
            switch (o.getType()) {
                case BYTE:
                case SHORT:
                case INT: {
                    int i= Integer.compare(getValue(), o.getInt());
                    return i == 0 ? getType().compareTo(o.getType()):i;
                }
                case LONG: {
                    int i= Long.compare(getValue(), o.getLong());
                    return i == 0 ? getType().compareTo(o.getType()):i;
                }
                case FLOAT: {
                    int i= Float.compare(getValue(), o.getFloat());
                    return i == 0 ? getType().compareTo(o.getType()):i;
                }
                case DOUBLE: {
                    int i= Double.compare(getValue(), o.getDouble());
                    return i == 0 ? getType().compareTo(o.getType()):i;
                }
            }
        }
        return super.compareTo(o);
    }
}
