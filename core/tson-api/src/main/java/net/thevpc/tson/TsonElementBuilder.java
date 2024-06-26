package net.thevpc.tson;

import java.util.Collection;

public interface TsonElementBuilder extends TsonElementBase{

    TsonElementBuilder comments(TsonComments comments);

    TsonElementBuilder setComments(TsonComments comments);

    TsonElementBuilder setAnnotations(TsonAnnotation... annotations);

    TsonElementBuilder addAnnotations(TsonAnnotation... annotations);
    
    TsonElementBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonElementBuilder anchor(String name);

    TsonElementBuilder annotation(String name, TsonElementBase... elements);

    TsonElementBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonElementBuilder addAnnotation(TsonAnnotation annotation);

    TsonElementBuilder removeAnnotationAt(int index);

    TsonElementBuilder removeAllAnnotations();

    TsonElement build();

    TsonArrayBuilder toArray();

    TsonObjectBuilder toObject();

    TsonUpletBuilder toUplet();

    TsonFunctionBuilder toFunction();

    TsonElementType type();

    TsonComments getComments();

    TsonAnnotation[] getAnnotations();

}
