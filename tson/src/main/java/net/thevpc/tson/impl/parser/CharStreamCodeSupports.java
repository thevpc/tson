package net.thevpc.tson.impl.parser;

public class CharStreamCodeSupports {
    public static CharStreamCodeSupport of(String language) {
        if (language == null || language.isEmpty()) {
            return new CharStreamCodeSupportDefault();
        }
        switch (language) {
            case "java": {
                return new CharStreamCodeSupportJava();
            }
        }
        throw new IllegalArgumentException("Unsupported Language " + language);
    }
}
