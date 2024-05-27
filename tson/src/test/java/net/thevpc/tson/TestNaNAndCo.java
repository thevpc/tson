package net.thevpc.tson;

import org.junit.jupiter.api.Test;

public class TestNaNAndCo {
    @Test
    public void testRemoveComments(){
        TsonElement a = Tson.ofObj()
                .add("i", Tson.of(Integer.MAX_VALUE))
                .add("d", Tson.of(Double.NaN))
                .add("i",Tson.ofObj()
                        .add("v", Tson.of(169).builder().annotation("format",Tson.of("dec")))
                        .add("v", Tson.of(169).builder().annotation("format",Tson.of("bin")))
                        .add("v", Tson.of(169).builder().annotation("format",Tson.of("oct")))
                        .add("v", Tson.of(169).builder().annotation("format",Tson.of("hex")))
                )
                .add("l",Tson.ofObj()
                        .add("v", Tson.of(169L).builder().annotation("format",Tson.of("dec")))
                        .add("v", Tson.of(169L).builder().annotation("format",Tson.of("bin")))
                        .add("v", Tson.of(169L).builder().annotation("format",Tson.of("oct")))
                        .add("v", Tson.of(169L).builder().annotation("format",Tson.of("hex")))
                )
                .add("s",Tson.ofObj()
                        .add("v", Tson.of((short)169).builder().annotation("format",Tson.of("dec")))
                        .add("v", Tson.of((short)169).builder().annotation("format",Tson.of("bin")))
                        .add("v", Tson.of((short)169).builder().annotation("format",Tson.of("oct")))
                        .add("v", Tson.of((short)169).builder().annotation("format",Tson.of("hex")))
                )
                .add("b",Tson.ofObj()
                        .add("v", Tson.of((byte)19).builder().annotation("format",Tson.of("dec")))
                        .add("v", Tson.of((byte)19).builder().annotation("format",Tson.of("bin")))
                        .add("v", Tson.of((byte)19).builder().annotation("format",Tson.of("oct")))
                        .add("v", Tson.of((byte)19).builder().annotation("format",Tson.of("hex")))
                )
                .build();
        System.out.println(a);
    }
}
