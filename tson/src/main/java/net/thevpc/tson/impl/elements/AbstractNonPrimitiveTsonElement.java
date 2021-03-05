package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public abstract class AbstractNonPrimitiveTsonElement extends AbstractTsonElement {
    public AbstractNonPrimitiveTsonElement(TsonElementType type) {
        super(type);
    }

    protected <T> T throwPrimitive(TsonElementType type){
        throw new ClassCastException(getType()+" is not a primitive. Cannot cast to "+type);
    }

    protected <T> T throwNonPrimitive(TsonElementType type){
        throw new ClassCastException(getType()+" cannot be cast to "+type);
    }

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
    public Number getNumber() {
        return throwNonPrimitive(TsonElementType.DOUBLE);
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
}
