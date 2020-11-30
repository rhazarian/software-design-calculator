package token;

public class Tokenizer {
    private static abstract class State {
        abstract void process(char ch);
        abstract void eof();
    }

    private class BasicState extends State {
        private Token last;

        @Override
        void process(char ch) {
            if (last != null) {
                token = last;
                last = null;
            }
            switch (ch) {
                case '(' -> last = new LParen();
                case ')' -> last = new RParen();
                case '+' -> last = new Plus();
                case '-' -> last = new Minus();
                case '/' -> last = new Div();
                case '*' -> last = new Mul();
                default -> {
                    if (ch >= '0' && ch <= '9') {
                        state = new NumberState();
                        state.process(ch);
                    } else if (!Character.isWhitespace(ch)) {
                        throw new IllegalArgumentException("unexpected character '%s'".formatted(ch));
                    }
                }
            }
       }

       @Override
       void eof() {
           if (last != null) {
               token = last;
               last = null;
           }
       }
    }

    private class NumberState extends State {
        private int value = 0;

        @Override
        void process(char ch) {
            if (ch >= '0' && ch <= '9') {
                value = value * 10 + (ch - '0');
            } else {
                token = new NumberToken(value);
                state = new BasicState();
                state.process(ch);
            }
        }

        @Override
        void eof() {
            token = new NumberToken(value);
            state = new BasicState();
        }
    }

    private State state = new BasicState();
    private Token token;

    private final String string;
    private int pos = 0;

    public Tokenizer(final String string) {
        this.string = string;
    }

    public boolean hasNext() {
        while (token == null && pos < string.length()) {
            state.process(string.charAt(pos));
            ++pos;
        }
        if (pos == string.length()) {
            state.eof();
        }
        return token != null;
    }

    public Token nextToken() {
        hasNext();
        final Token token = this.token;
        this.token = null;
        return token;
    }
}
