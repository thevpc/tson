package net.thevpc.tson;

public interface TsonOp extends TsonElement {
    TsonOpType opType();

    TsonElement second();

    TsonElement first();

    String opName();

    TsonOpBuilder builder();
}
