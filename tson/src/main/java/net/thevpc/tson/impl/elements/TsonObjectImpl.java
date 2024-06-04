package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonObjectBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;
import java.util.stream.Collectors;

public class TsonObjectImpl extends AbstractNonPrimitiveTsonElement implements TsonObject {
    private TsonElementList elements;
    private Map<TsonElement, TsonElement> cachedMap = new LinkedHashMap<>();
    private TsonElementHeader header;

    public TsonObjectImpl(TsonElementHeader header, UnmodifiableArrayList<TsonElement> elements) {
        super(TsonElementType.OBJECT);
        this.elements = new TsonElementListImpl(elements.stream().map(x->x).collect(Collectors.toList()));
        this.header = header;
    }

    @Override
    public TsonElementHeader getHeader() {
        return header;
    }


    @Override
    public TsonElementList args() {
        return header==null ? null:header.toElementList();
    }
    @Override
    public TsonContainer toContainer() {
        return this;
    }

    @Override
    public String name() {
        return header==null?null:header.name();
    }

    @Override
    public TsonObject toObject() {
        return this;
    }

    @Override
    public List<TsonElement> all() {
        return elements.toList();
    }

    @Override
    public TsonElement get(String name) {
        return get(Tson.name(name));
    }

    private Map<TsonElement, TsonElement> asMap() {
        if (cachedMap == null) {
            cachedMap = elements.toMap();
        }
        return cachedMap;
    }

    @Override
    public TsonElementList body() {
        return elements;
    }

    @Override
    public TsonElement get(TsonElement element) {
        return asMap().get(element);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return elements.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonObjectImpl that = (TsonObjectImpl) o;
        return Objects.equals(elements, that.elements) &&
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elements, header);
    }

    @Override
    public TsonObjectBuilder builder() {
        return new TsonObjectBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (header != null) {
            if (!visitor.visit(header, this)) {
                return false;
            }
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
        TsonObject no = o.toObject();
        int i = TsonUtils.compareHeaders(header, no.getHeader());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(all(), no.all());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        if (header != null) {
            header.visit(visitor);
        }
        visitor.visitNamedObjectStart();
        for (TsonElement element : all()) {
            visitor.visitObjectElementStart();
            element.visit(visitor);
            visitor.visitObjectElementEnd();
        }
        visitor.visitObjectEnd();
    }
}
