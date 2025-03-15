package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Pattern;

public abstract class TsonElementDecorator extends AbstractTsonElementBase {

    private TsonElement base;
    private TsonComments comments;
    private TsonAnnotation[] annotations;

    private static TsonComment[] trimToNull(TsonComment[] comments) {
        if (comments == null) {
            return null;
        }
        List<TsonComment> ok = new ArrayList<>();
        for (TsonComment c : comments) {
            if (c != null) {
                ok.add(c);
            }
        }
        if (ok.isEmpty()) {
            return null;
        }
        return ok.toArray(new TsonComment[0]);
    }

    public static TsonElement of(TsonElement base, TsonComments comments, TsonAnnotation[] annotations) {
        boolean decorated = base instanceof TsonElementDecorator;
        if (
                comments == null
                        && (annotations == null || annotations.length == 0)
        ) {
            if (!decorated) {
                return base;
            } else {
                return ((TsonElementDecorator) base).base;
            }
        }
        if (annotations == null) {
            annotations = TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY;
        }
        if (decorated) {
            TsonComments oldComments = base.comments();
            TsonAnnotation[] oldAnnotations = base.annotations();
            if (
                    Objects.equals(comments, oldComments)
                            && Arrays.equals(annotations, oldAnnotations)) {
                return base;
            }
        }
        switch (base.type()) {
            case NULL:
                return new AsNull((TsonNull) base, comments, annotations);
            case STRING:
                return new AsString((TsonString) base, comments, annotations);
            case BOOLEAN:
                return new AsBoolean((TsonBoolean) base, comments, annotations);
            case BYTE:
                return new AsByte((TsonByte) base, comments, annotations);
            case SHORT:
                return new AsShort((TsonShort) base, comments, annotations);
            case INT:
                return new AsInt((TsonInt) base, comments, annotations);
            case LONG:
                return new AsLong((TsonLong) base, comments, annotations);
            case FLOAT:
                return new AsFloat((TsonFloat) base, comments, annotations);
            case DOUBLE:
                return new AsDouble((TsonDouble) base, comments, annotations);
            case NAME:
                return new AsName((TsonName) base, comments, annotations);
            case ALIAS:
                return new AsAlias((TsonAlias) base, comments, annotations);
            case REGEX:
                return new AsRegex((TsonRegex) base, comments, annotations);
            case DATE:
                return new AsDate((TsonDate) base, comments, annotations);
            case DATETIME:
                return new AsDateTime((TsonDateTime) base, comments, annotations);
            case TIME:
                return new AsTime((TsonTime) base, comments, annotations);
            case CHAR:
                return new AsChar((TsonChar) base, comments, annotations);
            case OBJECT:
                return new AsObject((TsonObject) base, comments, annotations);
            case ARRAY:
                return new AsArray((TsonArray) base, comments, annotations);
            case MATRIX:
                return new AsMatrix((TsonMatrix) base, comments, annotations);
            case UPLET:
                return new AsUplet((TsonUplet) base, comments, annotations);
            case PAIR:
                return new AsPair((TsonPair) base, comments, annotations);
            case BIG_INT:
                return new AsBigInt((TsonBigInt) base, comments, annotations);
            case BIG_COMPLEX:
                return new AsBigComplex((TsonBigComplex) base, comments, annotations);
            case BIG_DECIMAL:
                return new AsBigDecimal((TsonBigDecimal) base, comments, annotations);
            case BINARY_STREAM:
                return new AsBinaryStream((TsonBinaryStream) base, comments, annotations);
            case CHAR_STREAM:
                return new AsCharStream((TsonCharStream) base, comments, annotations);
            case DOUBLE_COMPLEX:
                return new AsDoubleComplex((TsonDoubleComplex) base, comments, annotations);
            case FLOAT_COMPLEX:
                return new AsFloatComplex((TsonFloatComplex) base, comments, annotations);
            case OP:
                return new AsOp((TsonOp) base, comments, annotations);
            case CUSTOM:
                return new AsCustom((TsonOp) base, comments, annotations);
        }
        throw new IllegalArgumentException("Unsupported " + base.type());
    }

