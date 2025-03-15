package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonArrayBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;

public class TsonMatrixImpl extends AbstractNonPrimitiveTsonElement implements TsonMatrix {
    private final UnmodifiableArrayList<TsonArray> rows;
    private final String name;
    private TsonElementList args;

    public TsonMatrixImpl(String name, TsonElementList args, UnmodifiableArrayList<TsonArray> rows) {
        super(TsonElementType.MATRIX);
        this.name = name;
        this.args = args;
        this.rows = rows;
    }

    @Override
    public TsonMatrix toMatrix() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    @Override
    public int rowSize() {
        return rows.size();
    }

    public int columnSize() {
        return rows.size() == 0 ? 0 : rows.get(0).size();
    }

    @Override
    public List<TsonArray> rows() {
        return getRows();
    }

    @Override
    public TsonElement get(int col, int row) {
        TsonArray a = rows.get(row);
        return a.get(col);
    }

    @Override
    public TsonArray getRow(int row) {
        return rows.get(row);
    }

    @Override
    public List<TsonArray> getRows() {
        return rows;
    }

    @Override
    public TsonArray getColumn(int column) {
        List<TsonElement> c = new ArrayList<>(rowSize());
        for (int row = 0; row < rowSize(); row++) {
            c.add(TsonMatrixImpl.this.get(column, row));
        }
        return TsonUtils.toArray(c);
    }

    @Override
    public List<TsonArray> getColumns() {
        return new AbstractList<TsonArray>() {
            @Override
            public TsonArray get(int column) {
                return getColumn(column);
            }

            @Override
            public int size() {
                return columnSize();
            }
        };
    }

    @Override
    public Iterator<TsonArray> iterator() {
        return getRows().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonMatrixImpl that = (TsonMatrixImpl) o;
        return Objects.equals(rows, that.rows) &&
                Objects.equals(name, that.name()) &&
                Objects.equals(args, that.args())
                ;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), name, args, rows);
        result = 31 * result + Objects.hashCode(rows);
        return result;
    }

    @Override
    public TsonArrayBuilder builder() {
        return new TsonArrayBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (args != null) {
            for (TsonElement element : args) {
                if (!visitor.visit(element)) {
                    return false;
                }
            }
        }
        for (TsonElement element : rows) {
            if (!visitor.visit(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int compareCore(TsonElement o) {
        TsonMatrix na = o.toMatrix();
        int i = this.name().compareTo(na.name());
        if (i != 0) {
            return i;
        }
        i = TsonUtils.compareElementsArray(this.args(), na.args());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray((List) getRows(), (List) na.getRows());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        if (name != null) {
            visitor.visitNamedStart(this.name());
        }
        if (args != null) {
            visitor.visitParamsStart();
            for (TsonElement param : this.args()) {
                visitor.visitParamElementStart();
                param.visit(visitor);
                visitor.visitParamElementEnd();
            }
            visitor.visitParamsEnd();
        }
        visitor.visitNamedArrayStart();
        for (TsonArray element : getRows()) {
            visitor.visitArrayElementStart();
            element.visit(visitor);
            visitor.visitArrayElementEnd();
        }
        visitor.visitArrayEnd();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isWithArgs() {
        return args != null;
    }

    @Override
    public TsonElementList args() {
        return args;
    }
}
