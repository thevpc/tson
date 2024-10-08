package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Objects;

public class TsonDateTimeImpl extends AbstractTemporalTsonElement implements TsonDateTime {
    private Instant value;

    public TsonDateTimeImpl(Instant value) {
        super(TsonElementType.DATETIME);
        this.value = value;
    }

    public TsonDateTimeImpl(String value) {
        super(TsonElementType.DATE);
        this.value = Instant.parse(value);
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
    public TsonDateTime toDateTime() {
        return this;
    }

    @Override
    public Instant dateTimeValue() {
        return value();
    }

    @Override
    public TsonTime toTime() {
        return (TsonTime) Tson.ofTime(time());
    }

    @Override
    public LocalDate dateValue() {
        return value().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public LocalTime time() {
        return LocalTime.from(value());
    }

    @Override
    public TsonDate toDate() {
        return (TsonDate) Tson.ofDate(dateValue());
    }

    @Override
    public Instant value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonDateTimeImpl tsonDate = (TsonDateTimeImpl) o;
        return Objects.equals(value, tsonDate.value);
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
        return value.compareTo(o.toDateTime().value());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.type().isTemporal()) {
            switch (o.type()) {
                case DATETIME: {
                    int i = value().compareTo(o.dateTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case DATE: {
                    int i = value().compareTo(o.dateTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case TIME: {
                    int i = value().compareTo(o.dateTimeValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
