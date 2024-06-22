package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonByteImpl extends AbstractNumberTsonElement implements TsonByte {
    private byte value;

    public TsonByteImpl(byte value,TsonNumberLayout layout,String unit) {
        super(TsonElementType.BYTE,layout,unit);
        this.value = value;
    }

    @Override
    public Number numberValue() {
        return getValue();
    }

    @Override
    public TsonByte toByte() {
        return this;
    }

    @Override
    public byte getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonByteImpl tsonByte = (TsonByteImpl) o;
        return value == tsonByte.value;
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
        return Byte.compare(value, o.toByte().getValue());
    }

    @Override
    public TsonShort toShort() {
        return Tson.of((short) getValue()).toShort();
    }

    @Override
    public TsonInt toInt() {
        return Tson.of((int) getValue()).toInt();
    }

    @Override
    public TsonLong toLong() {
        return Tson.of((long) getValue()).toLong();
    }

    @Override
    public TsonFloat toFloat() {
        return Tson.of((float) getValue()).toFloat();
    }

    @Override
    public TsonDouble toDouble() {
        return Tson.of((double) getValue()).toDouble();
    }

    @Override
    public Byte byteObject() {
        return (byte) getValue();
    }

    @Override
    public Long longObject() {
        return (long)getValue();
    }

    @Override
    public Integer intObject() {
        return (int)getValue();
    }

    @Override
    public Short shortObject() {
        return (short)getValue();
    }

    @Override
    public Float floatObject() {
        return (float)getValue();
    }

    @Override
    public Double doubleObject() {
        return (double) getValue();
    }

    @Override
    public byte byteValue() {
        return ((byte) getValue());
    }

    @Override
    public short shortValue() {
        return ((short) getValue());
    }

    @Override
    public int intValue() {
        return ((int) getValue());
    }

    @Override
    public long longValue() {
        return ((long) getValue());
    }

    @Override
    public float floatValue() {
        return ((float) getValue());
    }

    @Override
    public double doubleValue() {
        return (double) getValue();
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.type().isNumber()) {
            switch (o.type()) {
                case BYTE: {
                    int i= Byte.compare(getValue(), o.byteValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case SHORT: {
                    int i= Short.compare(getValue(), o.shortValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case INT: {
                    int i= Integer.compare(getValue(), o.intValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case LONG: {
                    int i= Long.compare(getValue(), o.longValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case FLOAT: {
                    int i= Float.compare(getValue(), o.floatValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case DOUBLE: {
                    int i= Double.compare(getValue(), o.doubleValue());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
            }
        }
        return super.compareTo(o);
    }
}
