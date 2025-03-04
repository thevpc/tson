package net.thevpc.tson;

public interface TsonElementHeader extends Comparable<TsonElementHeader> {

    String name();

    boolean isBlank();

    boolean isArgs();

    TsonElementList args();

    int size();

    boolean visit(TsonDocumentVisitor visitor);

    void visit(TsonParserVisitor visitor);
}
