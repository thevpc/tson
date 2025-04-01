package net.thevpc.tson;

public class TestParser1 {
    public static void main(String[] args) {
        a01();
        a02();
        a03();
    }
    public static void a01() {
        String text="-1-2";
        TsonDocument e = Tson.reader().readDocument(text);
        System.out.println(e);
    }
    public static void a02() {
        String text="switch(){\n" +
                "    case \"\":{}\n" +
                "    case \"\":{}\n" +
                "}";
        TsonDocument e = Tson.reader().readDocument(text);
        System.out.println(e);
    }
    public static void a03() {
        String text="switch(a+2-3){\n" +
                "    case \"\"=>{}\n" +
                "    case \"\"=>{}\n" +
                "}";
        TsonDocument e = Tson.reader().readDocument(text);
        System.out.println(e);
    }

}
