/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.tson;

import com.google.gson.Gson;
import net.thevpc.tson.impl.util.Chrono;

import java.io.StringReader;

/**
 * @author thevpc
 */
public class TestPerfReadWrite {

    public static void main(String[] args) {
    }

    public static void runObj() {
        try {
            int times = 100;
            int complexity = 100;
            TsonRandom.Person[] a = TsonRandom.randomPersonArray(complexity);

            Chrono ch1 = Chrono.start("custom:false");
            TsonWriter writer = Tson.writer().setOptionCompact(true);
            TsonReader reader = Tson.reader().setOption("custom", false);
            for (int i = 0; i < times; i++) {
                StringBuilder sb = new StringBuilder();
                writer.writeDocument(sb, a);
                TsonRandom.Person[] b = reader.read(sb, TsonRandom.Person[].class);
                sb = new StringBuilder();
                writer.writeDocument(sb, b);
            }
            ch1.stop();

            Chrono ch2 = Chrono.start("custom:true");
            writer = Tson.writer().setOptionCompact(true);
            reader = Tson.reader().setOption("custom", true);
            for (int i = 0; i < times; i++) {
                StringBuilder sb = new StringBuilder();
                writer.writeDocument(sb, a);
                TsonRandom.Person[] b = reader.read(sb, TsonRandom.Person[].class);
                sb = new StringBuilder();
                writer.writeDocument(sb, b);
            }
            ch2.stop();

            Chrono ch3 = Chrono.start("Gson");
            Gson gson = new Gson();
            for (int i = 0; i < times; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(gson.toJson(a));
                TsonRandom.Person[] b = gson.fromJson(new StringReader(sb.toString()), TsonRandom.Person[].class);
                sb = new StringBuilder();
                sb.append(gson.toJson(b));
            }
            ch3.stop();
            //LocalDate is not supported by yaml arg ....
//            Chrono ch4=Chrono.start();
//            PropertyUtils propUtils = new PropertyUtils();
//            propUtils.setAllowReadOnlyProperties(true);
//            Representer repr = new Representer();
//            repr.setPropertyUtils(propUtils);
//            Yaml yaml = new Yaml(new Constructor(), repr);
//            for (int i = 0; i < c1; i++) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(yaml.dump(a));
//                TestClassConversion.Person[] b = yaml.loadAs(new StringReader(sb.toString()), TestClassConversion.Person[].class);
//                sb = new StringBuilder();
//                sb.append(yaml.dump(b));
//            }
//            ch4.stop();
            System.out.println("custom=false : " + ch1);
            System.out.println("custom=true  : " + ch2);
            System.out.println("gson         : " + ch3);
//            System.out.println("yaml         : "+ch4);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
