package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonParserVisitor;
import net.thevpc.tson.*;

public abstract class AbstractPrimitiveTsonElement extends AbstractTsonElement {
    public AbstractPrimitiveTsonElement(TsonElementType type) {
        super(type);
    }

    protected <T> T throwPrimitive(TsonElementType type){
        throw new ClassCastException(getType()+" Cannot cast to "+type);
    }

    protected <T> T throwNonPrimitive(TsonElementType type){
        throw new ClassCastException(getType()+" cannot be cast to "+type);
    }

//    @Override
//    public boolean getBoolean() {
//        return toBooleanElement().getValue();
//    }
//
//    @Override
//    public float getFloat() {
//        return toFloatElement().getValue();
//    }
//
//    @Override
//    public double getDouble() {
//        return toDoubleElement().getValue();
//    }
//
//    @Override
//    public byte getByte() {
//        return toByteElement().getValue();
//    }
//
//    @Override
//    public char getChar() {
//        return toCharElement().getValue();
//    }
//
//    @Override
//    public Boolean getBooleanObject() {
//        return getBoolean();
//    }
//
//    @Override
//    public Character getCharObject() {
//        return getChar();
//    }
//
//    @Override
//    public Byte getByteObject() {
//        return getByte();
//    }
//
//    @Override
//    public Long getLongObject() {
//        return getLong();
//    }
//
//    @Override
//    public Integer getIntObject() {
//        return getInt();
//    }
//
//    @Override
//    public Short getShortObject() {
//        return getShort();
//    }
//
//    @Override
//    public Float getFloatObject() {
//        return getFloat();
//    }
//
//    @Override
//    public Double getDoubleObject() {
//        return getDouble();
//    }
//
//    @Override
//    public String getString() {
//        return toStringElement().getValue();
//    }
//
//    @Override
//    public int getInt() {
//        return toIntElement().getValue();
//    }
//
//    @Override
//    public long getLong() {
//        return toLongElement().getValue();
//    }
//
//    @Override
//    public short getShort() {
//        return toShortElement().getValue();
//    }
//
//    @Override
//    public LocalDate getDate() {
//        return toDateElement().getDate();
//    }
//
//    @Override
//    public Instant getDateTime() {
//        return toDateTimeElement().getDateTime();
//    }
//
//    @Override
//    public LocalTime getTime() {
//        return toTimeElement().getTime();
//    }
//
//    @Override
//    public Pattern getRegex() {
//        return toRegexElement().getRegex();
//    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        visitor.visitPrimitiveEnd(this);
    }
}
