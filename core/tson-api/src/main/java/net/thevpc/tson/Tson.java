package net.thevpc.tson;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.ServiceLoader;
import java.util.regex.Pattern;

public class Tson {

    private static String VERSION = "1.0";
    private static TsonElementsFactory factory;
    private static TsonSerializer serializer;

    static {
        ServiceLoader<TsonElementsFactory> loader = ServiceLoader.load(TsonElementsFactory.class);
        for (TsonElementsFactory f : loader) {
            factory = f;
            break;
        }
        if (factory == null) {
            throw new IllegalArgumentException("Missing TsonElementsFactory");
        }
        serializer = factory.serializer();
    }

    public static final TsonFormat COMPACT_FORMAT = format().setCompact(true).build();
    public static final TsonFormat DEFAULT_FORMAT = format().build();


    public static String getVersion() {
        return VERSION;
    }

    public static TsonReader reader() {
        return factory.reader(serializer());
    }

    public static TsonReader reader(TsonSerializer serializer) {
        return factory.reader(serializer == null ? serializer() : serializer);
    }

    public static TsonElement ofTrue() {
        return of(true);
    }

    public static TsonElement ofFalse() {
        return of(false);
    }

    public static TsonElement ofNull() {
        return factory.ofNull();
    }

    public static TsonElement ofBoolean(boolean val) {
        return factory.ofBoolean(val);
    }

    public static TsonElement ofString(String value, TsonStringLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofString(value, layout == null ? TsonStringLayout.DOUBLE_QUOTE : layout);
    }

    public static TsonElement ofElement(TsonElementBase value) {
        if (value == null) {
            return ofNull();
        }
        return value.build();
    }

    public static TsonElementBase ofElementBase(TsonElementBase value) {
        if (value == null) {
            return ofNull();
        }
        return value;
    }

    public static TsonElement ofString(String value) {
        if (value == null) {
            return ofNull();
        }
        return ofString(value, TsonStringLayout.DOUBLE_QUOTE);
    }

    public static TsonElement ofDatetime(Instant value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofDatetime(value);
    }

