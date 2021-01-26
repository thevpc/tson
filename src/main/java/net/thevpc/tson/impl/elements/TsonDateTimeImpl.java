package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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
    public TsonDateTime toDateTime() {
        return this;
    }

    @Override
    public Instant getDateTime() {
        return getValue();
    }

    @Override
    public TsonTime toTime() {
        return (TsonTime) Tson.time(getTime());
    }

    @Override
    public LocalDate getDate() {
        return getValue().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public LocalTime getTime() {
        return LocalTime.from(getValue());
    }

    @Override
    public TsonDate toDate() {
        return (TsonDate) Tson.date(getDate());
    }

    @Override
    public Instant getValue() {
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
        return value.compareTo(o.toDateTime().getValue());
    }

    @Override
    public int compareTo(TsonElement o) {
        if (o.getType().isTemporal()) {
            switch (o.getType()) {
                case DATETIME: {
                    int i = getValue().compareTo(o.getDateTime());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case DATE: {
                    int i = getValue().compareTo(o.getDateTime());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case TIME: {
                    int i = getValue().compareTo(o.getDateTime());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
