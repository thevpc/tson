package net.thevpc.tson;

import org.junit.jupiter.api.Test;

public class TestProcessor {
    @Test
    public void testRemoveComments(){
        TsonElement a = Tson.ofObjectBuilder().comments(TsonComments.ofMultiLine("Hello"))
                .add("name", Tson.of().set("Really me").anchor("ref"))
                .add("otherName", Tson.ofAlias("ref"))
                .build();
        System.out.println(a);
        TsonElement b = Tson.processor().resolveAliases(a);
        System.out.println(b);
    }
}