    public static TsonElement of(TsonElement base, TsonComments comments, Collection<TsonAnnotation> annotationsList) {
        boolean decorated = base instanceof TsonElementDecorator;
        if (comments == null && (annotationsList == null || annotationsList.isEmpty())) {
            if (!decorated) {
                return base;
            } else {
                return ((TsonElementDecorator) base).base;
            }
        }
        TsonAnnotation[] annotations = annotationsList == null ? TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY : annotationsList.stream().filter(Objects::nonNull).toArray(TsonAnnotation[]::new);
        if (decorated) {
            TsonComments oldComments = base.comments();
            TsonAnnotation[] oldAnnotations = base.annotations();
            if (Objects.equals(comments, oldComments)
                    && Arrays.equals(annotations, oldAnnotations)) {
                return base;
            }
        }
        if ((comments == null || comments.isBlank()) && annotations.length == 0) {
            return base;
        }
        switch (base.type()) {
            case NULL:
                return new AsNull((TsonNull) base, comments, annotations);
            case STRING:
                return new AsString((TsonString) base, comments, annotations);
            case BOOLEAN:
                return new AsBoolean((TsonBoolean) base, comments, annotations);
            case BYTE:
                return new AsByte((TsonByte) base, comments, annotations);
            case SHORT:
                return new AsShort((TsonShort) base, comments, annotations);
            case INT:
                return new AsInt((TsonInt) base, comments, annotations);
            case LONG:
                return new AsLong((TsonLong) base, comments, annotations);
            case FLOAT:
                return new AsFloat((TsonFloat) base, comments, annotations);
            case DOUBLE:
                return new AsDouble((TsonDouble) base, comments, annotations);
            case NAME:
                return new AsName((TsonName) base, comments, annotations);
            case REGEX:
                return new AsRegex((TsonRegex) base, comments, annotations);
            case DATE:
                return new AsDate((TsonDate) base, comments, annotations);
            case DATETIME:
                return new AsDateTime((TsonDateTime) base, comments, annotations);
            case TIME:
                return new AsTime((TsonTime) base, comments, annotations);
            case CHAR:
                return new AsChar((TsonChar) base, comments, annotations);
            case OBJECT:
                return new AsObject((TsonObject) base, comments, annotations);
            case ARRAY:
                return new AsArray((TsonArray) base, comments, annotations);
            case UPLET:
                return new AsUplet((TsonUplet) base, comments, annotations);
            case PAIR:
                return new AsPair((TsonPair) base, comments, annotations);
        }
        throw new IllegalArgumentException("Unsupported " + base.type());
    }

    private TsonElementDecorator(TsonElement base, TsonComments comments, TsonAnnotation[] annotations) {
        if (base instanceof TsonElementDecorator) {
            base = ((TsonElementDecorator) base).base;
        }
        this.base = base;
        this.comments = comments;
        this.annotations = Arrays.copyOf(annotations, annotations.length);
    }

    protected void processCommentsAndAnnotations(TsonParserVisitor visitor) {
        for (TsonComment c : comments().getLeadingComments()) {
            visitor.visitComments(c);
        }
        for (TsonAnnotation annotation : annotations()) {
            visitor.visitAnnotationStart(annotation.name());
            for (TsonElement param : annotation.args()) {
                visitor.visitAnnotationParamStart();
                param.visit(visitor);
                visitor.visitAnnotationParamEnd();
            }
            visitor.visitAnnotationEnd();
        }
    }

    public TsonElement getBase() {
        return base;
    }

    public TsonElement simplify() {
        if (comments == null && annotations.length == 0) {
            return base;
        }
        return this;
    }

    @Override
    public TsonMatrix toMatrix() {
        return base.toMatrix();
    }

    @Override
    public TsonElementType type() {
        return base.type();
    }

    @Override
    public TsonComments comments() {
        return comments;
    }

