package net.thevpc.tson.impl.parser;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonNumberLayout;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberHelper {
    public static final Pattern ANY_NUMBER_PATTERN = Pattern.compile("^(?<a>[^%_L]+)(?<b>L[1248L])?(?<c>([%_][a-zA-Z]*)?)$");
    private Number real;
    private Number imag;
    private boolean complex;
    private NumberType type;
    private String unit;
    private TsonNumberLayout radix;

    public static NumberHelper of(Number real) {
        return real == null ? null : new NumberHelper(real, NumberType.of(real.getClass()), TsonNumberLayout.DECIMAL, null);
    }

    private NumberHelper(Number real, Number imag, NumberType type, TsonNumberLayout radix, String unit) {
        this.real = real;
        this.imag = imag;
        this.complex = imag != null;
        this.type = type;
        this.unit = unit;
        this.radix = radix;
    }

    private NumberHelper(Number real, NumberType type, TsonNumberLayout radix, String unit) {
        this.real = real;
        this.complex = false;
        this.type = type;
        this.radix = radix;
        this.unit = unit;
    }

    public NumberHelper to(NumberType type) {
        if (type == this.type) {
            return this;
        }
        if (complex) {
            switch (type) {
                case BYTE:
                case SHORT:
                case INT:
                    return to(NumberType.FLOAT);
                case LONG:
                    return to(NumberType.DOUBLE);
                case FLOAT:
                    return new NumberHelper(real.floatValue(), imag.floatValue(), NumberType.FLOAT, radix, unit);
                case DOUBLE:
                    return new NumberHelper(real.doubleValue(), imag.doubleValue(), NumberType.DOUBLE, radix, unit);
                case BIG_INTEGER: {
                    return to(NumberType.BIG_DECIMAL);
                }
                case BIG_DECIMAL: {
                    switch (this.type) {
                        case BIG_INTEGER: {
                            return new NumberHelper(new BigDecimal(((BigInteger) real)), NumberType.BIG_DECIMAL, radix, unit);
                        }
                        case BYTE:
                        case SHORT:
                        case INT:
                        case LONG: {
                            return new NumberHelper(new BigDecimal(BigInteger.valueOf(real.longValue())), NumberType.BIG_DECIMAL, radix, unit);
                        }
                        case FLOAT:
                        case DOUBLE: {
                            return new NumberHelper(BigDecimal.valueOf(real.doubleValue()), NumberType.BIG_INTEGER, radix, unit);
                        }
                    }
                    break;
                }
            }
        } else {
            switch (type) {
                case BYTE:
                    return new NumberHelper(real.byteValue(), NumberType.BYTE, radix, unit);
                case SHORT:
                    return new NumberHelper(real.shortValue(), NumberType.SHORT, radix, unit);
                case INT:
                    return new NumberHelper(real.intValue(), NumberType.INT, radix, unit);
                case LONG:
                    return new NumberHelper(real.longValue(), NumberType.LONG, radix, unit);
                case FLOAT:
                    return new NumberHelper(real.floatValue(), NumberType.FLOAT, radix, unit);
                case DOUBLE:
                    return new NumberHelper(real.doubleValue(), NumberType.DOUBLE, radix, unit);
                case BIG_INTEGER: {
                    switch (this.type) {
                        case BIG_DECIMAL: {
                            return new NumberHelper(((BigDecimal) real).toBigInteger(), NumberType.BIG_INTEGER, radix, unit);
                        }
                        case BYTE:
                        case SHORT:
                        case INT:
                        case LONG: {
                            return new NumberHelper(BigInteger.valueOf(real.longValue()), NumberType.BIG_INTEGER, radix, unit);
                        }
                        case FLOAT:
                        case DOUBLE: {
                            return new NumberHelper(BigDecimal.valueOf(real.doubleValue()).toBigInteger(), NumberType.BIG_INTEGER, radix, unit);
                        }
                    }
                    break;
                }
                case BIG_DECIMAL: {
                    switch (this.type) {
                        case BIG_INTEGER: {
                            return new NumberHelper(new BigDecimal(((BigInteger) real)), NumberType.BIG_DECIMAL, radix, unit);
                        }
                        case BYTE:
                        case SHORT:
                        case INT:
                        case LONG: {
                            return new NumberHelper(new BigDecimal(BigInteger.valueOf(real.longValue())), NumberType.BIG_DECIMAL, radix, unit);
                        }
                        case FLOAT:
                        case DOUBLE: {
                            return new NumberHelper(BigDecimal.valueOf(real.doubleValue()), NumberType.BIG_INTEGER, radix, unit);
                        }
                    }
                    break;
                }
            }
        }

        throw new IllegalArgumentException("unsupported conversion : " + this.type + "->" + type);
    }


//    private static Number parseReal(String a, boolean decimal, int radix, int[] precisions) {
//        for (int precision : precisions) {
//            Number u = parseReal(a, decimal, radix, precision);
//            if (u != null) {
//                return u;
//            }
//        }
//        return null;
//    }

    private static TsonNumberLayout detectRadix(String nbr) {
        int i = 0;
        int len = nbr.length();
        while (true) {
            if (!(i < len)) break;
            char c = nbr.charAt(i);
            if (
                    Character.isWhitespace(c)
                            || c == '+'
                            || c == '-'
            ) {
                i++;
            } else {
                if (c == '0') {
                    if (i + 1 < len) {
                        c = nbr.charAt(i + 1);
                        if (c == 'b') {
                            return TsonNumberLayout.BINARY;
                        }
                        if (c == 'x') {
                            return TsonNumberLayout.HEXADECIMAL;
                        }
                        return TsonNumberLayout.OCTAL;
                    } else {
                        return TsonNumberLayout.DECIMAL;
                    }
                }else{
                    return TsonNumberLayout.DECIMAL;
                }
            }
        }
        return TsonNumberLayout.DECIMAL;
    }

    private static NumberHelper parseReal(String a, String unit, NumberType[] numberType) {
        TsonNumberLayout r = detectRadix(a);
        for (NumberType type : numberType) {
            NumberHelper v = parseReal0(a, type, r, unit);
            if (v != null) {
                return v;
            }
        }
        throw new IllegalArgumentException("unable to parse number " + a);
    }

    private static NumberType[] parsePrecisions(String b, String s, boolean decimal) {
        int precision = parsePrecision(b, s);
        NumberType[] types = {};
        if (precision <= 0) {
            if (decimal) {
                types = new NumberType[]{NumberType.FLOAT, NumberType.DOUBLE, NumberType.BIG_DECIMAL};
            } else {
                types = new NumberType[]{NumberType.BYTE, NumberType.SHORT, NumberType.INT, NumberType.LONG, NumberType.BIG_INTEGER};
            }
            return types;
        }
        if (decimal) {
            if (precision <= 4) {
                return new NumberType[]{NumberType.FLOAT};
            }
            if (precision <= 8) {
                return new NumberType[]{NumberType.DOUBLE};
            }
            return new NumberType[]{NumberType.BIG_DECIMAL};
        } else {
            if (precision <= 1) {
                return new NumberType[]{NumberType.BYTE};
            }
            if (precision <= 2) {
                return new NumberType[]{NumberType.SHORT};
            }
            if (precision <= 4) {
                return new NumberType[]{NumberType.INT};
            }
            if (precision <= 8) {
                return new NumberType[]{NumberType.LONG};
            }
            return new NumberType[]{NumberType.BIG_INTEGER};
        }
    }

    private static int parsePrecision(String b, String s) {
        int precision = 0;
        if (b != null) {
            switch (String.valueOf(b)) {
                case "L1": {
                    precision = 1;
                    break;
                }
                case "L2": {
                    precision = 2;
                    break;
                }
                case "L4": {
                    precision = 4;
                    break;
                }
                case "L8": {
                    precision = 8;
                    break;
                }
                case "LL": {
                    precision = 128;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("invalid number " + s + ". wrong precision " + b);
                }
            }
        }
        return precision;
    }

    private static NumberHelper parseReal0(String a, NumberType numberType, TsonNumberLayout radix, String unit) {
        try {
            switch (numberType) {
                case BYTE: {
                    return new NumberHelper(Byte.parseByte(a, radix.radix()), NumberType.BYTE, radix, unit);
                }
                case SHORT: {
                    return new NumberHelper(Short.parseShort(a, radix.radix()), NumberType.SHORT, radix, unit);
                }
                case INT: {
                    return new NumberHelper(Integer.parseInt(a, radix.radix()), NumberType.INT, radix, unit);
                }
                case LONG: {
                    return new NumberHelper(Long.parseLong(a, radix.radix()), NumberType.LONG, radix, unit);
                }
                case FLOAT: {
                    return new NumberHelper(Float.parseFloat(a), NumberType.FLOAT, radix, unit);
                }
                case DOUBLE: {
                    return new NumberHelper(Double.parseDouble(a), NumberType.DOUBLE, radix, unit);
                }
                case BIG_DECIMAL: {
                    return new NumberHelper(new BigDecimal(a), NumberType.BIG_DECIMAL, radix, unit);
                }
                case BIG_INTEGER: {
                    return new NumberHelper(new BigInteger(a, radix.radix()), NumberType.BIG_INTEGER, radix, unit);
                }
            }
        } catch (Exception ex) {
            return null;
        }
        throw new IllegalArgumentException("unexpected precision: " + numberType);
    }


    public static NumberHelper parse(String s) {
        if (s == null) {
            throw new IllegalArgumentException("null string cannot be parse as number");
        }
        s = s.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("empty string cannot be parse as number");
        }
        Matcher matcher = ANY_NUMBER_PATTERN.matcher(s);
        boolean decimal = false;
        NumberHelper real = null;
        NumberHelper imag = null;
        if (matcher.find()) {
            String a = matcher.group("a");
            String b = matcher.group("b");
            String c = matcher.group("c");
            if (a.indexOf('.') >= 0 || a.indexOf('e') >= 0 || a.indexOf('E') >= 0) {
                decimal = true;
            }
            int li = Math.max(a.indexOf('+'), a.indexOf('-'));
            if (li > 0) {
                NumberType[] precisions = parsePrecisions(b, s, decimal);
                real = parseReal(a.substring(0, li), c, precisions);
                imag = parseReal(a.substring(li), c, precisions);
                NumberType newType = real.type.combine(imag.type);
                switch (newType) {
                    case BYTE:
                    case SHORT:
                    case INT: {
                        newType = NumberType.FLOAT;
                        break;
                    }
                    case BIG_INTEGER: {
                        newType = NumberType.BIG_DECIMAL;
                        break;
                    }
                }
                real = real.to(newType);
                imag = imag.to(newType);
                return new NumberHelper(real.real, imag.imag, newType, TsonNumberLayout.DECIMAL, c);
            } else {
                NumberType[] precisions = parsePrecisions(b, s, decimal);
                return parseReal(a, c, precisions);
            }
        }
        throw new IllegalArgumentException("unable to parse number : " + s);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG: {
                sb.append(Long.toString(real.longValue(), radix.radix()));
                break;
            }
            case BIG_INTEGER: {
                sb.append((((BigInteger) real).toString(radix.radix())));
                break;
            }
            case FLOAT:
            case DOUBLE:
            case BIG_DECIMAL: {
                sb.append(real);
                break;
            }
        }
        if (complex) {
            switch (type) {
                case BYTE:
                case SHORT:
                case INT:
                case LONG: {
                    if (imag.longValue() >= 0) {
                        sb.append("+");
                    }
                    sb.append(Long.toString(imag.longValue(), radix.radix()));
                    break;
                }
                case BIG_INTEGER: {
                    if (((BigInteger) imag).compareTo(BigInteger.ZERO) >= 0) {
                        sb.append("+");
                    }
                    sb.append((((BigInteger) imag).toString(radix.radix())));
                    break;
                }
                case FLOAT:
                case DOUBLE: {
                    if (imag.doubleValue() >= 0) {
                        sb.append("+");
                    }
                    sb.append(imag);
                    break;
                }
                case BIG_DECIMAL: {
                    if (((BigDecimal) imag).compareTo(BigDecimal.ZERO) >= 0) {
                        sb.append("+");
                    }
                    sb.append(imag);
                    break;
                }
            }
        }
        if (unit != null && unit.length() > 0) {
            if (!unit.startsWith("%") && !unit.startsWith("_")) {
                sb.append("_");
            }
            sb.append(unit);
        }
        return sb.toString();
    }

    public Number getReal() {
        return real;
    }

    public Number getImag() {
        return imag;
    }

    public boolean isComplex() {
        return complex;
    }

    public NumberType getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public TsonNumberLayout getRadix() {
        return radix;
    }

    public TsonElement toTson() {
        if (isComplex()) {
            switch (getType()) {
                case BYTE:
                case SHORT:
                case INT:
                case FLOAT:
                    return Tson.ofFloatComplex(
                            getReal().floatValue(),
                            getImag().floatValue(),
                            getUnit());
                case LONG:
                case DOUBLE:
                    return Tson.ofDoubleComplex(
                            getReal().doubleValue(),
                            getImag().doubleValue(), getUnit());
                case BIG_INTEGER:
                    return Tson.bigComplex(
                            new BigDecimal((BigInteger) getReal()),
                            new BigDecimal((BigInteger) getImag()),
                            getUnit());
                case BIG_DECIMAL:
                    return Tson.bigComplex(
                            (BigDecimal) getReal(),
                            (BigDecimal) getImag(),
                            getUnit());
            }
        } else {
            switch (getType()) {
                case BYTE:
                    return Tson.ofByte(getReal().byteValue(), getRadix(), getUnit());
                case SHORT:
                    return Tson.ofShort(getReal().shortValue(), getRadix(), getUnit());
                case INT:
                    return Tson.ofInt(getReal().intValue(), getRadix(), getUnit());
                case LONG:
                    return Tson.ofLong(getReal().longValue(), getRadix(), getUnit());
                case FLOAT:
                    return Tson.ofFloat(getReal().floatValue(), getUnit());
                case DOUBLE:
                    return Tson.ofDouble(getReal().floatValue(), getUnit());
                case BIG_INTEGER:
                    return Tson.bigInt((BigInteger) getReal(), getRadix(), getUnit());
                case BIG_DECIMAL:
                    return Tson.bigDecimal((BigDecimal) getReal(), getUnit());
            }
        }
        throw new IllegalArgumentException("Unable to parse number " + toString());
    }
}
