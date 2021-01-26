package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.*;

public class TsonNamedAnyBuilderSupport extends TsonParamsBuilderSupport {
    private String name;


    public void reset() {
        super.reset();
        name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set(TsonElementBase element0) {
        TsonElement element = Tson.elem(element0);
        switch (element.getType()) {
            case ARRAY: {
                TsonElementHeader narr = element.toArray().getHeader();
                setName(narr.getName());
                addParams(narr.getAll());
                return;
            }
            case OBJECT: {
                TsonElementHeader nobj = element.toObject().getHeader();
                setName(nobj.getName());
                addParams(nobj.getAll());
                return;
            }
            case FUNCTION: {
                TsonFunction fct = element.toFunction();
                setName(fct.getName());
                addParams(fct.getAll());
                return;
            }
            case UPLET: {
                addParams(element.toUplet().getAll());
                return;
            }
        }
        throw new IllegalArgumentException("Unsupported copy from " + element.getType());
    }
}
