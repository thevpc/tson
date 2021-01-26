package net.thevpc.tson.impl.format;

import net.thevpc.tson.TsonFormat;
import net.thevpc.tson.TsonFormatBuilder;
import net.thevpc.tson.*;

public class TsonFormatImplBuilder implements TsonFormatBuilder {

    private DefaultTsonFormatConfig config = new DefaultTsonFormatConfig();

    public TsonFormatImplBuilder() {
    }

    public TsonFormatImplBuilder setConfig(DefaultTsonFormatConfig config) {
        this.config = config.copy();
        return this;
    }

    @Override
    public TsonFormatBuilder setOption(String optionName, Object configValue) {
        config.set(optionName, configValue);
        return this;
    }

    @Override
    public TsonFormatBuilder setCompact(boolean compact) {
        config.setCompact(compact);
        return this;
    }

    @Override
    public boolean isCompact() {
        return config.isCompact();
    }

    @Override
    public TsonFormatBuilder option(String optionName, Object configValue) {
        return setOption(optionName,configValue);
    }

    @Override
    public Object getOption(String optionName) {
        return config.get(optionName);
    }

    @Override
    public TsonFormat build() {
        return new TsonFormatImpl(config);
    }
}
