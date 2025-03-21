package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.util.Objects;

public class TsonStringImpl extends AbstractPrimitiveTsonElement implements TsonString {

    private TsonStringLayout layout;
    private String rawValue;
    private String value;

    public TsonStringImpl(String value, String rawValue, TsonStringLayout layout) {
        super(TsonElementType.STRING);
        this.value = value;
        this.rawValue = rawValue;
        this.layout = layout;
    }

    @Override
    public TsonBoolean toBoolean() {
        String svalue = String.valueOf(value).trim().toLowerCase();
        if (!svalue.isEmpty()) {
            if (svalue.matches("true|enable|enabled|yes|always|y|on|ok|t|o")) {
                return (TsonBoolean) Tson.of(true);
            }
            if (svalue.matches("false|disable|disabled|no|none|never|n|off|ko|f")) {
                return (TsonBoolean) Tson.of(false);
            }
        }
        return super.toBoolean();
    }

    @Override
    public String quoted() {
        switch (layout()) {
            case DOUBLE_QUOTE:
                return quoted("\"");
            case SINGLE_QUOTE:
                return quoted("'");
            case ANTI_QUOTE:
                return quoted("`");
            case TRIPLE_DOUBLE_QUOTE:
                return quoted("\"\"\"");
            case TRIPLE_SINGLE_QUOTE:
                return quoted("'''");
            case TRIPLE_ANTI_QUOTE:
                return quoted("```");
        }
        throw new IllegalArgumentException("unexpected");
    }

    private String quoted(String quotes) {
        String str = raw();
        if (quotes.length() == 1) {
            char c0 = quotes.charAt(0);
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (c == c0) {
                    sb.append('\\');
                    sb.append(c);
                } else if (c == '\'') {
                    sb.append('\\');
                    sb.append('\\');
                } else {
                    sb.append(c);
                }
            }
            sb.insert(0, c0);
            sb.append(c0);
            return sb.toString();
        }
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '\\'
                    && i + 3 < charArray.length
                    && charArray[i + 1] == quotes.charAt(0) && charArray[i + 2] == quotes.charAt(1) && charArray[i + 3] == quotes.charAt(2)) {
                sb.append("\\\\");
            } else if (i + 2 < charArray.length
                    && charArray[i + 0] == quotes.charAt(0) && charArray[i + 1] == quotes.charAt(1) && charArray[i + 2] == quotes.charAt(2)
            ) {
                sb.append("\\");
                sb.append(quotes);
                i += quotes.length() - 1;
            } else {
                sb.append(c);
            }
        }
        sb.insert(0, quotes);
        sb.append(quotes);
        return sb.toString();
    }

    public TsonStringLayout layout() {
        return layout;
    }

    @Override
    public TsonString toStr() {
        return this;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String raw() {
        return rawValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TsonStringImpl that = (TsonStringImpl) o;
        return Objects.equals(value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value());
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().copyFrom(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return value().compareTo(o.stringValue());
    }

    @Override
    public String stringValue() {
        return value();
    }
}
