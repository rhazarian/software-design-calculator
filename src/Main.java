import calculator.Calculator;
import parser.Parser;
import token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String expression = reader.readLine();
            final List<Token> tokens = new Parser().parse(expression);
            System.out.println(tokens.stream().map(Objects::toString).collect(Collectors.joining(" ")));
            System.out.println(new Calculator().calculate(tokens));
        } catch (final IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
