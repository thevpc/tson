package net.thevpc.tson;

public interface TsonStreamParser {
    Object source();

    void setConfig(TsonStreamParserConfig config);

    void parseElement() ;

    void parseDocument() ;
}
