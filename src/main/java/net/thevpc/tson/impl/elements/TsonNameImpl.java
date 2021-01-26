package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonNameImpl extends AbstractPrimitiveTsonElement implements TsonName {
    private String value;

    public TsonNameImpl(String value) {
        super(TsonElementType.NAME);
        if(value==null){
            throw new NullPointerException();
        }
        this.value = value;
    }

    @Override
    public TsonName toName() {
        return this;
    }

    @Override
    public TsonString toStr() {
        return (TsonString) Tson.elem(value);
    }

    @Override
    public String getName() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonNameImpl tsonId = (TsonNameImpl) o;
        return Objects.equals(value, tsonId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return value.compareTo(o.toName().getName());
    }

    @Override
    public String getString() {
        return getName();
    }



}
