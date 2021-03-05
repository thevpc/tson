package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonObjectImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.Iterator;
import java.util.List;

public class TsonObjectBuilderImpl extends AbstractTsonElementBuilder<TsonObjectBuilder> implements TsonObjectBuilder {

    private TsonObjectBuilderSupport elementsSupport = new TsonObjectBuilderSupport();
    private TsonElementHeaderBuilderImpl<TsonObjectBuilder> header = new TsonElementHeaderBuilderImpl(this);

    @Override
    public TsonElementType getType() {
        return TsonElementType.OBJECT;
    }

    @Override
    public TsonObjectBuilder reset() {
        header.reset();
        elementsSupport.reset();
        return this;
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return elementsSupport.iterator();
    }

    @Override
    public TsonObjectBuilder add(TsonElementBase key, TsonElementBase value) {
        elementsSupport.add(key, value);
        return this;
    }

    @Override
    public TsonObjectBuilder add(String key, String value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, int value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, long value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, float value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, double value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, byte value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, short value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, char value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, Enum value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, boolean value) {
        return add(key,Tson.elem(value));
    }

    @Override
    public TsonObjectBuilder add(String key, TsonElementBase value) {
        elementsSupport.add(key, value);
        return this;
    }

    @Override
    public TsonObjectBuilder add(TsonElementBase element) {
        elementsSupport.add(element);
        return this;
    }

    @Override
    public TsonObjectBuilder remove(TsonElementBase element) {
        elementsSupport.remove(element);
        return this;
    }

    @Override
    public TsonObjectBuilder remove(String name) {
        elementsSupport.remove(name);
        return this;
    }

    @Override
    public TsonObjectBuilder add(TsonElementBase element, int index) {
        elementsSupport.add(element, index);
        return this;
    }

    @Override
    public TsonObjectBuilder removeAt(int index) {
        elementsSupport.removeAt(index);
        return this;
    }

    @Override
    public TsonElementHeaderBuilder<TsonObjectBuilder> header() {
        return getHeader();
    }

    @Override
    public TsonElementHeaderBuilder<TsonObjectBuilder> getHeader() {
        return header;
    }

    @Override
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public List<TsonElement> getAll() {
        return elementsSupport.getAll();
    }

    @Override
    public TsonObjectBuilder removeAll() {
        elementsSupport.removeAll();
        return this;
    }

    @Override
    public TsonElement build() {
        TsonObjectImpl built = new TsonObjectImpl(header.build(), TsonUtils.unmodifiableElements(elementsSupport.getAll()));
        return TsonUtils.decorate(
                built,
                getComments(), getAnnotations());
    }

    @Override
    public TsonObjectBuilder addAll(TsonElement[] elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonObjectBuilder addAll(TsonElementBase[] elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonObjectBuilder addAll(Iterable<? extends TsonElementBase> elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonObjectBuilder merge(TsonElementBase element) {
        TsonElement e = Tson.elem(element);
        switch (e.getType()) {
            case UPLET: {
                header.addAll(e.toUplet());
                break;
            }
            case FUNCTION: {
                header.setName(e.toFunction().getName());
                header.addAll(e.toUplet());
                break;
            }
            case NAME: {
                header.setName(e.toName().getName());
                break;
            }
            case OBJECT: {
                TsonElementHeader h = e.toObject().getHeader();
                this.header.set(h);
                addAll(e.toObject().getAll());
                break;
            }
            case ARRAY: {
                TsonElementHeader h = e.toArray().getHeader();
                this.header.set(h);
                addAll(e.toArray().getAll());
                break;
            }
        }
        return this;
    }

    @Override
    public TsonObjectBuilder ensureCapacity(int length) {
        elementsSupport.ensureCapacity(length);
        return this;
    }
}
