package net.thevpc.tson.impl.parser;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonDocument;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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


    public static TsonElement parseNumber(String s) {
        return NumberHelper.parse(s).toTson();
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
        return parseRawString(s, TsonStringLayout.SINGLE_QUOTE).charAt(0);
    }

    public static TsonElement parseCharElem(String s) {
        if (s.length() != 1) {
            return parseStringElem(s, TsonStringLayout.SINGLE_QUOTE);
        }
        return new TsonCharImpl(parseRawString(s, TsonStringLayout.SINGLE_QUOTE).charAt(0));
    }

    public static TsonElement parseStringElem(String s) {
        return parseStringElem(s, TsonStringLayout.DOUBLE_QUOTE);
    }

    public static TsonElement parseStringElem(String s, TsonStringLayout layout) {
        layout = layout == null ? TsonStringLayout.DOUBLE_QUOTE : layout;
        return Tson.rawString(s, layout);
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


    public static String extractRawString(String s, TsonStringLayout layout) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int borderLen;
        switch (layout) {
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
            case ANTI_QUOTE: {
                borderLen = 1;
                break;
            }
            case TRIPLE_ANTI_QUOTE:
            case TRIPLE_DOUBLE_QUOTE:
            case TRIPLE_SINGLE_QUOTE: {
                borderLen = 3;
                break;
            }
            default: {
                throw new IllegalArgumentException("unsupported");
            }
        }
        return s.substring(borderLen, len - borderLen);
    }


    public static String parseRawString(String s, TsonStringLayout layout) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int prefixLen = 1;
        int suffixLen = 1;
        String border = "\"";
        switch (layout) {
            case DOUBLE_QUOTE: {
                border = "\"";
                break;
            }
            case SINGLE_QUOTE: {
                border = "'";
                break;
            }
            case ANTI_QUOTE: {
                border = "`";
                break;
            }
            case TRIPLE_ANTI_QUOTE: {
                border = "```";
                break;
            }
            case TRIPLE_DOUBLE_QUOTE: {
                border = "\"\"\"";
                break;
            }
            case TRIPLE_SINGLE_QUOTE: {
                border = "'''";
                break;
            }
        }
        prefixLen = border.length();
        suffixLen = prefixLen;
        if (s.length() < prefixLen + suffixLen) {
            throw new IllegalArgumentException("unsupported: " + s);
        }
        if (
                !s.startsWith(border)
                        || !s.endsWith(border)
        ) {
            throw new IllegalArgumentException("unsupported: " + s);
        }
        switch (layout) {
            case DOUBLE_QUOTE:
            case SINGLE_QUOTE:
            case ANTI_QUOTE: {
                final int beforeLen = len - suffixLen;
                StringBuilder sb = new StringBuilder();
                for (int i = suffixLen; i < beforeLen; i++) {
                    char c = s.charAt(i);
                    switch (c) {
                        case '\\': {
                            int ip = i + 1;
                            boolean processed = false;
                            if (ip < beforeLen) {
                                switch (s.charAt(ip)) {
                                    case 'n': {
                                        sb.append('\n');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 't': {
                                        sb.append('\t');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 'f': {
                                        sb.append('\f');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case 'b': {
                                        sb.append('\b');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                    case '\\': {
                                        sb.append('\\');
                                        i++;
                                        processed = true;
                                        break;
                                    }
                                }
                            }
                            if (!processed) {
                                sb.append(c);
                            }
                            break;
                        }
                        default: {
                            sb.append(c);
                        }
                    }
                }
                return sb.toString();
            }
            case TRIPLE_ANTI_QUOTE:
            case TRIPLE_DOUBLE_QUOTE:
            case TRIPLE_SINGLE_QUOTE: {
                final int beforeLen = len - prefixLen;
                StringBuilder sb = new StringBuilder(s.length());
                for (int i = prefixLen; i < beforeLen; i++) {
                    char c = s.charAt(i);
                    switch (c) {
                        case '\\': {
                            boolean processed = false;
                            if (i + 3 < len) {
                                String substring = s.substring(i + 1, i + 1 + suffixLen);
                                if (substring.equals(border)) {
                                    sb.append(substring);
                                    i += suffixLen;
                                    processed = true;
                                }
                            }
                            if (!processed) {
                                sb.append(c);
                            }
                            break;
                        }
                        default: {
                            sb.append(c);
                        }
                    }
                }
                return sb.toString();
            }
        }
        throw new IllegalArgumentException("unsupported: " + s);
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

    public static TsonDocument elementsToDocument(TsonElement[] roots) {
        TsonElement c = null;
        if (roots.length == 0) {
            return Tson.ofDocument().header(null).content(Tson.ofObj().build()).build();
        } else if (roots.length == 1) {
            return elementToDocument(roots[0]);
        } else {
            TsonAnnotation[] annotations = roots[0].getAnnotations();
            if (annotations != null && annotations.length > 0 && "tson".equals(annotations[0].getName())) {
                // will remove it
                TsonAnnotation[] annotations2 = new TsonAnnotation[annotations.length - 1];
                System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 1);
                List<TsonElement> newList = new ArrayList<>(Arrays.asList(roots));
                TsonElement c0 = roots[0].builder().setAnnotations(annotations2).build();
                newList.set(0, c0);
                roots = newList.toArray(new TsonElement[0]);
            }
            return Tson.ofDocument().content(Tson.ofObj(roots).build()).build();
        }
    }

    public static TsonDocument elementToDocument(TsonElement root) {
        TsonAnnotation[] annotations = root.getAnnotations();
        if (annotations != null && annotations.length > 0 && "tson".equals(annotations[0].getName())) {
            // will remove it
            TsonAnnotation[] annotations2 = new TsonAnnotation[annotations.length - 1];
            System.arraycopy(annotations, 1, annotations2, 0, annotations.length - 1);
            return Tson.ofDocument().header(Tson.ofDocumentHeader().addParams(annotations[0].getAll()).build())
                    .content(root.builder().setAnnotations(annotations2).build()).build();
        }
        return Tson.ofDocument().header(null).content(root).build();
    }

    public static TsonComment parseComments(String c) {
        if (c == null) {
            return null;
        }
        if (c.startsWith("/*")) {
            return TsonComment.ofMultiLine(escapeMultiLineComments(c));
        }
        if (c.startsWith("//")) {
            return TsonComment.ofSingleLine(escapeSingleLineComments(c));
        }
        throw new IllegalArgumentException("unsupported comments " + c);
    }

    public static String escapeSingleLineComments(String c) {
        if (c == null) {
            return null;
        }
        if (c.startsWith("//")) {
            return c.substring(2);
        }
        throw new IllegalArgumentException("unsupported comments " + c);
    }

    public static String escapeMultiLineComments(String c) {
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
                if (s.endsWith("*/")) {
                    s = s.substring(0, s.length() - 2);
                }
            }
            if (s.equals("*")) {
                s = s.substring(1);
            } else if (s.equals("**")) {
                s = s.substring(1);
            } else if (s.startsWith("*") && s.length() > 1 && Character.isWhitespace(s.charAt(1))) {
                s = s.substring(2).trim();
            } else if (s.startsWith("**") && s.length() > 2 && Character.isWhitespace(s.charAt(1))) {
                s = s.substring(2).trim();
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

    private static BigInteger fastDecodeBigIntOctal(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;
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
            result = new BigInteger(nm.substring(index, nm.length() - 1), 8);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index, nm.length() - 1))
                    : nm.substring(index, nm.length() - 1);
            return new BigInteger(constant, 8);
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

    private static BigInteger fastDecodeBigIntHex(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;

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
            result = new BigInteger(nm.substring(index), 16);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = new BigInteger(constant, 16);
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

    private static BigInteger fastDecodeBigIntBin(String nm) throws NumberFormatException {
        int index = 0;
        boolean negative = false;
        BigInteger result;

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
            result = new BigInteger(nm.substring(index), 2);
            if (negative) {
                return result.negate();
            }
            return result;
        } catch (NumberFormatException e) {
            String constant = negative ? ("-" + nm.substring(index))
                    : nm.substring(index);
            result = new BigInteger(constant, 2);
        }
        return result;
    }

    public static TsonElement parseNaNElem(String s) {
        if (s == null) {
            return Tson.of(Double.NaN);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.NaN);
            case "float":
                return Tson.of(Float.NaN);
        }
        throw new IllegalArgumentException("Unsupported NaN(" + s + ")");
    }

    public static TsonElement parsePosInfElem(String s) {
        if (s == null) {
            return Tson.of(Double.POSITIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.POSITIVE_INFINITY);
            case "float":
                return Tson.of(Float.POSITIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported +Bound(" + s + ")");
    }

    public static TsonElement parseNegInfElem(String s) {
        if (s == null) {
            return Tson.of(Double.NEGATIVE_INFINITY);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.NEGATIVE_INFINITY);
            case "float":
                return Tson.of(Float.NEGATIVE_INFINITY);
        }
        throw new IllegalArgumentException("Unsupported -Bound(" + s + ")");
    }

    public static TsonElement parsePosBoundElem(String s) {
        if (s == null) {
            return Tson.of(Double.MAX_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.MAX_VALUE);
            case "float":
                return Tson.of(Float.MAX_VALUE);
            case "byte":
                return Tson.of(Byte.MAX_VALUE);
            case "short":
                return Tson.of(Short.MAX_VALUE);
            case "int":
                return Tson.of(Integer.MAX_VALUE);
            case "long":
                return Tson.of(Long.MAX_VALUE);
        }
        throw new IllegalArgumentException("Unsupported +Inf(" + s + ")");
    }

    public static TsonElement parseNegBoundElem(String s) {
        if (s == null) {
            return Tson.of(Double.MIN_VALUE);
        }
        switch (s) {
            case "double":
                return Tson.of(Double.MIN_VALUE);
            case "float":
                return Tson.of(Float.MIN_VALUE);
            case "byte":
                return Tson.of(Byte.MIN_VALUE);
            case "short":
                return Tson.of(Short.MIN_VALUE);
            case "int":
                return Tson.of(Integer.MIN_VALUE);
            case "long":
                return Tson.of(Long.MIN_VALUE);
        }
        throw new IllegalArgumentException("Unsupported +Inf(" + s + ")");
    }

}
