package net.thevpc.tson;

import java.util.List;

public interface TsonFunction extends TsonElement {
    String name();

    String getName();

    List<TsonElement> all();

    List<TsonElement> getAll();

    int size();

    TsonFunctionBuilder builder();
}
