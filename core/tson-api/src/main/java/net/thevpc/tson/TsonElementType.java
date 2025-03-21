package net.thevpc.tson;

public enum TsonElementType {
    NULL,
    BINARY_STREAM,
    CHAR_STREAM,
    STRING,
    CHAR,
    BYTE,
    SHORT,
    INTEGER,
    LONG,
    FLOAT,
    DOUBLE,
    DOUBLE_COMPLEX,
    FLOAT_COMPLEX,
    BIG_INTEGER,
    BIG_COMPLEX,
    BIG_DECIMAL,
    BOOLEAN,
    NAME,
    ALIAS,
    LOCAL_DATETIME,
    LOCAL_DATE,
    LOCAL_TIME,
    REGEX,

    OP,
    PAIR,
    CUSTOM,

    MATRIX,
    NAMED_MATRIX,
    PARAMETRIZED_MATRIX,
    NAMED_PARAMETRIZED_MATRIX,

    ARRAY,
    NAMED_ARRAY,
    PARAMETRIZED_ARRAY,
    NAMED_PARAMETRIZED_ARRAY,

    OBJECT,
    NAMED_OBJECT,
    PARAMETRIZED_OBJECT,
    NAMED_PARAMETRIZED_OBJECT,

    UPLET,
    NAMED_UPLET,

    ;

    TsonElementType() {
    }

    public boolean isNamed() {
        switch (this) {
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
                return true;

            case PARAMETRIZED_MATRIX:
            case ARRAY:
            case OBJECT:
            case UPLET:
            case PARAMETRIZED_OBJECT:
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case MATRIX:
            case PAIR:
            case OP:
            case CUSTOM:
                return false;
        }
        return false;
    }

    public boolean isParametrized() {
        switch (this) {
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:
            case PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case UPLET:
                return true;

            case NAMED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_MATRIX:
            case ARRAY:
            case OBJECT:
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case MATRIX:
            case PAIR:
            case OP:
            case CUSTOM:
                return false;
        }
        return false;
    }

    public boolean isListContainer() {
        switch (this) {
            case ARRAY:
            case OBJECT:
            case UPLET:
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
                return true;

            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:

            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case MATRIX:
            case PAIR:
            case OP:
            case CUSTOM:
                return false;
        }
        return false;
    }

    public boolean isTemporal() {
        switch (this) {
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
                return true;
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case MATRIX:
            case PAIR:
            case OP:
            case ARRAY:
            case OBJECT:
            case UPLET:
            case CUSTOM:
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:
            case PARAMETRIZED_ARRAY:
            case PARAMETRIZED_OBJECT:
                return false;
        }
        return false;
    }

    public boolean isPrimitive() {
        switch (this) {
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
                return true;
            case MATRIX:
            case PAIR:
            case OP:
            case ARRAY:
            case OBJECT:
            case UPLET:
            case CUSTOM:
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:
            case PARAMETRIZED_ARRAY:
            case PARAMETRIZED_OBJECT:
                return false;
        }
        return false;
    }

    public boolean isNumber() {
        switch (this) {
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
                return true;
            case NULL:
            case BINARY_STREAM:
            case CHAR_STREAM:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case ALIAS:
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
            case MATRIX:
            case PAIR:
            case OP:
            case ARRAY:
            case OBJECT:
            case UPLET:
            case CUSTOM:
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:
            case PARAMETRIZED_ARRAY:
            case PARAMETRIZED_OBJECT:
                return false;
        }
        return false;
    }

    public boolean isSimple() {
        switch (this) {
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case DOUBLE_COMPLEX:
            case FLOAT_COMPLEX:
            case BIG_INTEGER:
            case BIG_COMPLEX:
            case BIG_DECIMAL:
            case NULL:
            case STRING:
            case CHAR:
            case BOOLEAN:
            case NAME:
            case LOCAL_DATETIME:
            case LOCAL_DATE:
            case LOCAL_TIME:
            case REGEX:
            case CUSTOM:
                return true;

            case CHAR_STREAM:
            case BINARY_STREAM:
            case OBJECT:
            case ARRAY:
            case ALIAS:
            case OP:
            case MATRIX:
            case UPLET:
            case PAIR:
            case NAMED_ARRAY:
            case NAMED_PARAMETRIZED_ARRAY:
            case NAMED_OBJECT:
            case NAMED_PARAMETRIZED_OBJECT:
            case NAMED_UPLET:
            case NAMED_MATRIX:
            case NAMED_PARAMETRIZED_MATRIX:
            case PARAMETRIZED_MATRIX:
            case PARAMETRIZED_ARRAY:
            case PARAMETRIZED_OBJECT:
                return false;
        }
        return true;
    }


}
