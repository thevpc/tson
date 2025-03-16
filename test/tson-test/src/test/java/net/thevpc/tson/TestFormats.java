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
            for (int i = 4; i <= 4; i++) {
                String name = "/test" + f.format(i) + ".tson";
                System.out.println(name + "  ==============================================================");
                TsonElement t = Tson.reader().readElement(TestFormats.class.getResource(name));

                System.out.println(Tson.format().compact(true).build().format(t));
                System.out.println(Tson.format().compact(false).build().format(t));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void test5() {
        TsonElement e;

        e = ofArray(ofName("a")).build();
        Assertions.assertEquals("[\n a\n]", e.toString(false));
        Assertions.assertEquals("[a]", e.toString(true));

        e = ofObj(ofPair(ofName("a"), of(16))).build();
        Assertions.assertEquals("{\n a : 16\n}", e.toString(false));
        Assertions.assertEquals("{a:16}", e.toString(true));

        e = ofObj("hello", new TsonElementBase[]{ofPair(ofName("a"), of(16))}, of('p')).build();
        Assertions.assertEquals("hello(a:16){'p'}", e.toString(true));

        e = ofObj("hello", of('p')).build();
        Assertions.assertEquals("hello{'p'}", e.toString(true));

        e = ofObj(of('p')).build();
        Assertions.assertEquals("{'p'}", e.toString(true));
//        Assertions.assertEquals("(){'p'}", e.toString(true));

        e = of(1.5f);
        Assertions.assertEquals("1.5f", e.toString());

        e = of((byte) 12);
        Assertions.assertEquals("12o", e.toString());

        e = of(1.5);
        Assertions.assertEquals("1.5", e.toString());

        e = of(15L);
        Assertions.assertEquals("15L", e.toString());

        e = of(15);
        Assertions.assertEquals("15", e.toString());

        e = of(new BigInteger("15"));
        Assertions.assertEquals("15g", e.toString());

        e = of(new BigDecimal("15.5"));
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
                System.out.println("============================================================== :: " + v1.type());
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
        TsonElement h = ofObj()
                .add("lastName", of(8).builder()
                        .comments(TsonComments.ofMultiLine("Hello\nworld"))
                        .annotation("an1", of(15.0)))
                .add("firstName", Tson.ofChar('a').builder()
                        .comments(TsonComments.ofSingleLine("By"))
                        .annotation("an2", of(20.0))
                )
                .build();

        System.out.println(h);
        System.out.println(h.toString(true));
        System.out.println(h.toString(false));
        try {
            TsonElement u = reader().readElement(Tson.ofDocument().content(h).toString(true));
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
