package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Lectura extends Sentencia {

	private Token identificador;

	public Lectura(Token identificador) {
		this.identificador = identificador;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Lectura");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));
		return raiz;

	}
}
