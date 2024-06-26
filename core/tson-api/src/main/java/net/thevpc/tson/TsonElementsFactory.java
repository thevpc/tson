package net.thevpc.tson;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

public interface TsonElementsFactory {
    TsonSerializer serializer();

    TsonReader reader(TsonSerializer serializer);

    TsonElement ofNull();

    TsonElement ofBoolean(boolean val);

    TsonElement ofString(String value, TsonStringLayout layout);

    TsonElement ofElement(TsonElementBase value);

    TsonElementBase ofElementBase(TsonElementBase value);

    TsonElement ofString(String value);

    TsonElement ofDatetime(Instant value);

    TsonElement ofDatetime(Date value);

    TsonElement ofDate(LocalDate value);

    TsonElement ofTime(LocalTime value);

    TsonElement ofTime(java.sql.Time value);

    TsonElement ofDate(java.sql.Date value);

    TsonElement ofRegex(Pattern value);

    TsonElement ofRegex(String value);

    TsonElement ofChar(char value);

    TsonElement ofInt(int value);

    TsonElement ofInt(int value, TsonNumberLayout layout);

    TsonElement ofInt(int value, TsonNumberLayout layout, String unit);

    TsonElement ofLong(long value);

    TsonElement ofLong(long value, TsonNumberLayout layout);

    TsonElement ofLong(long value, TsonNumberLayout layout, String unit);

    TsonElement ofByte(byte value, TsonNumberLayout layout);

    TsonElement ofByte(byte value, TsonNumberLayout layout, String unit);

    TsonElement ofByte(byte value);

    TsonElement ofShort(short value, TsonNumberLayout layout);

    TsonElement ofShort(short value, TsonNumberLayout layout, String unit);

    TsonElement ofShort(short value);

    TsonElement ofFloat(float value);

    TsonElement ofFloat(float value, String unit);

    TsonElement ofBigInt(BigInteger value);

    TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout);

    TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout, String unit);

    TsonElement ofBigDecimal(BigDecimal value);

    TsonElement ofBigDecimal(BigDecimal value, String unit);

    TsonElement ofBigComplex(BigDecimal real, BigDecimal imag);

    TsonElement ofBigComplex(BigDecimal real, BigDecimal imag, String unit);

    TsonElement ofFloatComplex(float real, float imag);

    TsonElement ofFloatComplex(float real, float imag, String unit);

    TsonElement ofDoubleComplex(double real, double imag);

    TsonElement ofDoubleComplex(double real, double imag, String unit);

    TsonElement ofDouble(double value);

    TsonElement ofDouble(double value, String unit);

    TsonElement ofName(String value);

    TsonElement ofAlias(String value);

    TsonPair ofPair(TsonElementBase key, TsonElementBase value);

    TsonBinOp ofBinOp(String op, TsonElementBase key, TsonElementBase value);

    TsonPair ofPair(String key, TsonElementBase value);

    TsonElement of(boolean value);

    TsonElement of(char value);

    TsonElement of(byte value);

    TsonElement of(short value);

    TsonElement of(int value);

    TsonElement of(long value);

    TsonElement of(float value);

    TsonElement of(double value);

    TsonElement of(BigInteger value);

    TsonElement of(BigDecimal value);

    TsonElement of(byte[] value);

    TsonElement of(InputStream value);

    TsonElement ofBinStream(byte[] value);

    TsonElement ofBinStream(InputStream value);

    TsonElement ofBinStream(File value);

    TsonElement ofBinStream(Path value);

    TsonElement ofCharStream(char[] value, String type);

    TsonElement ofCharStream(Reader value, String type);

    TsonElement ofCharStream(File value, String type);

    TsonElement ofCharStream(String value);

    TsonElement ofCharStream(char[] value);

    TsonElement ofCharStream(Reader value);

    TsonElement ofCharStream(File value);

    TsonElement ofCharStream(Path value);

    TsonElement ofCharStream(String value, String language);

    TsonElement ofCharStream(Path value, String language);

    TsonElement ofStopStream(String value, String stopWord);

    TsonElement ofStopStream(char[] value, String stopWord);

    TsonElement ofStopStream(Reader value, String stopWord);

    TsonElement ofStopStream(File value, String stopWord);

    TsonElement ofStopStream(Path value, String stopWord);

    TsonElement of(Boolean value);

    TsonElement of(Character value);

    TsonElement of(Byte value);

    TsonElement of(Short value);

    TsonElement of(Integer value);

    TsonElement of(Long value);

    TsonElement of(Float value);

    TsonElement of(Double value);

    TsonElement of(Date value);

    TsonElement of(Instant value);

    TsonElement of(LocalDate value);

    TsonElement of(java.sql.Date value);

    TsonElement of(java.sql.Time value);

    TsonElement of(LocalTime value);

    TsonElement of(Pattern value);

    TsonElement of(String value);

    TsonPrimitiveBuilder of();

    TsonArrayBuilder ofArray(TsonElementBase... elements);

    TsonArrayBuilder ofArray();

    TsonArrayBuilder ofArray(String name);

    TsonArrayBuilder ofArray(String name, TsonElementBase[] params, TsonElementBase... elems);

    TsonMatrixBuilder ofMatrix();

    TsonMatrixBuilder ofMatrix(String name);

    TsonMatrixBuilder ofMatrix(String name, TsonElementBase[] params);

    TsonFunctionBuilder ofFunction(String name, TsonElementBase... elems);

    TsonFunctionBuilder ofFunction();

    TsonElementBuilder ofPair();

    TsonObjectBuilder ofObj();

    TsonObjectBuilder ofObj(TsonElementBase... elems);

    TsonObjectBuilder ofObj(String name);

    TsonObjectBuilder ofObj(String name, TsonElementBase[] params, TsonElementBase... elems);

    TsonUpletBuilder ofUplet();

    TsonUpletBuilder ofUplet(TsonElementBase... elements);

    TsonAnnotationBuilder ofAnnotation();

    TsonAnnotation ofAnnotation(String name, TsonElementBase... elements);

    TsonFormatBuilder format();

    TsonWriter writer(TsonSerializer serializer);

    TsonDocumentBuilder ofDocument();

    TsonDocumentHeaderBuilder ofDocumentHeader();

    TsonElement of(Enum b);

    TsonElement of(TsonElementBase b);

    TsonProcessor processor();

    TsonBinaryStreamBuilder ofBinStream();


    TsonElement parseDateTimeElem(String s);

    TsonElement parseDateElem(String s);

    TsonElement parseTimeElem(String s);

    TsonElement parseRegexElem(String s);

    TsonElement parseNumber(String s);

    TsonElement parseChar(String s);

    TsonElement parseString(String s);

    TsonElement parseAliasElem(String s);

    TsonComment parseComments(String c);

    CharStreamCodeSupport charStreamCodeSupportOf(String language);
}
