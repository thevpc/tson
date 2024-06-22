package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonFloatImpl extends AbstractNumberTsonElement implements TsonFloat {
    private float value;

    public TsonFloatImpl(float value,String unit) {
        super(TsonElementType.FLOAT,TsonNumberLayout.DECIMAL,unit);
        this.value = value;
    }

    @Override
    public Number numberValue() {
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
        return Tson.of((byte) getValue()).toByte();
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
    protected int compareCore(TsonElement o) {
        return Float.compare(value, o.toFloat().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        long lon=Long.MAX_VALUE;
        float t=Float.MAX_VALUE;
        float v = lon + t;
        if (o.type().isNumber()) {
            switch (o.type()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
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
