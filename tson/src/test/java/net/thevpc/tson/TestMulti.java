package net.thevpc.tson;


import org.junit.Test;

import java.io.IOException;

public class TestMulti {
    @Test
    public void test1() {
        String str = "{\n}";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }

    @Test
    public void testComments1() {
        String str = "{"
                + "//hello\n"
                + "}";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }

    @Test
    public void testComments2() {
        String str = "{\n"
                + "//hello\n"
                + "item\n"
                + "}";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }

    @Test
    public void testComments3() {
        String str = "{\n"
                + "item\n"
                + "//hello\n"
                + "}";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }
    @Test
    public void testComments4() {
        String str = "\n"
                + "//hello\n"
                + "item\n"
                + "//bye\n"
                + "";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }
}
