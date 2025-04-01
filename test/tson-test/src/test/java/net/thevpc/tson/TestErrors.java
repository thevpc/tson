package net.thevpc.tson;

import org.junit.jupiter.api.Test;

public class TestErrors {

    @Test
    public void test1() {
        String name = "/net/thevpc/tson/test/test06.tson";
        System.out.println(name + "  ==============================================================");
        TsonElement t;
        t = Tson.reader().readElement(TestFormats.class.getResource(name));
        System.out.println(t);
    }

}
