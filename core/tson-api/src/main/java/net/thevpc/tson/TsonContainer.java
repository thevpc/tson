package net.thevpc.tson;

public interface TsonContainer extends TsonElement{
    TsonElementList body();
    TsonElementList args();
    String name();
}
