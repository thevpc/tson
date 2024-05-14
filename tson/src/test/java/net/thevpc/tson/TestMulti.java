package net.thevpc.tson;


import java.io.IOException;

public class TestMulti {
    public static void main(String[] args) throws IOException {
        String str = "{\n}";
        TsonDocument s = Tson.reader().readDocument(str);
        System.out.println(s);
    }
}
