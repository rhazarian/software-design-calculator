package token;

public class NumberToken implements Token {
    private final int value;

    public NumberToken(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "NUMBER(%d)".formatted(value);
    }
}
