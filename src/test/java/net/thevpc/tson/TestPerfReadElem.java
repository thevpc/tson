/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.tson;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.thevpc.tson.impl.parser.EmptyTsonParserVisitor;
import net.thevpc.tson.impl.parser.javacc.SimpleCharStream;
import net.thevpc.tson.impl.parser.javacc.Token;
import net.thevpc.tson.impl.parser.javacc.TsonStreamParserImplTokenManager;
import net.thevpc.tson.impl.parser.jflex.TsonFlex;
import net.thevpc.tson.impl.util.Chrono;
import org.json.simple.parser.JSONParser;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

/**
 * String,String
 * int times = 10000;
 * int complexity = 40;
 * --- PODIUM ---
 * Parser                     Time(s)                    Factor                     Fraction
 * json-jackson               1.640651158                100.0                      100.0
 * json-gson                  2.599644764                158.45201164938928         63.110590366799826
 * json-simple                4.838915439                294.9387147538892          33.905348805579735
 * tson-token-only(jflex)     5.392410949                328.6750460453459          30.425187796643204
 * tson-token-only-0(jflex)   5.554027504                338.5258028142019          29.539845757306857
 * tson-jflex                 6.050195761                368.76795725273854         27.11732351828607    (==
 * tson-token-only-0(javacc)  9.508645436                579.5653384105922          17.25430997551395
 * tson-custom-javacc         11.025653389               672.0291108342972          14.880307770574703
 * tson-token-only(javacc)    11.048258278               673.4069106724758          14.849862455396881
 * tson-javacc                11.73191841                715.0769590960176          13.984508762024369
 * yaml-beans                 15.344945156               935.2960305532542          10.691802032009818
 * yaml-jackson               15.667820096               954.9757131247519          10.471470491410983
 * yaml-snake                 24.362635529               1484.9369660457705         6.734292585246187
 * <p>
 * String,int
 * --- PODIUM ---
 * Parser                     Time(s)                    Factor                     Fraction
 * json-jackson               1.425062215                100.0                      100.0
 * tson-token-only-0(jflex)   2.644024712                185.5374933227038          53.89746202190564
 * json-gson                  2.944482449                206.62132628363878         48.39771469801008
 * tson-token-only(jflex)     3.187025445                223.6411443271619          44.71449128954162
 * json-simple                3.818521538                267.9547249100279          37.3197375166933
 * tson-jflex                 3.850730547                270.21490756457956         37.00758070725638    (==
 * tson-token-only-0(javacc)  9.967417428                699.4373524948172          14.297206124796
 * tson-token-only(javacc)    11.120347184               780.3411715607098          12.814907587151463
 * tson-custom-javacc         11.495718342               806.6818571847406          12.396460774386638
 * tson-javacc                11.790418045               827.361635225168           12.086613125684128
 * yaml-jackson               18.548764516               1301.610857460002          7.682787787675853
 * yaml-beans                 23.432622929               1644.3228009522377         6.081530946483827
 * yaml-snake                 30.86377669                2165.7845085731924         4.617264534128536
 * .
 *
 * @author thevpc
 */
public class TestPerfReadElem {

    public static void main(String[] args) {
        readElements();
    }

    public static void readElements() {
        int times = 10000;
        int complexity = 40;
//        int times = 10000;
//        int complexity = 40;
        //init all
        readElements(10, 10, false);

        for (int i = 0; i < 5; i++) {
            System.out.println();
            System.out.println("-------------------");
            System.out.println();
            readElements(times, complexity, true);
        }
    }

