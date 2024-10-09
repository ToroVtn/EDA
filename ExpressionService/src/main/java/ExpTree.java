import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExpTree implements ExpressionService {

    private Node root;
    private Map<String, Double> var;
    private boolean varSet = false;

    public ExpTree() {
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        buildTree(inputScanner.next());
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root= new Node(lineScanner);
        lineScanner.close();
    }

    static final class Node {
        private String data;
        private Node left, right;

        private Scanner lineScanner;

        public Node(Scanner theLineScanner) {
            lineScanner= theLineScanner;

            Node auxi = buildExpression();
            data= auxi.data;
            left= auxi.left;
            right= auxi.right;

            if (lineScanner.hasNext() )
                throw new RuntimeException("Bad expression");
        }

        private Node() 	{
        }

        private Node buildExpression() {
            Node n = new Node();

            if(lineScanner.hasNext("\\(")) {
                lineScanner.next();

                n.left = buildExpression();

                if (!lineScanner.hasNext()) {
                    throw new RuntimeException("missing or invalid operator");
                }
                n.data = lineScanner.next();

                if (!Utils.isOperator(n.data)) {
                    throw new RuntimeException("bad operator");
                }

                n.right = buildExpression();

                if (lineScanner.hasNext("\\)")) {
                    lineScanner.next();
                } else {
                    throw new RuntimeException("missing )");
                }
                return n;
            }

            if(!lineScanner.hasNext()) {
                throw new RuntimeException("missing expression");
            }

            n.data = lineScanner.next();
            if(!Utils.isConstant(n.data)){
                if(!isVariable(n.data))
                    throw new RuntimeException(String.format("bad term: %s", lineScanner));
            }

            return n;
        }

        private boolean isVariable(String data) {
            return !data.matches("[0-9].+");
        }
    }

    public double eval(){
        if (!varSet) throw new RuntimeException("variables not set");
        return evalRec(root);
    }

    private double evalRec(Node node) {
        if(node.left != null){ // && node.right != null
            switch(node.data){
                case "+":   return evalRec(node.left) + evalRec(node.right);
                case "-":   return evalRec(node.left) - evalRec(node.right);
                case "*":   return evalRec(node.left) * evalRec(node.right);
                case "/":   return evalRec(node.left) / evalRec(node.right);
                case "^":   return Math.pow(evalRec(node.left), evalRec(node.right));
            }
        }

        if(Utils.isConstant(node.data)) return Double.valueOf(node.data);

        Scanner scan = new Scanner(System.in);
        System.out.printf("%s = ", node.data);
        return scan.nextDouble();
    }

    public void setVar(Map<String, Double> variables) {
        var = variables;
        varSet = true;
    }

    // hasta que armen los testeos
    public static void main(String[] args) {
        ExpressionService myExp = new ExpTree();

        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 2.0);
        myExp.setVar(variables);

        System.out.println(myExp.eval());
    }
}