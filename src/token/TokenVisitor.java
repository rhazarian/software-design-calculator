package token;

interface TokenVisitor {
    void visit(NumberToken token);
    void visit(LParen token);
    void visit(RParen token);
    void visit(Operation token);
}
