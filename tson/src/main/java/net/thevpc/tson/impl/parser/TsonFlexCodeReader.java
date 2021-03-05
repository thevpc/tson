package net.thevpc.tson.impl.parser;

import net.thevpc.tson.TsonLexicalAnalyzer;
import net.thevpc.tson.TsonParserTokens;

import java.io.IOException;
import java.io.Reader;

public class TsonFlexCodeReader extends Reader {
    private TsonLexicalAnalyzer flex;
    private StringBuilder buffer = new StringBuilder();
    private boolean end;

    public TsonFlexCodeReader(TsonLexicalAnalyzer flex) {
        this.flex = flex;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (buffer.length() == 0) {
            if (!end) {
                int token = flex.nextToken();
                switch (token) {
                    case TsonParserTokens.CHARSTREAM_PART: {
                        buffer.append(flex.currentString());
                        break;
                    }
                    case TsonParserTokens.CHARSTREAM_END: {
                        buffer.append(flex.currentString());
                        end = true;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
            }
            if (len > buffer.length()) {
                len = buffer.length();
            }
            buffer.getChars(0, len, cbuf, off);
            buffer.delete(0, len);
        }
        return 0;
    }

    @Override
    public void close() throws IOException {
        end = true;
    }
}
