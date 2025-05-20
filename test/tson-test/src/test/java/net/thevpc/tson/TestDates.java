package net.thevpc.tson;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestDates {

    @Test
    public void test1() {
        TsonElement e = Tson.reader().readDocument("2025-05-18T10:00:49.016").getContent();
        System.out.println(e.type());
        System.out.println(e);
        Assertions.assertEquals(TsonElementType.LOCAL_DATETIME, e.type());
    }

    @Test
    public void test2() {
        TsonElement e = Tson.reader().readDocument("2025-05-18T10:00:49.016Z").getContent();
        System.out.println(e.type());
        System.out.println(e);
        Assertions.assertEquals(TsonElementType.INSTANT, e.type());
    }

    @Test
    public void test3() {
        TsonElement e = Tson.reader().readDocument("2025-05-18").getContent();
        System.out.println(e.type());
        System.out.println(e);
        Assertions.assertEquals(TsonElementType.LOCAL_DATE, e.type());
    }

    @Test
    public void test4() {
        TsonElement e = Tson.reader().readDocument("10:00:49.016").getContent();
        System.out.println(e.type());
        System.out.println(e);
        Assertions.assertEquals(TsonElementType.LOCAL_TIME, e.type());
    }

    @Test
    public void test5() {
        TsonElement e = Tson.reader().readDocument("10:00:49").getContent();
        System.out.println(e.type());
        System.out.println(e);
        Assertions.assertEquals(TsonElementType.LOCAL_TIME, e.type());
    }


}
