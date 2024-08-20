package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.util.Objects;

public class TsonDateImpl extends AbstractTemporalTsonElement implements TsonDate {
    private LocalDate value;

    public TsonDateImpl(LocalDate value) {
        super(TsonElementType.DATE);
        this.value = value;
    }

    public TsonDateImpl(String value) {
        super(TsonElementType.DATE);
        this.value = LocalDate.parse(value);
    }

    @Override
    public Temporal temporalValue() {
        return value;
    }

    @Override
    public TsonElement build() {
        return (TsonString) Tson.of(String.valueOf(value));
    }
    @Override
    public TsonString toStr() {
        return (TsonString) Tson.of(String.valueOf(value));
    }

    @Override
    public TsonTime toTime() {
        return (TsonTime) Tson.ofTime(this.time());
    }

    @Override
    public TsonDateTime toDateTime() {
        return (TsonDateTime) Tson.of(dateTimeValue());
    }

    @Override
    public LocalDate dateValue() {
        return value();
    }

    @Override
    public LocalTime time() {
        return LocalTime.from(value());
    }

    @Override
    public Instant dateTimeValue() {
        return value().atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    @Override
    public TsonDate toDate() {
        return this;
    }

    @Override
    public LocalDate value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonDateImpl tsonDate = (TsonDateImpl) o;
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
        return value.compareTo(o.toDate().value());
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
                    int i = value().compareTo(o.dateValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
                case TIME: {
                    int i = value().compareTo(o.dateValue());
                    return i == 0 ? type().compareTo(o.type()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
