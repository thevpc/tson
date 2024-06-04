package net.thevpc.tson;

import net.thevpc.tson.impl.TsonProcessorImpl;
import net.thevpc.tson.impl.builders.*;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.impl.format.TsonFormatImplBuilder;
import net.thevpc.tson.impl.format.TsonWriterImpl;
import net.thevpc.tson.impl.marshall.TsonSerializerImpl;
import net.thevpc.tson.impl.parser.TsonParserUtils;
import net.thevpc.tson.impl.parser.TsonReaderImpl;

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
import java.util.Date;
import java.util.regex.Pattern;

public class Tson {

    public static final TsonFormat COMPACT_FORMAT = format().setCompact(true).build();
    public static final TsonFormat DEFAULT_FORMAT = format().build();
    private static String VERSION = "1.0";
    private static TsonSerializer serializer = new TsonSerializerImpl();

    public static String getVersion() {
        return VERSION;
    }

    public static TsonReader reader() {
        return new TsonReaderImpl(serializer);
    }

    public static TsonElement ofNull() {
        return TsonNullImpl.INSTANCE;
    }

    public static TsonElement ofBoolean(boolean val) {
        return TsonBooleanImpl.valueOf(val);
    }

    public static TsonElement rawString(String value, TsonStringLayout layout) {
        if (value == null) {
            return ofNull();
        }
        TsonStringLayout ll = layout == null ? TsonStringLayout.DOUBLE_QUOTE : layout;
        return new TsonStringImpl(
                TsonParserUtils.parseRawString(value, ll),
                TsonParserUtils.extractRawString(value, ll),
                ll
        );
    }

