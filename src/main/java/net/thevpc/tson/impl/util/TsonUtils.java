package net.thevpc.tson.impl.util;

import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonArray;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementHeader;
import net.thevpc.tson.impl.elements.TsonArrayImpl;
import net.thevpc.tson.impl.elements.TsonElementDecorator;
import net.thevpc.tson.*;

import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TsonUtils {

    public static final TsonAnnotation[] TSON_ANNOTATIONS_EMPTY_ARRAY = new TsonAnnotation[0];
    public static final TsonElement[] TSON_ELEMENTS_EMPTY_ARRAY = new TsonElement[0];
    public static final char[] hexDigit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static TsonElement decorate(TsonElement element, String comments, TsonAnnotation[] annotations) {
        String c2 = comments;
        TsonAnnotation[] a2 = annotations == null ? new TsonAnnotation[0] : annotations;

        String c = element.getComments();
        TsonAnnotation[] a = element.getAnnotations();

        if (!Objects.equals(c, c2) || !Arrays.equals(a, a2)) {
            return TsonElementDecorator.of(element, c2, a2);
        }
        return element;
    }

    public static TsonElement setComments(TsonElement e, String comments) {
        String c = e.getComments();
        if (Objects.equals(c, comments)) {
            return e;
        }
        return TsonElementDecorator.of(
                e,
                comments,
                e.getAnnotations()
        );
    }

    public static TsonElement setAnnotations(TsonElement e, TsonAnnotation[] annotations) {
        TsonAnnotation[] c = e.getAnnotations();
        if (Arrays.equals(c, annotations)) {
            return e;
        }
        return TsonElementDecorator.of(
                e,
                e.getComments(),
                e.getAnnotations()
        );
    }

    public static String indent(String v, String indent) {
        StringBuilder sb = new StringBuilder();
        for (String s : v.split("\n")) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(indent);
            sb.append(s);
        }
        return sb.toString();
    }

    public static String formatComments(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("/*");
                int i = 0;
                for (String s : str.split("\n")) {
                    if (i > 0) {
                        sb.append("\n *");
                    }
                    sb.append(" ").append(s);
                    i++;
                }
                if (i > 1) {
                    sb.append("\n");
                }
                sb.append(" */");
                return sb.toString();
            }
        }
        return "";
    }

    public static String toRegex(String str) {
        return escapeString(str, '/');
    }

    public static String toSmpStr(String str) {
        return escapeString(str, '\'');
    }

    public static String toSmpStr(char str) {
        return escapeString("" + str, '\'');
    }

    public static String escapeString(String str, char escape) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int bestLen = len * 2 + 2;
        if (bestLen < 0) {
            bestLen = Integer.MAX_VALUE;
        }
        StringBuilder sb = new StringBuilder(bestLen + 2);
        sb.append(escape);
        for (int x = 0; x < len; x++) {
            char c = chars[x];
            if ((c > 61) && (c < 127)) {
                if (c == '\\') {
                    sb.append('\\');
                    sb.append('\\');
                    continue;
                }
                sb.append(c);
                continue;
            }
            switch (c) {
                case '\t':
                    sb.append('\\').append('t');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\f':
                    sb.append('\\').append('f');
                    break;
                case '"':
                    if (escape == '"') {
                        sb.append('\\').append(c);
                    } else {
                        sb.append(c);
                    }
                    break;
                case '\'':
                    if (escape == '\'') {
                        sb.append('\\').append(c);
                    } else {
                        sb.append(c);
                    }
                    break;
                case '/':
                    if (escape == '/') {
                        sb.append('\\').append(c);
                    } else {
                        sb.append(c);
                    }
                    break;
                default:
                    if (((c < 0x0020) || (c > 0x007e))) {
                        sb.append('\\');
                        sb.append('u');
                        sb.append(toHex((c >> 12) & 0xF));
                        sb.append(toHex((c >> 8) & 0xF));
                        sb.append(toHex((c >> 4) & 0xF));
                        sb.append(toHex(c & 0xF));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append(escape);
        return sb.toString();
    }

    public static String toDblStr(String str) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int bestLen = len * 2 + 2;
        if (bestLen < 0) {
            bestLen = Integer.MAX_VALUE;
        }
        StringBuilder sb = new StringBuilder(bestLen + 2);
        sb.append('\"');
        for (int x = 0; x < len; x++) {
            char c = chars[x];
            switch (c) {
                case '\t':
                    sb.append('\\').append('t');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\f':
                    sb.append('\\').append('f');
                    break;
                case '"':
                    sb.append('\\').append(c);
                    break;
                case '\'':
                    sb.append(c);
                    break;
                case '/':
                    sb.append(c);
                    break;
                case '\\':
                    sb.append('\\');
                    sb.append('\\');
                    break;
                default:
                    if (((c < 0x0020) || (c > 0x007e))) {
                        sb.append('\\');
                        sb.append('u');
                        sb.append(hexDigit[((c >> 12) & 0xF)]);
                        sb.append(hexDigit[((c >> 8) & 0xF)]);
                        sb.append(hexDigit[((c >> 4) & 0xF)]);
                        sb.append(hexDigit[(c & 0xF)]);
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    public static void toDblStr(String str, Writer sb) throws IOException {
        char[] chars = str.toCharArray();
        int len = chars.length;
//        int bestLen = len * 2 + 2;
//        if (bestLen < 0) {
//            bestLen = Integer.MAX_VALUE;
//        }
//        sb.ensureNext(sb.length()+bestLen + 2);
        sb.append('\"');
        for (int x = 0; x < len; x++) {
            char c = chars[x];
            switch (c) {
                case '\t':
                    sb.append('\\').append('t');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\f':
                    sb.append('\\').append('f');
                    break;
                case '"':
                    sb.append('\\').append(c);
                    break;
                case '\\':
                    sb.append('\\');
                    sb.append('\\');
                    break;
                default:
                    if (((c < 0x0020) || (c > 0x007e))) {
                        sb.append('\\');
                        sb.append('u');
                        sb.append(hexDigit[((c >> 12) & 0xF)]);
                        sb.append(hexDigit[((c >> 8) & 0xF)]);
                        sb.append(hexDigit[((c >> 4) & 0xF)]);
                        sb.append(hexDigit[(c & 0xF)]);
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('\"');
    }
    public static void toDblStr(String str, StringBuilder sb) throws IOException {
        char[] chars = str.toCharArray();
        int len = chars.length;
//        int bestLen = len * 2 + 2;
//        if (bestLen < 0) {
//            bestLen = Integer.MAX_VALUE;
//        }
//        sb.ensureNext(sb.length()+bestLen + 2);
//        sb.ensureCapacity(sb.length()+bestLen + 2);
        sb.append('\"');
        for (int x = 0; x < len; x++) {
            char c = chars[x];
            switch (c) {
                case '\t':
                    sb.append('\\').append('t');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\f':
                    sb.append('\\').append('f');
                    break;
                case '"':
                    sb.append('\\').append(c);
                    break;
                case '\\':
                    sb.append('\\');
                    sb.append('\\');
                    break;
                default:
                    if (((c < 0x0020) || (c > 0x007e))) {
                        sb.append('\\');
                        sb.append('u');
                        sb.append(hexDigit[((c >> 12) & 0xF)]);
                        sb.append(hexDigit[((c >> 8) & 0xF)]);
                        sb.append(hexDigit[((c >> 4) & 0xF)]);
                        sb.append(hexDigit[(c & 0xF)]);
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('\"');
    }

    public static boolean isValidIdentifier(String id) {
        if (id == null) {
            return false;
        }
        if (id.isEmpty()) {
            return false;
        }
        for (String s : id.split("\\.")) {
            if (s.isEmpty()) {
                return false;
            }
            if (!Character.isJavaIdentifierStart(s.charAt(0))) {
                return false;
            }
            for (int i = 1; i < s.length(); i++) {
                if (!Character.isJavaIdentifierPart(s.charAt(0))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String trim(String string) {
        return string == null ? "" : string.trim();
    }

    public static String trimToNull(String string) {
        if (string == null) {
            return null;
        }
        String y = string.trim();
        if (y.isEmpty()) {
            return null;
        }
        return y;
    }

    public static <T extends Comparable<T>> int compare(T a1, T a2) {
        if (a1 == null && a2 == null) {
            return 0;
        } else if (a1 == null) {
            return -1;
        } else if (a2 == null) {
            return 1;
        } else {
            return a1.compareTo(a2);
        }
    }

    public static int compareElementsArray(TsonElement[] a1, TsonElement[] a2) {
        int i = 0;
        final int max = Math.max(a1.length, a2.length);
        for (int j = 0; j < max; j++) {
            if (j >= a1.length) {
                return -1;
            }
            if (j >= a2.length) {
                return 1;
            }
            i = a1[j].compareTo(a2[j]);
            if (i != 0) {
                return i;
            }
        }
        return i;
    }

    public static int compareElementsArray(TsonArray[] a1, TsonArray[] a2) {
        int i = 0;
        final int max = Math.max(a1.length, a2.length);
        for (int j = 0; j < max; j++) {
            if (j >= a1.length) {
                return -1;
            }
            if (j >= a2.length) {
                return 1;
            }
            i = a1[j].compareTo(a2[j]);
            if (i != 0) {
                return i;
            }
        }
        return i;
    }

    public static int compareHeaders(TsonElementHeader a1, TsonElementHeader a2) {
        if (a1 == a2) {
            return 0;
        }
        if (a1 == null) {
            return -1;
        }
        if (a2 == null) {
            return 1;
        }
        return a1.compareTo(a2);
    }

    public static int compareElementsArray(List<TsonElement> a1, List<TsonElement> a2) {
        int i = 0;
        int size1 = a1.size();
        int size2 = a2.size();
        final int max = Math.max(size1, size2);
        for (int j = 0; j < max; j++) {
            if (j >= size1) {
                return -1;
            }
            if (j >= size2) {
                return 1;
            }
            i = a1.get(j).compareTo(a2.get(j));
            if (i != 0) {
                return i;
            }
        }
        return i;
    }

    public static Date convertToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static java.sql.Date convertToSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static java.sql.Time convertToSqlTime(LocalTime dateToConvert) {
        return java.sql.Time.valueOf(dateToConvert);
    }

    public static Date convertToDate(Instant dateToConvert) {
        return java.util.Date.from(dateToConvert);
    }

    public static String nullIfBlank(String name) {
        if (name == null) {
            return null;
        }
        int len = name.length();
        int st = 0;
        while ((st < len)) {
            if (!(name.charAt(st) <= ' ')) {
                return name;
            }
            st++;
        }
        return null;
    }

    public static UnmodifiableArrayList<TsonElement> unmodifiableElements(List<TsonElement> all) {
        return UnmodifiableArrayList.ofRef(all.toArray(new TsonElement[0]));
    }

    public static UnmodifiableArrayList<TsonArray> unmodifiableArrays(List<TsonArray> all) {
        return UnmodifiableArrayList.ofRef(all.toArray(new TsonArray[0]));
    }

    public static TsonArray toArray(List<TsonElement> elements){
        return new TsonArrayImpl(null, TsonUtils.unmodifiableElements(elements));
    }

    public static TsonArray toArray(TsonElementHeader header,List<TsonElement> elements){
        return new TsonArrayImpl(header, TsonUtils.unmodifiableElements(elements));
    }
}
