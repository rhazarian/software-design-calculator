package calculator;

import token.*;

import java.util.*;

public class Calculator {
    private static class Visitor implements TokenVisitor {
        private final Deque<Integer> stack = new ArrayDeque<>();

        public int getResult() {
            if (stack.isEmpty()) {
                throw new IllegalArgumentException("the expression is empty");
            }
            if (stack.size() > 1) {
                throw new IllegalArgumentException("there are unused numbers in the expression");
            }
            return stack.pop();
        }

        @Override
        public void visit(NumberToken token) {
            stack.add(token.getValue());
        }

        @Override
        public void visit(Operation token) {
            if (stack.size() < 2) {
                throw new IllegalArgumentException("an operation should have exactly two arguments");
            }
            final int a = stack.pop();
            final int b = stack.pop();
            stack.push(token.evaluate(a, b));
        }

        @Override
        public void visit(LParen token) {
            throw new Error("parenthesis should never occur in RPN");
        }

        @Override
        public void visit(RParen token) {
            throw new Error("parenthesis should never occur in RPN");
        }
    };

    public static int calculate(List<Token> tokens) {
        final Visitor visitor = new Visitor();
        for (final Token token : tokens) {
            token.accept(visitor);
        }
        return visitor.getResult();
    }
}
