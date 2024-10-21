// bajar el paquete nativo
// https://gluonhq.com/products/javafx/ 

// en el VM poner el lib del paquete nativo
// --module-path C:\Users\lgomez\Downloads\javafx-sdk-11.0.2\lib --add-modules javafx.fxml,javafx.controls


import controller.GraphicsTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class TestGUI extends Application {

	public static void main(String[] args) {
		// GUI
		//adjust javafx version on POM and maybe run config before running
		launch(args);
	}

    @Override
	public void start(Stage stage) {
		stage.setTitle("Drawing the BST");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 1300, 700);

		BST<Integer> myTree = createModel();
		GraphicsTree<Integer> c = new GraphicsTree<>(myTree);
		/*BST<Person> myTree = createModel2();
		GraphicsTree<Person> c = new GraphicsTree<>(myTree);*/

		c.widthProperty().bind(scene.widthProperty());
		c.heightProperty().bind(scene.heightProperty());
	
		root.getChildren().add(c);
		stage.setScene(scene);
		stage.show();
		

	}

    
	private BST<Integer> createModel() {
		BST<Integer> myTree = new BST<>();
		myTree.insert(35);
		myTree.insert(74);
		myTree.insert(20);
		myTree.insert(22);
		myTree.insert(55);
		myTree.insert(15);
		myTree.insert(8);
		myTree.insert(27);
		myTree.insert(25);

		myTree.remove(20);

		myTree.setTraversal(BST.Traversal.PREORDER);
		myTree.forEach(t -> System.out.print(t + " "));

		return myTree;
	}

	private BST<Person> createModel2() {
		BST<Person> myTree = new BST<>();
		myTree = new BST<>();
		myTree.insert(new Person( 50, "Ana" ));
		myTree.insert(new Person( 60, "Juan") );
		myTree.insert(new Person( 80, "Sergio") );
		myTree.insert(new Person( 20, "Lila ") );
		myTree.insert(new Person( 77, "Ana") );
		//myTree.inOrder();

		return myTree;
	}
	

}