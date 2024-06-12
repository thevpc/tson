package net.thevpc.tson.impl.parser.javacc;

import net.thevpc.tson.TsonComment;
import net.thevpc.tson.TsonParserVisitor;
import net.thevpc.tson.impl.parser.TsonParserUtils;
import net.thevpc.tson.impl.parser.TsonStreamParserImplConfig;

public class CommentsHelper {
    private static ThreadLocal<Data> CURR=new ThreadLocal<>();
    private static class Data{
        private TsonStreamParserImplConfig config;
        private TsonParserVisitor visitor;
        private Object source;

        public Data(TsonStreamParserImplConfig config, TsonParserVisitor visitor, Object source) {
            this.config = config;
            this.visitor = visitor;
            this.source = source;
        }
    }

    public static void init(TsonStreamParserImplConfig config,TsonParserVisitor visitor,Object source){
        CURR.set(new Data(config, visitor, source));
    }

    public static void onComments(String image){
        Data c = CURR.get();
        if(!c.config.isSkipComments()) {
            TsonComment rc=(!c.config.isRawComments())
                    ?TsonParserUtils.parseComments(image)
                    :TsonComment.ofMultiLine(image);
            c.visitor.visitComments(rc);
        }
    }
}
