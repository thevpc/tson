package net.thevpc.tson;

public interface TsonCustom extends TsonElement {
    Object value();
    TsonCustomBuilder builder();
}
