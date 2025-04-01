package net.thevpc.tson;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestStrings {

    @Test
    public void test1() {
//        check(Tson.ofString("Hello", TsonStringLayout.DOUBLE_QUOTE), "\"Hello\"");
//        check(Tson.ofString("Hello", TsonStringLayout.SINGLE_QUOTE), "'Hello'");
//        check(Tson.ofString("Hello", TsonStringLayout.ANTI_QUOTE), "`Hello`");
//
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.DOUBLE_QUOTE), "\"Hello\nWorld\"");
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.SINGLE_QUOTE), "'Hello\nWorld'");
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.ANTI_QUOTE), "`Hello\nWorld`");
//
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.TRIPLE_DOUBLE_QUOTE), "\"\"\"Hello\nWorld\"\"\"");
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.TRIPLE_SINGLE_QUOTE), "'''Hello\nWorld'''");
//        check(Tson.ofString("Hello\nWorld", TsonStringLayout.TRIPLE_ANTI_QUOTE), "```Hello\nWorld```");
//
//
//        check(Tson.ofString("Hello\"World", TsonStringLayout.DOUBLE_QUOTE), "\"Hello\\\"World\"");
//        check(Tson.ofString("Hello\"\"\"World", TsonStringLayout.TRIPLE_DOUBLE_QUOTE), "\"\"\"Hello\\\"\"\"World\"\"\"");

        TsonElement e;
//        TsonElement e = Tson.reader().readElement(
//                        "                \"\"\"\n" +
//                        "                    \"a\" 'b' `c`\n" +
//                        "                    \\\"\\\"\\\"a\\\"\\\"\\\"\n" +
//                        "                    '''b'''\n" +
//                        "                    ```c```\n" +
//                        "                \"\"\"\n"
//        );
//        System.out.println(e.toString());
        e = Tson.reader().readElement(
                        "\"\"\"\n" +
                                "                    \"a\" 'b' `c`\n" +
                                "                    \\\"\"\"a\\\"\"\"\n" +
                                "                    '''b'''\n" +
                                "                    ```c```\n" +
                                "                \"\"\""
        );
        System.out.println(e.toString());
    }

    private void check(TsonElement elem, String ...str){
        System.out.println(str[0]);
        System.out.println(elem.toStr().literalString());
        Assertions.assertEquals(str[0], elem.toStr().literalString());
        for (String s : str) {
            TsonElement u = Tson.parseString(s);
            Assertions.assertEquals(u, elem);
        }
    }


}
