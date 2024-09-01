package net.thevpc.tson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import static net.thevpc.tson.Tson.*;

public class TestErrors {

    @Test
    public void test1() {
        String name = "/test06.tson";
        System.out.println(name + "  ==============================================================");
        TsonElement t;
        try {
            t = Tson.reader().readElement(TestFormats.class.getResource(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(t);
    }

}
