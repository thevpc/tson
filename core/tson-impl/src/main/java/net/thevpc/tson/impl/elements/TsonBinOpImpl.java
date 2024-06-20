package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonBinOpBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.Objects;

public class TsonBinOpImpl extends AbstractNonPrimitiveTsonElement implements TsonBinOp {
    private String op;
    private TsonElement key;
    private TsonElement value;

    public TsonBinOpImpl(String op,TsonElement key, TsonElement value) {
        super(TsonElementType.BINOP);
        if (op == null) {
            throw new IllegalArgumentException("op cannot be null. Try to use NULL Tson element");
        }
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null. Try to use NULL Tson element");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null. Try to use NULL Tson element");
        }
        if (key.type() == TsonElementType.PAIR) {
            throw new IllegalArgumentException("Key of Key Value cannot be a key value as well");
        }
        if (value.type() == TsonElementType.PAIR) {
            throw new IllegalArgumentException("Key of Key Value cannot be a key value as well");
        }
        this.op=op;
        this.key = key;
        this.value = value;
    }

    @Override
    public String op() {
        return op;
    }

    @Override
    public TsonBinOp toBinOp() {
        return this;
    }

    @Override
    public TsonElement getSecond() {
        return value;
    }

    @Override
    public TsonElement getFirst() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonBinOpImpl that = (TsonBinOpImpl) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, value);
    }

    @Override
    public TsonBinOpBuilder builder() {
        return new TsonBinOpBuilderImpl().setFirst(getFirst()).setSecond(getSecond());
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
        TsonPair oo = o.toPair();
        return TsonUtils.compareElementsArray(
                new TsonElement[]{getFirst(), getSecond()},
                new TsonElement[]{oo.getKey(), oo.getValue()}
        );
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitInstructionStart();
        getFirst().visit(visitor);
        getSecond().visit(visitor);
        visitor.visitKeyValueEnd();
    }
}
