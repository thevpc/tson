package net.thevpc.tson.impl;

import net.thevpc.tson.TsonAnnotation;
import net.thevpc.tson.TsonElement;
import net.thevpc.tson.TsonElementType;
import net.thevpc.tson.TsonProcessor;
import net.thevpc.tson.impl.parser.SimpleTsonParserVisitor;

import java.util.Map;
import java.util.Set;


public class TsonProcessorImpl implements TsonProcessor {

    @Override
    public TsonElement removeComments(TsonElement element) {
        SimpleTsonParserVisitor v = new SimpleTsonParserVisitor() {
            @Override
            public void visitComments(String comments) {
                super.visitComments(null);
            }
        };
        element.visit(v);
        element = v.getElement();
        return element;
    }

    @Override
    public TsonElement resolveAliases(TsonElement element) {
        SimpleTsonParserVisitor v = new SimpleTsonParserVisitor() {
            @Override
            protected TsonAnnotation onAddAnnotation(TsonAnnotation a) {
                if (a.getName() == null && a.size() == 1) {
                    TsonElement aliasParam = a.get(0);
                    if (aliasParam.getType() == TsonElementType.NAME) {
                        //this is an alias definition
                        //mark it
                        String aliasName = aliasParam.getString();
                        peek().addToSetContextValue("alias", aliasName);
                        //and remove it
                        return null;
                    }
                }
                return a;
            }

            @Override
            protected void repush(StackContext n) {
                Set<String> all = getMergedSetsContextValues("alias", 0);
                super.repush(n);
                StackContext a = peek();
                if (a instanceof ElementContext) {
                    ElementContext ac = (ElementContext) a;
//                    StackContext p = peekOrRoot(1);
                    for (String s : all) {
                        getRootContext().addToMapContextValue("vars", s, ac.value);
                    }
                    if (ac.value.getType() == TsonElementType.ALIAS) {
                        Map<String, TsonElement> vars = getMergedMapsContextValues("vars", 1);
                        TsonElement tt = vars.get(ac.value.getString());
                        if (tt == null) {
                            throw new IllegalArgumentException("Alias " + ac.value + " not found");
                        }
                        super.repush(new ElementContext(tt));
                    }
                }
            }
        };
        element.visit(v);
        element = v.getElement();
        return element;
    }


}