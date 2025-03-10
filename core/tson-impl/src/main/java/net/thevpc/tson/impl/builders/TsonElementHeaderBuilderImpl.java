package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonElementHeaderImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsonElementHeaderBuilderImpl<T extends TsonElementBuilder> implements TsonElementHeaderBuilder<T> {

    private String name;
    private List<TsonElement> elements = new ArrayList<>();
    private boolean hasArgs;
    private T then;

    public TsonElementHeaderBuilderImpl(T then) {
        this.then = then;
    }

    public boolean isArgs() {
        return hasArgs;
    }

    public TsonElementHeaderBuilder<T> setHas(boolean hasArgs) {
        this.hasArgs = hasArgs;
        return this;
    }

    @Override
    public List<TsonElement> args() {
        return elements;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public TsonElementHeaderBuilder removeAll() {
        elements.clear();
        return this;
    }

    @Override
    public TsonElementHeaderBuilder clear() {
        name = null;
        elements.clear();
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public TsonElementHeaderBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TsonElementHeaderBuilder name(String name) {
        return setName(name);
    }

    @Override
    public TsonElementHeaderBuilder add(TsonElementBase element) {
        elements.add(Tson.of(element).build());
        return this;
    }

    @Override
    public TsonElementHeaderBuilder remove(TsonElementBase element) {
        elements.remove(Tson.of(element).build());
        return this;
    }

    @Override
    public TsonElementHeaderBuilder add(TsonElementBase element, int index) {
        elements.add(index, Tson.of(element).build());
        return this;
    }

    @Override
    public TsonElementHeaderBuilder removeAt(int index) {
        elements.remove(index);
        return this;
    }

    public TsonElementHeader build() {
        String n = name == null ? null : name.trim();
        if (n != null) {
            if (!TsonUtils.isValidIdentifier(name)) {
                throw new IllegalArgumentException("Invalid header name '" + name + "'");
            }
        }
        if (n == null && elements.isEmpty()) {
            return null;
        }
        return new TsonElementHeaderImpl(name, hasArgs, TsonUtils.unmodifiableElements(elements));
    }

    @Override
    public TsonElementHeaderBuilder addAll(TsonElement[] element) {
        if (element != null) {
            for (TsonElement tsonElement : element) {
                add(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonElementHeaderBuilder addAll(TsonElementBase[] element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                add(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonElementHeaderBuilder addAll(Iterable<? extends TsonElementBase> element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                add(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonElementHeaderBuilder set(TsonElementHeader header) {
        if (header != null) {
            addAll(header.args());
            setName(header.name());
        }
        return this;
    }

    @Override
    public T then() {
        return then;
    }
}
