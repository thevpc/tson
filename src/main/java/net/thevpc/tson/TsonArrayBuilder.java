package net.thevpc.tson;

import java.util.Collection;
import java.util.List;

public interface TsonArrayBuilder extends Iterable<TsonElement>, TsonElementBuilder {
    TsonArrayBuilder reset();

    TsonElementHeaderBuilder<TsonArrayBuilder> header();

    TsonElementHeaderBuilder<TsonArrayBuilder> getHeader();

    TsonArrayBuilder merge(TsonElementBase element);

    TsonArrayBuilder addAll(TsonElement... element);

    TsonArrayBuilder addAll(TsonElementBase... element);

    TsonArrayBuilder addAll(Iterable<? extends TsonElementBase> element);

    TsonArrayBuilder add(TsonElementBase element);

    TsonArrayBuilder remove(TsonElementBase element);

    TsonArrayBuilder add(TsonElementBase element, int index);

    TsonArrayBuilder removeAt(int index);

    TsonElement build();

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonArrayBuilder removeAll();

    ////////////////////////////////////////////////

    TsonArrayBuilder comments(String comments);

    TsonArrayBuilder setComments(String comments);

    TsonArrayBuilder setAnnotations(TsonAnnotation... annotations);

    TsonArrayBuilder addAnnotations(TsonAnnotation... annotations);

    TsonArrayBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonArrayBuilder annotation(String name, TsonElementBase... elements);

    TsonArrayBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonArrayBuilder addAnnotation(TsonAnnotation annotation);

    TsonArrayBuilder removeAnnotationAt(int index);

    TsonArrayBuilder removeAllAnnotations();

    TsonArrayBuilder ensureCapacity(int length);
}
