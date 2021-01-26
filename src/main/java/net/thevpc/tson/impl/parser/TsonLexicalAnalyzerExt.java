package net.thevpc.tson.impl.parser;

import net.thevpc.tson.TsonLexicalAnalyzer;

public interface TsonLexicalAnalyzerExt extends TsonLexicalAnalyzer {
    String currentStreamPart();

    String currentText();

}
