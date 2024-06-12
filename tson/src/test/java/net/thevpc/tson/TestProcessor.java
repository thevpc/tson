package net.thevpc.tson;

import org.junit.jupiter.api.Test;

public class TestProcessor {
    @Test
    public void testRemoveComments(){
        TsonElement a = Tson.ofObj().comments(TsonComments.ofMultiLine("Hello"))
                .add("name", Tson.of().set("Really me").anchor("ref"))
                .add("otherName", Tson.alias("ref"))
                .build();
        System.out.println(a);
        TsonElement b = Tson.processor().resolveAliases(a);
        System.out.println(b);
    }
}
