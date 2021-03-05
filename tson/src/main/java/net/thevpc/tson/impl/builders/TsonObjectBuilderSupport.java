package net.thevpc.tson.impl.builders;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementBase;
import net.thevpc.tson.TsonElementType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TsonObjectBuilderSupport {
    private ArrayList<TsonElement> elements = new ArrayList<>();

    public void reset() {
        elements.clear();
    }

    public Iterator<TsonElement> iterator() {
        return getAll().iterator();
    }

    public void add(TsonElementBase element) {
        elements.add(Tson.elem(element).build());
    }

    public void remove(TsonElementBase element) {
        TsonElement e = Tson.elem(element).build();
        elements.removeIf(elem -> elem.equals(e) || (elem.getType() == TsonElementType.PAIR && elem.toKeyValue().getKey().equals(e)));
    }

    public void remove(String name) {
        remove(Tson.name(name));
    }

    public void add(TsonElementBase key, TsonElementBase value) {
        add(Tson.pair(key, value));
    }

    public void add(String key, TsonElementBase value) {
        add(Tson.pair(key, value));
    }

    public void add(TsonElementBase element, int index) {
        elements.add(index, Tson.elem(element).build());
    }

    public void removeAt(int index) {
        elements.remove(index);
    }

    public void removeAll() {
        elements.clear();
    }

    public List<TsonElement> getAll() {
        return Collections.unmodifiableList(elements);
    }


    public void addAll(TsonElement[] element) {
        for (TsonElement tsonElement : element) {
            add(tsonElement);
        }
    }

    public void addAll(TsonElementBase[] element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
    }

    public void addAll(Iterable<? extends TsonElementBase> element) {
        for (TsonElementBase tsonElement : element) {
            add(tsonElement);
        }
    }

    public void set(TsonElementBase element0) {
        TsonElement element = element0.build();
        switch (element.getType()) {
            case ARRAY: {
                addAll(element.toArray().getAll());
                return;
            }
            case OBJECT: {
                addAll(element.toObject().getAll());
                return;
            }
        }
        throw new IllegalArgumentException("Unsupported copy from " + element.getType());
    }

    public void ensureCapacity(int length) {
        elements.ensureCapacity(length);
    }
}
