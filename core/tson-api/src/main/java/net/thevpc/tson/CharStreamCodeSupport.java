package net.thevpc.tson;

public interface CharStreamCodeSupport {
    void reset();

    String getErrorMessage();

    boolean isValid();

    void next(char cbuf[]);

    void next(char cbuf[], int off, int len);

    void next(char c);
}
