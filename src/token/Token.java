package token;

interface Token {
    void accept(TokenVisitor visitor);
}
