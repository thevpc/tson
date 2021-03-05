package net.thevpc.tson.impl.parser;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonDocument;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

import net.thevpc.tson.impl.elements.*;

public class TsonParserUtils {

    public static TsonElement parseDateTimeElem(String s) {
        return new TsonDateTimeImpl(Instant.parse(s));
    }

    public static TsonElement parseDateElem(String s) {
        return new TsonDateImpl(LocalDate.parse(s));
    }

    public static TsonElement parseTimeElem(String s) {
        return new TsonTimeImpl(LocalTime.parse(s));
    }

    public static TsonElement parseRegexElem(String s) {
        final String p = s.substring(1, s.length() - 1);
        //should unescape
        return new TsonRegexImpl(Pattern.compile(p));
    }

    public static TsonElement parseByteElem(String s) {
        return new TsonByteImpl(Byte.parseByte(s.substring(0, s.length() - 1)));
    }

    public static TsonElement parseByteElemBin(String s) {
        return new TsonByteImpl((byte) fastDecodeShortBin(s));
    }

    public static TsonElement parseByteElemOctal(String s) {
        return new TsonByteImpl((byte) fastDecodeShortOctal(s));
    }

    public static TsonElement parseByteElemHex(String s) {
        return new TsonByteImpl((byte) fastDecodeShortHex(s));
    }


    public static TsonElement parseIntElemOctal(String s) {
        return new TsonIntImpl(fastDecodeIntOctal(s));
    }

    public static TsonElement parseLongElemOctal(String s) {
        return new TsonLongImpl(fastDecodeLongOctal(s));
    }

    public static TsonElement parseIntElemBin(String s) {
        return new TsonIntImpl(fastDecodeIntBin(s));
    }


    public static TsonElement parseLongElemBin(String s) {
        return new TsonLongImpl(fastDecodeLongBin(s));
    }

    public static TsonElement parseIntElemHex(String s) {
        return new TsonIntImpl(fastDecodeIntHex(s));
    }


    public static TsonElement parseShortElem(String s) {
        return new TsonShortImpl(Short.parseShort(s.substring(0, s.length() - 1)));
    }

    public static TsonElement parseShortElemBin(String s) {
        return new TsonShortImpl(fastDecodeShortBin(s));
    }

    public static TsonElement parseShortElemOctal(String s) {
        return new TsonShortImpl(fastDecodeShortOctal(s));
    }

    public static TsonElement parseShortElemHex(String s) {
        return new TsonShortImpl(fastDecodeShortHex(s));
    }

    public static TsonElement parseLongElemHex(String s) {
        return new TsonLongImpl(fastDecodeLongHex(s));
    }

    public static TsonElement parseIntElem(String s) {
        return new TsonIntImpl(Integer.parseInt(s));
    }

    public static TsonElement parseLongElem(String s) {
        return new TsonLongImpl(Long.parseLong(s));
    }

    public static TsonElement parseFloatElem(String s) {
        return new TsonFloatImpl(Float.parseFloat(s));
    }

    public static TsonElement parseDoubleElem(String s) {
        return new TsonDoubleImpl(Double.parseDouble(s));
    }

    public static Instant parseDateTime(String s) {
        return Instant.parse(s);
    }

    public static LocalDate parseDate(String s) {
        return LocalDate.parse(s);
    }

    public static LocalTime parseTime(String s) {
        return LocalTime.parse(s);
    }

    public static Pattern parseRegex(String s) {
        return Pattern.compile(s.substring(1, s.length() - 1));
    }

    public static byte parseByte(String s) {
        if (s.endsWith("b") || s.endsWith("B")) {
            s = s.substring(0, s.length() - 1);
        }
        return Byte.parseByte(s);
    }

    public static short parseShort(String s) {
        if (s.endsWith("s") || s.endsWith("S")) {
            s = s.substring(0, s.length() - 1);
        }
        return Short.parseShort(s);
    }

    public static long parseLong(String s) {
        if (s.endsWith("l") || s.endsWith("L")) {
            s = s.substring(0, s.length() - 1);
        }
        return Long.parseLong(s);
    }

    public static int parseInt(String s) {
        if (s.endsWith("i") || s.endsWith("I")) {
            s = s.substring(0, s.length() - 1);
        }
        return Integer.parseInt(s);
    }

