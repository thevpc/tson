package net.thevpc.tson;

public interface TsonFormatBuilder {
    TsonFormatBuilder setCompact(boolean compact);

    boolean isCompact();

    TsonFormatBuilder option(String optionName, Object configValue);

    TsonFormatBuilder setOption(String optionName, Object configValue);

    Object getOption(String optionName);

    TsonFormat build();
}
