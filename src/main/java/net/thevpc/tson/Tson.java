package net.thevpc.tson;

import net.thevpc.tson.impl.TsonProcessorImpl;
import net.thevpc.tson.impl.builders.*;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.impl.format.TsonFormatImplBuilder;
import net.thevpc.tson.impl.format.TsonWriterImpl;
import net.thevpc.tson.impl.marshall.TsonSerializerImpl;
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

    public static TsonElement nullElem() {
        return TsonNullImpl.INSTANCE;
    }

    public static TsonElement booleanElem(boolean val) {
        return TsonBooleanImpl.valueOf(val);
    }

    public static TsonElement string(String value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStringImpl(value);
    }

    public static TsonElement datetime(Instant value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonDateTimeImpl(value);
    }

    public static TsonElement datetime(Date value) {
        if (value == null) {
            return nullElem();
        }
        return datetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement date(LocalDate value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonDateImpl(value);
    }

    public static TsonElement time(LocalTime value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonTimeImpl(value);
    }

    public static TsonElement time(java.sql.Time value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonTimeImpl(value.toLocalTime());
    }

    public static TsonElement date(java.sql.Date value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonDateImpl(value.toLocalDate());
    }

    public static TsonElement regex(Pattern value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonRegexImpl(value);
    }

    public static TsonElement regex(String value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonRegexImpl(Pattern.compile(value));
    }

    public static TsonElement charElem(char value) {
        return new TsonCharImpl(value);
    }

    public static TsonElement intElem(int value) {
        return new TsonIntImpl(value);
    }

    public static TsonElement longElem(long value) {
        return new TsonLongImpl(value);
    }

    public static TsonElement byteElem(byte value) {
        return new TsonByteImpl(value);
    }

    public static TsonElement shortElem(short value) {
        return new TsonShortImpl(value);
    }

    public static TsonElement floatElem(float value) {
        return new TsonFloatImpl(value);
    }

    public static TsonElement bigInt(BigInteger value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBigIntImpl(value);
    }

    public static TsonElement bigDecimal(BigDecimal value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBigDecimalImpl(value);
    }

    public static TsonElement bigComplex(BigDecimal real, BigDecimal imag) {
        if (real == null && imag == null) {
            return nullElem();
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


    public static TsonElement doubleElem(double value) {
        return new TsonDoubleImpl(value);
    }

    public static TsonElement name(String value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonNameImpl(value);
    }

    public static TsonElement alias(String value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonAliasImpl(value);
    }

    public static TsonPair pair(TsonElementBase key, TsonElementBase value) {
        return new TsonPairImpl(elem(key), elem(value));
    }

    public static TsonPair pair(String key, TsonElementBase value) {
        return new TsonPairImpl(name(key), elem(value));
    }

    public static TsonElement elem(boolean value) {
        return booleanElem(value);
    }

    public static TsonElement elem(char value) {
        return charElem(value);
    }

    public static TsonElement elem(byte value) {
        return byteElem(value);
    }

    public static TsonElement elem(short value) {
        return shortElem(value);
    }

    public static TsonElement elem(int value) {
        return intElem(value);
    }

    public static TsonElement elem(long value) {
        return longElem(value);
    }

    public static TsonElement elem(float value) {
        return floatElem(value);
    }

    public static TsonElement elem(double value) {
        return doubleElem(value);
    }

    public static TsonElement elem(BigInteger value) {
        return bigInt(value);
    }

    public static TsonElement elem(BigDecimal value) {
        return bigDecimal(value);
    }

    public static TsonElement elem(byte[] value) {
        if (value == null) {
            return nullElem();
        }
        return bstream(value);
    }

    public static TsonElement elem(InputStream value) {
        if (value == null) {
            return nullElem();
        }
        return bstream(value);
    }

    public static TsonElement bstream(byte[] value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement bstream(InputStream value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement bstream(File value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    public static TsonElement bstream(Path value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    private static TsonElement cstream(char[] value, String type) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    private static TsonElement cstream(Reader value, String type) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    private static TsonElement cstream(File value, String type) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    public static TsonElement cstream(String value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement cstream(char[] value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement cstream(Reader value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement cstream(File value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement cstream(Path value) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    public static TsonElement cstream(String value, String language) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    public static TsonElement cstream(Path value, String language) {
        if (value == null) {
            return nullElem();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    public static TsonElement stopStream(String value, String stopWord) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement stopStream(char[] value, String stopWord) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement stopStream(Reader value, String stopWord) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement stopStream(File value, String stopWord) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement stopStream(Path value, String stopWord) {
        if (value == null) {
            return nullElem();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    public static TsonElement elem(Boolean value) {
        if (value == null) {
            return nullElem();
        }
        return booleanElem(value);
    }

    public static TsonElement elem(Character value) {
        if (value == null) {
            return nullElem();
        }
        return charElem(value);
    }

    public static TsonElement elem(Byte value) {
        if (value == null) {
            return nullElem();
        }
        return byteElem(value);
    }

    public static TsonElement elem(Short value) {
        if (value == null) {
            return nullElem();
        }
        return shortElem(value);
    }

    public static TsonElement elem(Integer value) {
        if (value == null) {
            return nullElem();
        }
        return intElem(value);
    }

    public static TsonElement elem(Long value) {
        if (value == null) {
            return nullElem();
        }
        return longElem(value);
    }

    public static TsonElement elem(Float value) {
        if (value == null) {
            return nullElem();
        }
        return floatElem(value);
    }

    public static TsonElement elem(Double value) {
        if (value == null) {
            return nullElem();
        }
        return doubleElem(value);
    }

    public static TsonElement elem(Date value) {
        if (value == null) {
            return nullElem();
        }
        if (value instanceof java.sql.Time) {
            return time((Time) value);
        }
        if (value instanceof java.sql.Date) {
            return date(((java.sql.Date) value).toLocalDate());
        }
        return datetime(Instant.ofEpochMilli(value.getTime()));
    }

    public static TsonElement elem(Instant value) {
        return datetime(value);
    }

    public static TsonElement elem(LocalDate value) {
        return date(value);
    }

    public static TsonElement elem(java.sql.Date value) {
        return date(value);
    }

    public static TsonElement elem(java.sql.Time value) {
        return time(value);
    }

    public static TsonElement elem(LocalTime value) {
        return time(value);
    }

    public static TsonElement elem(Pattern value) {
        return regex(value);
    }

    public static TsonElement elem(String value) {
        return string(value);
    }

    public static TsonPrimitiveBuilder elem() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    public static TsonArrayBuilder array(TsonElementBase... elements) {
        return array().addAll(elements);
    }

    public static TsonArrayBuilder array() {
        return new TsonArrayBuilderImpl();
    }

    public static TsonArrayBuilder array(String name) {
        return array().getHeader().setName(name).then();
    }

    public static TsonArrayBuilder array(String name, TsonElementBase[] params, TsonElementBase... elems) {
        return array().getHeader().setName(name).addAll(params).then().addAll(elems);
    }

    public static TsonMatrixBuilder matrix() {
        return new TsonMatrixBuilderImpl();
    }

    public static TsonMatrixBuilder matrix(String name) {
        return matrix().getHeader().setName(name).then();
    }


    public static TsonMatrixBuilder matrix(String name, TsonElementBase[] params) {
        return matrix().getHeader().setName(name).addAll(params).then();
    }

    public static TsonFunctionBuilder function(String name, TsonElementBase... elems) {
        return new TsonFunctionBuilderImpl().name(name).addAll(elems);
    }

    public static TsonFunctionBuilder function() {
        return new TsonFunctionBuilderImpl();
    }

    public static TsonElementBuilder pair() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    public static TsonObjectBuilder obj() {
        return new TsonObjectBuilderImpl();
    }

    public static TsonObjectBuilder obj(TsonElementBase... elems) {
        return obj().addAll(elems);
    }

    public static TsonObjectBuilder obj(String name) {
        TsonObjectBuilder e = obj();
        e.getHeader().name(name);
        return e;
    }

    public static TsonObjectBuilder obj(String name, TsonElementBase[] params, TsonElementBase... elems) {
        TsonObjectBuilder o = obj();
        o.getHeader().name(name).addAll(params);
        return o.addAll(elems);
    }

    public static TsonUpletBuilder uplet() {
        return new TsonUpletBuilderImpl();
    }

    public static TsonUpletBuilder uplet(TsonElementBase... elements) {
        return new TsonUpletBuilderImpl().addAll(elements);
    }

    public static TsonAnnotationBuilder annotation() {
        return new TsonAnnotationBuilderImpl();
    }

    public static TsonAnnotation annotation(String name, TsonElementBase... elements) {
        return annotation().name(name).addAll(elements).build();
    }

    public static TsonFormatBuilder format() {
        return new TsonFormatImplBuilder();
    }

    public static TsonWriter writer() {
        return new TsonWriterImpl(serializer);
    }

    public static TsonDocumentBuilder document() {
        return new TsonDocumentBuilderImpl();
    }

    public static TsonDocumentHeaderBuilder documentHeader() {
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

    public static TsonElement elem(Enum b) {
        return b == null ? nullElem() : name(b.name());
    }

    public static TsonElement elem(TsonElementBase b) {
        return b == null ? nullElem() : b.build();
    }
}
