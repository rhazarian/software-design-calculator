package token;

public class Plus extends Operation {
    @Override
    public int evaluate(int a, int b) {
        return a + b;
    }
}
