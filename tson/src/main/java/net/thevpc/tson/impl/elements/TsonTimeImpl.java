package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TsonTimeImpl extends AbstractTemporalTsonElement implements TsonTime {
    private LocalTime value;

    public TsonTimeImpl(LocalTime value) {
        super(TsonElementType.TIME);
        this.value = value;
    }

    public TsonTimeImpl(String value) {
        super(TsonElementType.DATE);
        this.value = LocalTime.parse(value);
    }

    @Override
    public TsonDate toDate() {
        return throwPrimitive(TsonElementType.DATE);
    }

    @Override
    public TsonTime toTime() {
        return this;
    }

    @Override public LocalTime getValue() {
        return value;
    }

    @Override
    public Instant getDateTime() {
        return Instant.from(getTime());
    }

    @Override
    public LocalDate getDate() {
        return LocalDate.from(getTime());
    }

    @Override
    public LocalTime getTime() {
        return getValue();
    }

    @Override
    public TsonDateTime toDateTime() {
        return (TsonDateTime) Tson.elem(getDateTime());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonTimeImpl tsonTime = (TsonTimeImpl) o;
        return Objects.equals(value, tsonTime.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().set(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return value.compareTo(o.toTime().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.getType().isTemporal()) {
            switch (o.getType()) {
                case DATETIME: {
                    int i = getDateTime().compareTo(o.getDateTime());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case DATE: {
                    int i = getDate().compareTo(o.getDate());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case TIME: {
                    int i = getValue().compareTo(o.getTime());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