    public static void readElements(int times, int complexity, boolean verbose) {
        try {
            ChronoList chronoList = new ChronoList(verbose);
            String json = TsonRandom.randomMapListJson(complexity);
            chronoList.add(readElementsTsonEventsJavacc(times, json));
            chronoList.add(readElementsTsonEventsJflex(times, json));
            for (String parser : new String[]{"javacc", "jflex"}) {
                chronoList.add(readElementsTsonEvents(parser, times, json));
            }
            for (String parser : new String[]{"custom-javacc", "javacc", "jflex"}) {
                chronoList.add(readElementsTson(parser, times, json));
            }
            chronoList.add(readElementsJsonGson(times, json));
            chronoList.add(readElementsJsonJackson(times, json));
            chronoList.add(readElementsJsonSimple(times, json));
            chronoList.add(readElementsYamlJackson(times, json));
            chronoList.add(readElementsYamlBeans(times, json));
            chronoList.add(readElementsYamlSnake(times, json));

            chronoList.podium();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    private static Chrono readElementsYamlSnake(int c1, String json) {
        Chrono ch4 = Chrono.start("yaml-snake");
        Yaml yaml = new Yaml();
        for (int i = 0; i < c1; i++) {
            Object y = yaml.load(new StringReader(json));
        }
        ch4.stop();
        return ch4;
    }

    private static Chrono readElementsYamlBeans(int c1, String json) {
        Chrono ch4 = Chrono.start("yaml-beans");
        for (int i = 0; i < c1; i++) {
            YamlReader reader = new YamlReader(new StringReader(json));
            try {
                Object o = reader.read();
            } catch (YamlException e) {
                e.printStackTrace();
            }
        }
        ch4.stop();
        return ch4;
    }

    private static Chrono readElementsJsonGson(int c1, String json) {
        Chrono ch3 = Chrono.start("json-gson");
        Gson gson = new Gson();
        for (int i = 0; i < c1; i++) {
            final JsonElement y = gson.fromJson(new StringReader(json), JsonElement.class);
        }
        ch3.stop();
        return ch3;
    }

    private static Chrono readElementsJsonJackson(int c1, String json) {
        Chrono ch3 = Chrono.start("json-jackson");
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < c1; i++) {
            try {
                JsonNode rootNode = objectMapper.readTree(new StringReader(json));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        ch3.stop();
        return ch3;
    }

    private static Chrono readElementsJsonSimple(int c1, String json) {
        Chrono ch3 = Chrono.start("json-simple");
        org.json.simple.parser.JSONParser jsonParser = new JSONParser();
        for (int i = 0; i < c1; i++) {

            try (Reader reader = new StringReader(json)) {
                // Read JSON file
                Object obj = jsonParser.parse(reader);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ch3.stop();
        return ch3;
    }

    private static Chrono readElementsYamlJackson(int c1, String json) {
        Chrono ch3 = Chrono.start("yaml-jackson");
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        for (int i = 0; i < c1; i++) {
            try {
                JsonNode rootNode = objectMapper.readTree(new StringReader(json));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        ch3.stop();
        return ch3;
    }

    private static Chrono readElementsTson(String parser, int c1, String json) {
        TsonReader reader;
        Chrono ch1 = Chrono.start("tson-" + parser);
        reader = Tson.reader().setOption("parser", parser);
        for (int i = 0; i < c1; i++) {
            reader.readElement(json);
        }
        ch1.stop();
        return ch1;
    }

    private static Chrono readElementsTsonEventsJflex(int c1, String json) {
        Chrono ch1 = Chrono.start("tson-token-only-0(jflex)");
        for (int i = 0; i < c1; i++) {
            TsonFlex f = new TsonFlex(new StringReader(json));
            while (true) {
                try {
                    if (!((f.yylex()) >= 0)) {
                        break;
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        ch1.stop();
        return ch1;
    }

    private static Chrono readElementsTsonEventsJavacc(int c1, String json) {
        Chrono ch1 = Chrono.start("tson-token-only-0(javacc)");
        for (int i = 0; i < c1; i++) {
            SimpleCharStream jj_input_stream = new SimpleCharStream(new StringReader(json), 1, 1);
            TsonStreamParserImplTokenManager token_source = new TsonStreamParserImplTokenManager(jj_input_stream);
            while (true) {
                Token token = token_source.getNextToken();
                if (token == null || token.kind <= 0) {
                    break;
                }
            }
        }
        ch1.stop();
        return ch1;
    }

    private static Chrono readElementsTsonEvents(String parser, int c1, String json) {
        TsonReader reader;
        Chrono ch1 = Chrono.start("tson-token-only(" + parser + ")");
        reader = Tson.reader().setOption("parser", parser);
        for (int i = 0; i < c1; i++) {
            reader.visitElement(json, new EmptyTsonParserVisitor());
        }
        ch1.stop();
        return ch1;
    }
}
