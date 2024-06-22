package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class TsonBigIntImpl extends AbstractNumberTsonElement implements TsonBigInt {
    private BigInteger value;

    public TsonBigIntImpl(BigInteger value,TsonNumberLayout layout,String unit) {
        super(TsonElementType.BIG_INT,layout,unit);
        this.value = value;
    }

    @Override
    public Number numberValue() {
        return getValue();
    }

    @Override
    public TsonBigInt toBigInt() {
        return this;
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    @Override
    public BigInteger getBigInteger() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonBigIntImpl tsonInt = (TsonBigIntImpl) o;
        return value.equals(tsonInt.value);
    }

    @Override
    public TsonByte toByte() {
        return Tson.of(this.numberValue().byteValue()).toByte();
    }

    @Override
    public TsonShort toShort() {
        return Tson.of(this.numberValue().shortValue()).toShort();
    }

    @Override
    public TsonLong toLong() {
        return Tson.of(this.numberValue().longValue()).toLong();
    }

    @Override
    public TsonFloat toFloat() {
        return Tson.of(this.numberValue().floatValue()).toFloat();
    }

    @Override
    public TsonDouble toDouble() {
        return Tson.of(this.numberValue().doubleValue()).toDouble();
    }

    @Override
    public Byte byteObject() {
        return this.numberValue().byteValue();
    }

    @Override
    public Long longObject() {
        return this.numberValue().longValue();
    }

    @Override
    public Integer intObject() {
        return this.numberValue().intValue();
    }

    @Override
    public Short shortObject() {
        return this.numberValue().shortValue();
    }

    @Override
    public Float floatObject() {
        return this.numberValue().floatValue();
    }

    @Override
    public Double doubleObject() {
        return this.numberValue().doubleValue();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal(getValue());
    }

    public TsonBigDecimal toBigDecimal(){
        return new TsonBigDecimalImpl(getBigDecimal(),unit());
    }

    @Override
    public byte byteValue() {
        return this.numberValue().byteValue();
    }

    @Override
    public short shortValue() {
        return this.numberValue().shortValue();
    }

    @Override
    public int intValue() {
        return this.numberValue().intValue();
    }

    @Override
    public long longValue() {
        return this.numberValue().longValue();
    }

    @Override
    public float floatValue() {
        return this.numberValue().floatValue();
    }

    @Override
    public double doubleValue() {
        return this.numberValue().doubleValue();
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
        return value.compareTo(o.toBigInt().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.type().isNumber()) {
            switch (o.type()) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG:
                case BIG_INT:{
                    int i= getValue().compareTo(o.getBigInteger());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
                case FLOAT:
                case DOUBLE:
                    {
                    int i= getBigDecimal().compareTo(o.getBigDecimal());
                    return i == 0 ? type().compareTo(o.type()):i;
                }
            }
        }
        return super.compareTo(o);
    }
}
