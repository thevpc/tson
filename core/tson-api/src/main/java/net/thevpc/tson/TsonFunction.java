package net.thevpc.tson;

import java.util.List;

public interface TsonFunction extends TsonContainer {
    int size();
    TsonFunctionBuilder builder();
}
