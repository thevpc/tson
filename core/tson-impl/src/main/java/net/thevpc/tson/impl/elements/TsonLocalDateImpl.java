package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.*;
import java.time.temporal.Temporal;
import java.util.Objects;

public class TsonLocalDateImpl extends AbstractTemporalTsonElement implements TsonLocalDate {
    private LocalDate value;

    public TsonLocalDateImpl(LocalDate value) {
        super(TsonElementType.LOCAL_DATE);
        this.value = value;
    }

    public TsonLocalDateImpl(String value) {
        super(TsonElementType.LOCAL_DATE);
        this.value = LocalDate.parse(value);
    }

    @Override
    public Temporal temporalValue() {
        return value;
    }

    @Override
    public TsonString toStr() {
        return (TsonString) Tson.of(String.valueOf(value));
    }

    @Override
    public TsonLocalTime toLocalTime() {
        return (TsonLocalTime) Tson.ofLocalTime(this.localTimeValue());
    }

    @Override
    public TsonLocalDateTime toLocalDateTime() {
        return (TsonLocalDateTime) Tson.of(this.localDateTimeValue());
    }

    @Override
    public LocalDate localDateValue() {
        return value();
    }

    @Override
    public LocalTime localTimeValue() {
        return LocalTime.from(value());
    }

    @Override
    public LocalDateTime localDateTimeValue() {
        return value().atStartOfDay();
    }

    @Override
    public TsonLocalDate toLocalDate() {
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
        TsonLocalDateImpl tsonDate = (TsonLocalDateImpl) o;
        return Objects.equals(value, tsonDate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public TsonPrimitiveBuilder builder() {
        return new TsonPrimitiveElementBuilderImpl().copyFrom(this);
    }

    @Override
    protected int compareCore(TsonElement o) {
        return value.compareTo(o.toLocalDate().value());
    }


}
