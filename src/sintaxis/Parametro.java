package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;

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
		return new TreeItem(nombre.getPalabra() + " : " + tipoDato.getPalabra());
	}
}
