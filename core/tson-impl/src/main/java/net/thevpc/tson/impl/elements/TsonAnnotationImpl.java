package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonAnnotationBuilder;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonAnnotationBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.List;
import java.util.Objects;

public class TsonAnnotationImpl implements TsonAnnotation {

    private String name;
    private UnmodifiableArrayList<TsonElement> params;

    public TsonAnnotationImpl(String name, UnmodifiableArrayList<TsonElement> params) {
        this.name = name;
        this.params = params;
    }

    @Override
    public TsonAnnotationBuilder builder() {
        return new TsonAnnotationBuilderImpl().merge(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public TsonElement get(int index) {
        return params.get(index);
    }

    @Override
    public List<TsonElement> getAll() {
        return params;
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
        int i = TsonUtils.compare(name, o.getName());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray(params, o.getAll());
    }
}
