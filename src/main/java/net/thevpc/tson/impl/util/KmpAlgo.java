package net.thevpc.tson.impl.util;

import java.util.Arrays;

public class KmpAlgo {
    public static void main(String[] args) {
        KmpAlgo kmp = KmpAlgo.compile("AB");
        System.out.println(kmp.search("EZABH"));
        for (char c : "EZABH".toCharArray()) {
            if (kmp.next(c)) {
                System.out.println("OK " + c);
            } else {
                System.out.println("KO " + c);
            }
        }
    }

    private char[] pattern;
    private int[] lsp;
    private int incrementalIndex = 0;
    private int len = 0;

    public static KmpAlgo compile(char[] pattern, int offset, int count) {
        return new KmpAlgo(Arrays.copyOfRange(pattern, offset, offset + count));
    }
    public static KmpAlgo compile(char[] pattern) {
        return new KmpAlgo(pattern);
    }

    public static KmpAlgo compile(String pattern) {
        return new KmpAlgo(pattern);
    }

    private KmpAlgo(char[] pattern, int offset, int count) {
        this(Arrays.copyOfRange(pattern, offset, offset + count));

    }

    private KmpAlgo(String pattern) {
        this(pattern.toCharArray());
    }

    private KmpAlgo(char[] patternChars) {
        init(patternChars);
    }

    private void init(char[] patternChars) {
        this.pattern = patternChars;
        int len0 = patternChars.length;
        this.len = len0;
        int[] lsp0 = new int[len0];
//        this.lsp[0] = 0;  // Base case
        for (int i = 1; i < len0; i++) {
            // Start by assuming we're extending the previous LSP
            int j = lsp0[i - 1];
            while (j > 0 && patternChars[i] != patternChars[j]) {
                j = lsp0[j - 1];
            }
            if (patternChars[i] == patternChars[j]) {
                j++;
            }
            lsp0[i] = j;
        }
        this.lsp = lsp0;
    }

    public int length() {
        return len;
    }
    public KmpAlgo reset() {
        incrementalIndex = 0;
        return this;
    }

    public boolean next(char c) {
        int j = incrementalIndex;
        while (j > 0 && c != pattern[j]) {
            // Fall back in the pattern
            j = lsp[j - 1];  // Strictly decreasing
        }
        if (c == pattern[j]) {
            // Next char matched, increment position
            j++;
            boolean ok = j == pattern.length;
            incrementalIndex = j;
            return ok;
        }
        incrementalIndex = j;
        return false;
    }

    public int search(String text) {
        int j = 0;  // Number of chars matched in pattern
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (j > 0 && c != pattern[j]) {
                // Fall back in the pattern
                j = lsp[j - 1];  // Strictly decreasing
            }
            if (c == pattern[j]) {
                // Next char matched, increment position
                j++;
                if (j == pattern.length)
                    return i - (j - 1);
            }
        }

        return -1;  // Not found
    }

}
