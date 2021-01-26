package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonKeyValueBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.Objects;

public class TsonPairImpl extends AbstractNonPrimitiveTsonElement implements TsonPair {
    private TsonElement key;
    private TsonElement value;

    public TsonPairImpl(TsonElement key, TsonElement value) {
        super(TsonElementType.PAIR);
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null. Try to use NULL Tson element");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null. Try to use NULL Tson element");
        }
        if (key.getType() == TsonElementType.PAIR) {
            throw new IllegalArgumentException("Key of Key Value cannot be a key value as well");
        }
        if (value.getType() == TsonElementType.PAIR) {
            throw new IllegalArgumentException("Key of Key Value cannot be a key value as well");
        }
        this.key = key;
        this.value = value;
    }

    @Override
    public TsonPair toKeyValue() {
        return this;
    }

    @Override
    public TsonElement getValue() {
        return value;
    }

    @Override
    public TsonElement getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonPairImpl that = (TsonPairImpl) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, value);
    }

    @Override
    public TsonKeyValueBuilder builder() {
        return new TsonKeyValueBuilderImpl().setKey(getKey()).setValue(getValue());
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (visitor.visit(this)) {
            if (!key.visit(visitor)) {
                return false;
            }
            if (!value.visit(visitor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int compareCore(TsonElement o) {
        TsonPair oo = o.toKeyValue();
        return TsonUtils.compareElementsArray(
                new TsonElement[]{getKey(), getValue()},
                new TsonElement[]{oo.getKey(), oo.getValue()}
        );
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitInstructionStart();
        getKey().visit(visitor);
        getValue().visit(visitor);
        visitor.visitKeyValueEnd();
    }
}
