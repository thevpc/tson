package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonFunctionBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.List;
import java.util.Objects;

public class TsonFunctionImpl extends AbstractNonPrimitiveTsonElement implements TsonFunction {
    private String name;
    private UnmodifiableArrayList<TsonElement> params;

    public TsonFunctionImpl(String name, UnmodifiableArrayList<TsonElement> params) {
        super(TsonElementType.FUNCTION);
        this.name = name;
        this.params = params;
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public TsonFunction toFunction() {
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public List<TsonElement> getAll() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonFunctionImpl that = (TsonFunctionImpl) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), name);
        result = 31 * result + Objects.hashCode(params);
        return result;
    }

    @Override
    public TsonFunctionBuilder builder() {
        return new TsonFunctionBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (!visitor.visit(this)) {
            return false;
        }
        for (TsonElement element : params) {
            if (!element.visit(visitor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int compareCore(TsonElement o) {
        int i = getName().compareTo(o.toFunction().getName());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(getAll(), o.toFunction().getAll());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
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
