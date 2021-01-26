package net.thevpc.tson;

import java.util.Collection;
import java.util.List;

public interface TsonObjectBuilder extends Iterable<TsonElement>, TsonElementBuilder {
    TsonObjectBuilder reset();

    TsonElementHeaderBuilder<TsonObjectBuilder> header();

    TsonElementHeaderBuilder<TsonObjectBuilder> getHeader();

    TsonObjectBuilder merge(TsonElementBase element);

    TsonObjectBuilder addAll(TsonElementBase... element);

    TsonObjectBuilder addAll(TsonElement... element);

    TsonObjectBuilder addAll(Iterable<? extends TsonElementBase> element);


    TsonObjectBuilder add(TsonElementBase key, TsonElementBase value);

    TsonObjectBuilder add(String key, TsonElementBase value);

    TsonObjectBuilder add(String key, String value);

    TsonObjectBuilder add(String key, int value);

    TsonObjectBuilder add(String key, long value);

    TsonObjectBuilder add(String key, float value);

    TsonObjectBuilder add(String key, double value);

    TsonObjectBuilder add(String key, byte value);

    TsonObjectBuilder add(String key, short value);

    TsonObjectBuilder add(String key, char value);

    TsonObjectBuilder add(String key, boolean value);

    TsonObjectBuilder add(String key, Enum value);

    TsonObjectBuilder add(TsonElementBase element);

    TsonObjectBuilder remove(TsonElementBase element);

    TsonObjectBuilder add(TsonElementBase element, int index);

    TsonObjectBuilder removeAt(int index);

    List<TsonElement> all();

    List<TsonElement> getAll();

    TsonObjectBuilder removeAll();


    ////////////////////////////////////////////////

    TsonObjectBuilder comments(String comments);

    TsonObjectBuilder setComments(String comments);

    TsonObjectBuilder setAnnotations(TsonAnnotation... annotations);

    TsonObjectBuilder addAnnotations(TsonAnnotation... annotations);

    TsonObjectBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonObjectBuilder annotation(String name, TsonElementBase... elements);

    TsonObjectBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonObjectBuilder addAnnotation(TsonAnnotation annotation);

    TsonObjectBuilder removeAnnotationAt(int index);

    TsonObjectBuilder removeAllAnnotations();

    TsonObjectBuilder remove(String name);

    TsonObjectBuilder ensureCapacity(int length);
}
