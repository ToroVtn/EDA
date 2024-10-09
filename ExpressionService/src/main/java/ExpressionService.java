import java.util.Map;

public interface ExpressionService {

    // lanza exception si no se puede evaluar porque hay algo mal formado en la expresion
	double eval();

    void setVar(Map<String, Double> variables);

//	void preorder();

//	void inorder();

//	void postorder();

}