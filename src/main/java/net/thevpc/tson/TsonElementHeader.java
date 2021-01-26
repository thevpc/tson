package net.thevpc.tson;

import java.util.List;

public interface TsonElementHeader extends Comparable<TsonElementHeader> {
    String name();

    String getName();

    List<TsonElement> all();

    List<TsonElement> getAll();

    int size();

    boolean visit(TsonDocumentVisitor visitor);

    void visit(TsonParserVisitor visitor);
}
