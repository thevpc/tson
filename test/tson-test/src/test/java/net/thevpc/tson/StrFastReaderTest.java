package net.thevpc.tson;

import net.thevpc.tson.impl.parser.StrFastReader;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class StrFastReaderTest {
    @Test
    public void test1(){
        StrFastReader t=new StrFastReader("ab");
        Assertions.assertFalse(t.read("b"));
        Assertions.assertTrue(t.read("ab"));
    }
}
