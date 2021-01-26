package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class TsonBigComplexImpl extends AbstractNumberTsonElement implements TsonBigComplex {
    private BigDecimal real;
    private BigDecimal imag;

    public TsonBigComplexImpl(BigDecimal real, BigDecimal imag) {
        super(TsonElementType.BIG_COMPLEX);
        this.real = real;
        this.imag = imag;
    }

    @Override
    public Number getNumber() {
        return new TsonComplex(real, imag);
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        return new TsonBigDecimalImpl(getReal());
    }

    @Override
    public BigDecimal getReal() {
        return real;
    }

    @Override
    public BigDecimal getImag() {
        return imag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonBigComplexImpl tsonInt = (TsonBigComplexImpl) o;
        return real.equals(tsonInt.real) && imag.equals(tsonInt.imag);
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
        return getReal();
    }

    @Override
    public BigInteger getBigInteger() {
        return getReal().toBigInteger();
    }


    public TsonBigInt toBigInt() {
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
        return Objects.hash(super.hashCode(), real, imag);
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return compare(this, o.toBigComplex());
    }

    public static int compare(TsonBigComplex a, TsonBigComplex oc) {
        int c = a.getReal().compareTo(oc.getReal());
        if (c != 0) {
            return c;
        }
        c = a.getImag().compareTo(oc.getImag());
        if (c != 0) {
            return c;
        }
        return c;
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
                case BIG_COMPLEX: {
                    TsonBigComplex bce = o.toBigComplex();
                    int i = compare(this, o.toBigComplex());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
            }
        }
        return super.compareTo(o);
    }

    private static class TsonComplex extends Number {
        private BigDecimal real;
        private BigDecimal image;

        public TsonComplex(BigDecimal real, BigDecimal image) {
            this.real = real;
            this.image = image;
        }

        public BigDecimal getReal() {
            return real;
        }

        public BigDecimal getImage() {
            return image;
        }

        @Override
        public int intValue() {
            return real.intValue();
        }

        @Override
        public long longValue() {
            return real.longValue();
        }

        @Override
        public float floatValue() {
            return real.floatValue();
        }

        @Override
        public double doubleValue() {
            return real.doubleValue();
        }

        @Override
        public String toString() {
            return "TsonBigComplex{" +
                    "real=" + real +
                    ", image=" + image +
                    '}';
        }
    }
}
