package net.thevpc.tson;

public interface TsonDocumentBuilder {
    TsonDocumentHeader getHeader();

    TsonDocumentHeader header();

    TsonDocumentBuilder setHeader(TsonDocumentHeader header);

    TsonDocumentBuilder header(TsonDocumentHeader header);

    TsonElement getValue();

    TsonElement content();

    TsonDocumentBuilder setValue(TsonElementBase value);

    TsonDocumentBuilder content(TsonElementBase value);

    TsonDocument build();

    String toString();

    String toString(boolean compact);

    String toString(TsonFormat format);

}
