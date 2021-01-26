package net.thevpc.tson.impl.parser;

import net.thevpc.tson.impl.elements.TsonBooleanImpl;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.impl.parser.javacc.TsonStreamParserImplConstants;
import net.thevpc.tson.impl.parser.jflex.TsonFlex;
import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonParserVisitor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;

public class CustomJFlexTsonStreamParserImpl implements ITsonStreamParser {

    private TsonFlex flex;
    private TsonStreamParserImplConfig config;
    private TsonParserVisitor visitor;

    private int c_kind;

    private boolean pushedBack;

    public String getTokenString() {
        switch (c_kind) {
            case TsonStreamParserImplConstants.STRING: {
                return TsonStreamParserImplConstants.tokenImage[c_kind] + " " + flex.stringVal;
            }
            case TsonStreamParserImplConstants.CHARACTER: {
                return "CHAT " + flex.charVal;
            }
            default: {
                return TsonStreamParserImplConstants.tokenImage[c_kind];
            }
        }
    }

    public CustomJFlexTsonStreamParserImpl(java.io.InputStream stream) {
        this(new InputStreamReader(stream));
    }

    public CustomJFlexTsonStreamParserImpl(java.io.InputStream stream, String encoding) throws UnsupportedEncodingException {
        this(new InputStreamReader(stream, encoding));
    }

    public CustomJFlexTsonStreamParserImpl(java.io.Reader stream) {
        flex = new TsonFlex(stream);
    }

    @Override
    public void setConfig(TsonStreamParserImplConfig config) {
        this.config = config;
        this.visitor = config.getVisitor();
    }

    @Override
    public void parseElement() {
        elementLevel2();
    }

    @Override
    public void parseDocument() {
        elementLevel2();
        visitor.visitDocumentEnd();
    }

    void elementLevel2() {
        visitor.visitInstructionStart();
        elementLevel1();
        nextToken();
        switch (c_kind) {
            case TsonStreamParserImplConstants.COLON: {
                elementLevel1();
                visitor.visitKeyValueEnd();
                break;
            }
            default: {
                pushBackToken();
                visitor.visitSimpleEnd();
            }
        }
    }

    private void pushBackToken() {
        pushedBack = true;
    }

