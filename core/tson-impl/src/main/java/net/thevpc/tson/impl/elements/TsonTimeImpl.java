package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
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
    public TsonString toStr() {
        return (TsonString) Tson.of(String.valueOf(value));
    }
    @Override
    public Temporal temporalValue() {
        return value;
    }

    @Override
    public TsonDate toDate() {
        return throwPrimitive(TsonElementType.DATE);
    }

    @Override
    public TsonTime toTime() {
        return this;
    }

    @Override public LocalTime value() {
        return value;
    }

    @Override
    public Instant dateTimeValue() {
        return Instant.from(this.time());
    }

    @Override
    public LocalDate dateValue() {
        return LocalDate.from(this.time());
    }

    @Override
    public LocalTime time() {
        return value();
    }

    @Override
    public TsonDateTime toDateTime() {
        return (TsonDateTime) Tson.of(dateTimeValue());
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
        return value.compareTo(o.toTime().value());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.type().isTemporal()) {
            switch (o.type()) {
                case DATETIME: {
                    int i = dateTimeValue().compareTo(o.dateTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case DATE: {
                    int i = dateValue().compareTo(o.dateValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case TIME: {
                    int i = value().compareTo(o.time());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