    public static float parseFloat(String s) {
        if (s.endsWith("f") || s.endsWith("F")) {
            s = s.substring(0, s.length() - 1);
        }
        return Float.parseFloat(s);
    }

    public static double parseDouble(String s) {
//        if(s.endsWith("f") || s.endsWith("F")){
//            s=s.substring(s.length()-1);
//        }
        return Double.parseDouble(s);
    }

    public static char parseChar(String s) {
        return parseString(s).charAt(0);
    }

    public static TsonElement parseCharElem(String s) {
        return new TsonCharImpl(parseString(s).charAt(0));
    }

    public static TsonElement parseStringElem(String s) {
        return new TsonStringImpl(parseString(s));
    }

    public static TsonElement parseAliasElem(String s) {
        return new TsonAliasImpl(s.substring(1));
    }

//    public static void main(String[] args) {
//        for (String string : new String[]{"\"Hello \\nWorld\"","\"Hello World\""}) {
//            System.out.println(string);
//            System.out.println(parseString(string));
//            System.out.println(parseString2(string));
//        }
//        final int count = 10000;
//
//        Chrono c2=Chrono.start();
//        for (int i = 0; i < count; i++) {
//            for (String string : new String[]{"\"Hello \\nWorld\""}) {
//                parseString(string);
//            }
//        }
//        c2.stop();
//        
//        Chrono c3=Chrono.start();
//        for (int i = 0; i < count; i++) {
//            for (String string : new String[]{"\"Hello \\nWorld\""}) {
//                parseString2(string);
//            }
//        }
//        c3.stop();
//        System.out.println(c2);
//        System.out.println(c3);
//    }

    public static String parseString(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        final int beforeLen = len - 1;
        int lastPut = 1;
        StringBuilder sb = null;
        for (int i = 1; i < beforeLen; i++) {
            switch (s.charAt(i)) {
                case '\\': {
                    if (sb == null) {
                        sb = new StringBuilder(len - 2);
                    }
                    sb.append(chars, lastPut, i - lastPut);
                    i++;
                    switch (s.charAt(i)) {
                        case 'n': {
                            sb.append('\n');
                            break;
                        }
                        case 't': {
                            sb.append('\t');
                            break;
                        }
                        case 'f': {
                            sb.append('\f');
                            break;
                        }
                        case 'b': {
                            sb.append('\b');
                            break;
                        }
                        case '\\': {
                            sb.append('\\');
                            break;
                        }
                        default: {
                            sb.append(chars[i]);
                        }
                    }
                    lastPut = i + 1;
                    break;
                }
            }
        }
        if (sb == null) {
//            return s.substring(1, beforeLen-1);
            return new String(chars, 1, beforeLen - 1);
        }
        sb.append(chars, lastPut, beforeLen - lastPut);
        return sb.toString();
    }

