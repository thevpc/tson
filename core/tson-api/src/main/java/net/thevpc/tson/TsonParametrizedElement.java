package net.thevpc.tson;

public interface TsonParametrizedElement extends TsonElement {
    boolean isParametrized();

    TsonElementList params();

    int paramsCount();
}
