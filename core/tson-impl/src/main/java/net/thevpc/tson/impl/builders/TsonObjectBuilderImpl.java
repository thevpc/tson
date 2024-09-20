package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonObjectImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.Iterator;
import java.util.List;

public class TsonObjectBuilderImpl extends AbstractTsonElementBuilder<TsonObjectBuilder> implements TsonObjectBuilder {

    private TsonElementBaseListBuilder elementsSupport = new TsonElementBaseListBuilderImpl();
    private TsonElementHeaderBuilderImpl<TsonObjectBuilder> header = new TsonElementHeaderBuilderImpl(this);

    @Override
    public TsonElementType type() {
        return TsonElementType.OBJECT;
    }

    @Override
    public TsonObjectBuilder clear() {
        header.clear();
        elementsSupport.clear();
        return this;
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return elementsSupport.toIterator();
    }

    @Override
    public TsonElementBaseListBuilder content() {
        return elementsSupport;
    }

    @Override
    public TsonObjectBuilder add(TsonElementBase key, TsonElementBase value) {
        elementsSupport.add(key, value);
        return this;
    }

    @Override
    public TsonObjectBuilder add(String key, String value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, int value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, long value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, float value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, double value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, byte value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, short value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, char value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, Enum value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, boolean value) {
        return add(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder add(String key, TsonElementBase value) {
        elementsSupport.add(key, value);
        return this;
    }

    //////////////
    @Override
    public TsonObjectBuilder set(TsonElementBase key, TsonElementBase value) {
        elementsSupport.set(key, value);
        return this;
    }

    @Override
    public TsonObjectBuilder set(String key, String value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, int value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, long value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, float value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, double value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, byte value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, short value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, char value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, Enum value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, boolean value) {
        return set(key, Tson.of(value));
    }

    @Override
    public TsonObjectBuilder set(String key, TsonElementBase value) {
        elementsSupport.set(key, value);
        return this;
    }

    //////////////
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
    public TsonObjectBuilder addAt(int index, TsonElementBase element) {
        elementsSupport.addAt(index, element);
        return this;
    }

    @Override
    public TsonObjectBuilder removeAt(int index) {
        elementsSupport.removeAt(index);
        return this;
    }

    @Override
    public TsonObjectBuilder setAt(int index, TsonElementBase element) {
        elementsSupport.setAt(index, element);
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
        return elementsSupport.toList();
    }

    @Override
    public TsonObject build() {
        TsonObjectImpl built = new TsonObjectImpl(header.build(), TsonUtils.unmodifiableElements(elementsSupport.toList()));
        return (TsonObject) TsonUtils.decorate(
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
        TsonElement e = Tson.of(element);
        addAnnotations(e.annotations());
        switch (e.type()) {
            case UPLET: {
                header.addAll(e.toUplet());
                break;
            }
            case FUNCTION: {
                header.setName(e.toFunction().name());
                header.addAll(e.toUplet());
                break;
            }
            case NAME: {
                header.setName(e.toName().value());
                break;
            }
            case OBJECT: {
                TsonElementHeader h = e.toObject().header();
                this.header.set(h);
                addAll(e.toObject().body());
                break;
            }
            case ARRAY: {
                TsonElementHeader h = e.toArray().header();
                this.header.set(h);
                addAll(e.toArray().body());
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
