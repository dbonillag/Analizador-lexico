package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class DeclaracionDeVariable extends Sentencia {

	private Token tipoDato;
	private Token identificador;

	public DeclaracionDeVariable(Token tipoDato, Token identificador) {
		this.tipoDato = tipoDato;
		this.identificador = identificador;

	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Parametro");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("Tipo de dato: " + tipoDato.getPalabra()));

		return raiz;

	}

}
