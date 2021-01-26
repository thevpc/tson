package net.thevpc.tson.impl.builders;

import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementBase;
import net.thevpc.tson.TsonElementHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsonParamsBuilderSupport {
    private List<TsonElement> params = new ArrayList<>();


    public List<TsonElement> getParams() {
        return Collections.unmodifiableList(params);
    }

    public void removeAllParams() {
        params.clear();
    }

    public void reset() {
        params.clear();
    }

    public void addParam(TsonElementBase element) {
        params.add(Tson.elem(element));
    }

    public void removeParam(TsonElementBase element) {
        params.remove(Tson.elem(element));
    }

    public void addParam(TsonElementBase element, int index) {
        params.add(index, Tson.elem(element));
    }

    public void removeParamAt(int index) {
        params.remove(index);
    }

    public void addParams(TsonElement... elements) {
        if (elements != null) {
            for (TsonElement tsonElement : elements) {
                addParam(tsonElement);
            }
        }
    }

    public void addParams(TsonElementBase[] elements) {
        if (elements != null) {
            for (TsonElementBase tsonElement : elements) {
                addParam(tsonElement);
            }
        }
    }

    public void addParams(Iterable<? extends TsonElementBase> elements) {
        if (elements != null) {
            for (TsonElementBase tsonElement : elements) {
                addParam(tsonElement);
            }
        }
    }

    public void set(TsonElementBase element0) {
        TsonElement element = Tson.elem(element0);
        switch (element.getType()) {
            case ARRAY: {
                TsonElementHeader h = element.toArray().getHeader();
                if (h != null) {
                    addParams(h.getAll());
                }
                return;
            }
            case OBJECT: {
                TsonElementHeader h = element.toObject().getHeader();
                if (h != null) {
                    addParams(h.getAll());
                }
                return;
            }
            case FUNCTION: {
                addParams(element.toFunction().getAll());
                return;
            }
            case UPLET: {
                addParams(element.toUplet().getAll());
                return;
            }
        }
    }
}
