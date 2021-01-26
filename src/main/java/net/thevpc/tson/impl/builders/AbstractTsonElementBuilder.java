package net.thevpc.tson.impl.builders;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.util.TsonUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractTsonElementBuilder<T extends TsonElementBuilder> implements TsonElementBuilder {
    private String comments;
    private final List<TsonAnnotation> annotations = new ArrayList<>();

    @Override
    public String getComments() {
        return comments;
    }

    @Override
    public TsonAnnotation[] getAnnotations() {
        return annotations.toArray(TsonUtils.TSON_ANNOTATIONS_EMPTY_ARRAY);
    }

    @Override
    public T setComments(String comments) {
        if (comments != null) {
            comments = comments.trim();
            if (comments.isEmpty()) {
                comments = null;
            }
        }
        this.comments = comments;
        return (T)this;
    }

    @Override
    public T setAnnotations(TsonAnnotation[] annotations) {
        this.annotations.clear();
        addAnnotations(annotations);
        return (T)this;
    }

    @Override
    public T addAnnotations(TsonAnnotation... annotations) {
        for (TsonAnnotation annotation : annotations) {
            addAnnotation(annotation);
        }
        return (T)this;
    }

    @Override
    public T addAnnotations(Collection<TsonAnnotation> annotations) {
        if(annotations!=null){
            this.annotations.addAll(annotations);
        }
        return (T)this;
    }
    
    

    @Override
    public T addAnnotation(TsonAnnotation annotation) {
        if (annotation != null) {
            this.annotations.add(annotation);
        }
        return (T)this;
    }

    @Override
    public T addAnnotation(String name, TsonElementBase... elements) {
        return addAnnotation(Tson.annotation(name, elements));
    }

    @Override
    public T removeAnnotationAt(int index) {
        this.annotations.remove(index);
        return (T)this;
    }

    @Override
    public T annotation(String name, TsonElementBase... elements) {
        return addAnnotation(name, elements);
    }

    @Override
    public T comments(String comments) {
        return setComments(comments);
    }

    @Override
    public String toString() {
        return build().toString();
    }

    @Override
    public String toString(boolean compact) {
        return build().toString(compact);
    }

    @Override
    public String toString(TsonFormat format) {
        return build().toString(format);
    }

    @Override
    public T removeAllAnnotations() {
        annotations.clear();
        return (T)this;
    }

    @Override
    public TsonArrayBuilder toArray() {
        return (TsonArrayBuilder) this;
    }

    @Override
    public TsonObjectBuilder toObject() {
        return (TsonObjectBuilder) this;
    }

    @Override
    public TsonUpletBuilder toUplet() {
        return (TsonUpletBuilder) this;
    }

    @Override
    public TsonFunctionBuilder toFunction() {
        return (TsonFunctionBuilder) this;
    }

    @Override
    public TsonElementBuilder anchor(String name) {
        return addAnnotation(null,Tson.name(name));
    }

}
