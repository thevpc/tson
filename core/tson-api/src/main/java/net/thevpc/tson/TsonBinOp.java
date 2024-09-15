package net.thevpc.tson;

public interface TsonBinOp extends TsonElement {
    TsonElement second();
    TsonElement first();
    String op();
}
