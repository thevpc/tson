package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class TsonBigDecimalImpl extends AbstractNumberTsonElement implements TsonBigDecimal {
    private BigDecimal value;

    public TsonBigDecimalImpl(BigDecimal value) {
        super(TsonElementType.BIG_DECIMAL);
        this.value = value;
    }

    @Override
    public Number getNumber() {
        return getValue();
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        return this;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonBigDecimalImpl tsonInt = (TsonBigDecimalImpl) o;
        return value.equals(tsonInt.value);
    }

    @Override
    public TsonByte toByte() {
        return Tson.elem(getNumber().byteValue()).toByte();
    }

    @Override
    public TsonShort toShort() {
        return Tson.elem(getNumber().shortValue()).toShort();
    }

    @Override
    public TsonLong toLong() {
        return Tson.elem(getNumber().longValue()).toLong();
    }

    @Override
    public TsonFloat toFloat() {
        return Tson.elem(getNumber().floatValue()).toFloat();
    }

    @Override
    public TsonDouble toDouble() {
        return Tson.elem(getNumber().doubleValue()).toDouble();
    }

    @Override
    public Byte getByteObject() {
        return getNumber().byteValue();
    }

    @Override
    public Long getLongObject() {
        return getNumber().longValue();
    }

    @Override
    public Integer getIntObject() {
        return getNumber().intValue();
    }

    @Override
    public Short getShortObject() {
        return getNumber().shortValue();
    }

    @Override
    public Float getFloatObject() {
        return getNumber().floatValue();
    }

    @Override
    public Double getDoubleObject() {
        return getNumber().doubleValue();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return getValue();
    }

    @Override
    public BigInteger getBigInteger() {
        return getValue().toBigInteger();
    }


    public TsonBigInt toBigInt(){
        return new TsonBigIntImpl(getBigInteger());
    }

    @Override
    public byte getByte() {
        return getNumber().byteValue();
    }

    @Override
    public short getShort() {
        return getNumber().shortValue();
    }

    @Override
    public int getInt() {
        return getNumber().intValue();
    }

    @Override
    public long getLong() {
        return getNumber().longValue();
    }

    @Override
    public float getFloat() {
        return getNumber().floatValue();
    }

    @Override
    public double getDouble() {
        return getNumber().doubleValue();
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
        return value.compareTo(o.toBigDecimal().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.getType().isNumber()) {
            switch (o.getType()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case BIG_INT:
                case FLOAT:
                case DOUBLE:
                case BIG_DECIMAL:
                    {
                    int i= getBigDecimal().compareTo(o.getBigDecimal());
                    return i == 0 ? getType().compareTo(o.getType()):i;
                }
            }
        }
        return super.compareTo(o);
    }
}
