package net.thevpc.tson;

public interface TsonProcessor {
    TsonElement removeComments(TsonElement element);

    TsonElement resolveAliases(TsonElement element);
}