    public static TsonElement ofDatetime(Date value) {
        if (value == null) {
            return ofNull();
        }
        return ofDatetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement ofDate(LocalDate value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofDate(value);
    }

    public static TsonElement ofTime(LocalTime value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofTime(value);
    }

    public static TsonElement ofTime(java.sql.Time value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofTime(value.toLocalTime());
    }

    public static TsonElement ofDate(java.sql.Date value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofDate(value.toLocalDate());
    }

    public static TsonElement ofRegex(Pattern value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofRegex(value);
    }

    public static TsonElement ofRegex(String value) {
        if (value == null) {
            return ofNull();
        }
        return ofRegex(Pattern.compile(value));
    }

    public static TsonElement ofChar(char value) {
        return factory.ofChar(value);
    }

    public static TsonElement ofInt(int value) {
        return ofInt(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofInt(int value, TsonNumberLayout layout) {
        return factory.ofInt(value, layout, null);
    }

    public static TsonElement ofInt(int value, TsonNumberLayout layout, String unit) {
        return factory.ofInt(value, layout, unit);
    }

    public static TsonElement ofLong(long value) {
        return ofLong(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofLong(long value, TsonNumberLayout layout) {
        return factory.ofLong(value, layout, null);
    }

    public static TsonElement ofLong(long value, TsonNumberLayout layout, String unit) {
        return factory.ofLong(value, layout, unit);
    }

    public static TsonElement ofByte(byte value, TsonNumberLayout layout) {
        return factory.ofByte(value, layout, null);
    }

    public static TsonElement ofByte(byte value, TsonNumberLayout layout, String unit) {
        return factory.ofByte(value, layout, unit);
    }

    public static TsonElement ofByte(byte value) {
        return ofByte(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofShort(short value, TsonNumberLayout layout) {
        return factory.ofShort(value, layout, null);
    }

    public static TsonElement ofShort(short value, TsonNumberLayout layout, String unit) {
        return factory.ofShort(value, layout, unit);
    }

    public static TsonElement ofShort(short value) {
        return ofShort(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofFloat(float value) {
        return factory.ofFloat(value, null);
    }

    public static TsonElement ofFloat(float value, String unit) {
        return factory.ofFloat(value, unit);
    }

    public static TsonElement ofBigInt(BigInteger value) {
        return ofBigInt(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBigInt(value, layout, null);
    }

    public static TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout, String unit) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBigInt(value, layout, unit);
    }

    public static TsonElement ofBigDecimal(BigDecimal value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBigDecimal(value, null);
    }

    public static TsonElement ofBigDecimal(BigDecimal value, String unit) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBigDecimal(value, unit);
    }

    public static TsonElement ofBigComplex(BigDecimal real, BigDecimal imag) {
        if (real == null && imag == null) {
            return ofNull();
        }
        if (real == null || imag == null) {
            throw new IllegalArgumentException("Null real or imag");
        }
        return factory.ofBigComplex(real, imag, null);
    }

    public static TsonElement ofBigComplex(BigDecimal real, BigDecimal imag, String unit) {
        if (real == null && imag == null) {
            return ofNull();
        }
        if (real == null || imag == null) {
            throw new IllegalArgumentException("Null real or imag");
        }
        return factory.ofBigComplex(real, imag, unit);
    }

    public static TsonElement ofFloatComplex(float real, float imag) {
        return factory.ofFloatComplex(real, imag, null);
    }

    public static TsonElement ofFloatComplex(float real, float imag, String unit) {
        return factory.ofFloatComplex(real, imag, unit);
    }


    public static TsonElement ofDoubleComplex(double real, double imag) {
        return factory.ofDoubleComplex(real, imag, null);
    }

    public static TsonElement ofDoubleComplex(double real, double imag, String unit) {
        return factory.ofDoubleComplex(real, imag, unit);
    }


    public static TsonElement ofDouble(double value) {
        return factory.ofDouble(value, null);
    }

    public static TsonElement ofDouble(double value, String unit) {
        return factory.ofDouble(value, unit);
    }

    public static TsonElement ofName(String value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofName(value);
    }

    public static TsonElement ofAlias(String value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofAlias(value);
    }

    public static TsonPair ofPair(TsonElementBase key, TsonElementBase value) {
        return factory.ofPair(of(key), of(value));
    }

    public static TsonBinOp binOp(String op, TsonElementBase key, TsonElementBase value) {
        return factory.ofBinOp(op, of(key), of(value));
    }

    public static TsonPair ofPair(String key, TsonElementBase value) {
        return factory.ofPair(ofName(key), of(value));
    }

    public static TsonElement of(boolean value) {
        return ofBoolean(value);
    }

    public static TsonElement of(Number value) {
        if (value == null) {
            return ofNull();
        }
        switch (value.getClass().getName()) {
            case "byte":
            case "java.lang.Byte":
                return of(value.byteValue());
            case "short":
            case "java.lang.Short":
                return of(value.shortValue());
            case "int":
            case "java.lang.Integer":
                return of(value.intValue());
            case "long":
            case "java.lang.Long":
                return of(value.longValue());
            case "float":
            case "java.lang.Float":
                return of(value.floatValue());
            case "double":
            case "java.lang.Double":
                return of(value.doubleValue());
            case "java.math.BigInteger":
                return of((java.math.BigInteger) value);
            case "java.math.BigDecimal":
                return of((java.math.BigDecimal) value);
        }
        throw new IllegalArgumentException("Unsupported number type: " + value.getClass().getName());
    }

    public static TsonElement of(char value) {
        return ofChar(value);
    }

    public static TsonElement of(byte value) {
        return ofByte(value);
    }

    public static TsonElement of(short value) {
        return ofShort(value);
    }

    public static TsonElement of(int value) {
        return ofInt(value);
    }

    public static TsonElement of(long value) {
        return ofLong(value);
    }

    public static TsonElement of(float value) {
        return ofFloat(value);
    }

    public static TsonElement of(double value) {
        return ofDouble(value);
    }

    public static TsonElement of(BigInteger value) {
        return ofBigInt(value);
    }

    public static TsonElement of(BigDecimal value) {
        return ofBigDecimal(value);
    }

    public static TsonElement of(byte[] value) {
        if (value == null) {
            return ofNull();
        }
        return ofBinStream(value);
    }

    public static TsonElement of(InputStream value) {
        if (value == null) {
            return ofNull();
        }
        return ofBinStream(value);
    }

    public static TsonBinaryStreamBuilder ofBinStream() {
        return factory.ofBinStream();
    }

    public static TsonElement ofBinStream(byte[] value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBinStream(value);
    }

    public static TsonElement ofBinStream(InputStream value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBinStream(value);
    }

    public static TsonElement ofBinStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBinStream(value);
    }

    public static TsonElement ofBinStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofBinStream(value);
    }

    private static TsonElement ofCharStream(char[] value, String type) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, type);
    }

    private static TsonElement ofCharStream(Reader value, String type) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, type);
    }

    private static TsonElement ofCharStream(File value, String type) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, type);
    }

    public static TsonElement ofCharStream(String value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, "");
    }

    public static TsonElement ofCharStream(char[] value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, "");
    }

    public static TsonElement ofCharStream(Reader value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, "");
    }

    public static TsonElement ofCharStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, "");
    }

    public static TsonElement ofCharStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, "");
    }

    public static TsonElement ofCharStream(String value, String language) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, language);
    }

    public static TsonElement ofCharStream(Path value, String language) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, language);
    }

    public static TsonElement ofStopStream(String value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, stopWord);
    }

    public static TsonElement ofStopStream(char[] value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, stopWord);
    }

    public static TsonElement ofStopStream(Reader value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, stopWord);
    }

    public static TsonElement ofStopStream(File value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, stopWord);
    }

    public static TsonElement ofStopStream(Path value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return factory.ofStopStream(value, stopWord);
    }

    public static TsonElement of(Boolean value) {
        if (value == null) {
            return ofNull();
        }
        return ofBoolean(value);
    }

    public static TsonElement of(Character value) {
        if (value == null) {
            return ofNull();
        }
        return ofChar(value);
    }

    public static TsonElement of(Byte value) {
        if (value == null) {
            return ofNull();
        }
        return ofByte(value);
    }

    public static TsonElement of(Short value) {
        if (value == null) {
            return ofNull();
        }
        return ofShort(value);
    }

    public static TsonElement of(Integer value) {
        if (value == null) {
            return ofNull();
        }
        return ofInt(value);
    }

    public static TsonElement of(Long value) {
        if (value == null) {
            return ofNull();
        }
        return ofLong(value);
    }

    public static TsonElement of(Float value) {
        if (value == null) {
            return ofNull();
        }
        return ofFloat(value);
    }

    public static TsonElement of(Double value) {
        if (value == null) {
            return ofNull();
        }
        return ofDouble(value);
    }

    public static TsonElement of(Date value) {
        if (value == null) {
            return ofNull();
        }
        if (value instanceof java.sql.Time) {
            return ofTime((Time) value);
        }
        if (value instanceof java.sql.Date) {
            return ofDate(((java.sql.Date) value).toLocalDate());
        }
        return ofDatetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement of(Instant value) {
        return ofDatetime(value);
    }

    public static TsonElement of(LocalDate value) {
        return ofDate(value);
    }

    public static TsonElement of(java.sql.Date value) {
        return ofDate(value);
    }

    public static TsonElement of(java.sql.Time value) {
        return ofTime(value);
    }

    public static TsonElement of(LocalTime value) {
        return ofTime(value);
    }

    public static TsonElement of(Pattern value) {
        return ofRegex(value);
    }

    public static TsonElement of(String value) {
        return ofString(value);
    }

    public static TsonElement of(String[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(boolean[] value) {
        if (value == null) {
            return ofNull();
        }
        TsonArrayBuilder a = ofArray();
        for (boolean b : value) {
            a.add(of(b));
        }
        return a.build();
    }

    public static TsonElement of(short[] value) {
        if (value == null) {
            return ofNull();
        }
        TsonArrayBuilder a = ofArray();
        for (short b : value) {
            a.add(of(b));
        }
        return a.build();
    }

    public static TsonElement of(float[] value) {
        if (value == null) {
            return ofNull();
        }
        TsonArrayBuilder a = ofArray();
        for (float b : value) {
            a.add(of(b));
        }
        return a.build();
    }

    public static TsonElement of(int[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).mapToObj(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(long[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).mapToObj(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(double[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).mapToObj(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }


    public static TsonElement of(Boolean[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(Short[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(Float[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(Integer[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(Long[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonElement of(Double[] value) {
        return value == null ? ofNull() : ofArray(Arrays.stream(value).map(x -> Tson.of(x)).toArray(TsonElementBase[]::new)).build();
    }

    public static TsonPrimitiveBuilder of() {
        return factory.of();
    }

    public static TsonArrayBuilder ofArray(TsonElementBase... elements) {
        return ofArray().addAll(elements);
    }

    public static TsonArrayBuilder ofArray() {
        return factory.ofArray();
    }

    public static TsonArrayBuilder ofArray(String name) {
        return ofArray().getHeader().setName(name).then();
    }

    public static TsonArrayBuilder ofArray(String name, TsonElementBase[] params, TsonElementBase... elems) {
        return ofArray().getHeader().setName(name).addAll(params).then().addAll(elems);
    }

    public static TsonArrayBuilder ofArray(String name, TsonElementBase... elems) {
        return ofArray().getHeader().setName(name).then().addAll(elems);
    }

    public static TsonMatrixBuilder ofMatrix() {
        return factory.ofMatrix();
    }

    public static TsonMatrixBuilder ofMatrix(String name) {
        return ofMatrix().getHeader().setName(name).then();
    }


    public static TsonMatrixBuilder ofMatrix(String name, TsonElementBase[] params) {
        return ofMatrix().getHeader().setName(name).addAll(params).then();
    }

    public static TsonFunctionBuilder ofFunction(String name, TsonElementBase... elems) {
        return factory.ofFunction().name(name).addAll(elems);
    }

    public static TsonFunctionBuilder ofFunction() {
        return factory.ofFunction();
    }

    public static TsonElementBuilder ofPair() {
        return factory.ofPair();
    }

    public static TsonObjectBuilder ofObj() {
        return factory.ofObj();
    }

    public static TsonObjectBuilder ofObj(TsonElementBase... elems) {
        return ofObj().addAll(elems);
    }

    public static TsonObjectBuilder ofObj(String name) {
        TsonObjectBuilder e = ofObj();
        e.getHeader().name(name);
        return e;
    }

    public static TsonObjectBuilder ofObj(String name, TsonElementBase[] params, TsonElementBase... elems) {
        TsonObjectBuilder o = ofObj();
        o.getHeader().name(name).addAll(params);
        return o.addAll(elems);
    }

    public static TsonObjectBuilder ofObj(String name, TsonElementBase... elems) {
        TsonObjectBuilder o = ofObj();
        o.getHeader().name(name);
        return o.addAll(elems);
    }

    public static TsonUpletBuilder ofUplet() {
        return factory.ofUplet();
    }

    public static TsonUpletBuilder ofUplet(TsonElementBase... elements) {
        return factory.ofUplet().addAll(elements);
    }

    public static TsonAnnotationBuilder ofAnnotation() {
        return factory.ofAnnotation();
    }

    public static TsonAnnotation ofAnnotation(String name, TsonElementBase... elements) {
        return ofAnnotation().name(name).addAll(elements).build();
    }

    public static TsonFormatBuilder format() {
        return factory.format();
    }

    public static TsonWriter writer() {
        return factory.writer(serializer);
    }

    public static TsonDocumentBuilder ofDocument() {
        return factory.ofDocument();
    }

    public static TsonDocumentHeaderBuilder ofDocumentHeader() {
        return factory.ofDocumentHeader();
    }

    public static void setSerializer(TsonSerializer newSerializer) {
        if (newSerializer == null) {
            newSerializer = factory.serializer();
        }
        serializer = newSerializer;
    }

    public static TsonSerializer serializer() {
        return serializer;
    }

    public static TsonProcessor processor() {
        return factory.processor();
    }

    public static TsonElement of(Enum b) {
        return b == null ? ofNull() : ofName(b.name());
    }

    public static TsonElement of(TsonElementBase b) {
        return b == null ? ofNull() : b.build();
    }

    public static TsonElement parseDateTime(String image) {
        return factory.parseDateTimeElem(image);
    }

    public static TsonElement parseNumber(String image) {
        return factory.parseNumber(image);
    }

    public static TsonElement parseCharElem(String image) {
        return factory.parseChar(image);
    }

    public static TsonElement parseString(String image) {
        return factory.parseString(image);
    }

    public static TsonElement parseAlias(String image) {
        return factory.parseAliasElem(image);
    }

    public static TsonElement parseDate(String image) {
        return factory.parseDateElem(image);
    }

    public static TsonElement parseTime(String image) {
        return factory.parseTimeElem(image);
    }

    public static TsonElement parseRegex(String image) {
        return factory.parseRegexElem(image);
    }

    public static TsonComment parseComments(String image) {
        return factory.parseComments(image);
    }

    public static CharStreamCodeSupport charStreamCodeSupportOf(String language) {
        return factory.charStreamCodeSupportOf(language);
    }
}
