package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonChar;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonCharImpl extends AbstractPrimitiveTsonElement implements TsonChar {
    private char value;

    public TsonCharImpl(char value) {
        super(TsonElementType.CHAR);
        this.value = value;
    }

    @Override
    public TsonChar toChar() {
        return this;
    }

    @Override
    public char getValue() {
        return value;
    }

    @Override
    public char getChar() {
        return getValue();
    }

    @Override
    public Character getCharObject() {
        return getValue();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonCharImpl tsonChar = (TsonCharImpl) o;
        return value == tsonChar.value;
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
        return Character.compare(value,o.toChar().getValue());
    }
}
