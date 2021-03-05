package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.Iterator;
import java.util.List;

public class TsonArrayOrMatrixBuilderImpl extends AbstractTsonElementBuilder<TsonArrayBuilder> implements TsonArrayBuilder {
    private TsonArrayBuilderSupport elementsSupport = new TsonArrayBuilderSupport();
    private TsonElementHeaderBuilderImpl<TsonArrayBuilder> header = new TsonElementHeaderBuilderImpl(this);


    @Override
    public TsonElementHeaderBuilder<TsonArrayBuilder> header() {
        return getHeader();
    }

    @Override
    public TsonElementHeaderBuilder<TsonArrayBuilder> getHeader() {
        return header;
    }

    @Override
    public TsonElementType getType() {
        return TsonElementType.ARRAY;
    }

    @Override
    public Iterator<TsonElement> iterator() {
        return elementsSupport.iterator();
    }

    @Override
    public List<TsonElement> all() {
        return getAll();
    }

    @Override
    public List<TsonElement> getAll() {
        return elementsSupport.getRows();
    }

    @Override
    public TsonArrayBuilder removeAll() {
        elementsSupport.removeAll();
        return this;
    }

    @Override
    public TsonArrayBuilder reset() {
        elementsSupport.reset();
        header.reset();
        return this;
    }

    @Override
    public TsonArrayBuilder add(TsonElementBase element) {
        elementsSupport.add(element);
        return this;
    }

    @Override
    public TsonArrayBuilder remove(TsonElementBase element) {
        elementsSupport.remove(element);
        return this;
    }

    @Override
    public TsonArrayBuilder add(TsonElementBase element, int index) {
        elementsSupport.add(element, index);
        return this;
    }

    @Override
    public TsonArrayBuilder removeAt(int index) {
        elementsSupport.removeAt(index);
        return this;
    }

    @Override
    public TsonElement build() {
        TsonArray built = TsonUtils.toArray(header.build(),elementsSupport.getRows());
        return TsonUtils.decorate(
                built
                , getComments(), getAnnotations())
                ;
    }

    @Override
    public TsonArrayBuilder addAll(TsonElement[] elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonArrayBuilder addAll(TsonElementBase[] elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonArrayBuilder addAll(Iterable<? extends TsonElementBase> elements) {
        elementsSupport.addAll(elements);
        return this;
    }

    @Override
    public TsonArrayBuilder ensureCapacity(int length) {
        elementsSupport.ensureElementsCapacity(length);
        return this;
    }

    @Override
    public TsonArrayBuilder merge(TsonElementBase element) {
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
}
