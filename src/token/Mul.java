package token;

public class Mul extends Operation {
    @Override
    public int evaluate(int a, int b) {
        return a * b;
    }

    @Override
    public String toString() {
        return "MUL";
    }
}
