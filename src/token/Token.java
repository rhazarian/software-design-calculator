package token;

public interface Token {
    void accept(TokenVisitor visitor);
}
