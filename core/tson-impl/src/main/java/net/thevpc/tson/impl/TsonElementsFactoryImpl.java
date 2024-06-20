package net.thevpc.tson.impl;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.*;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.impl.format.TsonFormatImplBuilder;
import net.thevpc.tson.impl.format.TsonWriterImpl;
import net.thevpc.tson.impl.marshall.TsonSerializerImpl;
import net.thevpc.tson.impl.parser.CharStreamCodeSupports;
import net.thevpc.tson.impl.parser.TsonNumberHelper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class TsonElementsFactoryImpl implements TsonElementsFactory {
//    public char parseChar(String s) {
//        return parseRawString(s, TsonStringLayout.SINGLE_QUOTE).charAt(0);
//    }

    @Override
    public TsonElement parseString(String value) {
        if (value == null) {
            return ofNull();
        }
        return TsonParserUtils.parseRawString(value);
    }

    @Override
    public TsonElement ofString(String value, TsonStringLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStringImpl(value, value, layout == null ? TsonStringLayout.DOUBLE_QUOTE : layout);
    }

    @Override
    public TsonElement ofElement(TsonElementBase value) {
        if (value == null) {
            return ofNull();
        }
        return value.build();
    }

    @Override
    public TsonElementBase ofElementBase(TsonElementBase value) {
        if (value == null) {
            return ofNull();
        }
        return value;
    }

    @Override
    public TsonElement ofString(String value) {
        if (value == null) {
            return ofNull();
        }
        return ofString(value, TsonStringLayout.DOUBLE_QUOTE);
    }

    @Override
    public TsonElement ofDatetime(Instant value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateTimeImpl(value);
    }

    @Override
    public TsonElement ofDatetime(Date value) {
        if (value == null) {
            return ofNull();
        }
        return ofDatetime(Instant.ofEpochMilli(value.getTime()));
    }

    @Override
    public TsonElement ofDate(LocalDate value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateImpl(value);
    }

    @Override
    public TsonElement ofTime(LocalTime value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonTimeImpl(value);
    }

    @Override
    public TsonElement ofTime(java.sql.Time value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonTimeImpl(value.toLocalTime());
    }

    @Override
    public TsonElement ofDate(java.sql.Date value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonDateImpl(value.toLocalDate());
    }

    @Override
    public TsonElement ofRegex(Pattern value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonRegexImpl(value);
    }

    @Override
    public TsonElement ofRegex(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonRegexImpl(Pattern.compile(value));
    }

    @Override
    public TsonElement ofChar(char value) {
        return new TsonCharImpl(value);
    }

    @Override
    public TsonElement ofInt(int value) {
        return ofInt(value, TsonNumberLayout.DECIMAL);
    }

    @Override
    public TsonElement ofInt(int value, TsonNumberLayout layout) {
        return new TsonIntImpl(value, layout, null);
    }

    @Override
    public TsonElement ofInt(int value, TsonNumberLayout layout, String unit) {
        return new TsonIntImpl(value, layout, unit);
    }

    @Override
    public TsonElement ofLong(long value) {
        return ofLong(value, TsonNumberLayout.DECIMAL);
    }

    @Override
    public TsonElement ofLong(long value, TsonNumberLayout layout) {
        return new TsonLongImpl(value, layout, null);
    }

    @Override
    public TsonElement ofLong(long value, TsonNumberLayout layout, String unit) {
        return new TsonLongImpl(value, layout, unit);
    }

    @Override
    public TsonElement ofByte(byte value, TsonNumberLayout layout) {
        return new TsonByteImpl(value, layout, null);
    }

    @Override
    public TsonElement ofByte(byte value, TsonNumberLayout layout, String unit) {
        return new TsonByteImpl(value, layout, unit);
    }

    @Override
    public TsonElement ofByte(byte value) {
        return ofByte(value, TsonNumberLayout.DECIMAL);
    }

    @Override
    public TsonElement ofShort(short value, TsonNumberLayout layout) {
        return new TsonShortImpl(value, layout, null);
    }

    @Override
    public TsonElement ofShort(short value, TsonNumberLayout layout, String unit) {
        return new TsonShortImpl(value, layout, unit);
    }

    @Override
    public TsonElement ofShort(short value) {
        return ofShort(value, TsonNumberLayout.DECIMAL);
    }

    @Override
    public TsonElement ofFloat(float value) {
        return new TsonFloatImpl(value, null);
    }

    @Override
    public TsonElement ofFloat(float value, String unit) {
        return new TsonFloatImpl(value, unit);
    }

    @Override
    public TsonElement ofBigInt(BigInteger value) {
        return ofBigInt(value, TsonNumberLayout.DECIMAL);
    }

    @Override
    public TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigIntImpl(value, layout, null);
    }

    @Override
    public TsonElement ofBigInt(BigInteger value, TsonNumberLayout layout, String unit) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigIntImpl(value, layout, unit);
    }

    @Override
    public TsonElement ofBigDecimal(BigDecimal value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigDecimalImpl(value, null);
    }

    @Override
    public TsonElement ofBigDecimal(BigDecimal value, String unit) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBigDecimalImpl(value, unit);
    }

    @Override
    public TsonElement ofBigComplex(BigDecimal real, BigDecimal imag) {
        if (real == null && imag == null) {
            return ofNull();
        }
        if (real == null || imag == null) {
            throw new IllegalArgumentException("Null real or imag");
        }
        return new TsonBigComplexImpl(real, imag, null);
    }

    @Override
    public TsonElement ofBigComplex(BigDecimal real, BigDecimal imag, String unit) {
        if (real == null && imag == null) {
            return ofNull();
        }
        if (real == null || imag == null) {
            throw new IllegalArgumentException("Null real or imag");
        }
        return new TsonBigComplexImpl(real, imag, unit);
    }

    @Override
    public TsonElement ofFloatComplex(float real, float imag) {
        return new TsonFloatComplexImpl(real, imag, null);
    }

    @Override
    public TsonElement ofFloatComplex(float real, float imag, String unit) {
        return new TsonFloatComplexImpl(real, imag, unit);
    }


    @Override
    public TsonElement ofDoubleComplex(double real, double imag) {
        return new TsonDoubleComplexImpl(real, imag, null);
    }

    @Override
    public TsonElement ofDoubleComplex(double real, double imag, String unit) {
        return new TsonDoubleComplexImpl(real, imag, unit);
    }


    @Override
    public TsonElement ofDouble(double value) {
        return new TsonDoubleImpl(value, null);
    }

    @Override
    public TsonElement ofDouble(double value, String unit) {
        return new TsonDoubleImpl(value, unit);
    }

    @Override
    public TsonElement ofName(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonNameImpl(value);
    }

    @Override
    public TsonElement ofAlias(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonAliasImpl(value);
    }

    @Override
    public TsonPair ofPair(TsonElementBase key, TsonElementBase value) {
        return new TsonPairImpl(of(key), of(value));
    }

    @Override
    public TsonBinOp ofBinOp(String op, TsonElementBase key, TsonElementBase value) {
        return new TsonBinOpImpl(op, of(key), of(value));
    }

    @Override
    public TsonPair ofPair(String key, TsonElementBase value) {
        return new TsonPairImpl(ofName(key), of(value));
    }

    @Override
    public TsonElement of(boolean value) {
        return ofBoolean(value);
    }

    @Override
    public TsonElement of(char value) {
        return ofChar(value);
    }

    @Override
    public TsonElement of(byte value) {
        return ofByte(value);
    }

    @Override
    public TsonElement of(short value) {
        return ofShort(value);
    }

    @Override
    public TsonElement of(int value) {
        return ofInt(value);
    }

    @Override
    public TsonElement of(long value) {
        return ofLong(value);
    }

    @Override
    public TsonElement of(float value) {
        return ofFloat(value);
    }

    @Override
    public TsonElement of(double value) {
        return ofDouble(value);
    }

    @Override
    public TsonElement of(BigInteger value) {
        return ofBigInt(value);
    }

    @Override
    public TsonElement of(BigDecimal value) {
        return ofBigDecimal(value);
    }

    @Override
    public TsonElement of(byte[] value) {
        if (value == null) {
            return ofNull();
        }
        return ofBinStream(value);
    }

    @Override
    public TsonElement of(InputStream value) {
        if (value == null) {
            return ofNull();
        }
        return ofBinStream(value);
    }

    @Override
    public TsonElement ofBinStream(byte[] value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    @Override
    public TsonElement ofBinStream(InputStream value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    @Override
    public TsonElement ofBinStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    @Override
    public TsonElement ofBinStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonBinaryStreamImpl(TsonBinaryStreamSource.of(value));
    }

    @Override
    public TsonElement ofCharStream(char[] value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    @Override
    public TsonElement ofCharStream(Reader value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    @Override
    public TsonElement ofCharStream(File value, String type) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), type);
    }

    @Override
    public TsonElement ofCharStream(String value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    @Override
    public TsonElement ofCharStream(char[] value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    @Override
    public TsonElement ofCharStream(Reader value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    @Override
    public TsonElement ofCharStream(File value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    @Override
    public TsonElement ofCharStream(Path value) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), "");
    }

    @Override
    public TsonElement ofCharStream(String value, String language) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    @Override
    public TsonElement ofCharStream(Path value, String language) {
        if (value == null) {
            return ofNull();
        }
        return new TsonCharStreamImpl(TsonCharStreamSource.of(value), language);
    }

    @Override
    public TsonElement ofStopStream(String value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    @Override
    public TsonElement ofStopStream(char[] value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    @Override
    public TsonElement ofStopStream(Reader value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    @Override
    public TsonElement ofStopStream(File value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    @Override
    public TsonElement ofStopStream(Path value, String stopWord) {
        if (value == null) {
            return ofNull();
        }
        return new TsonStopWordCharStreamImpl(TsonCharStreamSource.of(value), stopWord);
    }

    @Override
    public TsonElement of(Boolean value) {
        if (value == null) {
            return ofNull();
        }
        return ofBoolean(value);
    }

    @Override
    public TsonElement of(Character value) {
        if (value == null) {
            return ofNull();
        }
        return ofChar(value);
    }

    @Override
    public TsonElement of(Byte value) {
        if (value == null) {
            return ofNull();
        }
        return ofByte(value);
    }

    @Override
    public TsonElement of(Short value) {
        if (value == null) {
            return ofNull();
        }
        return ofShort(value);
    }

    @Override
    public TsonElement of(Integer value) {
        if (value == null) {
            return ofNull();
        }
        return ofInt(value);
    }

    @Override
    public TsonElement of(Long value) {
        if (value == null) {
            return ofNull();
        }
        return ofLong(value);
    }

    @Override
    public TsonElement of(Float value) {
        if (value == null) {
            return ofNull();
        }
        return ofFloat(value);
    }

    @Override
    public TsonElement of(Double value) {
        if (value == null) {
            return ofNull();
        }
        return ofDouble(value);
    }

    @Override
    public TsonElement of(Date value) {
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

    @Override
    public TsonElement of(Instant value) {
        return ofDatetime(value);
    }

    @Override
    public TsonElement of(LocalDate value) {
        return ofDate(value);
    }

    @Override
    public TsonElement of(java.sql.Date value) {
        return ofDate(value);
    }

    @Override
    public TsonElement of(java.sql.Time value) {
        return ofTime(value);
    }

    @Override
    public TsonElement of(LocalTime value) {
        return ofTime(value);
    }

    @Override
    public TsonElement of(Pattern value) {
        return ofRegex(value);
    }

    @Override
    public TsonElement of(String value) {
        return ofString(value);
    }

    @Override
    public TsonPrimitiveBuilder of() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    @Override
    public TsonArrayBuilder ofArray(TsonElementBase... elements) {
        return ofArray().addAll(elements);
    }

    @Override
    public TsonArrayBuilder ofArray() {
        return new TsonArrayBuilderImpl();
    }

    @Override
    public TsonArrayBuilder ofArray(String name) {
        return ofArray().getHeader().setName(name).then();
    }

    @Override
    public TsonArrayBuilder ofArray(String name, TsonElementBase[] params, TsonElementBase... elems) {
        return ofArray().getHeader().setName(name).addAll(params).then().addAll(elems);
    }

    @Override
    public TsonMatrixBuilder ofMatrix() {
        return new TsonMatrixBuilderImpl();
    }

    @Override
    public TsonMatrixBuilder ofMatrix(String name) {
        return ofMatrix().getHeader().setName(name).then();
    }


    @Override
    public TsonMatrixBuilder ofMatrix(String name, TsonElementBase[] params) {
        return ofMatrix().getHeader().setName(name).addAll(params).then();
    }

    @Override
    public TsonFunctionBuilder ofFunction(String name, TsonElementBase... elems) {
        return new TsonFunctionBuilderImpl().name(name).addAll(elems);
    }

    @Override
    public TsonFunctionBuilder ofFunction() {
        return new TsonFunctionBuilderImpl();
    }

    @Override
    public TsonElementBuilder ofPair() {
        return new TsonPrimitiveElementBuilderImpl();
    }

    @Override
    public TsonObjectBuilder ofObj() {
        return new TsonObjectBuilderImpl();
    }

    @Override
    public TsonObjectBuilder ofObj(TsonElementBase... elems) {
        return ofObj().addAll(elems);
    }

    @Override
    public TsonObjectBuilder ofObj(String name) {
        TsonObjectBuilder e = ofObj();
        e.getHeader().name(name);
        return e;
    }

    @Override
    public TsonObjectBuilder ofObj(String name, TsonElementBase[] params, TsonElementBase... elems) {
        TsonObjectBuilder o = ofObj();
        o.getHeader().name(name).addAll(params);
        return o.addAll(elems);
    }

    @Override
    public TsonUpletBuilder ofUplet() {
        return new TsonUpletBuilderImpl();
    }

    @Override
    public TsonUpletBuilder ofUplet(TsonElementBase... elements) {
        return new TsonUpletBuilderImpl().addAll(elements);
    }

    @Override
    public TsonAnnotationBuilder ofAnnotation() {
        return new TsonAnnotationBuilderImpl();
    }

    @Override
    public TsonAnnotation ofAnnotation(String name, TsonElementBase... elements) {
        return ofAnnotation().name(name).addAll(elements).build();
    }

    @Override
    public TsonFormatBuilder format() {
        return new TsonFormatImplBuilder();
    }

    @Override
    public TsonWriter writer(TsonSerializer serializer) {
        return new TsonWriterImpl(serializer);
    }

    @Override
    public TsonDocumentBuilder ofDocument() {
        return new TsonDocumentBuilderImpl();
    }

    @Override
    public TsonDocumentHeaderBuilder ofDocumentHeader() {
        return new TsonDocumentHeaderBuilderImpl();
    }

    @Override
    public TsonElement of(Enum b) {
        return b == null ? ofNull() : ofName(b.name());
    }

    @Override
    public TsonElement of(TsonElementBase b) {
        return b == null ? ofNull() : b.build();
    }

    public TsonSerializer serializer() {
        return new TsonSerializerImpl();
    }

    public TsonReader reader(TsonSerializer serializer) {
        return new TsonReaderImpl(serializer);
    }

    public TsonElement ofNull() {
        return TsonNullImpl.INSTANCE;
    }

    public TsonElement ofBoolean(boolean val) {
        return TsonBooleanImpl.valueOf(val);
    }

    public TsonProcessor processor() {
        return new TsonProcessorImpl();
    }

    @Override
    public TsonBinaryStreamBuilder ofBinStream() {
        return new TsonBinaryStreamBuilder() {
            InputStreamTsonBinaryStreamSource s = new InputStreamTsonBinaryStreamSource();

            @Override
            public TsonBinaryStream build() {
                return new TsonBinaryStreamImpl(s);
            }

            @Override
            public void writeBase64(String b64) {
                s.pushBase64(b64);
            }
        };
    }


    @Override
    public TsonElement parseDateTimeElem(String s) {
        return new TsonDateTimeImpl(Instant.parse(s));
    }

    @Override
    public TsonElement parseDateElem(String s) {
        return new TsonDateImpl(LocalDate.parse(s));
    }

    @Override
    public TsonElement parseTimeElem(String s) {
        return new TsonTimeImpl(LocalTime.parse(s));
    }

    @Override
    public TsonElement parseRegexElem(String s) {
        final String p = s.substring(1, s.length() - 1);
        //should unescape
        return new TsonRegexImpl(Pattern.compile(p));
    }


    public Instant parseDateTime(String s) {
        return Instant.parse(s);
    }

    public LocalDate parseDate(String s) {
        return LocalDate.parse(s);
    }

    public LocalTime parseTime(String s) {
        return LocalTime.parse(s);
    }

    public Pattern parseRegex(String s) {
        return Pattern.compile(s.substring(1, s.length() - 1));
    }

    public byte parseByte(String s) {
        if (s.endsWith("b") || s.endsWith("B")) {
            s = s.substring(0, s.length() - 1);
        }
        return Byte.parseByte(s);
    }

    public short parseShort(String s) {
        if (s.endsWith("s") || s.endsWith("S")) {
            s = s.substring(0, s.length() - 1);
        }
        return Short.parseShort(s);
    }

    public long parseLong(String s) {
        if (s.endsWith("l") || s.endsWith("L")) {
            s = s.substring(0, s.length() - 1);
        }
        return Long.parseLong(s);
    }

    public int parseInt(String s) {
        if (s.endsWith("i") || s.endsWith("I")) {
            s = s.substring(0, s.length() - 1);
        }
        return Integer.parseInt(s);
    }

    public float parseFloat(String s) {
        if (s.endsWith("f") || s.endsWith("F")) {
            s = s.substring(0, s.length() - 1);
        }
        return Float.parseFloat(s);
    }

    public double parseDouble(String s) {
//        if(s.endsWith("f") || s.endsWith("F")){
//            s=s.substring(s.length()-1);
//        }
        return Double.parseDouble(s);
    }

    @Override
    public TsonElement parseChar(String s) {
        TsonString e = (TsonString) parseString(s).toStr();
        if (e.layout() == TsonStringLayout.SINGLE_QUOTE && e.getValue().length() == 1) {
            return new TsonCharImpl(e.getValue().charAt(0));
        }
        throw new TsonParseException("invalid char " + s, null);
    }


    @Override
    public TsonElement parseAliasElem(String s) {
        return new TsonAliasImpl(s.substring(1));
    }

//    public static void main(String[] args) {
//        for (String string : new String[]{"\"Hello \\nWorld\"","\"Hello World\""}) {
//            System.out.println(string);
//            System.out.println(parseString(string));
//            System.out.println(parseString2(string));
//        }
//        final int count = 10000;
//
//        Chrono c2=Chrono.start();
//        for (int i = 0; i < count; i++) {
//            for (String string : new String[]{"\"Hello \\nWorld\""}) {
//                parseString(string);
//            }
//        }
//        c2.stop();
//
//        Chrono c3=Chrono.start();
//        for (int i = 0; i < count; i++) {
//            for (String string : new String[]{"\"Hello \\nWorld\""}) {
//                parseString2(string);
//            }
//        }
//        c3.stop();
//        System.out.println(c2);
//        System.out.println(c3);
//    }


    public String extractRawString(String s, TsonStringLayout layout) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int borderLen;
        switch (layout) {
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
            case ANTI_QUOTE: {
                borderLen = 1;
                break;
            }
            case TRIPLE_ANTI_QUOTE:
            case TRIPLE_DOUBLE_QUOTE:
            case TRIPLE_SINGLE_QUOTE: {
                borderLen = 3;
                break;
            }
            default: {
                throw new IllegalArgumentException("unsupported");
            }
        }
        return s.substring(borderLen, len - borderLen);
    }


    public String parseRawString(String s, TsonStringLayout layout) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int prefixLen = 1;
        int suffixLen = 1;
        String border = "\"";
        switch (layout) {
            case DOUBLE_QUOTE: {
                border = "\"";
                break;
            }
            case SINGLE_QUOTE: {
                border = "'";
                break;
            }
            case ANTI_QUOTE: {
                border = "`";
                break;
            }
            case TRIPLE_ANTI_QUOTE: {
                border = "```";
                break;
            }
            case TRIPLE_DOUBLE_QUOTE: {
                border = "\"\"\"";
                break;
            }
            case TRIPLE_SINGLE_QUOTE: {
                border = "'''";
                break;
            }
        }
        prefixLen = border.length();
        suffixLen = prefixLen;
        if (s.length() < prefixLen + suffixLen) {
            throw new IllegalArgumentException("unsupported: " + s);
        }
        if (
                !s.startsWith(border)
                        || !s.endsWith(border)
        ) {
            throw new IllegalArgumentException("unsupported: " + s);
        }
        switch (layout) {
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
            case ANTI_QUOTE: {
                final int beforeLen = len - suffixLen;
                StringBuilder sb = new StringBuilder();
                for (int i = suffixLen; i < beforeLen; i++) {
                    char c = s.charAt(i);
                    switch (c) {
                        case '\\': {
                            int ip = i + 1;
                            boolean processed = false;
                            if (ip < beforeLen) {
                                switch (s.charAt(ip)) {
                                    case 'n': {
                                        sb.append('\n');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 't': {
                                        sb.append('\t');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 'f': {
                                        sb.append('\f');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 'b': {
                                        sb.append('\b');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case '\\': {
                                        sb.append('\\');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                }
                            }
                            if (!processed) {
                                sb.append(c);
                            }
                            break;
                        }
                        default: {
                            sb.append(c);
                        }
                    }
                }
                return sb.toString();
            }
            case TRIPLE_ANTI_QUOTE:
            case TRIPLE_DOUBLE_QUOTE:
            case TRIPLE_SINGLE_QUOTE: {
                final int beforeLen = len - prefixLen;
                StringBuilder sb = new StringBuilder(s.length());
                for (int i = prefixLen; i < beforeLen; i++) {
                    char c = s.charAt(i);
                    switch (c) {
                        case '\\': {
                            boolean processed = false;
                            if (i + 3 < len) {
                                String substring = s.substring(i + 1, i + 1 + suffixLen);
                                if (substring.equals(border)) {
                                    sb.append(substring);
                                    i += suffixLen;
                                    processed = true;
                                }
                            }
                            if (!processed) {
                                sb.append(c);
                            }
                            break;
                        }
                        default: {
                            sb.append(c);
                        }
                    }
                }
                return sb.toString();
            }
        }
        throw new IllegalArgumentException("unsupported: " + s);
    }

    public String parseStringOld(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder(len - 2);
        final int beforeLen = len - 1;
        for (int i = 1; i < beforeLen; i++) {
            switch (s.charAt(i)) {
                case '\\': {
                    i++;
                    switch (s.charAt(i)) {
                        case 'n': {
                            sb.append('\n');
                            break;
                        }
                        case 't': {
                            sb.append('\t');
                            break;
                        }
                        case 'f': {
                            sb.append('\f');
                            break;
                        }
                        case 'b': {
                            sb.append('\b');
                            break;
                        }
                        case '\\': {
                            sb.append('\\');
                            break;
                        }
                        default: {
                            sb.append(chars[i]);
                        }
                    }
                    break;
                }
                default: {
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    public TsonDocument elementsToDocument(TsonElement[] roots) {
        TsonElement c = null;
        if (roots.length == 0) {
            return Tson.ofDocument().header(null).content(Tson.ofObj().build()).build();
        } else if (roots.length == 1) {
            return elementToDocument(roots[0]);
        } else {
            TsonAnnotation[] annotations = roots[0].getAnnotations();
            if (annotations != null && annotations.length > 0 && "tson".equals(annotations[0].getName())) {
                // will remove it
                TsonAnnotation[] annotations2 = new TsonAnnotation[annotations.length - 1];
                System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 1);
                List<TsonElement> newList = new ArrayList<>(Arrays.asList(roots));
                TsonElement c0 = roots[0].builder().setAnnotations(annotations2).build();
                newList.set(0, c0);
                roots = newList.toArray(new TsonElement[0]);
            }
            return Tson.ofDocument().content(Tson.ofObj(roots).build()).build();
        }
    }

    public TsonDocument elementToDocument(TsonElement root) {
        TsonAnnotation[] annotations = root.getAnnotations();
        if (annotations != null && annotations.length > 0 && "tson".equals(annotations[0].getName())) {
            // will remove it
            TsonAnnotation[] annotations2 = new TsonAnnotation[annotations.length - 1];
            System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 1);
            return Tson.ofDocument().header(Tson.ofDocumentHeader().addParams(annotations[0].getAll()).build())
                    .content(root.builder().setAnnotations(annotations2).build()).build();
        }
        return Tson.ofDocument().header(null).content(root).build();
    }

    @Override
    public CharStreamCodeSupport charStreamCodeSupportOf(String language) {
        return CharStreamCodeSupports.of(language);
    }

    @Override
    public TsonComment parseComments(String c) {
        if (c == null) {
            return null;
        }
        if (c.startsWith("/*")) {
            return TsonComment.ofMultiLine(escapeMultiLineComments(c));
        }
        if (c.startsWith("//")) {
            return TsonComment.ofSingleLine(escapeSingleLineComments(c));
        }
        throw new IllegalArgumentException("unsupported comments " + c);
    }

    public String escapeSingleLineComments(String c) {
        if (c == null) {
            return null;
        }
        if (c.startsWith("//")) {
            return c.substring(2);
        }
        throw new IllegalArgumentException("unsupported comments " + c);
    }

    public String escapeMultiLineComments(String c) {
        if (c == null) {
            return null;
        }
        int line = 0;
        String[] lines = c.trim().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            s = s.trim();
            if (line == 0) {
                if (s.startsWith("/*")) {
                    s = s.substring(2);
                }
            }
            if (line == lines.length - 1) {
                if (s.endsWith("*/")) {
                    s = s.substring(0, s.length() - 2);
                }
            }
            if (s.equals("*")) {
                s = s.substring(1);
            } else if (s.equals("**")) {
                s = s.substring(1);
            } else if (s.startsWith("*") && s.length() > 1 && Character.isWhitespace(s.charAt(1))) {
                s = s.substring(2).trim();
            } else if (s.startsWith("**") && s.length() > 2 && Character.isWhitespace(s.charAt(1))) {
                s = s.substring(2).trim();
            }
            if (s.length() > 1 && s.charAt(0) == '*' && s.charAt(1) == ' ') {
                s = s.substring(2);
            }
            s = s.trim();
            if (line == lines.length - 1) {
                if (s.length() > 0) {
                    if (line > 0) {
                        sb.append("\n");
                    }
                    sb.append(s.trim());
                }
            } else {
                if (line > 0) {
                    sb.append("\n");
                }
                sb.append(s.trim());
            }
            line++;
        }
        return sb.toString().trim();
    }


    private int fastDecodeIntOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Integer.valueOf(nm.substring(index), 8);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            return Integer.parseInt(constant, 8);
        }
    }

    private short fastDecodeShortOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Short.parseShort(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return Short.parseShort(constant, 8);
        }
    }

    private long fastDecodeLongOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Long.parseLong(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return Long.parseLong(constant, 8);
        }
    }

    private BigInteger fastDecodeBigIntOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = new BigInteger(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return new BigInteger(constant, 8);
        }
    }


    private int fastDecodeIntHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Integer.parseInt(nm.substring(index), 16);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Integer.parseInt(constant, 16);
        }
        return result;
    }

    private int fastDecodeIntBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Integer.parseInt(nm.substring(index), 2);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Integer.parseInt(constant, 2);
        }
        return result;
    }

    private short fastDecodeShortHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Short.parseShort(nm.substring(index), 16);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Short.parseShort(constant, 16);
        }
        return result;
    }

    private short fastDecodeShortBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Short.parseShort(nm.substring(index), 2);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Short.parseShort(constant, 2);
        }
        return result;
    }

    private long fastDecodeLongHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Long.parseLong(nm.substring(index), 16);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Long.parseLong(constant, 16);
        }
        return result;
    }

    private BigInteger fastDecodeBigIntHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = new BigInteger(nm.substring(index), 16);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = new BigInteger(constant, 16);
        }
        return result;
    }

    private long fastDecodeLongBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Long.parseLong(nm.substring(index), 2);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Long.parseLong(constant, 2);
        }
        return result;
    }

    private BigInteger fastDecodeBigIntBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = new BigInteger(nm.substring(index), 2);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = new BigInteger(constant, 2);
        }
        return result;
    }

    public TsonElement parseNaNElem(String s) {
        if (s == null) {
            return Tson.of(Double.NaN);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.NaN);
            case "float":
                return Tson.of(Float.NaN);
        }
        throw new IllegalArgumentException("Unsupported NaN(" + s + ")");
    }

    public TsonElement parsePosInfElem(String s) {
        if (s == null) {
            return Tson.of(Double.POSITIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.POSITIVE_INFINITY);
            case "float":
                return Tson.of(Float.POSITIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported +Bound(" + s + ")");
    }

    public TsonElement parseNegInfElem(String s) {
        if (s == null) {
            return Tson.of(Double.NEGATIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.NEGATIVE_INFINITY);
            case "float":
                return Tson.of(Float.NEGATIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported -Bound(" + s + ")");
    }

    public TsonElement parsePosBoundElem(String s) {
        if (s == null) {
            return Tson.of(Double.MAX_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.MAX_VALUE);
            case "float":
                return Tson.of(Float.MAX_VALUE);
            case "byte":
                return Tson.of(Byte.MAX_VALUE);
            case "short":
                return Tson.of(Short.MAX_VALUE);
            case "int":
                return Tson.of(Integer.MAX_VALUE);
            case "long":
                return Tson.of(Long.MAX_VALUE);
        }
        throw new IllegalArgumentException("Unsupported +Inf(" + s + ")");
    }

    public TsonElement parseNegBoundElem(String s) {
        if (s == null) {
            return Tson.of(Double.MIN_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.MIN_VALUE);
            case "float":
                return Tson.of(Float.MIN_VALUE);
            case "byte":
                return Tson.of(Byte.MIN_VALUE);
            case "short":
                return Tson.of(Short.MIN_VALUE);
            case "int":
                return Tson.of(Integer.MIN_VALUE);
            case "long":
                return Tson.of(Long.MIN_VALUE);
        }
        throw new IllegalArgumentException("Unsupported -Inf(" + s + ")");
    }

    @Override
    public TsonElement parseNumber(String s) {
        TsonNumberHelper parse;
        try {
            parse = TsonNumberHelper.parse(s);
        }catch (RuntimeException ex){
            throw ex;
        }
        return parse.toTson();
    }
}
