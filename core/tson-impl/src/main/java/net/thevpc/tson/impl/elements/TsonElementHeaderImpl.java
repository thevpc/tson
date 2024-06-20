package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TsonElementHeaderImpl implements TsonElementHeader {
    private String name;
    private TsonElementList params;

    public TsonElementHeaderImpl(String name, UnmodifiableArrayList<TsonElement> params) {
        this.name = name;
        this.params = new TsonElementListImpl(params.stream().map(x->x).collect(Collectors.toList()));
    }

    @Override
    public TsonElementList toElementList() {
        return params;
    }

    @Override
    public int size() {
        return params.size();
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public List<TsonElement> all() {
        return params.toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonElementHeaderImpl that = (TsonElementHeaderImpl) o;
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
    public boolean visit(TsonDocumentVisitor visitor) {
        for (TsonElement element : params) {
            if (!element.visit(visitor)) {
                return false;
            }
        }
        return true;
    }

    public int compareTo(TsonElementHeader o) {
        int i = this.name().compareTo(o.name());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(this.all(), o.all());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        if (name != null && !name.isEmpty()) {
            visitor.visitNamedStart(this.name());
        }
        if (!params.isEmpty()) {
            visitor.visitParamsStart();
            for (TsonElement param : this.all()) {
                visitor.visitParamElementStart();
                param.visit(visitor);
                visitor.visitParamElementEnd();
            }
            visitor.visitParamsEnd();
        }
    }
}
