package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonArrayBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;

public class TsonArrayImpl extends AbstractNonPrimitiveTsonElement implements TsonArray {
    private final UnmodifiableArrayList<TsonElement> elements;
    private final TsonElementHeader header;

    public TsonArrayImpl(TsonElementHeader header, UnmodifiableArrayList<TsonElement> elements) {
        super(TsonElementType.ARRAY);
        this.header = header;
        this.elements = elements;
    }

    @Override
    public TsonElement get(int index) {
        return elements.get(index);
    }

    @Override
    public TsonElementHeader getHeader() {
        return header;
    }

    @Override
    public TsonArray toArray() {
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
        TsonArrayImpl that = (TsonArrayImpl) o;
        return Objects.equals(elements, that.elements) &&
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), header);
        result = 31 * result + Objects.hashCode(elements);
        return result;
    }

    @Override
    public TsonArrayBuilder builder() {
        return new TsonArrayBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (header!=null) {
            if (!visitor.visit(header,this)) {
                return false;
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
        int i = TsonUtils.compareHeaders(header, na.getHeader());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(getAll(), na.getAll());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        if(header!=null){
            header.visit(visitor);
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
