package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;

import java.time.temporal.Temporal;

public abstract class AbstractTsonElementBase implements TsonElement {

    @Override
    public String toString() {
        return Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(boolean compact) {
        return compact ? Tson.COMPACT_FORMAT.format(this) : Tson.DEFAULT_FORMAT.format(this);
    }

    @Override
    public String toString(TsonFormat format) {
        return format == null ? Tson.DEFAULT_FORMAT.format(this) : format.format(this);
    }

    @Override
    public boolean isContainer() {
        return type().isContainer();
    }

    @Override
    public boolean isNumber() {
        return type().isNumber();
    }

    @Override
    public boolean isFloatingNumber() {
        switch (type()) {
            case FLOAT:
            case DOUBLE:
            case BIG_DECIMAL: {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isComplexNumber() {
        switch (type()) {
            case BIG_COMPLEX:
            case FLOAT_COMPLEX:
            case DOUBLE_COMPLEX: {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOrdinalNumber() {
        switch (type()) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case BIG_INT: {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isString() {
        return type() == TsonElementType.STRING;
    }

    @Override
    public boolean isPair() {
        return type() == TsonElementType.PAIR;
    }

    public boolean isSimple() {
        return type().isSimple();
    }


    @Override
    public boolean isSimplePair() {
        if (!isPair()) return false;
        TsonPair pair = toPair();
        return type() == TsonElementType.PAIR && pair.key().isSimple();
    }

    @Override
    public boolean isName() {
        return type() == TsonElementType.NAME;
    }

    @Override
    public boolean isBoolean() {
        return type() == TsonElementType.BOOLEAN;
    }

    @Override
    public boolean isFunction() {
        return type() == TsonElementType.FUNCTION;
    }

    @Override
    public boolean isArray() {
        return type() == TsonElementType.ARRAY;
    }

    @Override
    public boolean isObject() {
        return type() == TsonElementType.OBJECT;
    }

    @Override
    public boolean isNamedObject() {
        return type() == TsonElementType.OBJECT && toObject().name() != null;
    }

    @Override
    public boolean isNamedArray() {
        return type() == TsonElementType.ARRAY && toArray().name() != null;
    }

    @Override
    public boolean isUplet() {
        return type() == TsonElementType.UPLET;
    }

    @Override
    public boolean isAnyString() {
        switch (type()) {
            case STRING:
            case CHAR:
            case NAME:
                return true;
        }
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return type().isPrimitive();
    }

    @Override
    public boolean isTemporal() {
        return type().isTemporal();
    }

    @Override
    public TsonContainer toContainer() {
        if (isContainer()) {
            return (TsonContainer) this;
        }
        return toArray();
    }

    @Override
    public TsonArray toArray() {
        if (isArray()) {
            return (TsonArray) this;
        }
        if (isContainer()) {
            return Tson.ofArray(toContainer().body().toList().toArray(new TsonElement[0])).build();
        }
        return Tson.ofArray(this).build();
    }

    @Override
    public TsonObject toObject() {
        if (isObject()) {
            return (TsonObject) this;
        }
        if (isContainer()) {
            return Tson.ofObj(toContainer().body().toList().toArray(new TsonElement[0])).build();
        }
        return Tson.ofObj(this).build();
    }

    @Override
    public TsonUplet toUplet() {
        if (isUplet()) {
            return (TsonUplet) this;
        }
        if (isContainer()) {
            return Tson.ofUplet(toContainer().body().toList().toArray(new TsonElement[0])).build();
        }
        return Tson.ofUplet(this).build();
    }
}
