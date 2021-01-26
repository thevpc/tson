package net.thevpc.tson;

import java.util.List;

public interface TsonAnnotation extends Comparable<TsonAnnotation> {

    String name();

    String getName();

    int size();

    TsonElement get(int index);

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonAnnotationBuilder builder();
}
