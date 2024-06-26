package net.thevpc.tson.impl.elements;

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

    public TsonElementType type() {
        return type;
    }

    @Override
    public TsonComments comments() {
        return null;
    }


    @Override
    public TsonAnnotation[] annotations() {
        return TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY;
    }

    @Override
    public int annotationsCount() {
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
    public Boolean booleanObject() {
        return throwPrimitive(TsonElementType.BOOLEAN);
    }

    @Override
    public Character charObject() {
        return throwPrimitive(TsonElementType.CHAR);
    }

    @Override
    public Byte byteObject() {
        return throwPrimitive(TsonElementType.BYTE);
    }

    public Number numberValue() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public Long longObject() {
        return throwPrimitive(TsonElementType.LONG);
    }

    @Override
    public Integer intObject() {
        return throwPrimitive(TsonElementType.INT);
    }

    @Override
    public Short shortObject() {
        return throwPrimitive(TsonElementType.SHORT);
    }

    @Override
    public Float floatObject() {
        return throwPrimitive(TsonElementType.FLOAT);
    }

    @Override
    public Double doubleObject() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public float floatValue() {
        return throwPrimitive(TsonElementType.FLOAT);
    }

    @Override
    public double doubleValue() {
        return throwPrimitive(TsonElementType.DOUBLE);
    }

    @Override
    public byte byteValue() {
        return throwPrimitive(TsonElementType.BYTE);
    }

    @Override
    public char charValue() {
        return throwPrimitive(TsonElementType.CHAR);
    }

    @Override
    public boolean booleanValue() {
        return throwPrimitive(TsonElementType.BOOLEAN);
    }

    @Override
    public int intValue() {
        return throwPrimitive(TsonElementType.INT);
    }

    @Override
    public long longValue() {
        return throwPrimitive(TsonElementType.LONG);
    }

    @Override
    public short shortValue() {
        return throwPrimitive(TsonElementType.SHORT);
    }

    @Override
    public LocalDate dateValue() {
        return throwPrimitive(TsonElementType.DATE);
    }

    @Override
    public Instant dateTimeValue() {
        return throwPrimitive(TsonElementType.DATETIME);
    }

    @Override
    public LocalTime time() {
        return throwPrimitive(TsonElementType.TIME);
    }

    @Override
    public Pattern regexValue() {
        return throwPrimitive(TsonElementType.REGEX);
    }

    @Override
    public String stringValue() {
        return throwPrimitive(TsonElementType.STRING);
    }

    @Override
    public TsonFunction toFunction() {
        return (TsonFunction) this;
    }

    @Override
    public TsonNumber toNumber() {
        throw new ClassCastException(type() + " is not a number");
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
    public TsonPair toPair() {
        return throwNonPrimitive(TsonElementType.PAIR);
    }

    @Override
    public TsonBinOp toBinOp() {
        return throwNonPrimitive(TsonElementType.BINOP);
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
        TsonElementType t1 = type();
        TsonElementType t2 = o.type();
        int i = t1.compareTo(t2);
        if (i != 0) {
            return i;
        }
        i = compareCore(o);
        if (i != 0) {
            return i;
        }
        i = TsonUtils.compareArrays(annotations(), o.annotations());
        if (i != 0) {
            return i;
        }
        TsonComments c1 = comments();
        TsonComments c2 = o.comments();
        if (c1 == null) {
            c1 = TsonComments.BLANK;
        }
        if (c2 == null) {
            c2 = TsonComments.BLANK;
        }
        return c1.compareTo(c2);
    }

    protected abstract int compareCore(TsonElement o);

    @Override
    public boolean isNull() {
        return false;
    }


    @Override
    public TsonContainer toContainer() {
        throw new ClassCastException(type() + " cannot be cast to Container");
    }

}
