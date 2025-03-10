package net.thevpc.tson;

import java.util.List;

public interface TsonElementHeaderBuilder<T extends TsonElementBuilder> {

    TsonElementHeaderBuilder<T> clear();

    TsonElementHeaderBuilder<T> set(TsonElementHeader header);

    TsonElementHeaderBuilder<T> name(String name);

    TsonElementHeaderBuilder<T> setName(String name);

    TsonElementHeaderBuilder<T> addAll(TsonElement... element);

    TsonElementHeaderBuilder<T> addAll(TsonElementBase... element);

    TsonElementHeaderBuilder<T> addAll(Iterable<? extends TsonElementBase> element);

    TsonElementHeaderBuilder<T> add(TsonElementBase element);

    TsonElementHeaderBuilder<T> remove(TsonElementBase element);

    TsonElementHeaderBuilder<T> add(TsonElementBase element, int index);

    TsonElementHeaderBuilder<T> removeAt(int index);

    List<TsonElement> args();

    int size();

    TsonElementHeaderBuilder<T> removeAll();

    boolean isArgs() ;

    TsonElementHeaderBuilder<T> setHas(boolean hasArgs);
    T then();
}
