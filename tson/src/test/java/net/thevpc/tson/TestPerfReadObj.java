/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.tson;

import com.google.gson.Gson;
import net.thevpc.tson.impl.util.Chrono;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import java.io.StringReader;
import java.util.List;

/**
 * @author thevpc
 */
public class TestPerfReadObj {

    public static void main(String[] args) {
        readObj();
    }

    public static void readObj() {
        try {
            int times = 100;
            int complexity = 1000;
            String sb = TsonRandom.randomMapListJson(complexity);

            Chrono ch1 = readObjTson(false, times, sb);
            Chrono ch2 = readObjTson(true, times, sb);
            Chrono ch3 = readObjGson(times, sb);
            Chrono ch4 = readObjYaml(complexity, sb);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Chrono readObjYaml(int complexity, String sb) {
//            LocalDate is not supported by yaml arg ....
        Chrono ch4 = Chrono.start("yaml");
        PropertyUtils propUtils = new PropertyUtils();
        propUtils.setAllowReadOnlyProperties(true);
        Representer repr = new Representer();
        repr.setPropertyUtils(propUtils);
        Yaml yaml = new Yaml(new Constructor(), repr);
        for (int i = 0; i < complexity; i++) {
            List b = yaml.loadAs(new StringReader(sb), List.class);
        }
        ch4.stop();
            System.out.println("yaml         : "+ch4);
        return ch4;
    }

    private static Chrono readObjGson(int times, String sb) {
        Chrono ch3 = Chrono.start("gson");
        Gson gson = new Gson();
        for (int i = 0; i < times; i++) {
            List b = gson.fromJson(new StringReader(sb), List.class);
        }
        ch3.stop();
        System.out.println("gson         : " + ch3);
        return ch3;
    }

    private static Chrono readObjTson(boolean custom, int times, String sb) {
        Chrono ch1 = Chrono.start("custom=" + (custom ? "true " : "false"));
        TsonReader reader = Tson.reader().setOption("custom", custom);
        for (int i = 0; i < times; i++) {
            List b = reader.read(sb, List.class);
        }
        ch1.stop();
        System.out.println("custom=" + (custom ? "true " : "false") + " : " + ch1);
        return ch1;
    }
}
