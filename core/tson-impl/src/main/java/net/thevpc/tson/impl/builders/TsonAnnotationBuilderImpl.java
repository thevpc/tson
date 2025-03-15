package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonAnnotationImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsonAnnotationBuilderImpl implements TsonAnnotationBuilder {
    private String name;
    private ArrayList<TsonElement> elements = new ArrayList<>();

    @Override
    public TsonAnnotationBuilder reset() {
        name = null;
        elements.clear();
        return this;
    }

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
    public TsonElement get(int index) {
        return elements.get(index);
    }

    @Override
    public TsonAnnotationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TsonAnnotationBuilder with(TsonElementBase... elements) {
        return addAll(elements);
    }

    @Override
    public TsonAnnotationBuilder add(TsonElementBase element) {
        elements.add(Tson.of(element));
        return this;
    }

    @Override
    public TsonAnnotationBuilder remove(TsonElementBase element) {
        elements.remove(Tson.of(element));
        return this;
    }

    @Override
    public TsonAnnotationBuilder add(TsonElementBase element, int index) {
        elements.add(index, Tson.of(element));
        return this;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public List<TsonElement> getAll() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public TsonAnnotationBuilder removeAt(int index) {
        elements.remove(index);
        return this;
    }

    @Override
    public TsonAnnotationBuilder addAll(TsonElement[] element) {
        for (TsonElement tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonAnnotationBuilder addAll(TsonElementBase[] element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonAnnotationBuilder addAll(Iterable<? extends TsonElementBase> element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonAnnotationBuilder param(TsonElementBase element) {
        return add(element);
    }

    @Override
    public TsonAnnotationBuilder merge(TsonElementBase element0) {
        TsonElement element = Tson.of(element0);
        switch (element.type()) {
            case ARRAY: {
                TsonArray h = element.toArray();
                if (h != null) {
                    addAll(h.args());
                }
                return this;
            }
            case OBJECT: {
                TsonObject h = element.toObject();
                if (h != null) {
                    addAll(h.args());
                }
                return this;
            }
            case UPLET: {
                addAll(element.toUplet().args());
                return this;
            }
        }
        throw new IllegalArgumentException("Unsupported copy from " + element.type());
    }

    @Override
    public TsonAnnotationBuilder merge(TsonAnnotation element) {
        this.name = element.name();
        addAll(element.args());
        return this;
    }

    @Override
    public TsonAnnotationBuilder name(String name) {
        return setName(name);
    }

    @Override
    public TsonAnnotation build() {
        boolean blank = TsonUtils.isBlank(name);
        if (!blank && !TsonUtils.isValidIdentifier(name)) {
            throw new IllegalArgumentException("Invalid function annotation '" + name + "'");
        }
        return new TsonAnnotationImpl(blank ? null : name, TsonUtils.unmodifiableElements(elements));
    }

    @Override
    public TsonAnnotationBuilder ensureCapacity(int length) {
        elements.ensureCapacity(length);
        return this;
    }
}
