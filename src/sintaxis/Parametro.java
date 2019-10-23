package sintaxis;


import javafx.scene.control.TreeItem;
import lexico.Token;

public class Parametro {

	Token tipoDato;
	Token nombre;

	public Parametro(Token tipoDato, Token nombre) {
		super();
		this.tipoDato = tipoDato;
		this.nombre = nombre;
	}

	public Token getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(Token tipoDato) {
		this.tipoDato = tipoDato;
	}

	public Token getNombre() {
		return nombre;
	}

	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Parametro");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + nombre.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("Tipo de dato: " + tipoDato.getPalabra()));

		return raiz;

	}
}
