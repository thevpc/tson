package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Objects;

public class TsonLocalTimeImpl extends AbstractTemporalTsonElement implements TsonLocalTime {
    private LocalTime value;

    public TsonLocalTimeImpl(LocalTime value) {
        super(TsonElementType.LOCAL_TIME);
        this.value = value;
    }

    public TsonLocalTimeImpl(String value) {
        super(TsonElementType.LOCAL_DATE);
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
    public TsonLocalDate toLocalDate() {
        return throwPrimitive(TsonElementType.LOCAL_DATE);
    }

    @Override
    public TsonLocalTime toLocalTime() {
        return this;
    }

    @Override public LocalTime value() {
        return value;
    }

    @Override
    public LocalDateTime localDateTimeValue() {
        return LocalDateTime.from(this.localTimeValue());
    }

    @Override
    public LocalDate localDateValue() {
        return LocalDate.from(this.localTimeValue());
    }

    @Override
    public LocalTime localTimeValue() {
        return value();
    }

    @Override
    public TsonLocalDateTime toLocalDateTime() {
        return (TsonLocalDateTime) Tson.of(this.localDateTimeValue());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TsonLocalTimeImpl tsonTime = (TsonLocalTimeImpl) o;
        return Objects.equals(value, tsonTime.value);
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
        return value.compareTo(o.toLocalTime().value());
    }

}
