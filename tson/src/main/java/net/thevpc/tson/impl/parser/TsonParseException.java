package net.thevpc.tson.impl.parser;

import net.thevpc.tson.impl.parser.javacc.ParseException;

public class TsonParseException extends RuntimeException {
    public String currentToken;

    public int[][] expectedTokenSequences;

    public String[] tokenImage;

    /**
     * An integer that describes the kind of this token.  This numbering
     * system is determined by JavaCCParser, and a table of these numbers is
     * stored in the file ...Constants.java.
     */
    public int currentTokenKind;

    /**
     * The line number of the first character of this Token.
     */
    public int currentTokenBeginLine;
    /**
     * The column number of the first character of this Token.
     */
    public int currentTokenBeginColumn;
    /**
     * The line number of the last character of this Token.
     */
    public int currentTokenEndLine;
    /**
     * The column number of the last character of this Token.
     */
    public int currentTokenEndColumn;

    /**
     * The string image of the token.
     */
    public String currentTokenImage;

    public TsonParseException(ParseException ex) {
        super(ex.getMessage());
        currentToken = ex.currentToken.toString();
        expectedTokenSequences = ex.expectedTokenSequences;
        tokenImage = ex.tokenImage;
        currentTokenKind = ex.currentToken.kind;
        currentTokenBeginLine = ex.currentToken.beginLine;
        currentTokenEndLine = ex.currentToken.endLine;
        currentTokenBeginColumn = ex.currentToken.beginColumn;
        currentTokenEndColumn = ex.currentToken.endColumn;
        currentTokenImage = ex.currentToken.image;
    }

    public TsonParseException(Throwable cause) {
        super(cause);
    }
}
