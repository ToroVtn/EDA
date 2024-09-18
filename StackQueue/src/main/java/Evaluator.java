import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Evaluator {
    private Stack<Double> stack = new Stack<>();
    private Scanner lineScanner;

    public Double evaluate() {
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        System.out.print("Introduzca la expresión en notación prefija: ");
        lineScanner = new Scanner(inputScanner.next()).useDelimiter("\\s+");
        return evaluatePostfix(infixToPostfix());
    }

    private Double evaluatePostfix(String in) {
        Scanner expression = new Scanner(in).useDelimiter("\\s+");
        while (expression.hasNext()) {
            String line = expression.next();
            operate(line);
        }
        return stack.pop();
    }

    public String infixToPostfix() {
        String postfija= "";
        Stack<String> auxStack = new Stack<>();
        while( lineScanner.hasNext() ) {
            String currentToken = lineScanner.next();
            if ( !isOperand(currentToken) ) {
                if(isVariable(currentToken)) {
                    currentToken = String.valueOf(vbles.get(currentToken));
                }
                postfija += String.format("%s ", currentToken);
            }
            else {
                while (!auxStack.empty() && getPrecedence(auxStack.peek(), currentToken) ) {
                    postfija += String.format("%s ", auxStack.pop() );
                }
                auxStack.push(currentToken);
                if(currentToken.equals(")")){
                    while(!auxStack.peek().equals("(")){
                        auxStack.pop();
                        postfija += String.format("%s ", auxStack.pop());
                    }
                    auxStack.pop();
                }
            }
        }
        while ( !auxStack.empty() ) {
            postfija += String.format("%s ", auxStack.pop() );
        }
        return postfija;
    }

    private boolean isOperand(String s) {
        return mapping.containsKey(s);
    }

    private boolean isVariable(String s){
        return vbles.containsKey(s);
    }

    private static Map<String, Double> vbles = new HashMap<String, Double>()
    { { put("nro1", 0.2); put("x", -2.0); put("y", 2.0); } };

    public void operate(String line) {
        switch (line) {
            case "+": {
                check();
                stack.push(stack.pop() + stack.pop());
                return;
            }
            case "-": {
                check();
                Double sub = stack.pop();
                stack.push(stack.pop() - sub);
                return;
            }
            case "*": {
                check();
                stack.push(stack.pop() * stack.pop());
                return;
            }
            case "/": {
                check();
                Double div = stack.pop();
                stack.push(stack.pop() / div);
                return;
            }
            case "^" : {
                check();
                Double pow = stack.pop();
                stack.push(Math.pow(stack.pop(), pow));
                return;
            }
            default: stack.push(Double.parseDouble(line));
        }
    }

    private void check() {
        if(stack.size() < 2) throw new IllegalArgumentException("invalid posfix expression");
    }

    private static Map<String, Integer> mapping = new HashMap<>(){
        {   put("+", 0); put("-", 1); put("*", 2); put("/", 3); put("^", 4); put("(", 5); put(")", 6);  }
    };

    private static boolean precedenceMatrix[][] = {
            {true, true, false, false, false, false, false},
            {true, true, false, false, false, false, false},
            {true, true, true, true, false, false, false},
            {true, true, true, true, false, false, false},
            {true, true, true, true, false, false, false},
            {false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false}
    };

    private boolean getPrecedence(String last, String current){
        return precedenceMatrix[mapping.get(last)][mapping.get(current)];
    }
}

