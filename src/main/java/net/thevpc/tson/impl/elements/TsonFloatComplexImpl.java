package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class TsonFloatComplexImpl extends AbstractNumberTsonElement implements TsonFloatComplex {
    private float real;
    private float imag;

    public TsonFloatComplexImpl(float real, float imag) {
        super(TsonElementType.FLOAT_COMPLEX);
        this.real = real;
        this.imag = imag;
    }

    @Override
    public Number getNumber() {
        return new TsonComplex(real, imag);
    }

    @Override
    public TsonFloatComplex toFloatComplex() {
        return this;
    }

    @Override
    public TsonDoubleComplex toDoubleComplex() {
        return new TsonDoubleComplexImpl(getReal(),getImag());
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        return new TsonBigDecimalImpl(BigDecimal.valueOf(getReal()));
    }

    @Override
    public float getReal() {
        return real;
    }

    @Override
    public float getImag() {
        return imag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonFloatComplexImpl tsonInt = (TsonFloatComplexImpl) o;
        return real == (tsonInt.real) && imag == (tsonInt.imag);
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
        return BigDecimal.valueOf(getReal());
    }

    @Override
    public BigInteger getBigInteger() {
        return getBigDecimal().toBigInteger();
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
        return compare(this, o.toFloatComplex());
    }

    public static int compare(TsonFloatComplex a, TsonFloatComplex oc) {
        int c = Float.compare(a.getReal(), oc.getReal());
        if (c != 0) {
            return c;
        }
        c = Float.compare(a.getImag(), oc.getImag());
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
                case DOUBLE:{
                    int i = this.toDoubleComplex().compareTo(o.toDoubleComplex());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case BIG_DECIMAL:
                case BIG_COMPLEX: {
                    int i = this.toBigComplex().compareTo(o.toBigComplex());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
            }
        }
        return super.compareTo(o);
    }

    private static class TsonComplex extends Number {
        private float real;
        private float image;

        public TsonComplex(float real, float image) {
            this.real = real;
            this.image = image;
        }

        public double getReal() {
            return real;
        }

        public double getImage() {
            return image;
        }

        @Override
        public int intValue() {
            return (int) real;
        }

        @Override
        public long longValue() {
            return (long) real;
        }

        @Override
        public float floatValue() {
            return (float) real;
        }

        @Override
        public double doubleValue() {
            return (double) real;
        }

        @Override
        public String toString() {
            return "TsonFloatComplex{" +
                    "real=" + real +
                    ", image=" + image +
                    '}';
        }
    }
}
