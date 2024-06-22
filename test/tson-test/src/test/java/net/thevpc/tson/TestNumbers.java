package net.thevpc.tson;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestNumbers {

    @Test
    public void test1() {
//        check(Tson.ofByte((byte) 123, TsonNumberLayout.DECIMAL), "0u1_123");
//        check(Tson.ofByte((byte) 123, TsonNumberLayout.HEXADECIMAL), "0u1x7b");
//        check(Tson.ofByte((byte) 123, TsonNumberLayout.BINARY), "0u1b1111011");
//        check(Tson.ofByte((byte) 123, TsonNumberLayout.OCTAL), "0u1o173");
//        check(Tson.ofByte((byte) 0x1a, TsonNumberLayout.HEXADECIMAL, "g"), "0u1x1a_g");
//        check(Tson.ofByte((byte) 0x1a, TsonNumberLayout.HEXADECIMAL, "amp"), "0u1x1a_amp");
//
//        check(Tson.ofShort((short) 123, TsonNumberLayout.DECIMAL), "0u2_123");
//        check(Tson.ofShort((short) 123, TsonNumberLayout.HEXADECIMAL), "0u2x7b");
//        check(Tson.ofShort((short) 123, TsonNumberLayout.BINARY), "0u2b1111011");
//        check(Tson.ofShort((short) 123, TsonNumberLayout.OCTAL), "0u2o173");
//        check(Tson.ofShort((short) 0x1a, TsonNumberLayout.HEXADECIMAL, "amp"), "0u2x1a_amp");
//
//
//        check(Tson.ofInt((int) 123, TsonNumberLayout.DECIMAL), "123");
//        check(Tson.ofInt((int) 123, TsonNumberLayout.HEXADECIMAL), "0x7b");
//        check(Tson.ofInt((int) 123, TsonNumberLayout.BINARY), "0b1111011");
//        check(Tson.ofInt((int) 123, TsonNumberLayout.OCTAL), "0173");
//        check(Tson.ofInt((int) 0x1a, TsonNumberLayout.HEXADECIMAL, "amp"), "0x1a_amp");
//
//
//        check(Tson.ofLong((long) 123, TsonNumberLayout.DECIMAL), "123L");
//        check(Tson.ofLong((long) 123, TsonNumberLayout.HEXADECIMAL), "0x7bL");
//        check(Tson.ofLong((long) 123, TsonNumberLayout.BINARY), "0b1111011L");
//        check(Tson.ofLong((long) 123, TsonNumberLayout.OCTAL), "0173L");
//        check(Tson.ofLong((long) 0x1a, TsonNumberLayout.HEXADECIMAL, "amp"), "0x1aL_amp");
//
//        check(Tson.ofFloat(123, "a"), "123.0f_a");
//
//        check(Tson.ofDouble((double) 123), "123.0");
//        check(Tson.ofDouble(123, "a"), "123.0_a");
//
//        check(Tson.ofBigInt(new BigInteger("123"), TsonNumberLayout.DECIMAL), "123LL");
//        check(Tson.ofBigInt(new BigInteger("123"), TsonNumberLayout.HEXADECIMAL), "0x7bLL");
//        check(Tson.ofBigInt(new BigInteger("123"), TsonNumberLayout.BINARY), "0b1111011LL");
//        check(Tson.ofBigInt(new BigInteger("123"), TsonNumberLayout.OCTAL), "0173LL");
//        check(Tson.ofBigInt(new BigInteger("1a",16),TsonNumberLayout.HEXADECIMAL, "amp"), "0x1aLL_amp");
//
//        check(Tson.ofBigDecimal(new BigDecimal("123.0")), "123.0LL");
//        check(Tson.ofBigDecimal(new BigDecimal("123.0"), "amp"), "123.0LL_amp");
//        check(Tson.ofDouble(0.75, "%g"), "0.75%g");
        check(Tson.ofInt(074, TsonNumberLayout.OCTAL,"%g"), "074%g");
    }

    private void check(TsonElement elem, String ...str){
        Assertions.assertEquals(str[0], elem.toString());
        for (String s : str) {
            TsonElement u = Tson.parseNumber(s);
            Assertions.assertEquals(u, elem);
        }
    }


}
