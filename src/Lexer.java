import java.util.regex.*;
import java.util.*;

class Token {
    String type;
    String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + ": " + value;
    }
}

public class Lexer {
    private static final Map<String, String> tokenPatterns = new LinkedHashMap<>();

    static {
        // Định nghĩa regex cho từng loại token
        tokenPatterns.put("NUMBER", "\\d+");
        tokenPatterns.put("KEYWORD", "\\b(if|else|while|return)\\b");
        tokenPatterns.put("IDENTIFIER", "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b");
        tokenPatterns.put("OPERATOR", "==|!=|&&|\\|\\||[+\\-*/=]");
        tokenPatterns.put("SEPARATOR", "[(){};]");
        tokenPatterns.put("WHITESPACE", "\\s+"); // Bỏ qua khoảng trắng
    }

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        String regex = String.join("|", tokenPatterns.values());

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            for (Map.Entry<String, String> entry : tokenPatterns.entrySet()) {
                if (matcher.group().matches(entry.getValue())) {
                    if (!entry.getKey().equals("WHITESPACE")) { // Bỏ qua khoảng trắng
                        tokens.add(new Token(entry.getKey(), matcher.group()));
                    }
                    break;
                }
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String code = "if (x == 10) { return y + 5; }";
        List<Token> tokens = tokenize(code);

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}

