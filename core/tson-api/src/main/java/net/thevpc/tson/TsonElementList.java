package net.thevpc.tson;

import java.util.List;
import java.util.Map;

public interface TsonElementList extends Iterable<TsonElement> {
    TsonElement getValueAt(int index);

    TsonElement getValue(String name);

    TsonElement getValue(TsonElement name);

    Map<TsonElement, TsonElement> toMap();

    Map<TsonElement, List<TsonElement>> toMultiMap();

    List<TsonElement> toList();

    List<TsonElement> getValues(String name);

    List<TsonElement> getValues(TsonElement name);

    int size();

    TsonElementBaseListBuilder builder();

    boolean isEmpty();
}
