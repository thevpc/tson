package net.thevpc.tson;

public interface TsonObjectToElement<T> {
    TsonElementBase toElement(T object, TsonObjectContext context);
}
