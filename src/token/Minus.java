package token;

public class Minus extends Operation {
    @Override
    public int evaluate(int a, int b) {
        return a - b;
    }
}
