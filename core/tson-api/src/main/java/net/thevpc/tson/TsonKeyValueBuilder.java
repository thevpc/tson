package net.thevpc.tson;

import java.util.Collection;

public interface TsonKeyValueBuilder extends TsonElementBuilder{
    TsonKeyValueBuilder merge(TsonPair other);

    TsonKeyValueBuilder reset();

    TsonElement key();

    TsonKeyValueBuilder setKey(TsonElementBase key);

    TsonElement value();

    TsonKeyValueBuilder setValue(TsonElementBase value);

    ////////////////////////////////////////////////

    TsonKeyValueBuilder comments(TsonComments comments);

    TsonKeyValueBuilder setComments(TsonComments comments);

    TsonKeyValueBuilder setAnnotations(TsonAnnotation... annotations);

    TsonKeyValueBuilder addAnnotations(TsonAnnotation... annotations);

    TsonKeyValueBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonKeyValueBuilder annotation(String name, TsonElementBase... elements);

    TsonKeyValueBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonKeyValueBuilder addAnnotation(TsonAnnotation annotation);

    TsonKeyValueBuilder removeAnnotationAt(int index);

    TsonKeyValueBuilder removeAllAnnotations();
}
