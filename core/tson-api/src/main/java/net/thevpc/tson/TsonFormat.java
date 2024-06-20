package net.thevpc.tson;

public interface TsonFormat {
    String format(TsonElement element);

    String format(TsonDocument element);

    TsonFormatBuilder builder();
}
