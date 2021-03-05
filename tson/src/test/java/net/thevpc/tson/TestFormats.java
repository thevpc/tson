package net.thevpc.tson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import static net.thevpc.tson.Tson.*;

public class TestFormats {

    @Test
    public void test1() {
        try {
            DecimalFormat f = new DecimalFormat("00");
            for (int i = 3; i <= 3; i++) {
                String name = "/test" + f.format(i) + ".tson";
                System.out.println(name + "  ==============================================================");
                TsonElement t = Tson.reader().readElement(TestFormats.class.getResource(name));

                System.out.println(Tson.format().setCompact(true).build().format(t));
                System.out.println(Tson.format().setCompact(false).build().format(t));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void test5() {
        TsonElement e;

        e = array(name("a")).build();
        Assertions.assertEquals("[\n a\n]", e.toString(false));
        Assertions.assertEquals("[a]", e.toString(true));

        e = obj(pair(name("a"), elem(16))).build();
        Assertions.assertEquals("{\n a : 16\n}", e.toString(false));
        Assertions.assertEquals("{a:16}", e.toString(true));

        e = obj("hello", new TsonElementBase[]{pair(name("a"), elem(16))}, elem('p')).build();
        Assertions.assertEquals("hello(a:16){'p'}", e.toString(true));

        e = obj("hello", null, elem('p')).build();
        Assertions.assertEquals("hello{'p'}", e.toString(true));

        e = obj(elem('p')).build();
        Assertions.assertEquals("(){'p'}", e.toString(true));

        e = elem(1.5f);
        Assertions.assertEquals("1.5f", e.toString());

        e = elem((byte) 12);
        Assertions.assertEquals("12o", e.toString());

        e = elem(1.5);
        Assertions.assertEquals("1.5", e.toString());

        e = elem(15L);
        Assertions.assertEquals("15L", e.toString());

        e = elem(15);
        Assertions.assertEquals("15", e.toString());

        e = elem(new BigInteger("15"));
        Assertions.assertEquals("15g", e.toString());

        e = elem(new BigDecimal("15.5"));
        Assertions.assertEquals("15.5g", e.toString());

    }

    @Test
    public void test4() {
        try {
            for (int i = 0; i < 20; i++) {
                System.out.println("==============================================================");
                TsonElement v1 = TsonRandom.randomElement(TsonElementType.ARRAY);
                System.out.println(v1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            for (int i = 0; i < 20; i++) {
                TsonElement v1 = TsonRandom.randomElement();
                System.out.println("============================================================== :: " + v1.getType());
                System.out.println(v1);
                String s = v1.toString();
                TsonElement v2 = null;
                v2 = Tson.reader().readElement(s);
                System.out.println(v2);
                System.out.println(v1.equals(v2));
                if (!v1.equals(v2)) {
                    System.out.println("PBM : Not Equal");
                    v2 = Tson.reader().readElement(s);
                    System.out.println(v2);
                    System.out.println(v1.equals(v2));

                    throw new IllegalArgumentException("Not equals");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        TsonElement h = obj()
                .add("lastName", elem(8).builder()
                        .comments("Hello\nworld")
                        .annotation("an1", elem(15.0)))
                .add("firstName", charElem('a').builder()
                        .comments("By")
                        .annotation("an2", elem(20.0))
                )
                .build();

        System.out.println(h);
        System.out.println(h.toString(true));
        System.out.println(h.toString(false));
        try {
            TsonElement u = reader().readElement(Tson.document().content(h).toString(true));
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
