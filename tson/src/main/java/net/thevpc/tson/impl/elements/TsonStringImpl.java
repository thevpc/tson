package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.TsonString;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonStringImpl extends AbstractPrimitiveTsonElement implements TsonString {

    private String stringType;
    private String value;

    public TsonStringImpl(String value,String stringType) {
        super(TsonElementType.STRING);
        this.value = value;
        this.stringType = stringType;
    }
    public String stringType(){
        return stringType;
    }

    @Override
    public TsonString toStr() {
        return this;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TsonStringImpl that = (TsonStringImpl) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getValue());
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return getValue().compareTo(o.getString());
    }

    @Override
    public String getString() {
        return getValue();
    }
}
