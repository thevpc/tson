package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonAnnotationBuilder;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementList;
import net.thevpc.tson.impl.builders.TsonAnnotationBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.ArrayList;
import java.util.Objects;

public class TsonAnnotationImpl implements TsonAnnotation {

    private String name;
    private TsonElementList params;

    public TsonAnnotationImpl(String name, UnmodifiableArrayList<TsonElement> params) {
        this.name = name;
        this.params = new TsonElementListImpl(new ArrayList<>(params));
    }

    @Override
    public TsonAnnotationBuilder builder() {
        return new TsonAnnotationBuilderImpl().merge(this);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public TsonElementList args() {
        return params;
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public TsonElement arg(int index) {
        return params.getAt(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TsonAnnotationImpl that = (TsonAnnotationImpl) o;
        return Objects.equals(name, that.name)
                && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Objects.hashCode(params);
        return result;
    }

    @Override
    public int compareTo(TsonAnnotation o) {
        int i = TsonUtils.compare(name, o.name());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(params.toArray(), o.args().toArray());
    }
}
