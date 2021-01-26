package net.thevpc.tson.impl.parser.jflex;
import net.thevpc.tson.impl.parser.*;
import net.thevpc.tson.impl.util.*;
import net.thevpc.tson.*;
import java.io.*;


%%

%class TsonFlex
//%8bit
%unicode
//%line
//%column
%int
%public
%final
%implements TsonLexicalAnalyzer

%state STRING, CHARLITERAL, REGEX,CHARSTREAM,CHARSTREAM_CODE,BINARYSTREAM
%{
  public StringBuilder string = new StringBuilder(1024);
  public String stringVal=null;
  public char charVal = '\0';
  public Reader charStream;
  public InputStream binaryStream;
  public KmpAlgo kmp=null;
  public CharStreamCodeSupport code;

  public int nextToken(){
      try{
        return yylex();
      }catch(IOException ex){
          throw new UncheckedIOException(ex);
      }
  }

  public Reader currentReader(){
      return charStream;
  }

  public InputStream currentInputStream(){
      return binaryStream;
  }

  public String currentImage(){
      return yytext();
  }

  public String currentString(){
      return stringVal;
  }

  public char currentChar(){
      return charVal;
  }

  private String yytext_substring(int from,int to){
      return new String(zzBuffer, zzStartRead+from, zzMarkedPos - zzStartRead-from+to);
  }

  private void commitCharString(){
      stringVal=string.toString();
      string.setLength(0);
  }

  private boolean appendAndCommitCharString(char c){
      string.append(c);
      int len=string.length();
      if(len>1024){
          commitCharString();
          return true;
      }
      return false;
  }

  private String deleteTailCharString(int count){
      int len=string.length();
      String tail=string.substring(len-count,len);
      string.delete(len-count,len);
      return tail;
  }

  private String prepareCharStringButTail(int count){
      int len=string.length();
      String tail=string.substring(len-count,len);
      stringVal=string.toString();
      string.setLength(0);
      string.append(tail);
      return tail;
  }

%}

DIGIT = [0-9]
LETTER = [a-zA-Z_$]
IDNAME = {LETTER} ({LETTER} | {DIGIT}) *
NAME = {IDNAME} ("." {IDNAME})*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
HexDigit          = [0-9a-fA-F]
OctDigit          = [0-7]
BinDigit          = [0-1]

DecLongLiteral    = {DecIntegerLiteral} [lL]
DecShortLiteral   = {DecIntegerLiteral} [sS]
DecByteLiteral    = {DecIntegerLiteral} [bB]

HexByteLiteral    = 0 [xX] {HexDigit}+ [bB]
HexShortLiteral   = 0 [xX] {HexDigit}+ [SS]
HexIntegerLiteral = 0 [xX] {HexDigit}+
HexLongLiteral    = 0 [xX] {HexDigit}+ [lL]

OctByteLiteral   = 0 {OctDigit}+ [bB]
OctShortLiteral   = 0 {OctDigit}+ [sS]
OctIntegerLiteral = 0 {OctDigit}+
OctLongLiteral    = 0 {OctDigit}+ [lL]

BinByteLiteral   =   0b {BinDigit}+ [bB]
BinShortLiteral   = 0b {BinDigit}+ [sS]
BinIntegerLiteral = 0b {BinDigit}+
BinLongLiteral    = 0b {BinDigit}+ [lL]

//HexByteLiteral    = 0 [xX] {HexDigit} {1,2} [bB]
//HexShortLiteral   = 0 [xX] {HexDigit} {1,4}
//HexIntegerLiteral = 0 [xX] {HexDigit} {1,8}
//HexLongLiteral    = 0 [xX] {HexDigit} {1,16} [lL]
//
//OctByteLiteral   = 0 {OctDigit} {1,3} [bB]
//OctShortLiteral   = 0 {OctDigit} {1,6} [sS]
//OctIntegerLiteral = 0 {OctDigit} {1,11}
//OctLongLiteral    = 0 {OctDigit} {1,22} [lL]
//
//BinByteLiteral   =   0b {BinDigit} {1,8}[bB]
//BinShortLiteral   = 0b {BinDigit} {1,16}[sS]
//BinIntegerLiteral = 0b {BinDigit} {1,32}
//BinLongLiteral    = 0b {BinDigit} {1,64} [lL]
//
/* floating point literals */
FloatLiteral  = ({FLit1}|{FLit2}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}) {Exponent}? [dD]?