    public static TsonElement ofString(String value, TsonStringLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStringImpl(value, value, layout == null ? TsonStringLayout.DOUBLE_QUOTE : layout);
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

    public static TsonElement datetime(Instant value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateTimeImpl(value);
    }

    public static TsonElement datetime(Date value) {
        if (value == null) {
            return ofNull();
        }
        return datetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement date(LocalDate value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateImpl(value);
    }

    public static TsonElement time(LocalTime value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonTimeImpl(value);
    }

    public static TsonElement time(java.sql.Time value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonTimeImpl(value.toLocalTime());
    }

    public static TsonElement date(java.sql.Date value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateImpl(value.toLocalDate());
    }

    public static TsonElement regex(Pattern value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonRegexImpl(value);
    }

    public static TsonElement regex(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonRegexImpl(Pattern.compile(value));
    }

    public static TsonElement charElem(char value) {
        return new TsonCharImpl(value);
    }

    public static TsonElement ofInt(int value) {
        return ofInt(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement ofInt(int value, TsonNumberLayout layout) {
        return new TsonIntImpl(value, layout);
    }

    public static TsonElement longElem(long value) {
        return longElem(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement longElem(long value, TsonNumberLayout layout) {
        return new TsonLongImpl(value, layout);
    }

    public static TsonElement byteElem(byte value, TsonNumberLayout layout) {
        return new TsonByteImpl(value, layout);
    }

    public static TsonElement byteElem(byte value) {
        return byteElem(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement shortElem(short value, TsonNumberLayout layout) {
        return new TsonShortImpl(value, layout);
    }

    public static TsonElement shortElem(short value) {
        return shortElem(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement floatElem(float value) {
        return new TsonFloatImpl(value);
    }

    public static TsonElement bigInt(BigInteger value) {
        return bigInt(value, TsonNumberLayout.DECIMAL);
    }

    public static TsonElement bigInt(BigInteger value, TsonNumberLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigIntImpl(value, layout);
    }

    public static TsonElement bigDecimal(BigDecimal value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigDecimalImpl(value);
    }

    public static TsonElement bigComplex(BigDecimal real, BigDecimal imag) {
        if (real == null && imag == null) {
            return ofNull();
        }
        if (real == null || imag == null) {
            throw new IllegalArgumentException("Null real or imag");
        }
        return new TsonBigComplexImpl(real, imag);
    }

    public static TsonElement floatComplex(float real, float imag) {
        return new TsonFloatComplexImpl(real, imag);
    }


    public static TsonElement doubleComplex(double real, double imag) {
        return new TsonDoubleComplexImpl(real, imag);
    }


    public static TsonElement ofDouble(double value) {
        return new TsonDoubleImpl(value);
    }

    public static TsonElement name(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonNameImpl(value);
    }

    public static TsonElement alias(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonAliasImpl(value);
    }

    public static TsonPair ofPair(TsonElementBase key, TsonElementBase value) {
        return new TsonPairImpl(of(key), of(value));
    }

    public static TsonBinOp binOp(String op, TsonElementBase key, TsonElementBase value) {
        return new TsonBinOpImpl(op, of(key), of(value));
    }

    public static TsonPair ofPair(String key, TsonElementBase value) {
        return new TsonPairImpl(name(key), of(value));
    }

    public static TsonElement of(boolean value) {
        return ofBoolean(value);
    }

    public static TsonElement of(char value) {
        return charElem(value);
    }

    public static TsonElement of(byte value) {
        return byteElem(value);
    }

    public static TsonElement of(short value) {
        return shortElem(value);
    }

    public static TsonElement of(int value) {
        return ofInt(value);
    }

    public static TsonElement of(long value) {
        return longElem(value);
    }

    public static TsonElement of(float value) {
        return floatElem(value);
    }

    public static TsonElement of(double value) {
        return ofDouble(value);
    }

    public static TsonElement of(BigInteger value) {
        return bigInt(value);
    }

    public static TsonElement of(BigDecimal value) {
        return bigDecimal(value);
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

    public static TsonElement ofBinStream(byte[] value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement ofBinStream(InputStream value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement ofBinStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement ofBinStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    private static TsonElement ofCharStream(char[] value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    private static TsonElement ofCharStream(Reader value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    private static TsonElement ofCharStream(File value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    public static TsonElement ofCharStream(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement ofCharStream(char[] value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement ofCharStream(Reader value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement ofCharStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement ofCharStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement ofCharStream(String value, String language) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    public static TsonElement ofCharStream(Path value, String language) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    public static TsonElement ofStopStream(String value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement ofStopStream(char[] value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement ofStopStream(Reader value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement ofStopStream(File value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement ofStopStream(Path value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
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
        return charElem(value);
    }

    public static TsonElement of(Byte value) {
        if (value == null) {
            return ofNull();
        }
        return byteElem(value);
    }

    public static TsonElement of(Short value) {
        if (value == null) {
            return ofNull();
        }
        return shortElem(value);
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
        return longElem(value);
    }

    public static TsonElement of(Float value) {
        if (value == null) {
            return ofNull();
        }
        return floatElem(value);
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
            return time((Time) value);
        }
        if (value instanceof java.sql.Date) {
            return date(((java.sql.Date) value).toLocalDate());
        }
        return datetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement of(Instant value) {
        return datetime(value);
    }

    public static TsonElement of(LocalDate value) {
        return date(value);
    }

    public static TsonElement of(java.sql.Date value) {
        return date(value);
    }

    public static TsonElement of(java.sql.Time value) {
        return time(value);
    }

    public static TsonElement of(LocalTime value) {
        return time(value);
    }

    public static TsonElement of(Pattern value) {
        return regex(value);
    }

    public static TsonElement of(String value) {
        return ofString(value);
    }

    public static TsonPrimitiveBuilder of() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    public static TsonArrayBuilder ofArray(TsonElementBase... elements) {
        return ofArray().addAll(elements);
    }

    public static TsonArrayBuilder ofArray() {
        return new TsonArrayBuilderImpl();
    }

    public static TsonArrayBuilder ofArray(String name) {
        return ofArray().getHeader().setName(name).then();
    }

    public static TsonArrayBuilder ofArray(String name, TsonElementBase[] params, TsonElementBase... elems) {
        return ofArray().getHeader().setName(name).addAll(params).then().addAll(elems);
    }

    public static TsonMatrixBuilder ofMatrix() {
        return new TsonMatrixBuilderImpl();
    }

    public static TsonMatrixBuilder ofMatrix(String name) {
        return ofMatrix().getHeader().setName(name).then();
    }


    public static TsonMatrixBuilder ofMatrix(String name, TsonElementBase[] params) {
        return ofMatrix().getHeader().setName(name).addAll(params).then();
    }

    public static TsonFunctionBuilder ofFunction(String name, TsonElementBase... elems) {
        return new TsonFunctionBuilderImpl().name(name).addAll(elems);
    }

    public static TsonFunctionBuilder ofFunction() {
        return new TsonFunctionBuilderImpl();
    }

    public static TsonElementBuilder ofPair() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    public static TsonObjectBuilder ofObj() {
        return new TsonObjectBuilderImpl();
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

    public static TsonUpletBuilder ofUplet() {
        return new TsonUpletBuilderImpl();
    }

    public static TsonUpletBuilder ofUplet(TsonElementBase... elements) {
        return new TsonUpletBuilderImpl().addAll(elements);
    }

    public static TsonAnnotationBuilder ofAnnotation() {
        return new TsonAnnotationBuilderImpl();
    }

    public static TsonAnnotation ofAnnotation(String name, TsonElementBase... elements) {
        return ofAnnotation().name(name).addAll(elements).build();
    }

    public static TsonFormatBuilder format() {
        return new TsonFormatImplBuilder();
    }

    public static TsonWriter writer() {
        return new TsonWriterImpl(serializer);
    }

    public static TsonDocumentBuilder ofDocument() {
        return new TsonDocumentBuilderImpl();
    }

    public static TsonDocumentHeaderBuilder ofDocumentHeader() {
        return new TsonDocumentHeaderBuilderImpl();
    }

    public static void setSerializer(TsonSerializer newSerializer) {
        if (newSerializer == null) {
            newSerializer = new TsonSerializerImpl();
        }
        serializer = newSerializer;
    }

    public static TsonSerializer serializer() {
        return serializer;
    }

    public static TsonProcessor processor() {
        return new TsonProcessorImpl();
    }

    public static TsonElement of(Enum b) {
        return b == null ? ofNull() : name(b.name());
    }

    public static TsonElement of(TsonElementBase b) {
        return b == null ? ofNull() : b.build();
    }
}
