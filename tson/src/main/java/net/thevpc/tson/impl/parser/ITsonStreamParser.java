package net.thevpc.tson.impl.parser;

public interface ITsonStreamParser {
    Object source();

    void setConfig(TsonStreamParserImplConfig config);

    void parseElement() throws Exception;

    void parseDocument() throws Exception;
}
