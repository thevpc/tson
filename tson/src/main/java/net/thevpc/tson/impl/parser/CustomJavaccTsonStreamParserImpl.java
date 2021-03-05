package net.thevpc.tson.impl.parser;

import net.thevpc.tson.impl.elements.TsonBooleanImpl;
import net.thevpc.tson.impl.elements.TsonNullImpl;
import net.thevpc.tson.impl.parser.javacc.SimpleCharStream;
import net.thevpc.tson.impl.parser.javacc.Token;
import net.thevpc.tson.impl.parser.javacc.TsonStreamParserImplConstants;
import net.thevpc.tson.impl.parser.javacc.TsonStreamParserImplTokenManager;
import net.thevpc.tson.Tson;
import net.thevpc.tson.TsonParserVisitor;
import net.thevpc.tson.impl.parser.javacc.*;

public class CustomJavaccTsonStreamParserImpl implements ITsonStreamParser {
    private SimpleCharStream jj_input_stream;
    private TsonStreamParserImplTokenManager token_source;
    private TsonStreamParserImplConfig config;
    private TsonParserVisitor visitor;
    private Token last;

    public CustomJavaccTsonStreamParserImpl(java.io.InputStream stream) {
        this(stream, null);
    }

    public CustomJavaccTsonStreamParserImpl(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new TsonStreamParserImplTokenManager(jj_input_stream);
    }

    public CustomJavaccTsonStreamParserImpl(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new TsonStreamParserImplTokenManager(jj_input_stream);
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
        Token nextToken = nextToken();
        switch (nextToken.kind) {
            case TsonStreamParserImplConstants.COLON: {
                elementLevel1();
                visitor.visitKeyValueEnd();
                break;
            }
            default: {
                pushBackToken(nextToken);
                visitor.visitSimpleEnd();
            }
        }
    }

    private void pushBackToken(Token t) {
        last = t;
    }

    private Token nextToken() {
        if (last != null) {
            Token t = last;
            last = null;
            return t;
        }
        return token_source.getNextToken();
    }

