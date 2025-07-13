package net.thevpc.tson;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestAnnotation {

    @Test
    public void test1() {

        TsonElement e;

        e = Tson.reader().readElement(
                                "{\n" +
                                "    @cover page(){\n" +
                                "        text(\"My Title Cover\"),\n" +
                                "        equation(\"\"\"\n" +
                                "            x=\\frac{-b \\pm \\sqrt {b^2-4ac}}{2a}\n" +
                                "            \"\"\",\n" +
                                "            at:center\n" +
                                "        ),\n" +
                                "    }\n" +
                                "}\n"
        );

        Assertions.assertEquals(1,((TsonObject)e).size());
        System.out.println(e.toString());
    }



}
