package net.thevpc.tson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.regex.Pattern;

public interface TsonElement extends TsonElementBase, Comparable<TsonElement> {
    static TsonElement of(TsonElementBase any) {
        return any == null ? Tson.ofNull() : any.build();
    }

    int annotationsCount();

    TsonAnnotation[] annotations();

    TsonComments comments();

    TsonString toStr();

    TsonNumber toNumber();

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

    TsonContainer toContainer();

    TsonBinaryStream toBinaryStream();

    TsonCharStream toCharStream();

    TsonObject toObject();

    TsonUplet toUplet();

    TsonPair toPair();

    TsonBinOp toBinOp();

    TsonBigInt toBigInt();

    TsonBigDecimal toBigDecimal();

    TsonBigComplex toBigComplex();

    TsonFloatComplex toFloatComplex();

    TsonDoubleComplex toDoubleComplex();

    String stringValue();

    boolean booleanValue();

    char charValue();

    byte byteValue();

    int intValue();

    long longValue();

    short shortValue();

    float floatValue();

    double doubleValue();

    BigInteger getBigInteger();

    BigDecimal getBigDecimal();

    Boolean booleanObject();

    Character charObject();

    Number numberValue();

    Temporal temporalValue();

    Byte byteObject();

    Integer intObject();

    Long longObject();

    Short shortObject();

    Float floatObject();

    Double doubleObject();

    Instant dateTimeValue();

    LocalDate dateValue();

    LocalTime time();

    Pattern regexValue();

    TsonElementBuilder builder();

    boolean visit(TsonDocumentVisitor visitor);

    void visit(TsonParserVisitor visitor);

    @Override
    default TsonElement build() {
        return this;
    }

    boolean isNull();

    boolean isContainer();

    boolean isNumber();

    boolean isBoolean();
    boolean isName();

    boolean isArray();

    boolean isNamedArray();

    boolean isObject();

    boolean isNamedObject();

    boolean isUplet();

    boolean isPair();

    boolean isSimple();

    boolean isSimplePair();

    boolean isString();

    boolean isAnyString();

    boolean isPrimitive();

    boolean isTemporal();
}