FLit1    = [0-9]+ (\. [0-9]*)
FLit2    = [0-9]+ (\. [0-9]*)? {Exponent}
Exponent = [eE] [+-]? [0-9]+

Date = {DIGIT}{4} "-" {DIGIT}{2} "-" {DIGIT}{2}
Time = {DIGIT}{2} ":" {DIGIT}{2} ":" {DIGIT}{2} ("." ({DIGIT}){1,9})?
DateTime = {Date} T {Time} ("Z" | ["+","-"]({DIGIT}){2} ":" ({DIGIT}){2})

%%
<YYINITIAL>{
    "(" {return TsonParserTokens.LPAREN;}
    ")" {return TsonParserTokens.RPAREN;}
    "[" {return TsonParserTokens.LBRACK;}
    "]" {return TsonParserTokens.RBRACK;}
    "{" {return TsonParserTokens.LBRACE;}
    "}" {return TsonParserTokens.RBRACE;}
    ":" {return TsonParserTokens.COLON;}
    "," {return TsonParserTokens.COMMA;}
    "@" {return TsonParserTokens.AT;}
    "/*" .* "*/" {return TsonParserTokens.COMMENT;}
    "&" {NAME} {return TsonParserTokens.ALIAS;}
    "null" {return TsonParserTokens.NULL;}
    "false" {return TsonParserTokens.FALSE;}
    "true" {return TsonParserTokens.TRUE;}
    "NaN" {return TsonParserTokens.NAN;}
    "+Bound" {return TsonParserTokens.POS_BOUND;}
    "-Bound" {return TsonParserTokens.NEG_BOUND;}
    "+Inf" {return TsonParserTokens.POS_INF;}
    "-Inf" {return TsonParserTokens.NEG_INF;}
    {NAME} {return TsonParserTokens.NAME;}
    /* string literal */
    \"                             { yybegin(STRING); string.setLength(0); }

    /* character literal */
    \'                             { yybegin(CHARLITERAL); }
    /* character literal */
    "/"                             { yybegin(REGEX); }

    "^(" [a-zA-Z0-9_]+ "){"            {
            yybegin(CHARSTREAM_CODE);
            string.setLength(0);
            String language=yytext_substring(2,-2);
            code=CharStreamCodeSupports.of(language);
            charStream=new TsonFlexCodeReader(this);
            return TsonParserTokens.CHARSTREAM_START;
    }

    "^" "{"            {
            yybegin(CHARSTREAM_CODE);
            string.setLength(0);
            code=CharStreamCodeSupports.of("");
            charStream=new TsonFlexCodeReader(this);
            code.reset();
            return TsonParserTokens.CHARSTREAM_START;
    }

    "^" "["            {
            yybegin(BINARYSTREAM);
            string.setLength(0);
            binaryStream=new Base64DecoderAdapter(new TsonFlexCodeReader(this));
            return TsonParserTokens.BINARYSTREAM_START;
    }

    "^"[a-zA-Z0-9_]+ "["            {
            yybegin(BINARYSTREAM);
            string.setLength(0);
            String streamName=yytext_substring(1,-1);
            char[] end=java.util.Arrays.copyOfRange(zzBuffer,zzStartRead,zzMarkedPos);
            end[end.length-1]=']';
            kmp=KmpAlgo.compile(end);
            charStream=new TsonFlexCharStreamReader(streamName,this);
            return TsonParserTokens.CHARSTREAM_START;
    }


    "^"[a-zA-Z0-9_]+ "{"            {
            yybegin(CHARSTREAM);
            string.setLength(0);
            String streamName=yytext_substring(1,-1);
            char[] end=java.util.Arrays.copyOfRange(zzBuffer,zzStartRead,zzMarkedPos);
            end[end.length-1]='}';
            kmp=KmpAlgo.compile(end);
            charStream=new TsonFlexCharStreamReader(streamName,this);
            return TsonParserTokens.CHARSTREAM_START;
    }

    {DecByteLiteral} {return TsonParserTokens.BYTE;}
    {DecShortLiteral} {return TsonParserTokens.SHORT;}
    {DecIntegerLiteral} {return TsonParserTokens.INTEGER;}
    {DecLongLiteral} {return TsonParserTokens.LONG;}

    {OctByteLiteral} {return TsonParserTokens.BYTE_O;}
    {OctShortLiteral} {return TsonParserTokens.SHORT_O;}
    {OctIntegerLiteral} {return TsonParserTokens.INTEGER_O;}
    {OctLongLiteral} {return TsonParserTokens.LONG_O;}

    {HexByteLiteral} {return TsonParserTokens.BYTE_H;}
    {HexShortLiteral} {return TsonParserTokens.SHORT_H;}
    {HexIntegerLiteral} {return TsonParserTokens.INTEGER_H;}
    {HexLongLiteral} {return TsonParserTokens.LONG_H;}

    {BinByteLiteral} {return TsonParserTokens.BYTE_B;}
    {BinShortLiteral} {return TsonParserTokens.SHORT_B;}
    {BinIntegerLiteral} {return TsonParserTokens.INTEGER_B;}
    {BinLongLiteral} {return TsonParserTokens.LONG_B;}

    {FloatLiteral} {return TsonParserTokens.FLOAT;}
    {DoubleLiteral} {return TsonParserTokens.DOUBLE;}
    {DateTime} {return TsonParserTokens.DATETIME;}
    {Date} {return TsonParserTokens.DATE;}
    {Time} {return TsonParserTokens.TIME;}
}
<STRING> {
  \"                             { yybegin(YYINITIAL); stringVal=string.toString();return TsonParserTokens.STRING; }

  [^\r\n\"\\]+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?[0-7]?[0-7]  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                        				   string.append( val ); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  \r|\n|\r\n                     { throw new RuntimeException("Unterminated string at end of line"); }
}

<REGEX> {
  "/"                         { yybegin(YYINITIAL); stringVal=string.toString(); return TsonParserTokens.REGEX; }

  [^\r\n\\/]+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  "\\//"                         { string.append( '/' ); }
  \\[0-3]?[0-7]?[0-7]  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                        				   string.append( val ); }
  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  \r|\n|\r\n                     { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARSTREAM_CODE> {
  "}"  {
    char c=zzBuffer[zzStartRead];
    if(code.isValid()){
        return TsonParserTokens.CHARSTREAM_END;
    }
    if(appendAndCommitCharString(c)){
        return TsonParserTokens.CHARSTREAM_PART;
    }
  }
  .  {
    char c=zzBuffer[zzStartRead];
    code.next(c);
    if(appendAndCommitCharString(c)){
        return TsonParserTokens.CHARSTREAM_PART;
    }
  }
}

<CHARSTREAM> {
  .  {
    char c=zzBuffer[zzStartRead];
    if(kmp.next(c)){
        yybegin(YYINITIAL);
        string.append(c);
        deleteTailCharString(kmp.length());
        commitCharString();
        return TsonParserTokens.CHARSTREAM_END;
    }else{
        if(appendAndCommitCharString(c)){
            return TsonParserTokens.CHARSTREAM_PART;
        }
    }
  }
}

<BINARYSTREAM> {
  "]"  {
    commitCharString();
    return TsonParserTokens.BINARYSTREAM_END;
  }
  .  {
    char c=zzBuffer[zzStartRead];
    if(appendAndCommitCharString(c)){
        return TsonParserTokens.BINARYSTREAM_PART;
    }
  }
}

<CHARLITERAL> {
  [^\r\n\'\\]\'            { yybegin(YYINITIAL); charVal=yytext().charAt(0); return TsonParserTokens.CHARACTER; }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); charVal='\b'; return TsonParserTokens.CHARACTER;}
  "\\t"\'                        { yybegin(YYINITIAL); charVal='\t'; return TsonParserTokens.CHARACTER;}
  "\\n"\'                        { yybegin(YYINITIAL); charVal='\n'; return TsonParserTokens.CHARACTER;}
  "\\f"\'                        { yybegin(YYINITIAL); charVal='\f'; return TsonParserTokens.CHARACTER;}
  "\\r"\'                        { yybegin(YYINITIAL); charVal='\r'; return TsonParserTokens.CHARACTER;}
  "\\\""\'                       { yybegin(YYINITIAL); charVal='\"'; return TsonParserTokens.CHARACTER;}
  "\\'"\'                        { yybegin(YYINITIAL); charVal='\''; return TsonParserTokens.CHARACTER;}
  "\\\\"\'                       { yybegin(YYINITIAL); charVal='\\';  return TsonParserTokens.CHARACTER;}
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
			                              int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
			                              charVal=(char)val;
			                            return TsonParserTokens.CHARACTER;}

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  \r|\n|\r\n                     { throw new RuntimeException("Unterminated character literal at end of line"); }
}

. {
 throw new RuntimeException("Unsupported "+yytext());
}