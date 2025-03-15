package net.thevpc.tson;

public interface TsonContainer extends TsonElement{
    TsonElementList body();
    TsonElementList args();
    int argsCount();
    boolean isNamed();
    String name();
}
