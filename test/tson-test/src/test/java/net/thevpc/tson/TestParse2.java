package net.thevpc.tson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

public class TestParse2 {

    @Test
    public void test1() {
        try {
            String str = "@normal page(name:\"Example\"){\n"
                    + "    @Header {\n"
                    + "        text(\"Example\"),\n"
                    + "    }\n"
                    + "    @OneColumn{\n"
                    + "        ul{\n"
                    + "        }\n"
                    + "    }\n"
                    + "//    source(\n"
                    + "//        tson\n"
                    + "//        \"\"\"\n"
                    + "//            12\n"
                    + "//        \"\"\"\n"
                    + "//    )\n"
                    + "//    source(tson,file:\"0020-example.tson\")\n"
                    + "}";
            String str2=new String(Files.readAllBytes(Paths.get("/home/vpc/xprojects/nuts/nuts-enterprise/halfa/documentation/tson-doc/02-pages/0030-general-form/0020-example.ndoc")));
            TsonDocument s = Tson.reader().readDocument(str2);
            System.out.println(s);
        } catch (IOException ex) {
            Logger.getLogger(TestParse2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
