package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonNumber;
import net.thevpc.tson.TsonNumberLayout;

public abstract class AbstractNumberTsonElement extends AbstractPrimitiveTsonElement implements TsonNumber{
    private TsonNumberLayout layout;
    public AbstractNumberTsonElement(TsonElementType type,TsonNumberLayout layout) {
        super(type);
        this.layout=layout;
    }

    @Override
    public TsonNumber toNumber() {
        return this;
    }

    public TsonNumberLayout layout() {
        return layout;
    }
}
