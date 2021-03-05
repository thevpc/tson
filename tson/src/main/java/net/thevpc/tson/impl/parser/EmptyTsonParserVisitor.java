package net.thevpc.tson.impl.parser;

import net.thevpc.tson.TsonParserVisitor;

public class EmptyTsonParserVisitor implements TsonParserVisitor {
    public static final TsonParserVisitor INSTANCE=new EmptyTsonParserVisitor();
}