    public static String parseStringOld(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder(len - 2);
        final int beforeLen = len - 1;
        for (int i = 1; i < beforeLen; i++) {
            switch (s.charAt(i)) {
                case '\\': {
                    i++;
                    switch (s.charAt(i)) {
                        case 'n': {
                            sb.append('\n');
                            break;
                        }
                        case 't': {
                            sb.append('\t');
                            break;
                        }
                        case 'f': {
                            sb.append('\f');
                            break;
                        }
                        case 'b': {
                            sb.append('\b');
                            break;
                        }
                        case '\\': {
                            sb.append('\\');
                            break;
                        }
                        default: {
                            sb.append(chars[i]);
                        }
                    }
                    break;
                }
                default: {
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }

    public static TsonDocument elementToDocument(TsonElement root) {
        TsonAnnotation[] annotations = root.getAnnotations();
        if (annotations.length > 0 && annotations[0].getName().equals("tson")) {
            // will remove it
            TsonAnnotation[] annotations2 = new TsonAnnotation[annotations.length - 1];
            System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 1);
            return Tson.document().header(Tson.documentHeader().addParams(annotations[0].getAll()).build())
                    .content(root.builder().setAnnotations(annotations2).build()).build();
        }
        return Tson.document().header(null).content(root).build();
    }

    public static String escapeComments(String c) {
        if (c == null) {
            return null;
        }
        int line = 0;
        String[] lines = c.trim().split("\n");
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            s = s.trim();
            if (line == 0) {
                if (s.startsWith("/*")) {
                    s = s.substring(2);
                }
            }
            if (line == lines.length - 1) {
                if (s.startsWith("*/")) {
                    s = s.substring(0, s.length() - 2);
                }
            }
            if (s.length() > 1 && s.charAt(0) == '*' && s.charAt(1) == ' ') {
                s = s.substring(2);
            }
            s = s.trim();
            if (line == lines.length - 1) {
                if (s.length() > 0) {
                    if (line > 0) {
                        sb.append("\n");
                    }
                    sb.append(s.trim());
                }
            } else {
                if (line > 0) {
                    sb.append("\n");
                }
                sb.append(s.trim());
            }
            line++;
        }
        return sb.toString().trim();
    }


    private static int fastDecodeIntOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Integer.valueOf(nm.substring(index), 8);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            return Integer.parseInt(constant, 8);
        }
    }

    private static short fastDecodeShortOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Short.parseShort(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return Short.parseShort(constant, 8);
        }
    }

    private static long fastDecodeLongOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;
        char firstChar = nm.charAt(0);
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }
        //(nm.startsWith("0", index) && nm.length() > 1 + index)
        index++;
        try {
            result = Long.parseLong(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return Long.parseLong(constant, 8);
        }
    }


    private static int fastDecodeIntHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Integer.parseInt(nm.substring(index), 16);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Integer.parseInt(constant, 16);
        }
        return result;
    }

    private static int fastDecodeIntBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        int result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Integer.parseInt(nm.substring(index), 2);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Integer.parseInt(constant, 2);
        }
        return result;
    }

    private static short fastDecodeShortHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Short.parseShort(nm.substring(index), 16);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Short.parseShort(constant, 16);
        }
        return result;
    }

    private static short fastDecodeShortBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        short result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Short.parseShort(nm.substring(index), 2);
            if (negative) {
                return (short) -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Short.parseShort(constant, 2);
        }
        return result;
    }

    private static long fastDecodeLongHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Long.parseLong(nm.substring(index), 16);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Long.parseLong(constant, 16);
        }
        return result;
    }

    private static long fastDecodeLongBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        long result;

        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+') {
            index++;
        }

        //(nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
        index += 2;

        try {
            result = Long.parseLong(nm.substring(index), 2);
            if (negative) {
                return -result;
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = Long.parseLong(constant, 2);
        }
        return result;
    }

    public static TsonElement parseNaNElem(String s) {
        if (s == null) {
            return Tson.elem(Double.NaN);
        }
        switch (s) {
            case "double":
                return Tson.elem(Double.NaN);
            case "float":
                return Tson.elem(Float.NaN);
        }
        throw new IllegalArgumentException("Unsupported NaN(" + s + ")");
    }

    public static TsonElement parsePosInfElem(String s) {
        if (s == null) {
            return Tson.elem(Double.POSITIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.elem(Double.POSITIVE_INFINITY);
            case "float":
                return Tson.elem(Float.POSITIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported +Bound(" + s + ")");
    }

    public static TsonElement parseNegInfElem(String s) {
        if (s == null) {
            return Tson.elem(Double.NEGATIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.elem(Double.NEGATIVE_INFINITY);
            case "float":
                return Tson.elem(Float.NEGATIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported -Bound(" + s + ")");
    }

    public static TsonElement parsePosBoundElem(String s) {
        if (s == null) {
            return Tson.elem(Double.MAX_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.elem(Double.MAX_VALUE);
            case "float":
                return Tson.elem(Float.MAX_VALUE);
            case "byte":
                return Tson.elem(Byte.MAX_VALUE);
            case "short":
                return Tson.elem(Short.MAX_VALUE);
            case "int":
                return Tson.elem(Integer.MAX_VALUE);
            case "long":
                return Tson.elem(Long.MAX_VALUE);
        }
        throw new IllegalArgumentException("Unsupported +Inf(" + s + ")");
    }

    public static TsonElement parseNegBoundElem(String s) {
        if (s == null) {
            return Tson.elem(Double.MIN_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.elem(Double.MIN_VALUE);
            case "float":
                return Tson.elem(Float.MIN_VALUE);
            case "byte":
                return Tson.elem(Byte.MIN_VALUE);
            case "short":
                return Tson.elem(Short.MIN_VALUE);
            case "int":
                return Tson.elem(Integer.MIN_VALUE);
            case "long":
                return Tson.elem(Long.MIN_VALUE);
        }
        throw new IllegalArgumentException("Unsupported +Inf(" + s + ")");
    }

}
