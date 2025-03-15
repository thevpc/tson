package net.thevpc.tson;

import java.util.Collection;

public interface TsonUpletBuilder extends Iterable<TsonElement>, TsonElementBuilder {
    String name();

    boolean isNamed();

    boolean isBlank();

    TsonUpletBuilder name(String name);

    TsonUpletBuilder reset();

    TsonUpletBuilder merge(TsonElementBase element);

    TsonUpletBuilder addAll(Iterable<? extends TsonElementBase> element);

    TsonUpletBuilder addAll(TsonElement[] element);

    TsonUpletBuilder addAll(TsonElementBase[] element);

    TsonUpletBuilder add(TsonElementBase element);

    TsonUpletBuilder remove(TsonElementBase element);

    TsonUpletBuilder add(TsonElementBase element, int index);

    TsonUpletBuilder removeAt(int index);

    TsonElement[] getParams();

    TsonUpletBuilder removeAll();


    /// /////////////////////////////////////////////

    TsonUpletBuilder comments(TsonComments comments);

    TsonUpletBuilder setComments(TsonComments comments);

    TsonUpletBuilder setAnnotations(TsonAnnotation[] annotations);

    TsonUpletBuilder addAnnotations(TsonAnnotation... annotations);

    TsonUpletBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonUpletBuilder annotation(String name, TsonElementBase... elements);

    TsonUpletBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonUpletBuilder addAnnotation(TsonAnnotation annotation);

    TsonUpletBuilder removeAnnotationAt(int index);

    TsonUpletBuilder removeAllAnnotations();

    TsonUpletBuilder ensureCapacity(int length);

    TsonUplet build();
}
