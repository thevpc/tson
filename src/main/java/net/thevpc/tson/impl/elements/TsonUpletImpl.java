package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonUpletBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;

public class TsonUpletImpl extends AbstractNonPrimitiveTsonElement implements TsonUplet {
    private UnmodifiableArrayList<TsonElement> elements;

    public TsonUpletImpl(UnmodifiableArrayList<TsonElement> elements) {
        super(TsonElementType.UPLET);
        this.elements = elements;
    }

    @Override
    public TsonUplet toUplet() {
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
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public List<TsonElement> getAll() {
        return elements;
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return getAll().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonUpletImpl that = (TsonUpletImpl) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(elements);
        return result;
    }

    @Override
    public TsonUpletBuilder builder() {
        return new TsonUpletBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (!visitor.visit(this)) {
            return false;
        }
        for (TsonElement element : elements) {
            if (!element.visit(visitor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int compareCore(TsonElement o) {
        return TsonUtils.compareElementsArray(getAll(), o.toUplet().getAll());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
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
