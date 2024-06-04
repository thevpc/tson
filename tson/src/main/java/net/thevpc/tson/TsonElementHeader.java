package net.thevpc.tson;

import java.util.List;

public interface TsonElementHeader extends Comparable<TsonElementHeader> {

    String name();

    TsonElementList toElementList();

    List<TsonElement> all();


    int size();

    boolean visit(TsonDocumentVisitor visitor);

    void visit(TsonParserVisitor visitor);
}
