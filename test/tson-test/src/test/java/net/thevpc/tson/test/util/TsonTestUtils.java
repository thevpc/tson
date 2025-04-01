package net.thevpc.tson.test.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class TsonTestUtils {

    public static String loadTsonTestAsString(String name) {
        return loadResourceAsString("/net/thevpc/tson/test/"+name+".tson");
    }

    public static String loadResourceAsString(String path) {
        return new String(loadResourceAsByteArray(path));
    }
    public static byte[] loadResourceAsByteArray(String path) {
        InputStream resourceAsStream = TsonTestUtils.class.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int r;
            while ((r = resourceAsStream.read(buffer)) > 0) {
                bos.write(buffer, 0, r);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
