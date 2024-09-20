package net.thevpc.tson;

import java.util.List;

public interface TsonAnnotation extends Comparable<TsonAnnotation> {

    String name();

    int size();

    TsonElement arg(int index);

    TsonElementList args();

    TsonAnnotationBuilder builder();
}
