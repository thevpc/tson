package net.thevpc.tson;

public interface TsonSerializer {
    TsonSerializerBuilder builder();

    TsonObjectContext context();

    TsonElement serialize(Object o);

    TsonElement[] serializeArray(Object ...o);

    <T> T deserialize(TsonElement e, Class<T> to);
}
