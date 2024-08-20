package net.thevpc.tson;

public enum TsonElementType {
    NULL(true, false, false, false),
    BINARY_STREAM(true, false, false, false),
    CHAR_STREAM(true, false, false, false),
    STRING(true, false, false, false),
    CHAR(true, false, false, false),
    BYTE(true, true, false, false),
    SHORT(true, true, false, false),
    INT(true, true, false, false),
    LONG(true, true, false, false),
    FLOAT(true, true, false, false),
    DOUBLE(true, true, false, false),
    DOUBLE_COMPLEX(true, true, false, false),
    FLOAT_COMPLEX(true, true, false, false),
    BIG_INT(true, true, false, false),
    BIG_COMPLEX(true, true, false, false),
    BIG_DECIMAL(true, true, false, false),
    BOOLEAN(true, false, false, false),
    NAME(true, false, false, false),
    ALIAS(true, false, false, false),
    DATETIME(true, false, true, false),
    DATE(true, false, true, false),
    TIME(true, false, true, false),
    REGEX(true, false, false, false),
    MATRIX(false, false, false, false),
    PAIR(false, false, false, false),
    BINOP(false, false, false, false),
    ARRAY(false, false, false, true),
    OBJECT(false, false, false, true),
    FUNCTION(false, false, false, true),
    UPLET(false, false, false, true),
    ;

    private boolean primitiveType;
    private boolean number;
    private boolean temporal;
    private boolean container;

    TsonElementType(boolean primitiveType, boolean number, boolean temporal, boolean container) {
        this.primitiveType = primitiveType;
        this.number = number;
        this.temporal = temporal;
        this.container = container;
    }

    public boolean isContainer() {
        return container;
    }

    public boolean isTemporal() {
        return temporal;
    }

    public boolean isPrimitive() {
        return primitiveType;
    }

    public boolean isNumber() {
        return number;
    }

    public boolean isSimple() {
        switch (this) {
            case FUNCTION:
            case CHAR_STREAM:
            case BINARY_STREAM:
            case OBJECT:
            case ARRAY:
            case ALIAS:
            case BINOP:
            case MATRIX:
            case UPLET:
            case PAIR:
                return false;
        }
        return true;
    }


}
