package net.thevpc.tson;

public interface TsonElementListBuilder{
    TsonElement getAt(int index);

    TsonElement get(String name);

    TsonElement get(TsonElementBase name);

    int size();

    TsonElementBaseList build();
}
