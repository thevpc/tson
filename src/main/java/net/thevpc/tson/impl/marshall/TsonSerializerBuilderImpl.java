package net.thevpc.tson.impl.marshall;

import net.thevpc.tson.*;
import net.thevpc.tson.*;

public class TsonSerializerBuilderImpl implements TsonSerializerBuilder {

    private TsonSerializerConfig config;

    public TsonSerializerBuilderImpl(TsonSerializerConfig other) {
        config=new TsonSerializerConfig(other);
    }

    public TsonSerializerBuilderImpl() {
        config=new TsonSerializerConfig();
        config.registerDefaults();
    }

    @Override
    public <T> TsonSerializerBuilder setToElement(Class<T> type, TsonObjectToElement<T> objToElem) {
        config.registerObjToElemConverter(type,objToElem);
        return this;
    }

    @Override
    public <T> TsonSerializerBuilder setToObject(TsonElementType type, String name, Class<T> to, TsonElementToObject<T> elemToObj) {
        config.registerElemToObjConverter(type,name, to, elemToObj);
        return this;
    }

    @Override
    public TsonSerializer build() {
        return new TsonSerializerImpl(config);
    }
}
