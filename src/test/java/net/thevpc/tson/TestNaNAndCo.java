package net.thevpc.tson;

import org.junit.jupiter.api.Test;

public class TestNaNAndCo {
    @Test
    public void testRemoveComments(){
        TsonElement a = Tson.obj()
                .add("i", Tson.elem(Integer.MAX_VALUE))
                .add("d", Tson.elem(Double.NaN))
                .add("i",Tson.obj()
                        .add("v", Tson.elem(169).builder().annotation("format",Tson.elem("dec")))
                        .add("v", Tson.elem(169).builder().annotation("format",Tson.elem("bin")))
                        .add("v", Tson.elem(169).builder().annotation("format",Tson.elem("oct")))
                        .add("v", Tson.elem(169).builder().annotation("format",Tson.elem("hex")))
                )
                .add("l",Tson.obj()
                        .add("v", Tson.elem(169L).builder().annotation("format",Tson.elem("dec")))
                        .add("v", Tson.elem(169L).builder().annotation("format",Tson.elem("bin")))
                        .add("v", Tson.elem(169L).builder().annotation("format",Tson.elem("oct")))
                        .add("v", Tson.elem(169L).builder().annotation("format",Tson.elem("hex")))
                )
                .add("s",Tson.obj()
                        .add("v", Tson.elem((short)169).builder().annotation("format",Tson.elem("dec")))
                        .add("v", Tson.elem((short)169).builder().annotation("format",Tson.elem("bin")))
                        .add("v", Tson.elem((short)169).builder().annotation("format",Tson.elem("oct")))
                        .add("v", Tson.elem((short)169).builder().annotation("format",Tson.elem("hex")))
                )
                .add("b",Tson.obj()
                        .add("v", Tson.elem((byte)19).builder().annotation("format",Tson.elem("dec")))
                        .add("v", Tson.elem((byte)19).builder().annotation("format",Tson.elem("bin")))
                        .add("v", Tson.elem((byte)19).builder().annotation("format",Tson.elem("oct")))
                        .add("v", Tson.elem((byte)19).builder().annotation("format",Tson.elem("hex")))
                )
                .build();
        System.out.println(a);
    }
}
