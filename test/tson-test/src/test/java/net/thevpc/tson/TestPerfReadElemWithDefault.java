/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.tson;

import net.thevpc.tson.impl.util.Chrono;

/**
 * @author thevpc
 */
public class TestPerfReadElemWithDefault {

    public static void main(String[] args) {
        readElements();
    }

    public static void readElements() {
        int times=100000;
        int complexity=1000;
        String json = TsonRandom.randomMapListJson(complexity);
        TsonReader reader;
        Chrono ch1 = Chrono.start("parser=" + "default");
        reader = Tson.reader().setOption("parser", "default");
        for (int i = 0; i < times; i++) {
            reader.readElement(json);
        }
        ch1.stop();
        System.out.println(ch1);
    }

}
