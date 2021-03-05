package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonMatrixImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TsonMatrixBuilderImpl extends AbstractTsonElementBuilder<TsonMatrixBuilder> implements TsonMatrixBuilder {
    private ArrayList<ArrayList<TsonElement>> rows = new ArrayList<>();
    private TsonElementHeaderBuilderImpl<TsonMatrixBuilder> header = new TsonElementHeaderBuilderImpl(this);
    private int rowsCount;
    private int columnsCount;

    public TsonMatrixBuilder ensureCapacity(int columns0, int rows0) {
        int oldColumnsCount = columnsCount;
        int oldRowsCount = rowsCount;

        columnsCount = columns0 = Math.max(columns0, columnsCount);
        rowsCount = rows0 = Math.max(rows0, rowsCount);

        TsonElement nullElem = Tson.nullElem();

        if (columnsCount != oldColumnsCount) {
            for (int i = 0; i < rowsCount; i++) {
                ArrayList<TsonElement> row = rows.get(i);
                row.ensureCapacity(columns0);
                while (row.size() < columns0) {
                    row.add(nullElem);
                }
            }
        }
        if (rowsCount != oldRowsCount) {
            while (rowsCount < rows0) {
                List<TsonElement> row = new ArrayList<>(columns0);
                for (int i = 0; i < columns0; i++) {
                    row.add(nullElem);
                }
            }
        }
        return this;
    }

    @Override
    public TsonElementHeaderBuilder<TsonMatrixBuilder> header() {
        return getHeader();
    }

    @Override
    public TsonElementHeaderBuilder<TsonMatrixBuilder> getHeader() {
        return header;
    }

    @Override
    public TsonElementType getType() {
        return TsonElementType.MATRIX;
    }

    @Override
    public TsonElement get(int column, int row) {
        return rows.get(row).get(column);
    }

    @Override
    public TsonArray getRow(int row) {
        return TsonUtils.toArray(rows.get(row));
    }

    @Override
    public TsonArray getColumn(int column) {
        List<TsonElement> col = new ArrayList<>(rowsCount());
        for (int i = 0; i < rowsCount(); i++) {
            col.add(rows.get(i).get(column));
        }
        return TsonUtils.toArray(col);
    }

    @Override
    public TsonMatrixBuilder addRow(TsonArray element) {
        rows.add(new ArrayList<>(element.getAll()));
        ensureCapacity(columnsCount, rowsCount + 1);
        return this;
    }

    @Override
    public TsonMatrixBuilder addRows(TsonArray... elements) {
        for (TsonArray element : elements) {
            rows.add(new ArrayList<>(element.getAll()));
        }
        ensureCapacity(columnsCount, rowsCount + elements.length);
        return this;
    }

    @Override
    public TsonMatrixBuilder addRows(Iterable<? extends TsonArray> elements) {
        int count = 0;
        for (TsonArray element : elements) {
            rows.add(new ArrayList<>(element.getAll()));
            count++;
        }
        ensureCapacity(columnsCount, rowsCount + count);
        return null;
    }

    @Override
    public TsonMatrixBuilder addColumn(TsonArray element) {
        ensureCapacity(columnsCount(), element.size());
        int i = 0;
        TsonElement nullElem = Tson.nullElem();
        for (ArrayList<TsonElement> row : rows) {
            if (i < element.size()) {
                row.add(element.get(i));
            } else {
                row.add(nullElem);
            }
            i++;
        }
        ensureCapacity(columnsCount() + 1, rowsCount());
        return this;
    }

    @Override
    public TsonMatrixBuilder addColumns(TsonArray... elements) {
        for (TsonArray element : elements) {
            addColumn(element);
        }
        ensureCapacity(columnsCount() + elements.length, rowsCount());
        return this;
    }


    @Override
    public TsonMatrixBuilder addColumns(Iterable<? extends TsonArray> elements) {
        int count = 0;
        for (TsonArray element : elements) {
            addColumn(element);
            count++;
        }
        ensureCapacity(columnsCount() + count, rowsCount());
        return this;
    }


    @Override
    public List<TsonArray> rows() {
        return getRows();
    }

    @Override
    public List<TsonArray> getRows() {
        return new AbstractList<TsonArray>() {
            @Override
            public TsonArray get(int index) {
                return getRow(index);
            }

            @Override
            public int size() {
                return columnsCount();
            }
        };
    }

    @Override
    public TsonMatrixBuilder removeRow(int row) {
        if (row >= 0 && row < rowsCount) {
            rows.remove(row);
            rowsCount--;
        } else {
            throw new ArrayIndexOutOfBoundsException(row);
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder removeColumn(int column) {
        if (column >= 0 && column < columnsCount) {
            for (ArrayList<TsonElement> row : rows) {
                row.remove(column);
            }
            columnsCount--;
        } else {
            throw new ArrayIndexOutOfBoundsException(column);
        }
        return this;
    }

    @Override
    public int columnsCount() {
        return columnsCount;
    }

    @Override
    public int rowsCount() {
        return columnsCount;
    }

    @Override
    public TsonMatrixBuilder set(TsonElementBase element, int column, int row) {
        ensureCapacity(column, row);
        rows.get(row).set(column, Tson.elem(element));
        return this;
    }

    @Override
    public Iterator<TsonArray> iterator() {
        Iterator<ArrayList<TsonElement>> t = rows.iterator();
        return new Iterator<TsonArray>() {
            @Override
            public boolean hasNext() {
                return t.hasNext();
            }

            @Override
            public TsonArray next() {
                return TsonUtils.toArray(t.next());
            }
        };
    }

    @Override
    public TsonMatrixBuilder reset() {
        rows.clear();
        header.reset();
        rowsCount = 0;
        columnsCount = 0;
        return this;
    }


    @Override
    public TsonMatrix build() {
        List<TsonArray> arrays = new ArrayList<>();
        for (ArrayList<TsonElement> row : rows) {
            arrays.add(TsonUtils.toArray(row));
        }
        TsonMatrixImpl built = new TsonMatrixImpl(header.build(),
                TsonUtils.unmodifiableArrays(arrays)
        );
        return (TsonMatrix) TsonUtils.decorate(
                built
                , getComments(), getAnnotations())
                ;
    }


    @Override
    public TsonMatrixBuilder merge(TsonElementBase element) {
        TsonElement e = Tson.elem(element);
        switch (e.getType()) {
            case UPLET: {
                header.addAll(e.toUplet());
                break;
            }
            case FUNCTION: {
                header.setName(e.toFunction().getName());
                header.addAll(e.toUplet());
                break;
            }
            case NAME: {
                header.setName(e.toName().getName());
                break;
            }
            case OBJECT: {
                TsonElementHeader h = e.toObject().getHeader();
                this.header.set(h);
                addRow(TsonUtils.toArray(e.toObject().getAll()));
                break;
            }
            case ARRAY: {
                TsonElementHeader h = e.toArray().getHeader();
                this.header.set(h);
                addRow(TsonUtils.toArray(e.toObject().getAll()));
                break;
            }
            case MATRIX: {
                TsonElementHeader h = e.toArray().getHeader();
                this.header.set(h);
                for (TsonArray m : e.toMatrix()) {
                    addRow(m);
                }
                break;
            }
        }
        return this;
    }
}