    void annotations() {
        while (true) {
            Token token = nextToken();
            if (token.kind == TsonStreamParserImplConstants.AT) {
                token = nextToken();
                String annName = null;
                if (token.kind == TsonStreamParserImplConstants.NAME) {
                    annName = token.image;
                    token = nextToken();
                }
                visitor.visitAnnotationStart(annName);
                if (token.kind == TsonStreamParserImplConstants.LPAREN) {
                    token = nextToken();
                    if (token.kind == TsonStreamParserImplConstants.RPAREN) {
                        //
                    } else {
                        pushBackToken(token);
                        visitor.visitAnnotationParamStart();
                        elementLevel2();
                        visitor.visitAnnotationParamEnd();
                        boolean repeat = true;
                        while (repeat) {
                            token = nextToken();
                            if (token.kind == TsonStreamParserImplConstants.COMMA) {
                                visitor.visitAnnotationParamStart();
                                elementLevel2();
                                visitor.visitAnnotationParamEnd();
                            } else if (token.kind == TsonStreamParserImplConstants.RPAREN) {
                                repeat = false;
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }

                    }
                } else {
                    throw new IllegalArgumentException();
                }
                visitor.visitAnnotationEnd();
            } else {
                pushBackToken(token);
                return;
            }
        }
    }

    void elementLevel1() {
        visitor.visitElementStart();
        Token token = nextToken();
        if (token.kind == TsonStreamParserImplConstants.COMMENT) {
            visitor.visitComments(TsonParserUtils.escapeComments(token.image));
        } else {
            pushBackToken(token);
        }

        annotations();
        token = nextToken();
        switch (token.kind) {
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
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDateTimeElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.DATE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDateElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.TIME: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseTimeElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.REGEX: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseRegexElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.BYTE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.SHORT: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.INTEGER: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.LONG: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElem(token.image));
                break;
            }

            case TsonStreamParserImplConstants.NAN: {
                Token t = nextToken();
                if (t.kind == TsonStreamParserImplConstants.LPAREN) {
                    t = nextToken();
                    if (t.kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNaNElem(t.image));
                        return;
                    }
                    throw new IllegalArgumentException("Expected NaN(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken(t);
                }
                break;
            }

            case TsonStreamParserImplConstants.POS_INF: {
                Token t = nextToken();
                if (t.kind == TsonStreamParserImplConstants.LPAREN) {
                    t = nextToken();
                    if (t.kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parsePosInfElem(t.image));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Inf(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken(t);
                }
                break;
            }

            case TsonStreamParserImplConstants.NEG_INF: {
                Token t = nextToken();
                if (t.kind == TsonStreamParserImplConstants.LPAREN) {
                    t = nextToken();
                    if (t.kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNegInfElem(t.image));
                        return;
                    }
                    throw new IllegalArgumentException("Expected -Inf(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken(t);
                }
                break;
            }


            case TsonStreamParserImplConstants.POS_BOUND: {
                Token t = nextToken();
                if (t.kind == TsonStreamParserImplConstants.LPAREN) {
                    t = nextToken();
                    if (t.kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parsePosBoundElem(t.image));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Bound(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken(t);
                }
                break;
            }

            case TsonStreamParserImplConstants.NEG_BOUND: {
                Token t = nextToken();
                if (t.kind == TsonStreamParserImplConstants.LPAREN) {
                    t = nextToken();
                    if (t.kind == TsonStreamParserImplConstants.NAME) {
                        visitor.visitPrimitiveEnd(TsonParserUtils.parseNegBoundElem(t.image));
                        return;
                    }
                    throw new IllegalArgumentException("Expected +Bound(type)");
                } else {
                    visitor.visitPrimitiveEnd(Tson.elem(Double.NaN));
                    pushBackToken(t);
                }
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemBin(token.image));
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemOctal(token.image));
                break;
            }

            case TsonStreamParserImplConstants.INTEGER_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseIntElemHex(token.image));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemBin(token.image));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemOctal(token.image));
                break;
            }

            case TsonStreamParserImplConstants.SHORT_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseShortElemHex(token.image));
                break;
            }

            case TsonStreamParserImplConstants.LONG_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemBin(token.image));
                break;
            }

            case TsonStreamParserImplConstants.LONG_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemOctal(token.image));
                break;
            }

            case TsonStreamParserImplConstants.LONG_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseLongElemHex(token.image));
                break;
            }


            case TsonStreamParserImplConstants.BYTE_B: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemBin(token.image));
                break;
            }

            case TsonStreamParserImplConstants.BYTE_O: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemOctal(token.image));
                break;
            }

            case TsonStreamParserImplConstants.BYTE_H: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseByteElemHex(token.image));
                break;
            }

            case TsonStreamParserImplConstants.FLOAT: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseFloatElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.DOUBLE: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseDoubleElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.CHARACTER: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseCharElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.STRING: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseStringElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.ALIAS: {
                visitor.visitPrimitiveEnd(TsonParserUtils.parseAliasElem(token.image));
                break;
            }
            case TsonStreamParserImplConstants.NAME: {
                Token nt = nextToken();
                visitor.visitNamedStart(nt.image);
                switch (nt.kind) {
                    case TsonStreamParserImplConstants.LPAREN: {
                        pushBackToken(token);
                        parStart(true);
                        break;
                    }
                    case TsonStreamParserImplConstants.LBRACK: {
                        arrStart(true);
                        break;
                    }
                    case TsonStreamParserImplConstants.LBRACE: {
                        objStart(true);
                        break;
                    }
                    default: {
                        visitor.visitPrimitiveEnd(Tson.name(nt.image));
                    }
                }
                break;
            }
            case TsonStreamParserImplConstants.LPAREN: {
                pushBackToken(token);
                parStart(false);
                break;
            }
            case TsonStreamParserImplConstants.LBRACK: {
                pushBackToken(token);
                arrStart(false);
                break;
            }
            case TsonStreamParserImplConstants.LBRACE: {
                pushBackToken(token);
                objStart(false);
                break;
            }
        }
    }

    private void parStart(boolean named) {
        visitor.visitParamsStart();
        Token token = nextToken(); //read (
        visitor.visitParamElementStart();
        elementLevel2();
        visitor.visitParamElementEnd();
        boolean repeat = true;
        while (repeat) {
            token = nextToken();
            switch (token.kind) {
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
                    throw new IllegalArgumentException("Unexpected " + token);
                }
            }
        }
        visitor.visitParamsEnd();

        token = nextToken();
        switch (token.kind) {
            case TsonStreamParserImplConstants.LBRACK: {
                arrStart(true);
                break;
            }
            case TsonStreamParserImplConstants.LBRACE: {
                objStart(true);
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
        Token token = nextToken(); //read [
        token = nextToken();
        if (token.kind == TsonStreamParserImplConstants.RBRACK) {
            //
        } else {
            pushBackToken(token);
            visitor.visitArrayElementStart();
            elementLevel2();
            visitor.visitArrayElementEnd();
            boolean repeat = true;
            while (repeat) {
                token = nextToken();
                switch (token.kind) {
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
                        throw new IllegalArgumentException("Unexpected " + token);
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

    private void objStart(boolean named) {
        if (named) {
            visitor.visitNamedObjectStart();
        } else {
            visitor.visitObjectStart();
        }
        Token token = nextToken(); //read {
        token = nextToken();
        if (token.kind == TsonStreamParserImplConstants.RBRACE) {
            //
        } else {
            pushBackToken(token);
            visitor.visitObjectElementStart();
            elementLevel2();
            visitor.visitObjectElementEnd();
            boolean repeat = true;
            while (repeat) {
                token = nextToken();
                switch (token.kind) {
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
                        throw new IllegalArgumentException("Unexpected " + token);
                    }
                }
            }
        }
        if (named) {
            visitor.visitNamedObjectEnd();
        } else {
            visitor.visitObjectEnd();
        }
    }

    void run() {

    }
}
