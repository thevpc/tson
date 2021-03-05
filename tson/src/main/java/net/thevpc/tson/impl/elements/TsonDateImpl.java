package net.thevpc.tson.impl.elements;

import net.thevpc.tson.*;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.builders.TsonPrimitiveElementBuilderImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
    public TsonTime toTime() {
        return (TsonTime) Tson.time(getTime());
    }

    @Override
    public TsonDateTime toDateTime() {
        return (TsonDateTime) Tson.elem(getDateTime());
    }

    @Override
    public LocalDate getDate() {
        return getValue();
    }

    @Override
    public LocalTime getTime() {
        return LocalTime.from(getValue());
    }

    @Override
    public Instant getDateTime() {
        return getValue().atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    @Override
    public TsonDate toDate() {
        return this;
    }

    @Override
    public LocalDate getValue() {
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
        return value.compareTo(o.toDate().getValue());
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
                    int i = getValue().compareTo(o.getDate());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
                case TIME: {
                    int i = getValue().compareTo(o.getDate());
                    return i == 0 ? getType().compareTo(o.getType()) : i;
                }
            }
        }
        return super.compareTo(o);
    }
}
