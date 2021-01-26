package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.*;
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
import java.util.*;
import java.util.regex.Pattern;

public abstract class TsonElementDecorator extends AbstractTsonElementBase {

    private TsonElement base;
    private String comments;
    private TsonAnnotation[] annotations;

    public static TsonElement of(TsonElement base, String comments, TsonAnnotation[] annotations) {
        boolean decorated = base instanceof TsonElementDecorator;
        comments = TsonUtils.trimToNull(comments);
        if (comments == null && (annotations == null || annotations.length == 0)) {
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
            String oldComments = TsonUtils.trimToNull(base.getComments());
            TsonAnnotation[] oldAnnotations = base.getAnnotations();
            if (Objects.equals(comments, oldComments)
                    && Arrays.equals(annotations, oldAnnotations)) {
                return base;
            }
        }
        switch (base.getType()) {
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
                return new Asalias((TsonAlias) base, comments, annotations);
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
            case FUNCTION:
                return new AsFunction((TsonFunction) base, comments, annotations);
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
        }
        throw new IllegalArgumentException("Unsupported " + base.getType());
    }

    public static TsonElement of(TsonElement base, String comments, Collection<TsonAnnotation> annotationsList) {
        boolean decorated = base instanceof TsonElementDecorator;
        comments = TsonUtils.trimToNull(comments);
        if (comments == null && (annotationsList == null || annotationsList.isEmpty())) {
            if (!decorated) {
                return base;
            } else {
                return ((TsonElementDecorator) base).base;
            }
        }
        TsonAnnotation[] annotations = annotationsList == null ? TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY : annotationsList.toArray(TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY);
        if (decorated) {
            String oldComments = TsonUtils.trimToNull(base.getComments());
            TsonAnnotation[] oldAnnotations = base.getAnnotations();
            if (Objects.equals(comments, oldComments)
                    && Arrays.equals(annotations, oldAnnotations)) {
                return base;
            }
        }
        switch (base.getType()) {
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
            case FUNCTION:
                return new AsFunction((TsonFunction) base, comments, annotations);
            case PAIR:
                return new AsPair((TsonPair) base, comments, annotations);
        }
        throw new IllegalArgumentException("Unsupported " + base.getType());
    }

    private TsonElementDecorator(TsonElement base, String comments, TsonAnnotation[] annotations) {
        if (base instanceof TsonElementDecorator) {
            base = ((TsonElementDecorator) base).base;
        }
        this.base = base;
        this.comments = comments;
        this.annotations = Arrays.copyOf(annotations, annotations.length);
    }

