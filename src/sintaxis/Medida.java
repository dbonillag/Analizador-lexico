package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Medida extends Sentencia {

	private Token identificadorArreglo;
	private Token identificadorVariable;

	public Medida(Token identificadorArreglo, Token identificadorVariable) {
		super();
		this.identificadorArreglo = identificadorArreglo;
		this.identificadorVariable = identificadorVariable;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Medida");
		raiz.getChildren().add(new TreeItem<>("Id Arreglo: " + identificadorArreglo.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("id Variable: " + identificadorVariable.getPalabra()));

		return raiz;

	}
}
