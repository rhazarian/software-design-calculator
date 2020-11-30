package token;

public class LParen implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