    public TsonAnnotation[] annotations() {
        return Arrays.copyOf(annotations, annotations.length);
    }

    @Override
    public int annotationsCount() {
        return annotations.length;
    }

    @Override
    public TsonString toStr() {
        return base.toStr();
    }

    @Override
    public TsonLong toLong() {
        return base.toLong();
    }

    @Override
    public TsonCustom toCustom() {
        return base.toCustom();
    }

    @Override
    public TsonNumber toNumber() {
        return base.toNumber();
    }

    @Override
    public TsonBinaryStream toBinaryStream() {
        return base.toBinaryStream();
    }

    @Override
    public TsonCharStream toCharStream() {
        return base.toCharStream();
    }

    @Override
    public TsonInt toInt() {
        return base.toInt();
    }

    @Override
    public TsonShort toShort() {
        return base.toShort();
    }

    @Override
    public TsonByte toByte() {
        return base.toByte();
    }

    @Override
    public TsonChar toChar() {
        return base.toChar();
    }

    @Override
    public TsonBoolean toBoolean() {
        return base.toBoolean();
    }


    @Override
    public TsonName toName() {
        return base.toName();
    }

    @Override
    public TsonAlias toAlias() {
        return base.toAlias();
    }

    @Override
    public Temporal temporalValue() {
        return base.temporalValue();
    }

    @Override
    public TsonArray toArray() {
        return base.toArray();
    }

    @Override
    public TsonContainer toContainer() {
        return base.toContainer();
    }

    @Override
    public TsonObject toObject() {
        return base.toObject();
    }

    @Override
    public TsonUplet toUplet() {
        return base.toUplet();
    }

    @Override
    public byte byteValue() {
        return base.byteValue();
    }

    @Override
    public char charValue() {
        return base.charValue();
    }

    @Override
    public boolean booleanValue() {
        return base.booleanValue();
    }

    @Override
    public String stringValue() {
        return base.stringValue();
    }

    @Override
    public int intValue() {
        return base.intValue();
    }

    @Override
    public long longValue() {
        return base.longValue();
    }

    @Override
    public short shortValue() {
        return base.shortValue();
    }

    @Override
    public TsonDate toDate() {
        return base.toDate();
    }

    @Override
    public TsonDateTime toDateTime() {
        return base.toDateTime();
    }

    @Override
    public TsonTime toTime() {
        return base.toTime();
    }

    @Override
    public TsonRegex toRegex() {
        return base.toRegex();
    }

    @Override
    public LocalDate dateValue() {
        return base.dateValue();
    }

    @Override
    public Instant dateTimeValue() {
        return base.dateTimeValue();
    }

    @Override
    public LocalTime time() {
        return base.time();
    }

    @Override
    public Pattern regexValue() {
        return base.regexValue();
    }

    @Override
    public TsonFloat toFloat() {
        return base.toFloat();
    }

    @Override
    public TsonDouble toDouble() {
        return base.toDouble();
    }

    @Override
    public TsonPair toPair() {
        return base.toPair();
    }

    @Override
    public TsonOp toOp() {
        return base.toOp();
    }

    @Override
    public float floatValue() {
        return base.floatValue();
    }

    @Override
    public double doubleValue() {
        return base.doubleValue();
    }

    @Override
    public Boolean booleanObject() {
        return base.booleanObject();
    }

    @Override
    public Character charObject() {
        return base.charObject();
    }

    @Override
    public Byte byteObject() {
        return base.byteObject();
    }

    @Override
    public Integer intObject() {
        return base.intObject();
    }

    @Override
    public Long longObject() {
        return base.longObject();
    }

    @Override
    public Short shortObject() {
        return base.shortObject();
    }

    @Override
    public Float floatObject() {
        return base.floatObject();
    }

    @Override
    public Double doubleObject() {
        return base.doubleObject();
    }

    @Override
    public TsonBigInt toBigInt() {
        return base.toBigInt();
    }

    @Override
    public TsonBigDecimal toBigDecimal() {
        return base.toBigDecimal();
    }

