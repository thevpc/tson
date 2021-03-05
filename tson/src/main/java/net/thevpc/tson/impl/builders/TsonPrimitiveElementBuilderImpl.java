package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonElementDecorator;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

public class TsonPrimitiveElementBuilderImpl extends AbstractTsonElementBuilder<TsonPrimitiveBuilder> implements TsonPrimitiveBuilder {
    private TsonElement value = TsonNullImpl.INSTANCE;

    @Override
    public TsonElementType getType() {
        return value.getType();
    }

    @Override
    public TsonPrimitiveBuilder set(TsonElementBase element0) {
        TsonElement element= Tson.elem(element0);
        if (element == null) {
            element = TsonNullImpl.INSTANCE;
        }
        if (element instanceof TsonElementDecorator) {
            TsonElementDecorator d = (TsonElementDecorator) element;
            this.value = d.getBase();
            setComments(d.getComments());
            setAnnotations(d.getAnnotations());
        } else {
            switch (element.getType()) {
                case PAIR:
                case OBJECT:
                case UPLET:
                case ARRAY:
                case FUNCTION:{
                    throw new ClassCastException("Not a primitive type " + element.getType());
                }
            }
            this.value = element;
        }
        return this;
    }

    @Override
    public TsonElement getValue() {
        return value == null ? TsonNullImpl.INSTANCE : value;
    }

    @Override
    public TsonPrimitiveBuilder setNull() {
        value = TsonNullImpl.INSTANCE;
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(boolean value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(String value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Date value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(LocalDate value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(java.sql.Date value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Time value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Instant value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(LocalTime value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Pattern value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(char value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(int value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(long value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(byte value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(short value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(float value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(double value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Character value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Integer value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Long value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Byte value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Short value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Float value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Double value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(Boolean value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(byte[] value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder set(InputStream value) {
        this.value = Tson.elem(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setBinary(File value) {
        this.value = Tson.bstream(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setBinary(Path value) {
        this.value = Tson.bstream(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setCode(File value) {
        this.value = Tson.cstream(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setCode(Path value) {
        this.value = Tson.cstream(value);
        return this;
    }


    @Override
    public TsonPrimitiveBuilder setStopStream(File value,String stopWord) {
        this.value = Tson.stopStream(value,stopWord);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setStopWordStream(Path value,String stopWord) {
        this.value = Tson.stopStream(value,stopWord);
        return this;
    }

    @Override
    public Boolean getBooleanObject() {
        return getValue().getBooleanObject();
    }

    @Override
    public Character getCharObject() {
        return getValue().getCharObject();
    }

    @Override
    public Byte getByteObject() {
        return getValue().getByteObject();
    }

    @Override
    public Short getShortObject() {
        return getValue().getShortObject();
    }

    @Override
    public Integer getIntObject() {
        return getValue().getIntObject();
    }

    @Override
    public Long getLongObject() {
        return getValue().getLongObject();
    }

    @Override
    public Float getFloatObject() {
        return getValue().getFloatObject();
    }

    @Override
    public Double getDoubleObject() {
        return getValue().getDoubleObject();
    }

    @Override
    public float getFloat() {
        return getValue().getFloat();
    }

    @Override
    public double getDouble() {
        return getValue().getDouble();
    }

    @Override
    public byte getByte() {
        return getValue().getByte();
    }

    @Override
    public char getChar() {
        return getValue().getChar();
    }

    @Override
    public boolean getBoolean() {
        return getValue().getBoolean();
    }

    @Override
    public String getString() {
        return getValue().getString();
    }

    @Override
    public int getInt() {
        return getValue().getInt();
    }

    @Override
    public long getLong() {
        return getValue().getLong();
    }

    @Override
    public short getShort() {
        return getValue().getShort();
    }

    @Override
    public LocalDate getDate() {
        return getValue().getDate();
    }

    @Override
    public Instant getDateTime() {
        return getValue().getDateTime();
    }

    @Override
    public LocalTime getTime() {
        return getValue().getTime();
    }

    @Override
    public Pattern getRegex() {
        return getValue().getRegex();
    }

    @Override
    public TsonPrimitiveBuilder setName(String value) {
        this.value = Tson.name(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setAlias(String value) {
        this.value = Tson.alias(value);
        return this;
    }

    @Override
    public TsonPrimitiveBuilder setRegex(String value) {
        this.value = Tson.regex(value);
        return this;
    }

    @Override
    public TsonElement build() {
        return TsonUtils.decorate(
                value
                , getComments(), getAnnotations())
                ;
    }
}
