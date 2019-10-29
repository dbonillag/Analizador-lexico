package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Retorno extends Sentencia {

	private Expresion expresion;

	public Retorno(Expresion expresion) {

		this.expresion = expresion;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Retorno");
		raiz.getChildren().add(expresion.getArbolVisual());

		return raiz;

	}

}
