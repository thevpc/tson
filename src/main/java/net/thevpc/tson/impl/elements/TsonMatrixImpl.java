package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonArrayBuilderImpl;
import net.thevpc.tson.impl.util.TsonUtils;
import net.thevpc.tson.impl.util.UnmodifiableArrayList;

import java.util.*;

public class TsonMatrixImpl extends AbstractNonPrimitiveTsonElement implements TsonMatrix {
    private final UnmodifiableArrayList<TsonArray> rows;
    private final TsonElementHeader header;

    public TsonMatrixImpl(TsonElementHeader header, UnmodifiableArrayList<TsonArray> rows) {
        super(TsonElementType.MATRIX);
        this.header = header;
        this.rows = rows;
    }

    @Override
    public TsonElementHeader getHeader() {
        return header;
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
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), header);
        result = 31 * result + Objects.hashCode(rows);
        return result;
    }

    @Override
    public TsonArrayBuilder builder() {
        return new TsonArrayBuilderImpl().merge(this);
    }

    @Override
    public boolean visit(TsonDocumentVisitor visitor) {
        if (header != null) {
            if (!visitor.visit(header, this)) {
                return false;
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
        int i = TsonUtils.compareHeaders(header, na.getHeader());
        if (i != 0) {
            return i;
        }
        return TsonUtils.compareElementsArray((List) getRows(), (List) na.getRows());
    }

    @Override
    public void visit(TsonParserVisitor visitor) {
        visitor.visitElementStart();
        if (header != null) {
            header.visit(visitor);
        }
        visitor.visitNamedArrayStart();
        for (TsonArray element : getRows()) {
            visitor.visitArrayElementStart();
            element.visit(visitor);
            visitor.visitArrayElementEnd();
        }
        visitor.visitArrayEnd();
    }

}
