package misc.utils;

import java.util.Stack;

public final class ExpressionEvaluator {

    private ExpressionEvaluator() {
    }

    public static int evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder stringBuffer = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    stringBuffer.append(tokens[i++]);
                i--;
                values.push(Integer.parseInt(stringBuffer.toString()));
            } else if (tokens[i] == '(')
                operators.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (operators.peek() != '(')
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                operators.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/') {
                while (!operators.empty() && hasPrecedence(tokens[i], operators.peek()))
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                operators.push(tokens[i]);
            }
        }
        while (!operators.empty())
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    private static boolean hasPrecedence(char firstOperation, char secondOperation) {
        if (secondOperation == '(' || secondOperation == ')')
            return false;
        return (firstOperation != '*' && firstOperation != '/') || (secondOperation != '+' && secondOperation != '-');
    }

    private static int applyOperation(char operation, int secondNum, int firstNum) {
        switch (operation) {
            case '+':
                return firstNum + secondNum;
            case '-':
                return firstNum - secondNum;
            case '*':
                return firstNum * secondNum;
            case '/':
                if (secondNum == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return firstNum / secondNum;
        }
        return 0;
    }
}