    @Override
    public TsonBigComplex toBigComplex() {
        return base.toBigComplex();
    }

    @Override
    public TsonFloatComplex toFloatComplex() {
        return base.toFloatComplex();
    }

    @Override
    public TsonDoubleComplex toDoubleComplex() {
        return base.toDoubleComplex();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return base.bigIntegerValue();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        return base.bigDecimalValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TsonElementDecorator that = (TsonElementDecorator) o;
        return Objects.equals(base, that.base)
                && Objects.equals(comments, that.comments)
                && Arrays.equals(annotations, that.annotations);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(base, comments);
        result = 31 * result + Arrays.hashCode(annotations);
        return result;
    }

    @Override
    public TsonElementBuilder builder() {
        return base.builder();
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (!visitor.visit(this)) {
            return false;
        }
        return base.visit(visitor);
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
    public Number numberValue() {
        return base.numberValue();
    }

    @Override
    public int compareTo(TsonElement o) {
        return base.compareTo(o);
    }

    public static abstract class AsPrimitive<T extends TsonElement> extends TsonElementDecorator {

        public AsPrimitive(T base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public T getBase() {
            return (T) super.getBase();
        }

        @Override
        public TsonPrimitiveBuilder builder() {
            return new TsonPrimitiveElementBuilderImpl().set(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            visitor.visitPrimitiveEnd(this);
        }
    }

    public static class AsBigInt extends AsPrimitive<TsonBigInt> implements TsonBigInt {

        public AsBigInt(TsonBigInt base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonBigInt toBigInt() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public BigInteger value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsBigDecimal extends AsPrimitive<TsonBigDecimal> implements TsonBigDecimal {

        public AsBigDecimal(TsonBigDecimal base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonBigDecimal toBigDecimal() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public BigDecimal value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsBigComplex extends AsPrimitive<TsonBigComplex> implements TsonBigComplex {

        public AsBigComplex(TsonBigComplex base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonBigComplex toBigComplex() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public BigDecimal getReal() {
            return getBase().getReal();
        }

        @Override
        public BigDecimal getImag() {
            return getBase().getImag();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsBinaryStream extends AsPrimitive<TsonBinaryStream> implements TsonBinaryStream {

        public AsBinaryStream(TsonBinaryStream base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonBinaryStream toBinaryStream() {
            return this;
        }

        @Override
        public InputStream value() {
            return getBase().value();
        }

        @Override
        public Reader getBase64Value() {
            return getBase().getBase64Value();
        }

        @Override
        public Reader getBase64Value(int lineMax) {
            return getBase().getBase64Value(lineMax);
        }
    }

    public static class AsCharStream extends AsPrimitive<TsonCharStream> implements TsonCharStream {

        public AsCharStream(TsonCharStream base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonCharStream toCharStream() {
            return this;
        }

        @Override
        public String getStreamType() {
            return getBase().getStreamType();
        }

        @Override
        public Reader value() {
            return getBase().value();
        }
    }

    public static class AsDoubleComplex extends AsPrimitive<TsonDoubleComplex> implements TsonDoubleComplex {

        public AsDoubleComplex(TsonDoubleComplex base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonDoubleComplex toDoubleComplex() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public double getReal() {
            return getBase().getReal();
        }

        @Override
        public double getImag() {
            return getBase().getImag();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsFloatComplex extends AsPrimitive<TsonFloatComplex> implements TsonFloatComplex {

        public AsFloatComplex(TsonFloatComplex base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonFloatComplex toFloatComplex() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public float getReal() {
            return getBase().getReal();
        }

        @Override
        public float getImag() {
            return getBase().getImag();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsOp extends TsonElementDecorator implements TsonOp {

        public AsOp(TsonOp base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonOp toOp() {
            return this;
        }

        @Override
        public TsonOpType opType() {
            return getBase().opType();
        }

        @Override
        public TsonOp getBase() {
            return (TsonOp) super.getBase();
        }

        @Override
        public TsonElement second() {
            return getBase().second();
        }

        @Override
        public TsonElement first() {
            return getBase().first();
        }

        @Override
        public String opName() {
            return getBase().opName();
        }


        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            first().visit(visitor);
            visitor.visitBinOpEnd(opName());
            second().visit(visitor);
        }
    }

    public static class AsCustom extends TsonElementDecorator implements TsonCustom {

        public AsCustom(TsonOp base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonCustom toCustom() {
            return this;
        }

        @Override
        public TsonCustom getBase() {
            return (TsonCustom) super.getBase();
        }

        @Override
        public Object value() {
            return getBase().value();
        }

        @Override
        public TsonCustomBuilder builder() {
            return getBase().builder();
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            visitor.visitCustomEnd(this);
        }
    }

    public static class AsString extends AsPrimitive<TsonString> implements TsonString {

        public AsString(TsonString base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonString toStr() {
            return this;
        }

        @Override
        public TsonStringLayout layout() {
            return getBase().layout();
        }

        @Override
        public String raw() {
            return getBase().raw();
        }

        @Override
        public String value() {
            return getBase().value();
        }

        @Override
        public String quoted() {
            return getBase().quoted();
        }
    }

    public static class AsName extends AsPrimitive<TsonName> implements TsonName {

        public AsName(TsonName base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonName toName() {
            return this;
        }

        @Override
        public String value() {
            return getBase().value();
        }
    }

    public static class AsAlias extends AsPrimitive<TsonAlias> implements TsonAlias {

        public AsAlias(TsonAlias base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonAlias toAlias() {
            return this;
        }

        @Override
        public String getName() {
            return getBase().getName();
        }
    }

    public static class AsRegex extends AsPrimitive<TsonRegex> implements TsonRegex {

        public AsRegex(TsonRegex base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonRegex toRegex() {
            return this;
        }

        @Override
        public Pattern value() {
            return getBase().value();
        }
    }

    public static class AsDate extends AsPrimitive<TsonDate> implements TsonDate {

        public AsDate(TsonDate base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonDate toDate() {
            return this;
        }

        @Override
        public LocalDate value() {
            return getBase().value();
        }
    }

    public static class AsDateTime extends AsPrimitive<TsonDateTime> implements TsonDateTime {

        public AsDateTime(TsonDateTime base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonDateTime toDateTime() {
            return this;
        }

        @Override
        public Instant value() {
            return getBase().value();
        }
    }

    public static class AsTime extends AsPrimitive<TsonTime> implements TsonTime {

        public AsTime(TsonTime base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonTime toTime() {
            return this;
        }

        @Override
        public LocalTime value() {
            return getBase().value();
        }
    }

    public static class AsNull extends AsPrimitive<TsonNull> implements TsonNull {

        public AsNull(TsonNull base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

    }

    public static class AsBoolean extends AsPrimitive<TsonBoolean> implements TsonBoolean {

        public AsBoolean(TsonBoolean base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonBoolean toBoolean() {
            return this;
        }

        @Override
        public boolean value() {
            return getBase().value();
        }
    }

    public static class AsChar extends AsPrimitive<TsonChar> implements TsonChar {

        public AsChar(TsonChar base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonChar toChar() {
            return this;
        }

        @Override
        public char value() {
            return getBase().value();
        }
    }

    public static class AsByte extends AsPrimitive<TsonByte> implements TsonByte {

        public AsByte(TsonByte base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonByte toByte() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public byte value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsShort extends AsPrimitive<TsonShort> implements TsonShort {

        public AsShort(TsonShort base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonShort toShort() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public short value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsInt extends AsPrimitive<TsonInt> implements TsonInt {

        public AsInt(TsonInt base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonInt toInt() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public int value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsLong extends AsPrimitive<TsonLong> implements TsonLong {

        public AsLong(TsonLong base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonLong toLong() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public long value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsFloat extends AsPrimitive<TsonFloat> implements TsonFloat {

        public AsFloat(TsonFloat base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonFloat toFloat() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public float value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsDouble extends AsPrimitive<TsonDouble> implements TsonDouble {

        public AsDouble(TsonDouble base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonDouble toDouble() {
            return this;
        }

        @Override
        public TsonNumberLayout layout() {
            return getBase().layout();
        }

        @Override
        public double value() {
            return getBase().value();
        }

        @Override
        public String unit() {
            return getBase().unit();
        }
    }

    public static class AsObject extends TsonElementDecorator implements TsonObject {

        public AsObject(TsonObject base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public boolean isNamed() {
            return name() != null;
        }

        @Override
        public TsonObject toObject() {
            return this;
        }

        @Override
        public TsonElementList args() {
            return getBase().args();
        }

        @Override
        public TsonContainer toContainer() {
            return this;
        }

        @Override
        public String name() {
            return getBase().name();
        }

        @Override
        public TsonObject getBase() {
            return (TsonObject) super.getBase();
        }

        @Override
        public TsonElement get(String name) {
            return getBase().get(name);
        }

        @Override
        public TsonElementList body() {
            return getBase().body();
        }

        @Override
        public TsonElement get(TsonElement element) {
            return getBase().get(element);
        }

        @Override
        public int size() {
            return getBase().size();
        }

        @Override
        public Iterator<TsonElement> iterator() {
            return getBase().iterator();
        }

        @Override
        public TsonObjectBuilder builder() {
            return new TsonObjectBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            if (name() != null) {
                visitor.visitNamedStart(name());
            }
            if (isWithArgs()) {
                visitor.visitParamsStart();
                for (TsonElement param : args()) {
                    visitor.visitParamElementStart();
                    param.visit(visitor);
                    visitor.visitParamElementEnd();
                }
                visitor.visitParamsEnd();
            }
            visitor.visitNamedObjectStart();
            for (TsonElement element : body()) {
                visitor.visitObjectElementStart();
                element.visit(visitor);
                visitor.visitObjectElementEnd();
            }
            visitor.visitObjectEnd();
        }

        @Override
        public boolean isWithArgs() {
            return getBase().isWithArgs();
        }

        @Override
        public int argsCount() {
            return getBase().argsCount();
        }
    }

    public static class AsUplet extends TsonElementDecorator implements TsonUplet {

        public AsUplet(TsonUplet base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonUplet toUplet() {
            return this;
        }

        @Override
        public TsonContainer toContainer() {
            return this;
        }

        @Override
        public boolean isNamed() {
            return getBase().isNamed();
        }

        @Override
        public TsonElementList body() {
            return getBase().body();
        }

        @Override
        public TsonElementList args() {
            return getBase().args();
        }

        @Override
        public String name() {
            return getBase().name();
        }

        @Override
        public TsonUplet getBase() {
            return (TsonUplet) super.getBase();
        }

        @Override
        public int size() {
            return getBase().size();
        }

        @Override
        public boolean isBlank() {
            return getBase().isBlank();
        }

        @Override
        public Iterator<TsonElement> iterator() {
            return getBase().iterator();
        }

        @Override
        public TsonUpletBuilder builder() {
            return new TsonUpletBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            if (isNamed()) {
                visitor.visitNamedStart(this.name());
            }
            visitor.visitParamsStart();
            for (TsonElement param : this.args()) {
                visitor.visitParamElementStart();
                param.visit(visitor);
                visitor.visitParamElementEnd();
            }
            visitor.visitParamsEnd();
            visitor.visitUpletEnd();
        }

        @Override
        public int argsCount() {
            return getBase().argsCount();
        }
    }

    public static class AsPair extends TsonElementDecorator implements TsonPair {

        public AsPair(TsonPair base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonPair toPair() {
            return this;
        }

        @Override
        public TsonPair getBase() {
            return (TsonPair) super.getBase();
        }

        @Override
        public TsonElement value() {
            return getBase().value();
        }

        @Override
        public TsonElement key() {
            return getBase().key();
        }

        @Override
        public TsonKeyValueBuilder builder() {
            return new TsonKeyValueBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitInstructionStart();
            processCommentsAndAnnotations(visitor);
            key().visit(visitor);
            value().visit(visitor);
            visitor.visitKeyValueEnd();
        }
    }

    public static class AsArray extends TsonElementDecorator implements TsonArray {

        public AsArray(TsonArray base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public boolean isNamed() {
            return getBase().isNamed();
        }

        @Override
        public TsonArray toArray() {
            return this;
        }

        @Override
        public TsonElementList body() {
            return getBase().body();
        }

        @Override
        public TsonElementList args() {
            return getBase().args();
        }

        @Override
        public String name() {
            return getBase().name();
        }

        @Override
        public TsonContainer toContainer() {
            return this;
        }

        @Override
        public TsonElement get(int index) {
            return getBase().get(index);
        }

        @Override
        public TsonArray getBase() {
            return (TsonArray) super.getBase();
        }

        @Override
        public boolean isEmpty() {
            return getBase().isEmpty();
        }

        @Override
        public int size() {
            return getBase().size();
        }

        @Override
        public Iterator<TsonElement> iterator() {
            return getBase().iterator();
        }

        @Override
        public TsonArrayBuilder builder() {
            return new TsonArrayBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            if (name() != null) {
                visitor.visitNamedStart(name());
            }
            if (isWithArgs()) {
                visitor.visitParamsStart();
                for (TsonElement param : args()) {
                    visitor.visitParamElementStart();
                    param.visit(visitor);
                    visitor.visitParamElementEnd();
                }
                visitor.visitParamsEnd();
            }
            visitor.visitNamedArrayStart();
            for (TsonElement element : this.body()) {
                visitor.visitArrayElementStart();
                element.visit(visitor);
                visitor.visitArrayElementEnd();
            }
            visitor.visitArrayEnd();
        }

        @Override
        public boolean isWithArgs() {
            return getBase().isWithArgs();
        }

        @Override
        public int argsCount() {
            return getBase().argsCount();
        }
    }

    public static class AsMatrix extends TsonElementDecorator implements TsonMatrix {

        public AsMatrix(TsonMatrix base, TsonComments comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonMatrix toMatrix() {
            return this;
        }

        @Override
        public TsonMatrix getBase() {
            return (TsonMatrix) super.getBase();
        }

        @Override
        public int rowSize() {
            return getBase().rowSize();
        }

        @Override
        public List<TsonArray> rows() {
            return getBase().rows();
        }

        @Override
        public TsonElement get(int col, int row) {
            return getBase().get(col, row);
        }

        @Override
        public TsonArray getRow(int row) {
            return getBase().getRow(row);
        }

        @Override
        public List<TsonArray> getRows() {
            return getBase().getRows();
        }

        @Override
        public TsonArray getColumn(int column) {
            return getBase().getColumn(column);
        }

        @Override
        public List<TsonArray> getColumns() {
            return getBase().getColumns();
        }

        @Override
        public boolean isEmpty() {
            return getBase().isEmpty();
        }

        @Override
        public Iterator<TsonArray> iterator() {
            return getBase().iterator();
        }

        @Override
        public TsonArrayBuilder builder() {
            return new TsonArrayBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            if (name() != null) {
                visitor.visitNamedStart(name());
            }
            if (isWithArgs()) {
                visitor.visitParamsStart();
                for (TsonElement param : args()) {
                    visitor.visitParamElementStart();
                    param.visit(visitor);
                    visitor.visitParamElementEnd();
                }
                visitor.visitParamsEnd();
            }
            visitor.visitNamedArrayStart();
            for (TsonElement element : getRows()) {
                visitor.visitArrayElementStart();
                element.visit(visitor);
                visitor.visitArrayElementEnd();
            }
            visitor.visitArrayEnd();
        }

        @Override
        public String name() {
            return getBase().name();
        }

        @Override
        public boolean isWithArgs() {
            return getBase().isWithArgs();
        }

        @Override
        public TsonElementList args() {
            return getBase().args();
        }
    }

    @Override
    public boolean isNull() {
        return base.isNull();
    }

}
