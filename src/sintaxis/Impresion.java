package sintaxis;

import javafx.scene.control.TreeItem;

public class Impresion extends Sentencia {

	private ExpresionCadena cadena;

	public Impresion(ExpresionCadena cadena) {
		this.cadena = cadena;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Impresion");
		raiz.getChildren().add(cadena.getArbolVisual());
		return raiz;

	}
}
