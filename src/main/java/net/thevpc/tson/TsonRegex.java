package net.thevpc.tson;

import java.util.regex.Pattern;

public interface TsonRegex extends TsonElement {
    Pattern getValue();

    TsonPrimitiveBuilder builder();
}
