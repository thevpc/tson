package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class TsonNullImpl extends AbstractTsonElementBase implements TsonNull {
    public static final TsonNull INSTANCE = new TsonNullImpl();

    public TsonNullImpl() {
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    public TsonElementType getType() {
        return TsonElementType.NULL;
    }

    @Override
    public String getString() {
        return null;
    }

    @Override
    public Boolean getBooleanObject() {
        return null;
    }

    @Override
    public Character getCharObject() {
        return null;
    }

    @Override
    public Byte getByteObject() {
        return null;
    }

    @Override
    public Integer getIntObject() {
        return null;
    }

    @Override
    public Long getLongObject() {
        return null;
    }

    @Override
    public Short getShortObject() {
        return null;
    }

    @Override
    public Float getFloatObject() {
        return null;
    }

    @Override
    public Number getNumber() {
        return null;
    }

    @Override
    public Double getDoubleObject() {
        return null;
    }

    @Override
    public short getShort() {
        throw new NullPointerException();
    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public TsonDateTime toDateTime() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public Instant getDateTime() {
        return null;
    }

    @Override
    public LocalTime getTime() {
        return null;
    }

    @Override
    public Pattern getRegex() {
        return null;
    }

    @Override
    public float getFloat() {
        throw new NullPointerException();
    }

    @Override
    public double getDouble() {
        throw new NullPointerException();
    }

    @Override
    public byte getByte() {
        throw new NullPointerException();
    }

    @Override
    public char getChar() {
        throw new NullPointerException();
    }

    @Override
    public boolean getBoolean() {
        throw new NullPointerException();
    }

    @Override
    public int getInt() {
        throw new NullPointerException();
    }

    @Override
    public long getLong() {
        throw new NullPointerException();
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
    //element implementation

    @Override
    public TsonPair toKeyValue() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonString toStr() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonLong toLong() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonInt toInt() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonFloat toFloat() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonDouble toDouble() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonShort toShort() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonByte toByte() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonChar toChar() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonBoolean toBoolean() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonFunction toFunction() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonName toName() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonAlias toAlias() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonDate toDate() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonTime toTime() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonRegex toRegex() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonArray toArray() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonObject toObject() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonUplet toUplet() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonCharStream toCharStream() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonBigInt toBigInt() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonBigComplex toBigComplex() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonFloatComplex toFloatComplex() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonDoubleComplex toDoubleComplex() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonBinaryStream toBinaryStream() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public TsonMatrix toMatrix() {
        throw new ClassCastException("Cannot cast Null to Non Null type");
    }

    @Override
    public BigInteger getBigInteger() {
        return null;
    }

    @Override
    public BigDecimal getBigDecimal() {
        return null;
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        return true;
    }

    @Override
    public String toString() {
        return Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(boolean compact) {
        return compact ? Tson.COMPACT_FORMAT.format(this) : Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(TsonFormat format) {
        return format == null ? Tson.DEFAULT_FORMAT.format(this) : format.format(this);
    }

    @Override
    public int hashCode() {
        return 96;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TsonNull;
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.getType() == TsonElementType.NULL) {
            return 0;
        }
        return -1;
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        visitor.visitPrimitiveEnd(this);
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
