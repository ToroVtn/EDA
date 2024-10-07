import java.util.Scanner;


public class ExpTree implements ExpressionService {

    private Node root;

    public ExpTree() {
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line= inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
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

                if (!lineScanner.hasNext("\\)")) {
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
                throw new RuntimeException(String.format("bad term: %s", lineScanner));
            }

            return n;
        }



    }  // end Node class



    // hasta que armen los testeos
    public static void main(String[] args) {
        ExpressionService myExp = new ExpTree();

    }

}  // end ExpTree class