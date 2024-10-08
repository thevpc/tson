package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonUpletImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class TsonUpletBuilderImpl extends AbstractTsonElementBuilder<TsonUpletBuilder> implements TsonUpletBuilder {

    private ArrayList<TsonElement> elements = new ArrayList<>();

    @Override
    public TsonElementType type() {
        return TsonElementType.UPLET;
    }

    @Override
    public TsonUpletBuilder reset() {
        elements.clear();
        return this;
    }

    @Override
    public TsonUpletBuilder addAll(TsonElement[] element) {
        for (TsonElement tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonUpletBuilder addAll(TsonElementBase[] element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonUpletBuilder addAll(Iterable<? extends TsonElementBase> element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
        return this;
    }

    @Override
    public TsonUpletBuilder add(TsonElementBase element) {
        elements.add(Tson.of(element).build());
        return this;
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return elements.iterator();
    }

    @Override
    public TsonUpletBuilder remove(TsonElementBase element) {
        elements.remove(Tson.of(element));
        return this;
    }

    @Override
    public TsonElement[] getParams() {
        return elements.toArray(new TsonElement[0]);
    }

    @Override
    public TsonUpletBuilder removeAll() {
        elements.clear();
        return this;
    }

    @Override
    public TsonUpletBuilder add(TsonElementBase element, int index) {
        elements.add(index, Tson.of(element).build());
        return this;
    }

    @Override
    public TsonUpletBuilder removeAt(int index) {
        elements.remove(index);
        return this;
    }

    @Override
    public TsonUplet build() {
        TsonUpletImpl built = new TsonUpletImpl(TsonUtils.unmodifiableElements(elements));
        return (TsonUplet) TsonUtils.decorate(
                built,
                getComments(), getAnnotations());
    }

    @Override
    public TsonUpletBuilder merge(TsonElementBase element0) {
        TsonElement element = element0.build();
        addAnnotations(element.annotations());
        switch (element.type()) {
            case ARRAY: {
                TsonElementHeader h = element.toArray().header();
                if (h != null) {
                    addAll(h.args());
                }
                return this;
            }
            case OBJECT: {
                TsonElementHeader h = element.toObject().header();
                if (h != null) {
                    addAll(h.args());
                }
                return this;
            }
            case FUNCTION: {
                addAll(element.toFunction().args());
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
    public TsonUpletBuilder ensureCapacity(int length) {
        elements.ensureCapacity(length);
        return this;
    }
}
