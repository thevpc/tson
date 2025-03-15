package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonArrayImpl;
import net.thevpc.tson.impl.elements.TsonElementListImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TsonArrayBuilderImpl extends AbstractTsonElementBuilder<TsonArrayBuilder> implements TsonArrayBuilder {
    private TsonArrayBuilderSupport elementsSupport = new TsonArrayBuilderSupport();
    private String name;
    private List<TsonElement> args = new ArrayList<>();

    @Override
    public TsonElementType type() {
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
        name = null;
        args = null;
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
    public TsonArray build() {
        TsonArray built = new TsonArrayImpl(name,
                args==null?null:new TsonElementListImpl((List) args),
                UnmodifiableArrayList.ofCopy(elementsSupport.getRows().toArray(new TsonElement[0])));
        return (TsonArray) TsonUtils.decorate(
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
        TsonElement e = Tson.of(element);
        addAnnotations(e.annotations());
        switch (e.type()) {
            case UPLET: {
                TsonUplet uplet = e.toUplet();
                if (uplet.isNamed()) {
                    name(uplet.name());
                }
                addArgs(uplet);
                break;
            }
            case NAME: {
                name(e.toName().value());
                break;
            }
            case OBJECT: {
                TsonObject h = e.toObject();
                name(h.name());
                addArgs(h.args());
                addAll(e.toObject().body());
                break;
            }
            case ARRAY: {
                TsonArray h = e.toArray();
                name(h.name());
                addArgs(h.args());
                addAll(e.toArray().body());
                break;
            }
        }
        return this;
    }

    /// ////////////////
    /// args

    @Override
    public boolean isWithArgs() {
        return args != null;
    }

    @Override
    public TsonArrayBuilder setWithArgs(boolean hasArgs) {
        if (hasArgs) {
            if (args == null) {
                args = new ArrayList<>();
            }
        } else {
            args = null;
        }
        return this;
    }

    @Override
    public List<TsonElement> args() {
        return args;
    }

    @Override
    public int argsCount() {
        return args == null ? 0 : args.size();
    }

    @Override
    public TsonArrayBuilder clearArgs() {
        args.clear();
        return this;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public TsonArrayBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TsonArrayBuilder addArg(TsonElementBase element) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonArrayBuilder removeArg(TsonElementBase element) {
        if (element != null && args != null) {
            args.remove(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonArrayBuilder addArg(TsonElementBase element, int index) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(index, Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonArrayBuilder removeArgAt(int index) {
        if (args != null) {
            args.remove(index);
        }
        return this;
    }

    @Override
    public TsonArrayBuilder addArgs(TsonElement[] element) {
        if (element != null) {
            for (TsonElement tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonArrayBuilder addArgs(TsonElementBase[] element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonArrayBuilder addArgs(Iterable<? extends TsonElementBase> element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    /// ////////////////


}
