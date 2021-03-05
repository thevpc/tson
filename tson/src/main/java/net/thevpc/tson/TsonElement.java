package net.thevpc.tson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public interface TsonElement extends TsonElementBase, Comparable<TsonElement> {

    int getAnnotationsCount();

    TsonAnnotation[] getAnnotations();

    String getComments();

    TsonString toStr();

    TsonLong toLong();

    TsonInt toInt();

    TsonFloat toFloat();

    TsonDouble toDouble();

    TsonShort toShort();

    TsonByte toByte();

    TsonChar toChar();

    TsonBoolean toBoolean();

    TsonFunction toFunction();

    TsonName toName();

    TsonAlias toAlias();

    TsonDate toDate();

    TsonDateTime toDateTime();

    TsonTime toTime();

    TsonRegex toRegex();

    TsonMatrix toMatrix();

    TsonArray toArray();

    TsonBinaryStream toBinaryStream();

    TsonCharStream toCharStream();

    TsonObject toObject();

    TsonUplet toUplet();

    TsonPair toKeyValue();

    TsonBigInt toBigInt();

    TsonBigDecimal toBigDecimal();

    TsonBigComplex toBigComplex();

    TsonFloatComplex toFloatComplex();

    TsonDoubleComplex toDoubleComplex();

    String getString();

    boolean getBoolean();

    char getChar();

    byte getByte();

    int getInt();

    long getLong();

    short getShort();

    float getFloat();

    double getDouble();

    BigInteger getBigInteger();

    BigDecimal getBigDecimal();

    Boolean getBooleanObject();

    Character getCharObject();

    Number getNumber();

    Byte getByteObject();

    Integer getIntObject();

    Long getLongObject();

    Short getShortObject();

    Float getFloatObject();

    Double getDoubleObject();

    Instant getDateTime();

    LocalDate getDate();

    LocalTime getTime();

    Pattern getRegex();

    TsonElementBuilder builder();

    boolean visit(TsonDocumentVisitor visitor);

    void visit(TsonParserVisitor visitor);

    @Override
    default TsonElement build() {
        return this;
    }

    boolean isNull();
}
