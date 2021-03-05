package net.thevpc.tson.impl.marshall;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class TsonSerializerConfig {

    private final ClassMap<TsonObjectToElement> objToElem = new ClassMap<TsonObjectToElement>(Object.class, TsonObjectToElement.class);
    private final Map<TypeElementSignature, TsonElementToObject> elemToObj = new HashMap<>();

    private static TypeElementSignature[] CACHED = new TypeElementSignature[TsonElementType.values().length];
    private final TsonObjectToElement objToElem_arr = new TsonObjectToElement() {
        @Override
        public TsonElement toElement(Object object, TsonObjectContext context) {
            TsonArrayBuilder a = Tson.array();
            int len = Array.getLength(object);
            for (int i = 0; i < len; i++) {
                a.add(context.elem(Array.get(object, i)));
            }
            return a.build();
        }
    };

    private final TsonElementToObject elemToObj_arr = new TsonElementToObject() {
        @Override
        public Object toObject(TsonElement element, Class to, TsonObjectContext context) {
            TsonArray arr = (TsonArray) element;
            Object oarr = Array.newInstance(to.getComponentType(), arr.size());
            int size = 0;
            for (TsonElement tsonElement : arr) {
                Array.set(oarr, size, context.obj(tsonElement, null));
                size++;
            }
            return null;
        }
    };


    private ClassPropertiesRegistry classPropertiesRegistry;

    static {
        for (TsonElementType value : TsonElementType.values()) {
            CACHED[value.ordinal()] = new TypeElementSignature(value, null, null);
        }
    }

    public TsonSerializerConfig(TsonSerializerConfig other) {
        if (other != null) {
            elemToObj.putAll(other.elemToObj);
            objToElem.putAll(other.objToElem);
            classPropertiesRegistry = other.classPropertiesRegistry;
        } else {
            classPropertiesRegistry = ClassPropertiesRegistry.DEFAULT;
        }
    }

    public TsonSerializerConfig() {
        classPropertiesRegistry = ClassPropertiesRegistry.DEFAULT;
    }

    public void registerDefaults() {
        registerObjToElemConverter(TsonSerializable.class, TsonSerializable::toTsonElement);
        registerObjToElemConverter(TsonElementBase.class, (object, context) -> object.build());
        registerObjToElemConverter(Enum.class, (object, context) -> Tson.name(object.name()));
        registerObjToElemConverter(String.class, (object, context) -> Tson.elem((String) object));
        registerObjToElemConverter(Double.class, (object, context) -> Tson.elem((Double) object));
        registerObjToElemConverter(Double.TYPE, (object, context) -> Tson.elem((Double) object));
        registerObjToElemConverter(Float.class, (object, context) -> Tson.elem((Float) object));
        registerObjToElemConverter(Float.TYPE, (object, context) -> Tson.elem((Float) object));
        registerObjToElemConverter(Long.class, (object, context) -> Tson.elem((Long) object));
        registerObjToElemConverter(Long.TYPE, (object, context) -> Tson.elem((Long) object));
        registerObjToElemConverter(Integer.class, (object, context) -> Tson.elem((Integer) object));
        registerObjToElemConverter(Integer.TYPE, (object, context) -> Tson.elem((Integer) object));
        registerObjToElemConverter(Short.class, (object, context) -> Tson.elem((Short) object));
        registerObjToElemConverter(Short.TYPE, (object, context) -> Tson.elem((Short) object));
        registerObjToElemConverter(Byte.class, (object, context) -> Tson.elem((Byte) object));
        registerObjToElemConverter(Byte.TYPE, (object, context) -> Tson.elem((Byte) object));
        registerObjToElemConverter(Character.class, (object, context) -> Tson.elem((Character) object));
        registerObjToElemConverter(Character.TYPE, (object, context) -> Tson.elem((Character) object));
        registerObjToElemConverter(Date.class, (object, context) -> Tson.elem((Date) object));
        registerObjToElemConverter(Instant.class, (object, context) -> Tson.elem((Instant) object));
        registerObjToElemConverter(Time.class, (object, context) -> Tson.elem((Time) object));
        registerObjToElemConverter(LocalTime.class, (object, context) -> Tson.elem((LocalTime) object));
        registerObjToElemConverter(LocalDate.class, (object, context) -> Tson.elem((LocalDate) object));
        registerObjToElemConverter(Pattern.class, (object, context) -> Tson.elem((Pattern) object));
        registerObjToElemConverter(Boolean.class, (object, context) -> Tson.elem((Boolean) object));
        registerObjToElemConverter(Boolean.TYPE, (object, context) -> Tson.elem((Boolean) object));
        registerObjToElemConverter(Collection.class, (object, context) -> {
            TsonArrayBuilder a = Tson.array();
            for (Object o : object) {
                a.add(context.elem(o));
            }
            return a.build();
        });
        registerObjToElemConverter(Map.class, (object, context) -> {
            TsonObjectBuilder a = Tson.obj();
            for (Map.Entry<?, ?> o : ((Map<?, ?>) object).entrySet()) {
                a.add(
                        context.elem(o.getKey()),
                        context.elem(o.getValue())
                );
            }
            return a.build();
        });

        registerObjToElemConverter(Map.Entry.class, (object, context) -> {
            Map.Entry me = (Map.Entry) object;
            return Tson.pair(
                    context.elem(me.getKey()),
                    context.elem(me.getValue())
            );
        });

        registerObjToElemConverter(Object.class, (object, context) -> {
            TsonObjectBuilder a = Tson.obj();
            ClassPropertiesRegistry.ClassInfo ci = classPropertiesRegistry.getClassInfo(object.getClass());
            for (ClassPropertiesRegistry.TypeProperty o : ci.getProperties(true)) {
                a.add(
                        context.elem(o.name()),
                        context.elem(o.get(object))
                );
            }
            return a.build();
        });

        registerElemToObjConverter(TsonElementType.NULL, null, null, (element, to, context) -> null);
        registerElemToObjConverter(TsonElementType.STRING, null, null, (element, to, context) -> element.getString());
        registerElemToObjConverter(TsonElementType.INT, null, null, (element, to, context) -> element.getInt());
        registerElemToObjConverter(TsonElementType.LONG, null, null, (element, to, context) -> element.getLong());
        registerElemToObjConverter(TsonElementType.SHORT, null, null, (element, to, context) -> element.getShort());
        registerElemToObjConverter(TsonElementType.BYTE, null, null, (element, to, context) -> element.getByte());
        registerElemToObjConverter(TsonElementType.BOOLEAN, null, null, (element, to, context) -> element.getBoolean());
        registerElemToObjConverter(TsonElementType.CHAR, null, null, (element, to, context) -> element.getChar());
        registerElemToObjConverter(TsonElementType.FLOAT, null, null, (element, to, context) -> element.getFloat());
        registerElemToObjConverter(TsonElementType.DOUBLE, null, null, (element, to, context) -> element.getDouble());

        registerElemToObjConverter(TsonElementType.DATETIME, null, null, (element, to, context) -> element.getDateTime());
        registerElemToObjConverter(TsonElementType.DATETIME, null, Instant.class, (element, to, context) -> element.getDateTime());
        registerElemToObjConverter(TsonElementType.DATETIME, null, Date.class, (element, to, context) -> TsonUtils.convertToDate(element.getDateTime()));

        registerElemToObjConverter(TsonElementType.DATE, null, null, (element, to, context) -> element.getDate());
        registerElemToObjConverter(TsonElementType.DATE, null, LocalDate.class, (element, to, context) -> element.getDate());
        registerElemToObjConverter(TsonElementType.DATE, null, Date.class, (element, to, context) -> TsonUtils.convertToDate(element.getDate()));
        registerElemToObjConverter(TsonElementType.DATE, null, java.sql.Date.class, (element, to, context) -> TsonUtils.convertToSqlDate(element.getDate()));

        registerElemToObjConverter(TsonElementType.TIME, null, null, (element, to, context) -> element.getTime());
        registerElemToObjConverter(TsonElementType.TIME, null, LocalTime.class, (element, to, context) -> element.getTime());
        registerElemToObjConverter(TsonElementType.TIME, null, Time.class, (element, to, context) -> TsonUtils.convertToSqlTime(element.getTime()));

        registerElemToObjConverter(TsonElementType.REGEX, null, null, (element, to, context) -> element.getRegex());

        registerElemToObjConverter(TsonElementType.PAIR, null, null, (element1, to, context1) -> keyValueToMapEntry(element1, to, context1));
        registerElemToObjConverter(TsonElementType.UPLET, null, null, (element, to, context) -> {
            return upletElementToObject(element, to, context);
        });
        registerElemToObjConverter(TsonElementType.ARRAY, null, null, (element, to, context) -> {
            if (to == null || to.equals(Map.class)) {
                TsonArray ee = element.toArray();
                Map<String, Object> namedArray = new LinkedHashMap<>();
                TsonElementHeader h = ee.getHeader();
                if(h!=null){
                    Map<String, Object> mheader = new LinkedHashMap<>();
                    mheader.put("name", h.name());
                    mheader.put("values", arrayElementToObject(h.getAll(), null, context));
                    namedArray.put("header",mheader);
                }
                namedArray.put("values", arrayElementToObject(ee.getAll(), null, context));
                return namedArray;
            }
            throw new UnsupportedOperationException("Unsupported Named Array to " + to);
        });
        registerElemToObjConverter(TsonElementType.OBJECT, null, null, (element, to, context) -> {
            if (to == null || to.equals(Map.class)) {
                TsonObject ee = element.toObject();
                Map<String, Object> namedArray = new LinkedHashMap<>();
                TsonElementHeader h = ee.getHeader();
                if(h!=null){
                    Map<String, Object> mheader = new LinkedHashMap<>();
                    mheader.put("name", h.name());
                    mheader.put("values", arrayElementToObject(h.getAll(), null, context));
                    namedArray.put("header",mheader);
                }
                namedArray.put("values", arrayElementToObject(ee.getAll(), null, context));
                return namedArray;
            }
            throw new UnsupportedOperationException("Unsupported Named Array to " + to);
        });
        registerElemToObjConverter(TsonElementType.FUNCTION, null, null, (element, to, context) -> {
            if (to == null || to.equals(Map.class)) {
                TsonFunction ee = element.toFunction();
                Map<String, Object> namedArray = new LinkedHashMap<>();
                namedArray.put("function", ee.name());
                namedArray.put("params", arrayElementToObject(ee.getAll(), null, context));
                return namedArray;
            }
            throw new UnsupportedOperationException("Unsupported Named Array to " + to);
        });

    }

    private Object upletElementToObject(TsonElement element, Class to, TsonObjectContext context) {
        if (to == null) {
            TsonUplet ee = element.toUplet();
            Map<String, Object> namedArray = new LinkedHashMap<>();
            namedArray.put("uplet", true);
            namedArray.put("params", arrayElementToObject(ee.getAll(), null, context));
            return namedArray;
        } else {
            return arrayElementToObject(element.toUplet().getAll(), to, context);
        }
    }

    private Object arrayElementToObject(TsonElement element, Class to, TsonObjectContext context) {
        return arrayElementToObject(element.toArray().getAll(), to, context);
    }

    private Object objectElementToObject(TsonElement element, Class to, TsonObjectContext context) {
        return objectElementToObject(element.toObject().getAll(), to, context);
    }

    private Object arrayElementToObject(List<TsonElement> elements, Class to, TsonObjectContext context) {
        return arrayElementToObject(elements == null ? null : elements.toArray(new TsonElement[0]), to, context);
    }

    private Object arrayElementToObject(TsonElement[] elements, Class to, TsonObjectContext context) {
        Collection coll = null;
        if (to == null) {
            coll = new ArrayList();
        } else {
            if (to.isArray()) {
                coll = new ArrayList();
                for (TsonElement a : elements) {
                    coll.add(context.obj(a, to.getComponentType()));
                }
                try {
                    return coll.toArray((Object[]) Array.newInstance(to.getComponentType(), coll.size()));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Unable to instantiate array", e);
                }
            } else {
                switch (to.getName()) {
                    case "java.util.Collection":
                    case "java.util.List": {
                        coll = new ArrayList();
                        break;
                    }
                    case "java.util.Set": {
                        coll = new HashSet();
                        break;
                    }
                    case "java.util.Queue": {
                        coll = new PriorityQueue();
                        break;
                    }
                    case "java.util.Deque": {
                        coll = new LinkedList();
                        break;
                    }
                    default: {
                        if (Collection.class.isAssignableFrom(to)) {
                            try {
                                coll = (Collection) to.newInstance();
                            } catch (Exception e) {
                                throw new IllegalArgumentException("Unable to create collection");
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (coll == null) {
            throw new IllegalArgumentException("Unable to convert to " + to);
        }
        for (TsonElement a : elements) {
            coll.add(context.obj(a, null));
        }
        return coll;
    }

    private Object objectElementToObject(List<TsonElement> elements, Class to, TsonObjectContext context) {
        return objectElementToObject(elements == null ? null : elements.toArray(new TsonElement[0]), to, context);
    }

    private Object objectElementToObject(TsonElement[] elements, Class to, TsonObjectContext context) {
        Map coll = null;
        if (to == null) {
            coll = new HashMap();
        } else {
            if (to.isArray()) {
                return arrayElementToObject(elements, to, context);
            } else {
                switch (to.getName()) {
                    case "java.util.Collection":
                    case "java.util.List":
                    case "java.util.Set":
                    case "java.util.Queue":
                    case "java.util.Deque": {
                        return arrayElementToObject(elements, to, context);
                    }
                    case "java.util.Map": {
                        return arrayElementToObject(elements, to, context);
                    }
                    default: {
                        if (Collection.class.isAssignableFrom(to)) {
                            return arrayElementToObject(elements, to, context);
                        }
                        if (Map.class.isAssignableFrom(to)) {
                            try {
                                coll = (Map) to.newInstance();
                            } catch (Exception e) {
                                throw new IllegalArgumentException("Unable to create map");
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (coll == null) {
            ClassPropertiesRegistry.ClassInfo ci = classPropertiesRegistry.getClassInfo(to);
            Object o = ci.newInstance();
            for (TsonElement a : elements) {
                if (a.getType() == TsonElementType.PAIR) {
                    TsonPair kv = a.toKeyValue();
                    Object u = context.obj(kv.getKey(), null);
                    if (u instanceof String) {
                        ClassPropertiesRegistry.TypeProperty property = ci.getProperty((String) u, true);
                        if (property == null) {
                            throw new IllegalArgumentException("Property not found " + u + " in " + ci.name());
                        }
                        property.set(o, context.obj(kv.getValue(), property.type()));
                    } else {
                        throw new IllegalArgumentException("Unable to initialize object with non name property");
                    }
                } else {
                    throw new IllegalArgumentException("Unable to initialize object with non name property");
                }
            }
            return o;
        }
        for (TsonElement a : elements) {
            if (a.getType() == TsonElementType.PAIR) {
                TsonPair kv = a.toKeyValue();
                coll.put(
                        context.obj(kv.getKey(), null),
                        context.obj(kv.getValue(), null)
                );
            } else {
                coll.put(
                        context.obj(a, null),
                        context.obj(Tson.nullElem(), null)
                );
            }
        }
        return coll;
    }

    private static Object keyValueToMapEntry(TsonElement element, Class to, TsonObjectContext context) {
        return new Map.Entry() {
            @Override
            public Object getKey() {
                return context.obj(element.toKeyValue().getKey(), null);
            }

            @Override
            public Object getValue() {
                return context.obj(element.toKeyValue().getValue(), null);
            }

            @Override
            public Object setValue(Object value) {
                throw new UnsupportedOperationException();
            }
        };
    }


    public final <T> void registerElemToObjConverter(TsonElementType type, String name, Class to, TsonElementToObject<T> converter) {
        switch (type) {
            case ARRAY:
            case OBJECT:
            case FUNCTION: {
                break;
            }
            default: {
                name = null;
            }
        }
        TypeElementSignature sig = new TypeElementSignature(type, name, to);
        if (converter == null) {
            elemToObj.remove(sig);
        } else {
            elemToObj.put(sig, converter);
        }
    }

    public final <T> void registerObjToElemConverter(Class<T> type, TsonObjectToElement<T> writer) {
        if (writer == null) {
            objToElem.remove(type);
        } else {
            objToElem.put(type, writer);
        }
    }

    public <T> TsonObjectToElement<T> getObjToElemConverter(Class<T> c) {
        if (c.isArray()) {
            return objToElem_arr;
        }
        TsonObjectToElement<T> w = objToElem.get(c);
        if (w == null) {
            throw new NoSuchElementException("missing object to element converter for " + c);
        }
        return w;
    }

    public void writeDefaultElement(Tson element, Writer writer) throws IOException {
        writer.write(element.toString());
    }

//    protected TsonElement objectToElement(Object any) {
//        return defaultObjectToElement(any, new DefaultTsonObjectContext(this));
//    }


    private TypeElementSignature sig(TsonElement e, Class to) {
        final TsonElementType etype = e.getType();
        switch (etype) {
            case ARRAY: {
                TsonElementHeader h = e.toArray().getHeader();
                return new TypeElementSignature(etype, h==null?null:h.name(), to);
            }
            case OBJECT: {
                TsonElementHeader h = e.toObject().getHeader();
                return new TypeElementSignature(etype, h==null?null:h.name(), to);
            }
            case FUNCTION: {
                return new TypeElementSignature(etype, e.toFunction().name(), to);
            }
        }
        if (to == null) {
            return CACHED[etype.ordinal()];
        }
        return new TypeElementSignature(etype, null, to);
    }


    public <T> TsonElementToObject<T> getElemToObj(TsonElement e, Class<T> to) {
        return (TsonElementToObject<T>) elemToObj.get(sig(e, to));
    }


    public final static class TypeElementSignature {

        private final TsonElementType type;
        private final String name;
        private final Class to;
        private final int hash;

        public TypeElementSignature(TsonElementType type, String name, Class to) {
            this.type = type;
            this.name = name;
            this.to = to;
            this.hash = Objects.hash(type, name, to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TypeElementSignature that = (TypeElementSignature) o;
            return type == that.type
                    && Objects.equals(name, that.name)
                    && Objects.equals(to, that.to);
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }

    public TsonSerializerConfig copy() {
        return new TsonSerializerConfig(this);
    }
}
