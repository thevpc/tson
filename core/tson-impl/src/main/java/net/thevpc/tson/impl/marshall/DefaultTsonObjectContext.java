package net.thevpc.tson.impl.marshall;

import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonObjectContext;

class DefaultTsonObjectContext implements TsonObjectContext {

    private TsonSerializerImpl m;

    public DefaultTsonObjectContext(TsonSerializerImpl m) {
        this.m = m;
    }

    public <T> TsonElement elem(T any) {
        return m.defaultObjectToElement(any, this);
    }

    @Override
    public <T> T obj(TsonElement element, Class<T> clazz) {
        return m.defaultElementToObject(element, clazz, this);
    }

}
