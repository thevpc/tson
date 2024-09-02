package net.thevpc.tson.impl.marshall;

import net.thevpc.tson.impl.marshall.reflect.JavaField;
import net.thevpc.tson.impl.marshall.reflect.JavaType;
import net.thevpc.tson.impl.marshall.reflect.JavaWord;
import net.thevpc.tson.*;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

public class CustomTsonObjectDeserializerImpl<T> implements TsonCustomDeserializer<T> {
    JavaType type;
    Function<String, String> renamer;
    List<TFieldImpl<T>> allTFields = new ArrayList<>();
    Map<String, TFieldImpl<T>> argFields = new HashMap<>();
    Map<String, TFieldImpl<T>> bodyFields = new HashMap<>();
    boolean built = false;
    boolean wrapCollections = true;
    List<MissingFieldConfigurer<T>> onUnsupportedBody = new ArrayList<>();
    List<MissingFieldConfigurer<T>> onUnsupportedArg = new ArrayList<>();
    List<InstanceConfigurer<T>> postProcess = new ArrayList<>();
    InstanceFactory<T> onNewInstance;
    Map<Type, Object> defaultValueByType = new HashMap<>();

    public CustomTsonObjectDeserializerImpl(JavaWord javaWord, Type type) {
        this.type = javaWord.of(type);
    }

    @Override
    public TsonCustomDeserializer<T> configureLenient() {
        this
                .addAllFields()
                .setTrueWhenMissing()
                .setWrapCollections(true)
        ;
        return this;
    }

    public TsonCustomDeserializer<T> setWrapCollections(boolean wrapCollections) {
        this.wrapCollections = wrapCollections;
        invalidateBuild();
        return this;
    }

    private Type uniformType(Type type) {
        if (type instanceof Class) {
            Class<?> c = (Class<?>) type;
            if (c.isPrimitive()) {
                switch (c.getName()) {
                    case "boolean":
                        return Boolean.class;
                    case "byte":
                        return Byte.class;
                    case "short":
                        return Short.class;
                    case "int":
                        return Integer.class;
                    case "long":
                        return Long.class;
                    case "float":
                        return Float.class;
                    case "double":
                        return Double.class;
                    case "char":
                        return Character.class;
                }
            }
        }
        return type;
    }

    public boolean hasDefaultValueByType(Type type) {
        return defaultValueByType.containsKey(uniformType(type));
    }

    public Object getDefaultValueByType(Type type) {
        return defaultValueByType.get(uniformType(type));
    }

    public static class TFieldImpl<T> implements FieldConfig<T> {
        CustomTsonObjectDeserializerImpl<T> parent;
        String uniformName;
        String name;
        boolean arg;
        boolean body;
        JavaField field;
        Boolean wrapCollections = true;
        Boolean useDefaultWhenMissingValue;
        Object valueWhenMissing;

        public TFieldImpl(String name, CustomTsonObjectDeserializerImpl<T> parent) {
            this.name = name;
            this.parent = parent;
        }

        @Override
        public FieldConfig<T> setTrueWhenMissing() {
            if (isBooleanType()) {
                setValueWhenMissing(Boolean.TRUE);
                return this;
            } else {
                throw new IllegalArgumentException("expected boolean");
            }
        }

        @Override
        public FieldConfig<T> setValueWhenMissing(Object valueWhenMissing) {
            this.useDefaultWhenMissingValue = true;
            this.valueWhenMissing = valueWhenMissing;
            return this;
        }

        private Object getValueWhenMissing() {
            if (valueWhenMissing != null) {
                return valueWhenMissing;
            }
            Type raw = field.getType().raw();
            return parent.getDefaultValueByType(raw);
        }

        private boolean isUseDefaultWhenMissingValue() {
            if (useDefaultWhenMissingValue != null) {
                return useDefaultWhenMissingValue;
            }
            Type raw = field.getType().raw();
            return parent.hasDefaultValueByType(raw);
        }

