package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonPrimitiveBuilder;
import net.thevpc.tson.TsonRegex;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;
import java.util.regex.Pattern;

public class TsonRegexImpl extends AbstractPrimitiveTsonElement implements TsonRegex {
    private Pattern value;

    public TsonRegexImpl(Pattern value) {
        super(TsonElementType.REGEX);
        this.value = value;
    }

    public TsonRegexImpl(String value) {
        super(TsonElementType.DATE);
        this.value = Pattern.compile(value);
    }

    @Override
    public TsonRegex toRegex() {
        return this;
    }

    @Override
    public Pattern getValue() {
        return value;
    }

    @Override
    public Pattern getRegex() {
        return getValue();
    }

    @Override
    public String getString() {
        return getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonRegexImpl tsonRegex = (TsonRegexImpl) o;
        return Objects.equals(value.toString(), tsonRegex.value.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value.toString());
    }
    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return value.toString().compareTo(o.toRegex().getValue().toString());
    }
}
