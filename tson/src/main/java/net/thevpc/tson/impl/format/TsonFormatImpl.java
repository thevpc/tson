package net.thevpc.tson.impl.format;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.parser.CharStreamCodeSupport;
import net.thevpc.tson.impl.parser.CharStreamCodeSupports;
import net.thevpc.tson.impl.util.AppendableWriter;
import net.thevpc.tson.impl.util.KmpAlgo;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.*;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TsonFormatImpl implements TsonFormat, Cloneable {
    public static final HashSet<String> FORMAT_NUMBER_TYPES = new HashSet<>(Arrays.asList("hex", "dec", "bin", "oct"));
    private static final RadixInfo R2 = new RadixInfo(2, "0b");
    private static final RadixInfo R8 = new RadixInfo(8, "0");
    private static final RadixInfo R10 = new RadixInfo(10, "");
    private static final RadixInfo R16 = new RadixInfo(16, "0x");

    private DefaultTsonFormatConfig config;

    public TsonFormatImpl(DefaultTsonFormatConfig config) {
        this.config = (config == null ? new DefaultTsonFormatConfig().setCompact(false) : config.copy());
    }

    @Override
    public String format(TsonElement element) {
        StringBuilder sb = new StringBuilder();
        try (AppendableWriter w = AppendableWriter.of(sb)) {
            try {
                formatElement(element, config.isShowComments(), config.isShowAnnotations(), w);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return sb.toString();
    }

    public void format(TsonElement element, Writer sb) throws IOException {
        formatElement(element, config.isShowComments(), config.isShowAnnotations(), sb);
    }

    @Override
    public String format(TsonDocument document) {
        StringBuilder sb = new StringBuilder();
        try (AppendableWriter w = AppendableWriter.of(sb)) {
            try {
                formatDocument(document, w);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return sb.toString();
    }

    public void formatDocument(TsonDocument document, Writer sb) throws IOException {
        TsonDocumentHeader h = document.getHeader();
        TsonElement elem = document.getContent();
        formatAnnotation(h.builder().toAnnotation(), config.isShowComments(), config.isShowAnnotations(), sb);
        if (config.compact) {
            sb.append(' ');
        } else {
            sb.append('\n');
        }
        formatElement(elem, config.isShowComments(), config.isShowAnnotations(), sb);
    }

    public void formatAnnotation(TsonAnnotation a, boolean showComments, boolean showAnnotations, Writer sb) throws IOException {
        final List<TsonElement> params = a.getAll();
        sb.append('@');
        if (a.getName() != null) {
            sb.append(a.getName());
        }
        sb.append('(');
        int i = 0;
        for (TsonElement p : params) {
            if (i > 0) {
                sb.append(',');
                sb.append(config.afterComma);
            }
            //cannot have embedded annotations
            formatElement(p, showComments, showAnnotations, sb);
            i++;
        }
        sb.append(')');
    }

    public void formatElement(TsonElement element, boolean showComments, boolean showAnnotations, Writer sb) throws IOException {
        if (showComments) {
            String c = element.getComments();
            if (c != null) {
                if (config.isIndentBraces()) {
                    sb.append(TsonUtils.formatComments(c));
                } else {
                    sb.append("/*").append(c).append("*/");
                }
                sb.append(config.afterComments);
            }
        }
        TsonAnnotation[] ann = element.getAnnotations();
        TsonAnnotation formatAnnotation = null;

        if (ann != null && ann.length > 0) {
            for (TsonAnnotation a : ann) {
                if ("format".equals(a.getName())) {
                    formatAnnotation = a;
                    if (showAnnotations) {
                        if (!config.showFormatNumber) {
                            TsonAnnotationBuilder ab = a.builder();
                            List<TsonElement> params = ab.getAll();
                            for (int i = params.size() - 1; i >= 0; i--) {
                                TsonElement o = params.get(i);
                                if (o.getType() == TsonElementType.NAME || o.getType() == TsonElementType.STRING && FORMAT_NUMBER_TYPES.contains(o.getString())) {
                                    ab.removeAt(i);
                                }
                            }
                            if (ab.size() != 0) {
                                formatAnnotation(a, showComments, true, sb);
                                sb.append(config.afterAnnotation);
                            }
                        } else {
                            formatAnnotation(a, showComments, true, sb);
                            sb.append(config.afterAnnotation);
                        }
                    }
                } else {
                    if (showAnnotations) {
                        formatAnnotation(a, showComments, true, sb);
                        sb.append(config.afterAnnotation);
                    }
                }
            }
            sb.append(config.afterAnnotations);
        }
        formatElementCore(element, formatAnnotation, sb);
    }

    public void formatElementCore(TsonElement element, TsonAnnotation format, Writer writer) throws IOException {
//        sb.ensureCapacity(sb.length() + 10);
        switch (element.getType()) {
            case NULL:
                writer.append("null");
                return;
            case INT: {
                int i = element.getInt();
                if (i == Integer.MAX_VALUE) {
                    writer.append("+Bound(int)");
                } else if (i == Integer.MIN_VALUE) {
                    writer.append("-Bound(int)");
                } else {
                    RadixInfo radix = decodeRadix(format);
                    writer.append(radix.prefix).append(Integer.toString(i, radix.radix));
                }
                return;
            }
            case BIG_INT: {
                BigInteger i = element.getBigInteger();
//                RadixInfo radix = decodeRadix(format);
//                writer.append(radix.prefix).append(i.toString(radix.radix));
                writer.append(i.toString());
                writer.append("g");
                return;
            }
            case BIG_DECIMAL: {
                BigDecimal i = element.getBigDecimal();
//                RadixInfo radix = decodeRadix(format);
//                writer.append(radix.prefix).append(i.toString(radix.radix));
                writer.append(i.toString());
                writer.append("g");
                return;
            }
            case LONG: {
                long i = element.getLong();
                if (i == Long.MAX_VALUE) {
                    writer.append("+Bound(long)");
                } else if (i == Long.MIN_VALUE) {
                    writer.append("-Bound(long)");
                } else {
                    RadixInfo radix = decodeRadix(format);
                    writer.append(radix.prefix).append(Long.toString(i, radix.radix)).append('L');
                }
                return;
            }
            case BYTE: {
                RadixInfo radix = decodeRadix(format);
                writer.append(radix.prefix).append(Integer.toString(element.getByte(), radix.radix)).append('o');
                return;
            }
            case SHORT: {
                short i = element.getShort();
                if (i == Short.MAX_VALUE) {
                    writer.append("+Bound(short)");
                } else if (i == Short.MIN_VALUE) {
                    writer.append("-Bound(short)");
                } else {
                    RadixInfo radix = decodeRadix(format);
                    writer.append(radix.prefix).append(Integer.toString(i, radix.radix)).append('s');
                }
                return;
            }
            case FLOAT: {
                float i = element.getFloat();
                if (Float.isNaN(i)) {
                    writer.append("NaN(float)");
                } else if (i == Float.POSITIVE_INFINITY) {
                    writer.append("+Bound(float)");
                } else if (i == Float.NEGATIVE_INFINITY) {
                    writer.append("-Bound(float)");
                } else {
                    writer.append(String.valueOf(i)).append('f');
                }
                return;
            }
            case DOUBLE: {
                double i = element.getDouble();
                if (Double.isNaN(i)) {
                    writer.append("NaN");
                } else if (i == Double.POSITIVE_INFINITY) {
                    writer.append("+Bound(double)");
                } else if (i == Double.NEGATIVE_INFINITY) {
                    writer.append("-Bound(double)");
                } else {
                    writer.append(String.valueOf(i));
                }
                return;
            }
            case FLOAT_COMPLEX: {
                TsonFloatComplex cc = element.toFloatComplex();
                writer.append(String.valueOf(cc.getImag())).append("i");
                String ii = String.valueOf(cc.getReal());
                if (!ii.startsWith("-")) {
                    writer.append("+");
                }
                writer.append(ii);
                writer.append("f");
                return;
            }
            case DOUBLE_COMPLEX: {
                TsonDoubleComplex cc = element.toDoubleComplex();
                writer.append(String.valueOf(cc.getImag())).append("i");
                String ii = String.valueOf(cc.getReal());
                if (!ii.startsWith("-")) {
                    writer.append("+");
                }
                writer.append(ii);
                return;
            }
            case BIG_COMPLEX: {
                TsonBigComplex cc = element.toBigComplex();
                writer.append(String.valueOf(cc.getImag())).append("i");
                String ii = String.valueOf(cc.getReal());
                if (!ii.startsWith("-")) {
                    writer.append("+");
                }
                writer.append(ii);
                writer.append("g");
                return;
            }
            case BOOLEAN:
                writer.append(String.valueOf(element.getBoolean()));
                return;
            case DATETIME:
                writer.append(String.valueOf(element.getDateTime()));
                return;
            case DATE:
                writer.append(String.valueOf(element.getDate()));
                return;
            case TIME:
                writer.append(String.valueOf(element.getTime()));
                return;
            case REGEX: {
                writer.append(TsonUtils.toRegex(element.getRegex().toString()));
                return;
            }
            case CHAR: {
                writer.append(TsonUtils.toSmpStr(element.getChar()));
                return;
            }
            case STRING: {
                String s = element.getString();
                StringBuilder sb=new StringBuilder(s.length()*2);
                TsonUtils.toDblStr(s, sb);
                //TsonUtils.toDblStr(s, writer);
                writer.append(sb);
                return;
            }
            case NAME: {
                writer.append(element.getString());
                return;
            }
            case ALIAS: {
                writer.append("&").append(element.getString());
                return;
            }
            case PAIR: {
                TsonPair t = element.toKeyValue();
                format(t.getKey(), writer);
                String vs = format(t.getValue());
                writer.append(config.afterKey);
                if (config.indent.length() > 0 && vs.indexOf("\n") > 0) {
                    writer.append(":\n").append(TsonUtils.indent(vs, config.indent));
                } else {
                    writer.append(':').append(config.beforeValue).append(vs);
                }
                return;
            }
            case UPLET: {
                TsonUplet list = element.toUplet();
                listToString(config.indentList, list.getAll(), '(', ')', writer, ListType.PARAMS);
                return;
            }
            case ARRAY: {
                TsonArray list = element.toArray();
                TsonElementHeader h = list.getHeader();
                if (h != null) {
                    String n = TsonUtils.nullIfBlank(h.getName());
                    boolean hasName = false;
                    if (n != null) {
                        writer.append(n);
                        hasName = n.length() > 0;
                    }
                    List<TsonElement> params = h.getAll();
                    if (!hasName || params.size() > 0) {
                        listToString(config.indentList, params, '(', ')', writer, ListType.PARAMS);
                    }
                }
                listToString(config.indentBrackets, list, '[', ']', writer, ListType.PARAMS);
                return;
            }
            case MATRIX: {
                TsonMatrix list = element.toMatrix();
                TsonElementHeader h = list.getHeader();
                if (h != null) {
                    String n = TsonUtils.nullIfBlank(h.getName());
                    boolean hasName = false;
                    if (n != null) {
                        writer.append(n);
                        hasName = n.length() > 0;
                    }
                    List<TsonElement> params = h.getAll();
                    if (!hasName || params.size() > 0) {
                        listToString(config.indentList, params, '(', ')', writer, ListType.PARAMS);
                    }
                }
                listToString(config.indentBrackets, (Iterable) list.rows(), '[', ']', writer, ListType.MATRIX);
                return;
            }
            case OBJECT: {
                TsonObject list = element.toObject();
                TsonElementHeader h = list.getHeader();
                if (h != null) {
                    String n = TsonUtils.nullIfBlank(h.getName());
                    boolean hasName = false;
                    if (n != null) {
                        writer.append(n);
                        hasName = n.length() > 0;
                    }
                    List<TsonElement> params = h.getAll();
                    if (!hasName || params.size() > 0) {
                        listToString(config.indentList, params, '(', ')', writer, ListType.PARAMS);
                    }
                }
                listToString(config.indentBraces, list, '{', '}', writer, ListType.OBJECT);
                return;
            }
            case FUNCTION: {
                TsonFunction list = element.toFunction();
                writer.append(list.getName());
                listToString(config.indentList, list.getAll(), '(', ')', writer, ListType.PARAMS);
                return;
            }
            case BINARY_STREAM: {
                TsonBinaryStream list = element.toBinaryStream();
                writer.write("^[");
                char[] c = new char[1024];
                try (Reader r = list.getBase64Value()) {
                    int x;
                    while ((x = r.read(c)) > 0) {
                        writer.write(c, 0, x);
                    }
                }
                writer.write("]");
                return;
            }
            case CHAR_STREAM: {
                TsonCharStream list = element.toCharStream();
                switch (list.getStreamType()) {
                    case "": {
                        //code
                        writer.write("^{");
                        char[] c = new char[1024];
                        CharStreamCodeSupport cscs = CharStreamCodeSupports.of("");
                        try (Reader r = list.getValue()) {
                            int x;
                            while ((x = r.read(c)) > 0) {
                                writer.write(c, 0, x);
                                cscs.next(c, 0, x);
                            }
                        }
                        if (!cscs.isValid()) {
                            throw new IllegalArgumentException("Invalid Code CharStream : " + cscs.getErrorMessage());
                        }
                        writer.write("}");
                    }
                    default: {
                        String n = list.getStreamType();
                        for (char c : n.toCharArray()) {
                            switch (c) {
                                case '}':
                                case '{': {
                                    throw new IllegalArgumentException("Invalid StopWord CharStream : " + n);
                                }
                            }
                        }
                        writer.write("^" + n + "{");
                        String stop = "^" + n + "}";
                        KmpAlgo kmp = KmpAlgo.compile(stop);
                        char[] c = new char[1024];
                        try (Reader r = list.getValue()) {
                            int x;
                            while ((x = r.read(c)) > 0) {
                                for (int i = 0; i < x; i++) {
                                    if (kmp.next(c[i])) {
                                        throw new IllegalArgumentException("Invalid StopWord CharStream StopWord detected in content : " + n);
                                    }
                                }
                                writer.write(c, 0, x);
                            }
                        }
                    }
                }
                return;
            }
        }
        throw new IllegalArgumentException("Format Tson : Unexpected type " + element.getType());
    }

    private static class RadixInfo {
        int radix;
        String prefix;

        public RadixInfo(int radix, String prefix) {
            this.radix = radix;
            this.prefix = prefix;
        }
    }

    private RadixInfo decodeRadix(TsonAnnotation format) {
        if (format != null) {
            for (TsonElement param : format.getAll()) {
                if (param.getType() == TsonElementType.NAME || param.getType() == TsonElementType.STRING) {
                    switch (param.getString()) {
                        case "hex": {
                            return R16;
                        }
                        case "dec": {
                            return R10;
                        }
                        case "oct": {
                            return R8;
                        }
                        case "bin": {
                            return R2;
                        }
                    }
                }
            }
        }
        return R10;
    }

    private void listToString(boolean indent, Iterable<TsonElement> it, char start, char end, Writer out, ListType applyIgnore) throws
            IOException {
        if (it == null) {
            return;
        }
        if (indent) {
            listToStringIndented(it, start, end, out, applyIgnore);
        } else {
            listToStringNotIndented(it, start, end, out, applyIgnore);
        }
    }

    private void listToStringIndented(Iterable<TsonElement> it, char start, char end, Writer out, ListType type) throws
            IOException {
        List<TsonElement> a = new ArrayList<>();
        for (TsonElement e : it) {
            a.add(e);
        }
        if (a.isEmpty()) {
            out.append(start).append(end);
            return;
        }
        out.append(start).append('\n');
        StringBuilder sb2 = new StringBuilder();

        try (AppendableWriter w = AppendableWriter.of(sb2)) {
            int i = 0;

            switch (type) {
                case OBJECT: {
                    for (TsonElement tsonElement : a) {
                        if (acceptObjectElement(tsonElement)) {
                            if (i > 0) {
                                sb2.append(",\n");
                            }
                            format(tsonElement, w);
                            i++;
                        }
                    }
                    break;
                }
                case ARRAY: {
                    for (TsonElement tsonElement : a) {
                        if (acceptArrayElement(tsonElement)) {
                            if (i > 0) {
                                sb2.append(",\n");
                            }
                            format(tsonElement, w);
                            i++;
                        }
                    }
                    break;
                }
                case PARAMS: {
                    for (TsonElement tsonElement : a) {
                        if (acceptParamElement(tsonElement)) {
                            if (i > 0) {
                                sb2.append(",\n");
                            }
                            format(tsonElement, w);
                            i++;
                        }
                    }
                    break;
                }
                case MATRIX: {
                    for (TsonElement tsonElement : a) {
//                        if (acceptParamElement(tsonElement)) {
                            if (i > 0) {
                                sb2.append(",\n");
                            }
                            format(tsonElement, w);
                            i++;
//                        }
                    }
                    if(i<2){
                        sb2.append(";\n");
                    }
                    break;
                }
            }
        }
        out.append(TsonUtils.indent(sb2.toString(), config.indent));
        out.append('\n').append(end);
    }

    private boolean acceptObjectElement(TsonElement tsonElement) {
        if (config.ignoreObjectNullFields) {
            if (tsonElement.isNull()) {
                return false;
            }
            if (tsonElement.getType() == TsonElementType.PAIR && tsonElement.toKeyValue().getValue().isNull()) {
                return false;
            }
        }
        if (config.ignoreObjectEmptyArrayFields) {
            if (tsonElement.getType() == TsonElementType.ARRAY && tsonElement.toArray().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void listToStringNotIndented(Iterable<TsonElement> it, char start, char end, Writer out, ListType type) throws
            IOException {
        out.append(start);
        int i = 0;
        switch (type) {
            case OBJECT: {
                for (TsonElement tsonElement : it) {
                    if (acceptObjectElement(tsonElement)) {
                        if (i > 0) {
                            out.append(',').append(config.afterComma);
                        }
                        format(tsonElement, out);
                        i++;
                    }
                }
                break;
            }
            case PARAMS: {
                for (TsonElement tsonElement : it) {
                    if (acceptParamElement(tsonElement)) {
                        if (i > 0) {
                            out.append(',').append(config.afterComma);
                        }
                        format(tsonElement, out);
                        i++;
                    }
                }
                break;
            }
            case ARRAY: {
                for (TsonElement tsonElement : it) {
                    if (acceptArrayElement(tsonElement)) {
                        if (i > 0) {
                            out.append(',').append(config.afterComma);
                        }
                        format(tsonElement, out);
                        i++;
                    }
                }
                break;
            }
            case MATRIX: {
                for (TsonElement tsonElement : it) {
                    //if (acceptArrayElement(tsonElement)) {
                    if (i > 0) {
                        out.append(';').append(config.afterComma);
                    }
                    format(tsonElement, out);
                    i++;
                    //}
                }
                if(i<2){
                    out.append(';').append(config.afterComma);
                }
                break;
            }
        }
        out.append(end);
    }

    private boolean acceptParamElement(TsonElement tsonElement) {
        return true;
    }

    private boolean acceptArrayElement(TsonElement tsonElement) {
        return true;
    }

    @Override
    public TsonFormatBuilder builder() {
        return new TsonFormatImplBuilder().setConfig(config);
    }

    private enum ListType {
        MATRIX,
        PARAMS,
        ARRAY,
        OBJECT,
    }
}
