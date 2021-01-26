package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonFunctionImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsonFunctionBuilderImpl extends AbstractTsonElementBuilder<TsonFunctionBuilder> implements TsonFunctionBuilder {

    private String name;
    private ArrayList<TsonElement> params = new ArrayList<>();

    @Override
    public TsonElementType getType() {
        return TsonElementType.FUNCTION;
    }

    @Override
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public List<TsonElement> getAll() {
        return Collections.unmodifiableList(params);
    }

    @Override
    public TsonFunctionBuilder removeAllParams() {
        params.clear();
        return this;
    }

    @Override
    public TsonFunctionBuilder reset() {
        name = null;
        params.clear();
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public TsonFunctionBuilderImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TsonFunctionBuilderImpl name(String name) {
        return setName(name);
    }

    @Override
    public TsonFunctionBuilder add(TsonElementBase element) {
        params.add(Tson.elem(element).build());
        return this;
    }

    @Override
    public TsonFunctionBuilder remove(TsonElementBase element) {
        params.remove(Tson.elem(element).build());
        return this;
    }

    @Override
    public TsonFunctionBuilder add(TsonElementBase element, int index) {
        params.add(index, Tson.elem(element).build());
        return this;
    }

    @Override
    public TsonFunctionBuilder removeAt(int index) {
        params.remove(index);
        return this;
    }

    @Override
    public TsonElement build() {
        if (!TsonUtils.isValidIdentifier(name)) {
            throw new IllegalArgumentException("Invalid function name '" + name + "'");
        }
        TsonFunctionImpl built = new TsonFunctionImpl(name, TsonUtils.unmodifiableElements(params));
        return TsonUtils.decorate(
                built,
                 getComments(), getAnnotations());
    }

    @Override
    public TsonFunctionBuilder addAll(TsonElement[] element) {
        for (TsonElement tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonFunctionBuilder addAll(TsonElementBase[] element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonFunctionBuilder addAll(Iterable<? extends TsonElementBase> element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                add(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonFunctionBuilder merge(TsonElementBase element0) {
        TsonElement element=element0.build();
        switch (element.getType()) {
            case ARRAY: {
                TsonArray e = element.toArray();
                TsonElementHeader h = e.getHeader();
                if(h!=null) {
                    addAll(h.getAll());
                    setName(h.getName());
                }
                return this;
            }
            case OBJECT: {
                TsonObject e = element.toObject();
                TsonElementHeader h = e.getHeader();
                if(h!=null) {
                    addAll(h.getAll());
                    setName(h.getName());
                }
                return this;
            }
            case FUNCTION: {
                TsonFunction o = element.toFunction();
                addAll(o.getAll());
                setName(o.getName());
                return this;
            }
            case UPLET: {
                addAll(element.toUplet().getAll());
                return this;
            }
        }
        throw new IllegalArgumentException("Unsupported copy from " + element.getType());
    }

    @Override
    public TsonFunctionBuilder ensureCapacity(int length) {
        params.ensureCapacity(length);
        return this;
    }
}