    private void nextToken2() {
        try {
            c_kind = flex.yylex();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void nextToken() {
        if (pushedBack) {
            pushedBack = false;
        } else {
            try {
                c_kind = flex.yylex();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

//            int yylex;
//            try {
//                yylex = flex.yylex();
//            } catch (IOException e) {
//                throw new UncheckedIOException(e);
//            }
//            if (yylex < 0) {
//                c_kind = TsonStreamParserImplConstants.EOF;
//            } else {
//                c_kind = yylex;
//            }
        }
    }

    void annotations() {
        while (true) {
            nextToken();
            if (c_kind == TsonStreamParserImplConstants.AT) {
                nextToken();
                String annName = null;
                if (c_kind == TsonStreamParserImplConstants.NAME) {
                    annName = flex.yytext();
                    nextToken2();
                }
                visitor.visitAnnotationStart(annName);
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken();
                    if (c_kind != TsonStreamParserImplConstants.RPAREN) {
                        pushBackToken();
                        visitor.visitAnnotationParamStart();
                        elementLevel2();
                        visitor.visitAnnotationParamEnd();
                        boolean repeat = true;
                        while (repeat) {
                            nextToken();
                            switch (c_kind) {
                                case TsonStreamParserImplConstants.COMMA:
                                    visitor.visitAnnotationParamStart();
                                    elementLevel2();
                                    visitor.visitAnnotationParamEnd();
                                    break;
                                case TsonStreamParserImplConstants.RPAREN:
                                    repeat = false;
                                    break;
                                default:
                                    throw new IllegalArgumentException();
                            }
                        }

                    }
                } else {
                    throw new IllegalArgumentException();
                }
                visitor.visitAnnotationEnd();
            } else {
                pushBackToken();
                return;
            }
        }
    }

    void elementLevel1() {
        visitor.visitElementStart();
        nextToken();
        if (c_kind == TsonStreamParserImplConstants.COMMENT) {
            visitor.visitComments(TsonParserUtils.escapeComments(flex.yytext()));
        } else {
            pushBackToken();
        }

        annotations();
        nextToken();
        switch (c_kind) {
            case TsonStreamParserImplConstants.NULL: {
                visitor.visitPrimitiveEnd(TsonNullImpl.INSTANCE);
                break;
            }
            case TsonStreamParserImplConstants.TRUE: {
                visitor.visitPrimitiveEnd(TsonBooleanImpl.TRUE);
                break;
            }
            case TsonStreamParserImplConstants.FALSE: {
                visitor.visitPrimitiveEnd(TsonBooleanImpl.FALSE);
                break;
            }
            case TsonStreamParserImplConstants.DATETIME: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDateTimeElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.DATE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDateElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.TIME: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseTimeElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.REGEX: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseRegexElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.BYTE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.SHORT: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.INTEGER: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.LONG: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElem(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.NAN: {
                nextToken();
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken2();
                    if (c_kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNaNElem(flex.yytext()));
                        return;
                    }
                    throw new IllegalArgumentException("Expected NaN(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken();
                }
                break;
            }

            case TsonStreamParserImplConstants.POS_INF: {
                nextToken();
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken2();
                    if (c_kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parsePosInfElem(flex.yytext()));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Inf(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.POSITIVE_INFINITY));
                    pushBackToken();
                }
                break;
            }

            case TsonStreamParserImplConstants.NEG_INF: {
                nextToken();
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken2();
                    if (c_kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNegInfElem(flex.yytext()));
                        return;
                    }
                    throw new IllegalArgumentException("Expected -Inf(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NEGATIVE_INFINITY));
                    pushBackToken();
                }
                break;
            }

            case TsonStreamParserImplConstants.POS_BOUND: {
                nextToken();
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken2();
                    if (c_kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parsePosBoundElem(flex.yytext()));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Bound(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.MAX_VALUE));
                    pushBackToken();
                }
                break;
            }

            case TsonStreamParserImplConstants.NEG_BOUND: {
                nextToken();
                if (c_kind == TsonStreamParserImplConstants.LPAREN) {
                    nextToken2();
                    if (c_kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNegBoundElem(flex.yytext()));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Bound(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.MIN_VALUE));
                    pushBackToken();
                }
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemBin(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemOctal(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemHex(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemBin(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemOctal(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemHex(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.LONG_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemBin(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.LONG_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemOctal(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.LONG_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemHex(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.BYTE_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemBin(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.BYTE_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemOctal(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.BYTE_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemHex(flex.yytext()));
                break;
            }

            case TsonStreamParserImplConstants.FLOAT: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseFloatElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.DOUBLE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDoubleElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.CHARACTER: {
                visitor.visitPrimitiveEnd(Tson.elem(flex.charVal));
                break;
            }
            case TsonStreamParserImplConstants.STRING: {
                visitor.visitPrimitiveEnd(Tson.elem(flex.stringVal));
                break;
            }
            case TsonStreamParserImplConstants.ALIAS: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseAliasElem(flex.yytext()));
                break;
            }
            case TsonStreamParserImplConstants.NAME: {
                nextToken();
                visitor.visitNamedStart(flex.yytext());
                switch (c_kind) {
                    case TsonStreamParserImplConstants.LPAREN: {
                        pushBackToken();
                        parStart(true);
                        break;
                    }
                    case TsonStreamParserImplConstants.LBRACK: {
                        arrStart(true);
                        break;
                    }
                    case TsonStreamParserImplConstants.LBRACE: {
                        objStartNamed();
                        break;
                    }
                    default: {
                        visitor.visitPrimitiveEnd(Tson.name(flex.yytext()));
                    }
                }
                break;
            }
            case TsonStreamParserImplConstants.LPAREN: {
                pushBackToken();
                parStart(false);
                break;
            }
            case TsonStreamParserImplConstants.LBRACK: {
                pushBackToken();
                arrStart(false);
                break;
            }
            case TsonStreamParserImplConstants.LBRACE: {
                pushBackToken();
                objStartUnNamed();
                break;
            }
        }
    }

    private void parStart(boolean named) {
        visitor.visitParamsStart();
        nextToken(); //read (
        visitor.visitParamElementStart();
        elementLevel2();
        visitor.visitParamElementEnd();
        boolean repeat = true;
        while (repeat) {
            nextToken();
            switch (c_kind) {
                case TsonStreamParserImplConstants.COMMA: {
                    visitor.visitParamElementStart();
                    elementLevel2();
                    visitor.visitParamElementEnd();
                    break;
                }
                case TsonStreamParserImplConstants.RPAREN: {
                    repeat = false;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unexpected " + getTokenString());
                }
            }
        }
        visitor.visitParamsEnd();

        nextToken();
        switch (c_kind) {
            case TsonStreamParserImplConstants.LBRACK: {
                arrStart(true);
                break;
            }
            case TsonStreamParserImplConstants.LBRACE: {
                objStartNamed();
                break;
            }
            default: {
                if (named) {
                    visitor.visitFunctionEnd();
                } else {
                    visitor.visitUpletEnd();
                }
            }
        }
    }

    private void arrStart(boolean named) {
        if (named) {
            visitor.visitNamedArrayStart();
        } else {
            visitor.visitArrayStart();
        }
        nextToken(); //read [
        nextToken2();
        if (c_kind == TsonStreamParserImplConstants.RBRACK) {
            //
        } else {
            pushBackToken();
            visitor.visitArrayElementStart();
            elementLevel2();
            visitor.visitArrayElementEnd();
            boolean repeat = true;
            while (repeat) {
                nextToken();
                switch (c_kind) {
                    case TsonStreamParserImplConstants.COMMA: {
                        visitor.visitArrayElementStart();
                        elementLevel2();
                        visitor.visitArrayElementEnd();
                        break;
                    }
                    case TsonStreamParserImplConstants.RBRACK: {
                        repeat = false;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unexpected " + getTokenString());
                    }
                }
            }
        }
        if (named) {
            visitor.visitNamedArrayEnd();
        } else {
            visitor.visitArrayEnd();
        }
    }

    private void objStartNamed() {
        visitor.visitNamedObjectStart();
        objStartCore();
        visitor.visitNamedObjectEnd();
    }

    private void objStartUnNamed() {
        visitor.visitObjectStart();
        objStartCore();
        visitor.visitObjectEnd();
    }

    private void objStartCore() {
        nextToken(); //read {
        nextToken2();
        if (c_kind == TsonStreamParserImplConstants.RBRACE) {
            //
        } else {
            pushBackToken();
            visitor.visitObjectElementStart();
            elementLevel2();
            visitor.visitObjectElementEnd();
            boolean repeat = true;
            while (repeat) {
                nextToken();
                switch (c_kind) {
                    case TsonStreamParserImplConstants.COMMA: {
                        visitor.visitObjectElementStart();
                        elementLevel2();
                        visitor.visitObjectElementEnd();
                        break;
                    }
                    case TsonStreamParserImplConstants.RBRACE: {
                        repeat = false;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unexpected " + getTokenString());
                    }
                }
            }
        }
    }

//    void run() {
//
//    }
}
