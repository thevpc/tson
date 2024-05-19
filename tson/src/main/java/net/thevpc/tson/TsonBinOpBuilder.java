package net.thevpc.tson;

import java.util.Collection;

public interface TsonBinOpBuilder extends TsonElementBuilder{
    TsonBinOpBuilder merge(TsonPair other);

    TsonBinOpBuilder reset();

    TsonElement getFirst();

    TsonBinOpBuilder setFirst(TsonElementBase key);

    TsonElement getSecond();

    TsonBinOpBuilder setSecond(TsonElementBase value);

    ////////////////////////////////////////////////

    TsonBinOpBuilder comments(String comments);

    TsonBinOpBuilder setComments(String comments);

    TsonBinOpBuilder setAnnotations(TsonAnnotation... annotations);

    TsonBinOpBuilder addAnnotations(TsonAnnotation... annotations);

    TsonBinOpBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonBinOpBuilder annotation(String name, TsonElementBase... elements);

    TsonBinOpBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonBinOpBuilder addAnnotation(TsonAnnotation annotation);

    TsonBinOpBuilder removeAnnotationAt(int index);

    TsonBinOpBuilder removeAllAnnotations();
}
