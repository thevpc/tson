package net.thevpc.tson;

import net.thevpc.tson.impl.util.Base64DecoderAdapter;
import net.thevpc.tson.impl.util.Base64EncoderAdapter;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class Base64Test {
    public static void main(String[] args) throws IOException {
        String str = "ABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFFABCDDDDDDDDDFFFFFFFFFFFF";

        System.out.println(str);

        String to1 = Base64EncoderAdapter.toBase64(str.getBytes(), 9);
        String to2 = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println("\t---ENCODE---");
        System.out.println("\t\t"+to1);
        System.out.println("\t\t----------------------------------");
        System.out.println("\t\t"+to2);
        Assertions.assertEquals(to2,to1.replace("\n",""));
        System.out.println("\t\t"+"SUCCESS-ENCODE");


        byte[] bytes1 = Base64DecoderAdapter.fromBase64(to1);
        byte[] bytes2 = Base64.getDecoder().decode(to2);
        System.out.println("\t"+"---DECODE---");
        System.out.println("\t\t"+Arrays.toString(bytes1));
        System.out.println("\t\t"+"----------------------------------");
        System.out.println("\t\t"+Arrays.toString(bytes2));
        Assertions.assertArrayEquals(bytes2,bytes1);
        System.out.println("\t\t"+"SUCCESS-DECODE");

    }
}
