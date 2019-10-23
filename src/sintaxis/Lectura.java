package sintaxis;


import javafx.scene.control.TreeItem;

public class Lectura extends Sentencia {

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Lectura");

		return raiz;

	}
}