    protected void processCommentsAndAnnotations(TsonParserVisitor visitor) {
        visitor.visitComments(getComments());
        for (TsonAnnotation annotation : getAnnotations()) {
            visitor.visitAnnotationStart(annotation.getName());
            for (TsonElement param : annotation.getAll()) {
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
    public TsonElementType getType() {
        return base.getType();
    }

    @Override
    public String getComments() {
        return comments;
    }

    public TsonAnnotation[] getAnnotations() {
        return Arrays.copyOf(annotations, annotations.length);
    }

    @Override
    public int getAnnotationsCount() {
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
    public TsonFunction toFunction() {
        return base.toFunction();
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
    public TsonArray toArray() {
        return base.toArray();
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
    public byte getByte() {
        return base.getByte();
    }

    @Override
    public char getChar() {
        return base.getChar();
    }

    @Override
    public boolean getBoolean() {
        return base.getBoolean();
    }

    @Override
    public String getString() {
        return base.getString();
    }

    @Override
    public int getInt() {
        return base.getInt();
    }

    @Override
    public long getLong() {
        return base.getLong();
    }

    @Override
    public short getShort() {
        return base.getShort();
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
    public LocalDate getDate() {
        return base.getDate();
    }

    @Override
    public Instant getDateTime() {
        return base.getDateTime();
    }

    @Override
    public LocalTime getTime() {
        return base.getTime();
    }

    @Override
    public Pattern getRegex() {
        return base.getRegex();
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
    public TsonPair toKeyValue() {
        return base.toKeyValue();
    }

    @Override
    public float getFloat() {
        return base.getFloat();
    }

    @Override
    public double getDouble() {
        return base.getDouble();
    }

    @Override
    public Boolean getBooleanObject() {
        return base.getBooleanObject();
    }

    @Override
    public Character getCharObject() {
        return base.getCharObject();
    }

    @Override
    public Byte getByteObject() {
        return base.getByteObject();
    }

    @Override
    public Integer getIntObject() {
        return base.getIntObject();
    }

    @Override
    public Long getLongObject() {
        return base.getLongObject();
    }

    @Override
    public Short getShortObject() {
        return base.getShortObject();
    }

    @Override
    public Float getFloatObject() {
        return base.getFloatObject();
    }

    @Override
    public Double getDoubleObject() {
        return base.getDoubleObject();
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
    public BigInteger getBigInteger() {
        return base.getBigInteger();
    }

    @Override
    public BigDecimal getBigDecimal() {
        return base.getBigDecimal();
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
    public Number getNumber() {
        return base.getNumber();
    }

    @Override
    public int compareTo(TsonElement o) {
        return base.compareTo(o);
    }

    public static abstract class AsPrimitive<T extends TsonElement> extends TsonElementDecorator {

        public AsPrimitive(T base, String comments, TsonAnnotation[] annotations) {
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

        public AsBigInt(TsonBigInt base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public BigInteger getValue() {
            return getBase().getValue();
        }
    }

    public static class AsBigDecimal extends AsPrimitive<TsonBigDecimal> implements TsonBigDecimal {

        public AsBigDecimal(TsonBigDecimal base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public BigDecimal getValue() {
            return getBase().getValue();
        }
    }

    public static class AsBigComplex extends AsPrimitive<TsonBigComplex> implements TsonBigComplex {

        public AsBigComplex(TsonBigComplex base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public BigDecimal getReal() {
            return getBase().getReal();
        }

        @Override
        public BigDecimal getImag() {
            return getBase().getImag();
        }
    }

    public static class AsBinaryStream extends AsPrimitive<TsonBinaryStream> implements TsonBinaryStream {

        public AsBinaryStream(TsonBinaryStream base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public InputStream getValue() {
            return getBase().getValue();
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

        public AsCharStream(TsonCharStream base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public String getStreamType() {
            return getBase().getStreamType();
        }

        @Override
        public Reader getValue() {
            return getBase().getValue();
        }
    }

    public static class AsDoubleComplex extends AsPrimitive<TsonDoubleComplex> implements TsonDoubleComplex {

        public AsDoubleComplex(TsonDoubleComplex base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public double getReal() {
            return getBase().getReal();
        }

        @Override
        public double getImag() {
            return getBase().getImag();
        }
    }

    public static class AsFloatComplex extends AsPrimitive<TsonFloatComplex> implements TsonFloatComplex {

        public AsFloatComplex(TsonFloatComplex base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public float getReal() {
            return getBase().getReal();
        }

        @Override
        public float getImag() {
            return getBase().getImag();
        }
    }

    public static class AsString extends AsPrimitive<TsonString> implements TsonString {

        public AsString(TsonString base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public String getValue() {
            return getBase().getValue();
        }
    }

    public static class AsName extends AsPrimitive<TsonName> implements TsonName {

        public AsName(TsonName base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public String getName() {
            return getBase().getName();
        }
    }

    public static class Asalias extends AsPrimitive<TsonAlias> implements TsonAlias {

        public Asalias(TsonAlias base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public String getName() {
            return getBase().getName();
        }
    }

    public static class AsRegex extends AsPrimitive<TsonRegex> implements TsonRegex {

        public AsRegex(TsonRegex base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public Pattern getValue() {
            return getBase().getValue();
        }
    }

    public static class AsDate extends AsPrimitive<TsonDate> implements TsonDate {

        public AsDate(TsonDate base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public LocalDate getValue() {
            return getBase().getValue();
        }
    }

    public static class AsDateTime extends AsPrimitive<TsonDateTime> implements TsonDateTime {

        public AsDateTime(TsonDateTime base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public Instant getValue() {
            return getBase().getValue();
        }
    }

    public static class AsTime extends AsPrimitive<TsonTime> implements TsonTime {

        public AsTime(TsonTime base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public LocalTime getValue() {
            return getBase().getValue();
        }
    }

    public static class AsNull extends AsPrimitive<TsonNull> implements TsonNull {

        public AsNull(TsonNull base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }
    }

    public static class AsBoolean extends AsPrimitive<TsonBoolean> implements TsonBoolean {

        public AsBoolean(TsonBoolean base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public boolean getValue() {
            return getBase().getValue();
        }
    }

    public static class AsChar extends AsPrimitive<TsonChar> implements TsonChar {

        public AsChar(TsonChar base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public char getValue() {
            return getBase().getValue();
        }
    }

    public static class AsByte extends AsPrimitive<TsonByte> implements TsonByte {

        public AsByte(TsonByte base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public byte getValue() {
            return getBase().getValue();
        }
    }

    public static class AsShort extends AsPrimitive<TsonShort> implements TsonShort {

        public AsShort(TsonShort base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public short getValue() {
            return getBase().getValue();
        }
    }

    public static class AsInt extends AsPrimitive<TsonInt> implements TsonInt {

        public AsInt(TsonInt base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public int getValue() {
            return getBase().getValue();
        }
    }

    public static class AsLong extends AsPrimitive<TsonLong> implements TsonLong {

        public AsLong(TsonLong base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public long getValue() {
            return getBase().getValue();
        }
    }

    public static class AsFloat extends AsPrimitive<TsonFloat> implements TsonFloat {

        public AsFloat(TsonFloat base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public float getValue() {
            return getBase().getValue();
        }
    }

    public static class AsDouble extends AsPrimitive<TsonDouble> implements TsonDouble {

        public AsDouble(TsonDouble base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public double getValue() {
            return getBase().getValue();
        }
    }

    public static class AsObject extends TsonElementDecorator implements TsonObject {

        public AsObject(TsonObject base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
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
        public TsonElementHeader getHeader() {
            return getBase().getHeader();
        }

        @Override
        public List<TsonElement> all() {
            return getBase().all();
        }

        @Override
        public List<TsonElement> getAll() {
            return getBase().getAll();
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
            if (getHeader() != null) {
                TsonElementHeader h = getHeader();
                if (h != null) {
                    if (!h.getName().isEmpty()) {
                        visitor.visitNamedStart(h.getName());
                    }
                    visitor.visitParamsStart();
                    for (TsonElement param : h.getAll()) {
                        visitor.visitParamElementStart();
                        param.visit(visitor);
                        visitor.visitParamElementEnd();
                    }
                    visitor.visitParamsEnd();
                }
            }
            visitor.visitNamedObjectStart();
            for (TsonElement element : getAll()) {
                visitor.visitObjectElementStart();
                element.visit(visitor);
                visitor.visitObjectElementEnd();
            }
            visitor.visitObjectEnd();
        }
    }

    public static class AsUplet extends TsonElementDecorator implements TsonUplet {

        public AsUplet(TsonUplet base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonUplet getBase() {
            return (TsonUplet) super.getBase();
        }

        @Override
        public List<TsonElement> getAll() {
            return getBase().getAll();
        }

        @Override
        public List<TsonElement> all() {
            return getBase().all();
        }

        @Override
        public int size() {
            return getBase().size();
        }

        @Override
        public boolean isEmpty() {
            return getBase().isEmpty();
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
            visitor.visitParamsStart();
            for (TsonElement param : getAll()) {
                visitor.visitParamElementStart();
                param.visit(visitor);
                visitor.visitParamElementEnd();
            }
            visitor.visitParamsEnd();
            visitor.visitUpletEnd();
        }
    }

    public static class AsFunction extends TsonElementDecorator implements TsonFunction {

        public AsFunction(TsonFunction base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public int size() {
            return getBase().size();
        }

        @Override
        public TsonFunction getBase() {
            return (TsonFunction) super.getBase();
        }

        @Override
        public String name() {
            return getBase().name();
        }

        @Override
        public String getName() {
            return getBase().getName();
        }

        @Override
        public List<TsonElement> getAll() {
            return getBase().getAll();
        }

        @Override
        public TsonFunctionBuilder builder() {
            return new TsonFunctionBuilderImpl().merge(this);
        }

        @Override
        public List<TsonElement> all() {
            return getAll();
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitElementStart();
            processCommentsAndAnnotations(visitor);
            visitor.visitNamedStart(getName());
            visitor.visitParamsStart();
            for (TsonElement param : getAll()) {
                visitor.visitParamElementStart();
                param.visit(visitor);
                visitor.visitParamElementEnd();
            }
            visitor.visitParamsEnd();
            visitor.visitFunctionEnd();
        }
    }

    public static class AsPair extends TsonElementDecorator implements TsonPair {

        public AsPair(TsonPair base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
        }

        @Override
        public TsonPair getBase() {
            return (TsonPair) super.getBase();
        }

        @Override
        public TsonElement getValue() {
            return getBase().getValue();
        }

        @Override
        public TsonElement getKey() {
            return getBase().getKey();
        }

        @Override
        public TsonKeyValueBuilder builder() {
            return new TsonKeyValueBuilderImpl().merge(this);
        }

        @Override
        public void visit(TsonParserVisitor visitor) {
            visitor.visitInstructionStart();
            processCommentsAndAnnotations(visitor);
            getKey().visit(visitor);
            getValue().visit(visitor);
            visitor.visitKeyValueEnd();
        }
    }

    public static class AsArray extends TsonElementDecorator implements TsonArray {

        public AsArray(TsonArray base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
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
        public TsonElementHeader getHeader() {
            return getBase().getHeader();
        }

        @Override
        public List<TsonElement> all() {
            return getBase().all();
        }

        @Override
        public List<TsonElement> getAll() {
            return getBase().getAll();
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
            if (getHeader() != null) {
                TsonElementHeader h = getHeader();
                if (h != null) {
                    if (!h.getName().isEmpty()) {
                        visitor.visitNamedStart(h.getName());
                    }
                    visitor.visitParamsStart();
                    for (TsonElement param : h.getAll()) {
                        visitor.visitParamElementStart();
                        param.visit(visitor);
                        visitor.visitParamElementEnd();
                    }
                    visitor.visitParamsEnd();
                }
            }
            visitor.visitNamedArrayStart();
            for (TsonElement element : getAll()) {
                visitor.visitArrayElementStart();
                element.visit(visitor);
                visitor.visitArrayElementEnd();
            }
            visitor.visitArrayEnd();
        }
    }

    public static class AsMatrix extends TsonElementDecorator implements TsonMatrix {

        public AsMatrix(TsonMatrix base, String comments, TsonAnnotation[] annotations) {
            super(base, comments, annotations);
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
        public TsonElementHeader getHeader() {
            return getBase().getHeader();
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
            if (getHeader() != null) {
                TsonElementHeader h = getHeader();
                if (h != null) {
                    if (!h.getName().isEmpty()) {
                        visitor.visitNamedStart(h.getName());
                    }
                    visitor.visitParamsStart();
                    for (TsonElement param : h.getAll()) {
                        visitor.visitParamElementStart();
                        param.visit(visitor);
                        visitor.visitParamElementEnd();
                    }
                    visitor.visitParamsEnd();
                }
            }
            visitor.visitNamedArrayStart();
            for (TsonElement element : getRows()) {
                visitor.visitArrayElementStart();
                element.visit(visitor);
                visitor.visitArrayElementEnd();
            }
            visitor.visitArrayEnd();
        }
    }

    @Override
    public boolean isNull() {
        return base.isNull();
    }
}