        public boolean isBooleanType() {
            Type raw = field.getType().raw();
            if (raw instanceof Class) {
                Class<?> cls = (Class<?>) raw;
                if (cls.equals(Boolean.class) || cls.equals(Boolean.TYPE)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isCollectionType() {
            Type raw = field.getType().raw();
            if (raw instanceof Class) {
                Class<?> cls = (Class<?>) raw;
                if (cls.isArray()) {
                    return true;
                }
                if (Collection.class.isAssignableFrom(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public FieldConfig<T> setWrapCollections(Boolean value) {
            this.wrapCollections = value;
            return this;
        }

        public boolean isWrapCollections() {
            if (wrapCollections != null) {
                return wrapCollections;
            }
            return parent.wrapCollections;
        }

        @Override
        public FieldConfig<T> setArg(boolean arg) {
            this.arg = arg;
            return this;
        }

        @Override
        public FieldConfig<T> setBody(boolean body) {
            this.body = body;
            return this;
        }

        @Override
        public TsonCustomDeserializer<T> end() {
            return parent;
        }
    }

    @Override
    public TsonCustomDeserializer<T> setTrueWhenMissing() {
        return setDefaultValueByType(Boolean.class, true);
    }

    public TsonCustomDeserializer<T> setInstanceFactory(InstanceFactory<T> instanceFactory) {
        this.onNewInstance = instanceFactory;
        return this;
    }

    public TsonCustomDeserializer<T> setDefaultValueByType(Type type, Object defaultValue) {
        defaultValueByType.put(uniformType(type), defaultValue);
        return this;
    }

    @Override
    public TsonCustomDeserializer<T> addAllFields() {
        for (JavaField allField : type.getAllFields()) {
            addField(allField.getName());
        }
        invalidateBuild();
        return this;
    }

    @Override
    public TsonCustomDeserializer<T> addFields(String... names) {
        for (String name : names) {
            addField(name);
        }
        return this;
    }

    @Override
    public TsonCustomDeserializer<T> onUnsupportedBody(MissingFieldConfigurer<T> a) {
        if (a != null) {
            onUnsupportedBody.add(a);
        }
        return this;
    }

    @Override
    public TsonCustomDeserializer<T> onUnsupportedArg(MissingFieldConfigurer<T> a) {
        if (a != null) {
            onUnsupportedArg.add(a);
        }
        return this;
    }

    @Override
    public TsonCustomDeserializer<T> postProcess(InstanceConfigurer<T> a) {
        if (a != null) {
            postProcess.add(a);
        }
        return this;
    }

    @Override
    public TFieldImpl<T> addField(String name) {
        TFieldImpl<T> f = allTFields.stream().filter(x -> x.name.equals(name)).findFirst().orElse(null);
        if (f == null) {
            f = new TFieldImpl<>(name, this);
            allTFields.add(f);
        }
        invalidateBuild();
        return f;
    }

    private void invalidateBuild() {
        this.built = false;
    }

    private String uniformName(String s) {
        s = s.trim();
        if (renamer == null) {
            return s;
        }
        return renamer.apply(s);
    }

    private void build() {
        if (!built) {
            argFields.clear();
            bodyFields.clear();
            for (TFieldImpl<T> f : allTFields) {
                f.uniformName = uniformName(f.name);
                f.field = null;
                for (JavaField field : type.getFields()) {
                    String u = uniformName(field.getName());
                    if (u.equals(f.uniformName)) {
                        f.field = field;
                        break;
                    }
                }

                boolean body = f.body || (!f.arg && !f.body);
                boolean arg = f.arg || (!f.arg && !f.body);
                if (body) {
                    bodyFields.put(f.uniformName, f);
                }
                if (arg) {
                    argFields.put(f.uniformName, f);
                }
            }
            built = true;
        }
    }

    private void processField(TsonElement arg, boolean isArg, T instance, TsonElement element, Class<T> to, TsonObjectContext context) {
        Map<String, TFieldImpl<T>> argFields = isArg ? this.argFields : this.bodyFields;
        if (arg.isSimplePair()) {
            TsonPair pair = arg.toPair();
            TsonElement key = pair.key();
            String expectedName = uniformName(key.toStr().stringValue());
            TFieldImpl<T> tField = argFields.get(expectedName);
            if (tField != null) {
                TsonElement value = pair.value();
                if (tField.isCollectionType() && tField.isWrapCollections()) {
                    value = Tson.ofArray().addAll(value.toContainer().body().toList()).build();
                }
                tField.field.set(instance, context.obj(value, (Class<?>) tField.field.getType().raw()));
            } else {
                onBodyNotSupported(instance, arg, isArg, element, to, context);
            }
        } else if (arg.isAnyString()) {
            String expectedName = uniformName(arg.toStr().stringValue());
            TFieldImpl<T> tField = argFields.get(expectedName);
            boolean found = false;
            if (tField != null) {
                if (tField.isUseDefaultWhenMissingValue()) {
                    tField.field.set(instance, tField.getValueWhenMissing());
                }
                found = true;
            }
            if (!found) {
                onBodyNotSupported(instance, arg, isArg, element, to, context);
            }
        } else {
            onBodyNotSupported(instance, arg, isArg, element, to, context);
        }
    }

    @Override
    public T toObject(TsonElement element, Class<T> to, TsonObjectContext context) {
        build();
        TsonContainer container = element.toContainer();
        TsonElementList args = container.args();
        T instance = null;
        if (onNewInstance != null) {
            instance = onNewInstance.newInstance(new FactoryConfigurerContext<T>() {
                @Override
                public TsonElement element() {
                    return element;
                }

                @Override
                public Class<T> to() {
                    return to;
                }

                @Override
                public <T1> TsonElement elem(T1 any) {
                    return context.elem(any);
                }

                @Override
                public <T1> T1 obj(TsonElement element, Class<T1> clazz) {
                    return context.obj(element,clazz);
                }
            });
        }
        if (instance == null) {
            Type rtype = type.raw();
            if (rtype instanceof Class) {
                Class cType = (Class) rtype;
                if (cType.isInterface()) {
                    if (cType.getName().equals("java.util.List")) {
                        instance = (T) new ArrayList<>();
                    } else if (cType.getName().equals("java.util.Map")) {
                        instance = (T) new LinkedHashMap<>();
                    }
                }
            }
            if (instance == null) {
                instance = (T) type.newInstance();
            }
        }
        if (args != null) {
            for (TsonElement arg : args) {
                processField(arg, true, instance, element, to, context);
            }
        }
        TsonElementList body = container.body();
        if (body != null) {
            for (TsonElement arg : body) {
                processField(arg, false, instance, element, to, context);
            }
        }
        T finalInstance = instance;
        InstanceConfigurerContext<T> cc = new InstanceConfigurerContext<T>() {
            @Override
            public T instance() {
                return finalInstance;
            }

            @Override
            public TsonElement element() {
                return element;
            }

            @Override
            public Class<T> to() {
                return to;
            }

            @Override
            public <T1> TsonElement elem(T1 any) {
                return context.elem(any);
            }

            @Override
            public <T1> T1 obj(TsonElement element, Class<T1> clazz) {
                return context.obj(element,clazz);
            }
        };
        for (InstanceConfigurer<T> process : postProcess) {
            process.prepareInstance(cc);
        }
        return (T) instance;
    }

    private void onBodyNotSupported(T instance, TsonElement arg, boolean isArg, TsonElement element, Class<T> to, TsonObjectContext context) {
        boolean found = false;
        if (!found) {

            List<MissingFieldConfigurer<T>> list = isArg ? onUnsupportedArg : onUnsupportedBody;
            FieldConfigurerContext<T> cc = new FieldConfigurerContext<T>() {
                @Override
                public T instance() {
                    return instance;
                }

                @Override
                public TsonElement field() {
                    return arg;
                }

                @Override
                public TsonElement element() {
                    return element;
                }

                @Override
                public Class<T> to() {
                    return to;
                }

                @Override
                public <T1> TsonElement elem(T1 any) {
                    return context.elem(any);
                }

                @Override
                public <T1> T1 obj(TsonElement element, Class<T1> clazz) {
                    return context.obj(element,clazz);
                }
            };
            for (MissingFieldConfigurer<T> tOnUnsupported : list) {

                if (tOnUnsupported.prepareField(cc)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            //System.err.println("unsupported " + arg + " in " + to);
        }
    }


    public boolean isCollectionType0(Type raw) {
        if (raw instanceof Class) {
            Class<?> cls = (Class<?>) raw;
            if (cls.isArray()) {
                return true;
            }
            if (Collection.class.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

}
