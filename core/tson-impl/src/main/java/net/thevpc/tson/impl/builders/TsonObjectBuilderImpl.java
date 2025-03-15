package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonElementListImpl;
import net.thevpc.tson.impl.elements.TsonObjectImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TsonObjectBuilderImpl extends AbstractTsonElementBuilder<TsonObjectBuilder> implements TsonObjectBuilder {

    private TsonElementBaseListBuilder elementsSupport = new TsonElementBaseListBuilderImpl();
    private String name;
    private List<TsonElement> args = new ArrayList<>();

    @Override
    public TsonElementType type() {
        return TsonElementType.OBJECT;
    }

    @Override
    public TsonObjectBuilder clear() {
        setWithArgs(false);
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

    /// ///////////
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

    /// ///////////
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
    public List<TsonElement> all() {
        return elementsSupport.toList();
    }

    @Override
    public TsonObject build() {
        TsonObjectImpl built = new TsonObjectImpl(name,
                args==null?null: new TsonElementListImpl((List) args),
                TsonUtils.unmodifiableElements(elementsSupport.toList()));
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

    /// ////////////////
    /// args

    @Override
    public boolean isWithArgs() {
        return args != null;
    }

    @Override
    public TsonObjectBuilder setWithArgs(boolean hasArgs) {
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
    public TsonObjectBuilder clearArgs() {
        args.clear();
        return this;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public TsonObjectBuilder name(String name) {
        this.name=name;
        return this;
    }

    @Override
    public TsonObjectBuilder addArg(TsonElementBase element) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonObjectBuilder removeArg(TsonElementBase element) {
        if (element != null && args != null) {
            args.remove(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonObjectBuilder addArg(TsonElementBase element, int index) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(index, Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonObjectBuilder removeArgAt(int index) {
        if (args != null) {
            args.remove(index);
        }
        return this;
    }

    @Override
    public TsonObjectBuilder addArgs(TsonElement[] element) {
        if (element != null) {
            for (TsonElement tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonObjectBuilder addArgs(TsonElementBase[] element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonObjectBuilder addArgs(Iterable<? extends TsonElementBase> element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    /// ////////////////


    @Override
    public TsonObjectBuilder merge(TsonElementBase element) {
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

    @Override
    public TsonObjectBuilder ensureCapacity(int length) {
        elementsSupport.ensureCapacity(length);
        return this;
    }
}
