package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonArrayBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;
import java.util.stream.Collectors;

public class TsonArrayImpl extends AbstractNonPrimitiveTsonElement implements TsonArray {
    private TsonElementList elements;
    private final TsonElementHeader header;

    public TsonArrayImpl(TsonElementHeader header, UnmodifiableArrayList<TsonElement> elements) {
        super(TsonElementType.ARRAY);
        this.header = header;
        this.elements = new TsonElementListImpl(elements.stream().map(x->x).collect(Collectors.toList()));
    }

    @Override
    public TsonElementList body() {
        return elements;
    }

    @Override
    public TsonElementList args() {
        return header==null?null:header.args();
    }

    @Override
    public String name() {
        return header==null?null:header.name();
    }

    @Override
    public TsonElement get(int index) {
        return elements.getAt(index);
    }

    @Override
    public TsonElementHeader header() {
        return header;
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
        int i = TsonUtils.compareHeaders(header, na.header());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(this.body(), na.body());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        if(header!=null){
            header.visit(visitor);
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
