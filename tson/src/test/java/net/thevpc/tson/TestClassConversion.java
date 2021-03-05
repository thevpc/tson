package net.thevpc.tson;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static net.thevpc.tson.Tson.*;

public class TestClassConversion {

    @Test
    public void test1() {
        try {
            TsonRandom.Person[] a = TsonRandom.randomPersonArray(10000);
            StringBuilder sb = new StringBuilder();
            Tson.writer().writeDocument(sb,a);

            TsonRandom.Person[] b = reader().read(sb, TsonRandom.Person[].class);

            Assertions.assertArrayEquals(a, b);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testTime() {
        try {
            long start = System.nanoTime();
            TsonRandom.Person[] a = TsonRandom.randomPersonArray(10000);
            for (int i = 0; i < 1; i++) {
                StringBuilder sb = new StringBuilder();
                Tson.writer().writeDocument(sb,a);
                TsonRandom.Person[] b = reader().read(sb, TsonRandom.Person[].class);
                sb = new StringBuilder();
                Tson.writer().writeDocument(sb,b);
            }
            long end = System.nanoTime();
            System.out.println((end-start)/1000000000.0);

            long start2 = System.nanoTime();
            Gson gson=new Gson();
            for (int i = 0; i < 1; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(gson.toJson(a));
                TsonRandom.Person[] b = gson.fromJson(new StringReader(sb.toString()), TsonRandom.Person[].class);
                sb = new StringBuilder();
                sb.append(gson.toJson(b));
            }
            long end2 = System.nanoTime();
            System.out.println((end2-start2)/1000000000.0);

            System.out.println(1.0*(end2-start2)/(end-start));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
