package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.TsonDocumentHeaderImpl;
import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TsonDocumentHeaderBuilderImpl implements TsonDocumentHeaderBuilder {
    private String version = null;
    private String encoding = null;
    private List<TsonElement> params = new ArrayList<>();

    public TsonDocumentHeaderBuilderImpl() {
    }

    @Override
    public TsonDocumentHeaderBuilderImpl reset() {
        version = null;
        encoding = null;
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilderImpl parse(TsonAnnotation a) {
        if (a.getName().equals("tson")) {
            List<TsonElement> params = a.getAll();
            boolean acceptStr=true;
            for (TsonElement param : params) {
                switch (param.getType()) {
                    case PAIR: {
                        acceptStr=false;
                        TsonPair kv = param.toKeyValue();
                        switch (kv.getKey().getString()) {
                            case "version": {
                                version = kv.getValue().getString();
                                break;
                            }
                            case "encoding": {
                                encoding = kv.getValue().getString();
                                break;
                            }
                            default: {
                                this.params.add(kv);
                            }
                        }
                        break;
                    }
                    case STRING:
                    case NAME: {
                        if(acceptStr) {
                            String v = param.getString();
                            if (version == null && v.startsWith("v")) {
                                version = v.substring(1);
                            } else if (encoding == null) {
                                String y = param.getString();
                                if (Charset.availableCharsets().containsKey(y)) {
                                    encoding = y;
                                }
                                acceptStr=false;
                            } else {
                                acceptStr=false;
                                this.params.add(param);
                            }
                        }else{
                            this.params.add(param);
                        }
                        break;
                    }
                    default: {
                        acceptStr=false;
                        this.params.add(param);
                    }
                }
            }
        }
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilderImpl setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilderImpl setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getEncoding() {
        return encoding;
    }

    @Override
    public TsonElement[] getParams() {
        return params.toArray(TsonUtils.TSON_ELEMENTS_EMPTY_ARRAY);
    }

    @Override
    public TsonDocumentHeaderBuilder with(TsonElementBase... elements) {
        return addParams(elements);
    }

    @Override
    public TsonDocumentHeaderBuilder addParam(TsonElementBase element) {
        params.add(Tson.elem(element));
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilder removeParam(TsonElementBase element) {
        params.remove(Tson.elem(element));
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilder addParam(TsonElementBase element, int index) {
        params.add(index, Tson.elem(element));
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilder removeParamAt(int index) {
        params.remove(index);
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilder addParams(TsonElementBase... element) {
        for (TsonElementBase tsonElement : element) {
            addParam(tsonElement);
        }
        return this;
    }

    @Override
    public TsonDocumentHeaderBuilder addParams(Iterable<? extends TsonElementBase> element) {
        for (TsonElementBase tsonElement : element) {
            addParam(tsonElement);
        }
        return this;
    }

    @Override
    public TsonAnnotation toAnnotation() {
        TsonAnnotationBuilder b = Tson.annotation().setName("tson");
        if (version == null && encoding == null && params.isEmpty()) {
            b.add(Tson.string("v"+Tson.getVersion()));
        } else {
            b.add(Tson.pair("version", Tson.string(TsonUtils.isBlank(version) ? Tson.getVersion() : version.trim())));
            if(encoding!=null){
                b.add(Tson.pair("encoding", Tson.string(TsonUtils.isBlank(version) ? Tson.getVersion() : version.trim())));
            }
            for (TsonElement e : params) {
                b.add(e);
            }
        }
        return b.build();
    }

    @Override
    public TsonDocumentHeader build() {
        return new TsonDocumentHeaderImpl(
                TsonUtils.isBlank(version) ? Tson.getVersion() : version.trim(),
                encoding,
                params.toArray(TsonUtils.TSON_ELEMENTS_EMPTY_ARRAY)
        );
    }
}
