package net.thevpc.tson.impl.elements;

import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonNumber;
import net.thevpc.tson.TsonNumberLayout;

public abstract class AbstractNumberTsonElement extends AbstractPrimitiveTsonElement implements TsonNumber{
    private TsonNumberLayout layout;
    private String unit;
    public AbstractNumberTsonElement(TsonElementType type,TsonNumberLayout layout,String unit) {
        super(type);
        this.layout=layout==null?TsonNumberLayout.DECIMAL : layout;
        if(unit!=null){
            unit=unit.trim();
        }
        this.unit=unit==null?null:unit.isEmpty()?null:unit;
    }

    @Override
    public TsonNumber toNumber() {
        return this;
    }

    public TsonNumberLayout layout() {
        return layout;
    }

    @Override
    public String unit() {
        return unit;
    }
}
