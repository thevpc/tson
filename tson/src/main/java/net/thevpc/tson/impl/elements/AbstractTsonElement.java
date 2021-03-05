package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class AbstractTsonElement extends AbstractTsonElementBase {
    private TsonElementType type;

    public AbstractTsonElement(TsonElementType type) {
        this.type = type;
    }

    public TsonElementType getType() {
        return type;
    }

    @Override
    public String getComments() {
        return null;
    }

    @Override
    public TsonAnnotation[] getAnnotations() {
        return TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY;
    }

    @Override
    public int getAnnotationsCount() {
        return 0;
    }

    protected abstract <T> T throwPrimitive(TsonElementType type);

    protected abstract <T> T throwNonPrimitive(TsonElementType type);

    @Override
    public TsonString toStr() {
        return throwPrimitive(TsonElementType.STRING);
    }


    @Override
    public TsonLong toLong() {
        return throwPrimitive(TsonElementType.LONG);
    }

    @Override
    public TsonInt toInt() {
        return throwPrimitive(TsonElementType.INT);
    }

    @Override
    public TsonFloat toFloat() {
        return throwPrimitive(TsonElementType.FLOAT);
    }

    @Override
    public TsonDouble toDouble() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public TsonShort toShort() {
        return throwPrimitive(TsonElementType.SHORT);
    }

    @Override
    public TsonByte toByte() {
        return throwPrimitive(TsonElementType.BYTE);
    }

    @Override
    public TsonChar toChar() {
        return throwPrimitive(TsonElementType.CHAR);
    }

    @Override
    public TsonBoolean toBoolean() {
        return throwPrimitive(TsonElementType.BOOLEAN);
    }

    @Override
    public TsonName toName() {
        return throwPrimitive(TsonElementType.NAME);
    }

    @Override
    public TsonAlias toAlias() {
        return throwPrimitive(TsonElementType.ALIAS);
    }

    @Override
    public TsonDate toDate() {
        return throwPrimitive(TsonElementType.DATE);
    }

    @Override
    public TsonDateTime toDateTime() {
        return throwPrimitive(TsonElementType.DATETIME);
    }

    @Override
    public TsonTime toTime() {
        return throwPrimitive(TsonElementType.TIME);
    }

    @Override
    public TsonRegex toRegex() {
        return throwPrimitive(TsonElementType.REGEX);
    }

    @Override
    public Boolean getBooleanObject() {
        return throwPrimitive(TsonElementType.BOOLEAN);
    }

    @Override
    public Character getCharObject() {
        return throwPrimitive(TsonElementType.CHAR);
    }

    @Override
    public Byte getByteObject() {
        return throwPrimitive(TsonElementType.BYTE);
    }

    public Number getNumber() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public Long getLongObject() {
        return throwPrimitive(TsonElementType.LONG);
    }

    @Override
    public Integer getIntObject() {
        return throwPrimitive(TsonElementType.INT);
    }

    @Override
    public Short getShortObject() {
        return throwPrimitive(TsonElementType.SHORT);
    }

    @Override
    public Float getFloatObject() {
        return throwPrimitive(TsonElementType.FLOAT);
    }

    @Override
    public Double getDoubleObject() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public float getFloat() {
        return throwPrimitive(TsonElementType.FLOAT);
    }

    @Override
    public double getDouble() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public byte getByte() {
        return throwPrimitive(TsonElementType.BYTE);
    }

    @Override
    public char getChar() {
        return throwPrimitive(TsonElementType.CHAR);
    }

    @Override
    public boolean getBoolean() {
        return throwPrimitive(TsonElementType.BOOLEAN);
    }

    @Override
    public int getInt() {
        return throwPrimitive(TsonElementType.INT);
    }

    @Override
    public long getLong() {
        return throwPrimitive(TsonElementType.LONG);
    }

    @Override
    public short getShort() {
        return throwPrimitive(TsonElementType.SHORT);
    }

    @Override
    public LocalDate getDate() {
        return throwPrimitive(TsonElementType.DATE);
    }

    @Override
    public Instant getDateTime() {
        return throwPrimitive(TsonElementType.DATETIME);
    }

    @Override
    public LocalTime getTime() {
        return throwPrimitive(TsonElementType.TIME);
    }

    @Override
    public Pattern getRegex() {
        return throwPrimitive(TsonElementType.REGEX);
    }

    @Override
    public String getString() {
        return throwPrimitive(TsonElementType.STRING);
    }

    @Override
    public TsonFunction toFunction() {
        return (TsonFunction) this;
    }

    @Override
    public TsonArray toArray() {
        return throwNonPrimitive(TsonElementType.ARRAY);
    }

    @Override
    public TsonMatrix toMatrix() {
        return throwNonPrimitive(TsonElementType.MATRIX);
    }

    @Override
    public TsonObject toObject() {
        return throwNonPrimitive(TsonElementType.OBJECT);
    }

    @Override
    public TsonUplet toUplet() {
        return throwNonPrimitive(TsonElementType.UPLET);
    }

    @Override
    public TsonPair toKeyValue() {
        return throwNonPrimitive(TsonElementType.PAIR);
    }

    @Override
    public TsonBinaryStream toBinaryStream() {
        return throwPrimitive(TsonElementType.BINARY_STREAM);
    }

    @Override
    public TsonCharStream toCharStream() {
        return throwPrimitive(TsonElementType.CHAR_STREAM);
    }

    @Override
    public TsonBigInt toBigInt() {
        return throwPrimitive(TsonElementType.BIG_INT);
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        return throwPrimitive(TsonElementType.BIG_DECIMAL);
    }

    @Override
    public TsonBigComplex toBigComplex() {
        return throwPrimitive(TsonElementType.BIG_COMPLEX);
    }

    @Override
    public TsonFloatComplex toFloatComplex() {
        return throwPrimitive(TsonElementType.FLOAT_COMPLEX);
    }

    @Override
    public TsonDoubleComplex toDoubleComplex() {
        return throwPrimitive(TsonElementType.DOUBLE_COMPLEX);
    }

    @Override
    public BigInteger getBigInteger() {
        return throwPrimitive(TsonElementType.BIG_INT);
    }

    @Override
    public BigDecimal getBigDecimal() {
        return throwPrimitive(TsonElementType.BIG_DECIMAL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTsonElement that = (AbstractTsonElement) o;
        return
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        return true;
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o == null) {
            o = TsonNullImpl.INSTANCE;
        }
        TsonElementType t1 = getType();
        TsonElementType t2 = o.getType();
        int i = t1.compareTo(t2);
        if (i != 0) {
            return i;
        }
        i = compareCore(o);
        if (i != 0) {
            return i;
        }
        TsonAnnotation[] a1 = getAnnotations();
        TsonAnnotation[] a2 = o.getAnnotations();
        for (int j = 0; j < Math.max(a1.length, a2.length); j++) {
            if (j >= a1.length) {
                return -1;
            }
            if (j >= a2.length) {
                return 1;
            }
            i = a1[j].compareTo(a2[j]);
            if (i != 0) {
                return i;
            }
        }
        String c1 = TsonUtils.trim(getComments());
        String c2 = TsonUtils.trim(o.getComments());
        return c1.compareTo(c2);
    }

    protected abstract int compareCore(TsonElement o);

    @Override
    public boolean isNull() {
        return false;
    }
}
