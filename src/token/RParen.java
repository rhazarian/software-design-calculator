package token;

public class RParen implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
