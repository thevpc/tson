package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonFloatImpl extends AbstractNumberTsonElement implements TsonFloat {
    private float value;

    public TsonFloatImpl(float value) {
        super(TsonElementType.FLOAT);
        this.value = value;
    }

    @Override
    public Number getNumber() {
        return getValue();
    }

    @Override
    public TsonFloat toFloat() {
        return this;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonFloatImpl tsonFloat = (TsonFloatImpl) o;
        return Float.compare(tsonFloat.value, value) == 0;
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
    public TsonByte toByte() {
        return Tson.elem((byte) getValue()).toByte();
    }

    @Override
    public TsonShort toShort() {
        return Tson.elem((short) getValue()).toShort();
    }

    @Override
    public TsonInt toInt() {
        return Tson.elem((int) getValue()).toInt();
    }

    @Override
    public TsonLong toLong() {
        return Tson.elem((long) getValue()).toLong();
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
    protected int compareCore(TsonElement o) {
        return Float.compare(value, o.toFloat().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        long lon=Long.MAX_VALUE;
        float t=Float.MAX_VALUE;
        float v = lon + t;
        if (o.getType().isNumber()) {
            switch (o.getType()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
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
