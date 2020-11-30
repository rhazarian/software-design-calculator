package token;

public class Tokenizer {
    private static abstract class State {
        abstract void process(char ch);
    }

    private class BasicState extends State {
        @Override
        void process(char ch) {
            switch (ch) {
                case '(' -> token = new LParen();
                case ')' -> token = new RParen();
                case '+' -> token = new Plus();
                case '-' -> token = new Minus();
                case '/' -> token = new Div();
                case '*' -> token = new Mul();
                default -> {
                    if (ch >= '0' && ch <= '9') {
                        state = new NumberState();
                        state.process(ch);
                    } else if (!Character.isWhitespace(ch)) {
                        throw new IllegalArgumentException("unexpected character '%s'".formatted(ch));
                    }
                }
            };
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
        return token != null;
    }

    public Token nextToken() {
        hasNext();
        final Token token = this.token;
        this.token = null;
        return token;
    }
}
