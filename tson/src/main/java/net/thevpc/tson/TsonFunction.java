package net.thevpc.tson;

import java.util.List;

public interface TsonFunction extends TsonContainer {
    List<TsonElement> all();

    int size();

    TsonFunctionBuilder builder();
}
