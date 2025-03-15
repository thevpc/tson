package net.thevpc.tson;

import java.util.Collection;
import java.util.List;

public interface TsonMatrixBuilder extends Iterable<TsonArray>, TsonElementBuilder {
    TsonMatrixBuilder reset();

    TsonMatrixBuilder merge(TsonElementBase element);

    TsonMatrixBuilder addRows(TsonArray... element);

    TsonMatrixBuilder addRows(Iterable<? extends TsonArray> element);

    TsonMatrixBuilder addRow(TsonArray element);

    TsonMatrixBuilder addColumns(TsonArray... element);

    TsonMatrixBuilder addColumns(Iterable<? extends TsonArray> element);

    TsonMatrixBuilder addColumn(TsonArray element);

    int columnsCount();

    int rowsCount();

    TsonMatrixBuilder set(TsonElementBase element, int column, int row);

    TsonMatrixBuilder removeColumn(int column);

    TsonMatrixBuilder removeRow(int row);

    TsonMatrix build();

    List<TsonArray> rows();

    TsonArray getRow(int row);

    TsonArray getColumn(int column);

    List<TsonArray> getRows();

    TsonElement get(int column, int row);

    ////////////////////////////////////////////////

    TsonMatrixBuilder comments(TsonComments comments);

    TsonMatrixBuilder setComments(TsonComments comments);

    TsonMatrixBuilder setAnnotations(TsonAnnotation... annotations);

    TsonMatrixBuilder addAnnotations(TsonAnnotation... annotations);

    TsonMatrixBuilder addAnnotations(Collection<TsonAnnotation> annotations);

    TsonMatrixBuilder annotation(String name, TsonElementBase... elements);

    TsonMatrixBuilder addAnnotation(String name, TsonElementBase... elements);

    TsonMatrixBuilder addAnnotation(TsonAnnotation annotation);

    TsonMatrixBuilder removeAnnotationAt(int index);

    TsonMatrixBuilder removeAllAnnotations();

    TsonMatrixBuilder ensureCapacity(int columns,int rows);

    /// ///////////////////////////////////////////////


    boolean isWithArgs();

    TsonMatrixBuilder setWithArgs(boolean hasArgs);

    List<TsonElement> args();

    int argsCount();

    TsonMatrixBuilder clearArgs();

    String name();

    TsonMatrixBuilder name(String name);

    TsonMatrixBuilder addArg(TsonElementBase element);

    TsonMatrixBuilder removeArg(TsonElementBase element);

    TsonMatrixBuilder addArg(TsonElementBase element, int index);

    TsonMatrixBuilder removeArgAt(int index);

    TsonMatrixBuilder addArgs(TsonElement[] element);

    TsonMatrixBuilder addArgs(TsonElementBase[] element);

    TsonMatrixBuilder addArgs(Iterable<? extends TsonElementBase> element);

}
