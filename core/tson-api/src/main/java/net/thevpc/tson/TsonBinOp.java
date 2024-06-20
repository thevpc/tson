package net.thevpc.tson;

public interface TsonBinOp extends TsonElement {
    TsonElement getSecond();
    TsonElement getFirst();
    String op();
}
