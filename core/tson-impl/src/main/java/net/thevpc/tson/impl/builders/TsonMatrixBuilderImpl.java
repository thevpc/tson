package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonElementListImpl;
import net.thevpc.tson.impl.elements.TsonMatrixImpl;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TsonMatrixBuilderImpl extends AbstractTsonElementBuilder<TsonMatrixBuilder> implements TsonMatrixBuilder {
    private ArrayList<ArrayList<TsonElement>> rows = new ArrayList<>();
    private int rowsCount;
    private int columnsCount;
    private String name;
    private List<TsonElement> args = new ArrayList<>();

    public TsonMatrixBuilder ensureCapacity(int columns0, int rows0) {
        int oldColumnsCount = columnsCount;
        int oldRowsCount = rowsCount;

        columnsCount = columns0 = Math.max(columns0, columnsCount);
        rowsCount = rows0 = Math.max(rows0, rowsCount);

        TsonElement nullElem = Tson.ofNull();

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
    public TsonElementType type() {
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
        rows.add(new ArrayList<>(element.body().toList()));
        ensureCapacity(columnsCount, rowsCount + 1);
        return this;
    }

    @Override
    public TsonMatrixBuilder addRows(TsonArray... elements) {
        for (TsonArray element : elements) {
            rows.add(new ArrayList<>(element.body().toList()));
        }
        ensureCapacity(columnsCount, rowsCount + elements.length);
        return this;
    }

    @Override
    public TsonMatrixBuilder addRows(Iterable<? extends TsonArray> elements) {
        int count = 0;
        for (TsonArray element : elements) {
            rows.add(new ArrayList<>(element.body().toList()));
            count++;
        }
        ensureCapacity(columnsCount, rowsCount + count);
        return null;
    }

    @Override
    public TsonMatrixBuilder addColumn(TsonArray element) {
        ensureCapacity(columnsCount(), element.size());
        int i = 0;
        TsonElement nullElem = Tson.ofNull();
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
        rows.get(row).set(column, Tson.of(element));
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
        name = null;
        args = null;
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
        TsonMatrixImpl built = new TsonMatrixImpl(name,
                args == null ? null : new TsonElementListImpl((List) args),
                TsonUtils.unmodifiableArrays(arrays)
        );
        return (TsonMatrix) TsonUtils.decorate(
                built
                , getComments(), getAnnotations())
                ;
    }


    @Override
    public TsonMatrixBuilder merge(TsonElementBase element) {
        TsonElement e = Tson.of(element);
        switch (e.type()) {
            case UPLET: {
                TsonUplet uplet = e.toUplet();
                if (uplet.isNamed()) {
                    name(uplet.name());
                }
                addArgs(uplet);
                break;
            }
            case NAME: {
                name(e.toName().value());
                break;
            }
            case OBJECT: {
                TsonObject h = e.toObject();
                name(h.name());
                addArgs(h.args());
                addRow(TsonUtils.toArray(e.toObject().body()));
                break;
            }
            case ARRAY: {
                TsonArray h = e.toArray();
                name(h.name());
                addArgs(h.args());
                addRow(TsonUtils.toArray(e.toArray().body()));
                break;
            }
            case MATRIX: {
                TsonMatrix h = e.toMatrix();
                name(h.name());
                addArgs(h.args());
                for (TsonArray m : e.toMatrix()) {
                    addRow(m);
                }
                break;
            }
        }
        return this;
    }


    /// ////////////////
    /// args

    @Override
    public boolean isWithArgs() {
        return args != null;
    }

    @Override
    public TsonMatrixBuilder setWithArgs(boolean hasArgs) {
        if (hasArgs) {
            if (args == null) {
                args = new ArrayList<>();
            }
        } else {
            args = null;
        }
        return this;
    }

    @Override
    public List<TsonElement> args() {
        return args;
    }

    @Override
    public int argsCount() {
        return args == null ? 0 : args.size();
    }

    @Override
    public TsonMatrixBuilder clearArgs() {
        args.clear();
        return this;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public TsonMatrixBuilder name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TsonMatrixBuilder addArg(TsonElementBase element) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder removeArg(TsonElementBase element) {
        if (element != null && args != null) {
            args.remove(Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder addArg(TsonElementBase element, int index) {
        if (element != null) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(index, Tson.of(element).build());
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder removeArgAt(int index) {
        if (args != null) {
            args.remove(index);
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder addArgs(TsonElement[] element) {
        if (element != null) {
            for (TsonElement tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder addArgs(TsonElementBase[] element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    @Override
    public TsonMatrixBuilder addArgs(Iterable<? extends TsonElementBase> element) {
        if (element != null) {
            for (TsonElementBase tsonElement : element) {
                addArg(tsonElement);
            }
        }
        return this;
    }

    /// ////////////////


}
