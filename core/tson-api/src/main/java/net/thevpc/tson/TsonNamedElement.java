package net.thevpc.tson;

public interface TsonNamedElement extends TsonElement {
    boolean isNamed();

    String name();
}
