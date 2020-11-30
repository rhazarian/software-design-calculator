package parser;

import token.*;

import java.util.*;

public class Parser {
    private static class Visitor implements TokenVisitor {
        private static final Map<Class<? extends Operation>, Integer> PRIORITIES = new HashMap<>() {{
            put(Plus.class, 1);
            put(Minus.class, 1);
            put(Mul.class, 2);
            put(Div.class, 2);
        }};

        private final Deque<Token> stack = new ArrayDeque<>();
        private final List<Token> result = new ArrayList<>();

        public List<Token> getResult() {
            while (!stack.isEmpty()) {
                final Token lastToken = stack.peek();
                if (lastToken instanceof Operation) {
                    result.add(lastToken);
                    stack.pop();
                } else {
                    throw new IllegalArgumentException("unmatched '('");
                }
            }
            return result;
        }

        @Override
        public void visit(NumberToken token) {
            result.add(token);
        }

        @Override
        public void visit(LParen token) {
            stack.push(token);
        }

        @Override
        public void visit(RParen token) {
            while (!stack.isEmpty()) {
                final Token lastToken = stack.peek();
                if (lastToken instanceof LParen) {
                    stack.pop();
                    break;
                } else if (lastToken instanceof Operation) {
                    result.add(lastToken);
                    stack.pop();
                } else {
                    throw new IllegalArgumentException("unmatched ')'");
                }
            }
        }

        @Override
        public void visit(Operation token) {
            while (!stack.isEmpty()) {
                final Token lastToken = stack.peek();
                if (lastToken instanceof Operation && PRIORITIES.get(token.getClass()) <= PRIORITIES.get(lastToken.getClass())) {
                    result.add(lastToken);
                    stack.pop();
                } else {
                    break;
                }
            }
            stack.push(token);
        }
    }

    public List<Token> parse(String string) {
        final Visitor visitor = new Visitor();
        final Tokenizer tokenizer = new Tokenizer(string);
        while (tokenizer.hasNext()) {
            tokenizer.nextToken().accept(visitor);
        }
        return visitor.getResult();
    }
}
