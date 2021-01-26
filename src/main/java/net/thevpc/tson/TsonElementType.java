package net.thevpc.tson;

public enum TsonElementType {
    NULL(true,false,false),
    BINARY_STREAM(true,false,false),
    CHAR_STREAM(true,false,false),
    STRING(true,false,false),
    CHAR(true,false,false),
    BYTE(true,true,false),
    SHORT(true,true,false),
    INT(true,true,false),
    LONG(true,true,false),
    FLOAT(true,true,false),
    DOUBLE(true,true,false),
    DOUBLE_COMPLEX(true,true,false),
    FLOAT_COMPLEX(true,true,false),
    BIG_INT(true,true,false),
    BIG_COMPLEX(true,true,false),
    BIG_DECIMAL(true,true,false),
    BOOLEAN(true,false,false),
    NAME(true,false,false),
    ALIAS(true,false,false),
    DATETIME(true,false,true),
    DATE(true,false,true),
    TIME(true,false,true),
    REGEX(true,false,false),
    ARRAY(false,false,false),
    OBJECT(false,false,false),
    MATRIX(false,false,false),
    PAIR(false,false,false),
    FUNCTION(false,false,false),
    UPLET(false,false,false),
    ;

    private boolean primitiveType;
    private boolean number;
    private boolean temporal;
    TsonElementType(boolean primitiveType,boolean number,boolean temporal) {
        this.primitiveType = primitiveType;
        this.number = number;
        this.temporal = temporal;
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
}
