package net.thevpc.tson.impl.parser;

import net.thevpc.tson.TsonParserVisitor;

public class TsonStreamParserImplConfig {
    private boolean skipComments=false;
    private boolean skipHeader=false;
    private boolean rawComments=false;
    private TsonParserVisitor visitor= EmptyTsonParserVisitor.INSTANCE;

    public boolean isSkipComments() {
        return skipComments;
    }

    public TsonStreamParserImplConfig setSkipComments(boolean skipComments) {
        this.skipComments = skipComments;
        return this;
    }

    public boolean isSkipHeader() {
        return skipHeader;
    }

    public TsonStreamParserImplConfig setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
        return this;
    }

    public TsonParserVisitor getVisitor() {
        return visitor;
    }

    public TsonStreamParserImplConfig setVisitor(TsonParserVisitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public boolean isRawComments() {
        return rawComments;
    }

    public TsonStreamParserImplConfig setRawComments(boolean rawComments) {
        this.rawComments = rawComments;
        return this;
    }
}
