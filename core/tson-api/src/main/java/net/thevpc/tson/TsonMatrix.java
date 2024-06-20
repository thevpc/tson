package net.thevpc.tson;

import java.util.List;

public interface TsonMatrix extends TsonElement, Iterable<TsonArray> {
    TsonElementHeader getHeader();

    boolean isEmpty();

    int rowSize();

    List<TsonArray> rows();

    TsonElement get(int col, int row);

    TsonArray getRow(int row);

    List<TsonArray> getRows();

    TsonArray getColumn(int column);

    List<TsonArray> getColumns();

    TsonArrayBuilder builder();
}
