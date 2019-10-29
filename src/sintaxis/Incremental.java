package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Incremental extends Sentencia {

	private Token identificador;

	public Incremental(Token identificador) {
		this.identificador = identificador;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Incremental");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));

		return raiz;

	}

}
