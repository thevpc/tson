package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonArrayBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;
import java.util.stream.Collectors;

public class TsonArrayImpl extends AbstractNonPrimitiveTsonElement implements TsonArray {
    private String name;
    private TsonElementList args;
    private TsonElementList elements;

    public TsonArrayImpl(String name, TsonElementList args, UnmodifiableArrayList<TsonElement> elements) {
        super(TsonElementType.ARRAY);
        this.name = name;
        this.args = args;
        this.elements = new TsonElementListImpl(elements.stream().map(x -> x).collect(Collectors.toList()));
    }

    @Override
    public boolean isNamed() {
        return name != null;
    }

    @Override
    public TsonElementList body() {
        return elements;
    }

    @Override
    public TsonElementList args() {
        return args;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TsonElement get(int index) {
        return elements.getAt(index);
    }

    @Override
    public boolean isWithArgs() {
        return args != null;
    }

    @Override
    public int argsCount() {
        return args == null ? 0 : args.size();
    }

    @Override
    public TsonArray toArray() {
        return this;
    }

    @Override
    public TsonContainer toContainer() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return this.elements.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonArrayImpl that = (TsonArrayImpl) o;
        return Objects.equals(elements, that.elements)
                && Objects.equals(name, that.name)
                && Objects.equals(args, that.args)
                ;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), name, args);
        result = 31 * result + Objects.hashCode(elements);
        return result;
    }

    @Override
    public TsonArrayBuilder builder() {
        return new TsonArrayBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (args != null) {
            for (TsonElement element : args) {
                if (!visitor.visit(element)) {
                    return false;
                }
            }
        }
        for (TsonElement element : elements) {
            if (!visitor.visit(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int compareCore(TsonElement o) {
        TsonArray na = o.toArray();
        int i = this.name().compareTo(na.name());
        if (i != 0) {
            return i;
        }
        i = TsonUtils.compareElementsArray(this.args(), na.args());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(this.body(), na.body());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();

        if (name != null) {
            visitor.visitNamedStart(this.name());
        }
        if (args != null) {
            visitor.visitParamsStart();
            for (TsonElement param : this.args()) {
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


}
