package net.thevpc.tson.impl.parser;

import net.thevpc.tson.*;
import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.*;

import java.util.*;

import net.thevpc.tson.impl.elements.*;
import net.thevpc.tson.impl.util.TsonUtils;

public final class ElementBuilderTsonParserVisitor implements TsonParserVisitor {

    private Object[] stack = new Object[10];
    private int stackSize = 0;

    public TsonElement getElement() {
        if (stackSize == 0) {
            return null;
        }
        if (stackSize != 1) {
            throw new IllegalArgumentException("Invalid stack state");
        }
        TsonElement u = peek();
        return u;
    }

    public TsonDocument getDocument() {
        if (stackSize == 0) {
            return null;
        }
        if (stackSize != 1) {
            throw new IllegalArgumentException("Invalid stack state");
        }
        TsonDocument u = peek();
        return u;
    }

    private static class AnnotationNode {

        String id;
        List<TsonElement> elements = new ArrayList<>();

        public AnnotationNode(String id) {
            this.id = id;
        }
    }

    private static class PartialElemNode {

        public TsonElement element;
        public String name;
        public ArrayList<TsonElement> params;
        public ArrayList<TsonElement> array;
        public ArrayList<TsonElement> object;
        boolean decorated;
        String comments;
        List<TsonAnnotation> annotations;
    }

    @Override
    public void visitKeyValueEnd() {
        TsonElement b = pop();
        TsonElement a = peek();
        repush(Tson.pair(a, b));
    }

    @Override
    public void visitElementStart() {
        push(new PartialElemNode());
    }

    @Override
    public void visitNamedStart(String id) {
        PartialElemNode a = peek();
        a.name = id;
    }

    @Override
    public void visitNamedArrayStart() {
        PartialElemNode a = peek();
        a.array = new ArrayList<>();
    }

    @Override
    public void visitArrayStart() {
        PartialElemNode a = peek();
        a.array = new ArrayList<>();
    }

    @Override
    public void visitObjectStart() {
        PartialElemNode a = peek();
        a.object = new ArrayList<>();
    }

    @Override
    public void visitNamedObjectStart() {
        PartialElemNode a = peek();
        a.object = new ArrayList<>();
    }

    @Override
    public void visitParamsStart() {
        PartialElemNode a = peek();
        a.params = new ArrayList<>();
    }

    @Override
    public void visitParamElementStart() {
    }

    @Override
    public void visitParamElementEnd() {
        TsonElement o = pop();
        PartialElemNode a = peek();
        a.params.add(o);
    }

    //    @Override
//    public void visitObjectElementStart() {
//        // do nothing
//    }
    @Override
    public void visitObjectElementEnd() {
        TsonElement o = pop();
        PartialElemNode a = peek();
        a.object.add(o);
    }

    //    @Override
//    public void visitArrayElementStart() {
//        //do nothing
//    }
    @Override
    public void visitArrayElementEnd() {
        TsonElement o = pop();
        PartialElemNode a = peek();
        a.array.add(o);
    }

    @Override
    public void visitComments(String comments) {
        PartialElemNode a = peek();
        a.comments = comments;
        if (comments != null) {
            a.decorated = true;
        }
    }

    private void repushDecorated(TsonElement base, PartialElemNode a) {
        if (a.decorated) {
            repush(
                    TsonElementDecorator.of(
                            base,
                            a.comments,
                            a.annotations
                    )
            );

        } else {
            repush(base);
        }
    }

    @Override
    public void visitObjectEnd() {
        PartialElemNode a = peek();
        repushDecorated(new TsonObjectImpl(null, TsonUtils.unmodifiableElements(a.object)), a);
    }

    @Override
    public void visitFunctionEnd() {
        PartialElemNode a = peek();
        repushDecorated(new TsonFunctionImpl(a.name, TsonUtils.unmodifiableElements(a.params)), a);
    }

    @Override
    public void visitUpletEnd() {
        PartialElemNode a = peek();
        repushDecorated(new TsonUpletImpl(TsonUtils.unmodifiableElements(a.params)), a);
    }

    @Override
    public void visitArrayEnd() {
        PartialElemNode a = peek();
        repushDecorated(TsonUtils.toArray(a.array), a);
    }

    @Override
    public void visitNamedObjectEnd() {
        PartialElemNode a = peek();
        TsonElementHeader h = null;
        if (a.name != null || !a.params.isEmpty()) {
            h = new TsonElementHeaderImpl(a.name, TsonUtils.unmodifiableElements(a.params));
        }
        repushDecorated(new TsonObjectImpl(h, TsonUtils.unmodifiableElements(a.object)), a);
    }

    @Override
    public void visitNamedArrayEnd() {
        PartialElemNode a = peek();
        TsonElementHeader h = null;
        if (a.name != null || !a.params.isEmpty()) {
            h = new TsonElementHeaderImpl(a.name, TsonUtils.unmodifiableElements(a.params));
        }
        repushDecorated(TsonUtils.toArray(h, a.array), a);
    }

    @Override
    public void visitPrimitiveEnd(TsonElement primitiveElement) {
        int index = stackSize - 1;
        PartialElemNode a = (PartialElemNode) stack[index];
        if (a.decorated) {
            stack[index] = (TsonElementDecorator.of(
                    primitiveElement,
                    a.comments,
                    a.annotations
            ));

        } else {
            stack[index] = primitiveElement;
        }

        //PartialElemNode a = peek();
        //repushDecorated(primitiveElement, a);
    }

    @Override
    public void visitAnnotationStart(String annotationName) {
        push(new AnnotationNode(annotationName));
    }

    @Override
    public void visitAnnotationEnd() {
        AnnotationNode a = pop();
        PartialElemNode n = peek();
        if (n.annotations == null) {
            n.annotations = new ArrayList<>();
            n.decorated = true;
        }
        n.annotations.add(new TsonAnnotationImpl(a.id, TsonUtils.unmodifiableElements(a.elements)));
    }

    @Override
    public void visitAnnotationParamEnd() {
        TsonElement e = pop();
        AnnotationNode n = peek();
        n.elements.add(e);
    }

    @Override
    public void visitDocumentEnd() {
        TsonElement e = peek();
        repush(TsonParserUtils.elementToDocument(e));
    }

    //---------------------------------------------------------------------
    private <T> T peek() {
        return (T) stack[stackSize - 1];
    }

    private <T> T pop() {
        T t = (T) stack[--stackSize];
        stack[stackSize + 1] = null;
        return t;
    }

    private void repush(Object n) {
        stack[stackSize - 1] = n;
    }

    private void push(Object n) {
        try {
            stack[stackSize] = n;
        } catch (ArrayIndexOutOfBoundsException e) {
            Object[] stack2 = new Object[stackSize + 20];
            System.arraycopy(stack, 0, stack2, 0, stack.length);
            stack = stack2;
        }
        stackSize++;
    }
}
