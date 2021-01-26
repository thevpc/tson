package net.thevpc.tson;

public interface TsonSerializerBuilder {
    <T> TsonSerializerBuilder setToElement(Class<T> type, TsonObjectToElement<T> objToElem) ;

    <T> TsonSerializerBuilder setToObject(TsonElementType type, String name, Class<T> to, TsonElementToObject<T> elemToObj);

    TsonSerializer build();
}
