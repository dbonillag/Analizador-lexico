package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class InvocacionDeFuncion extends Sentencia {

	private Token identificador;
	private ArrayList<Expresion> argumentos;

	public InvocacionDeFuncion(Token identificador, ArrayList<Expresion> argumentos) {
		this.identificador = identificador;
		this.argumentos = argumentos;
	}

	public Token getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Token identificador) {
		this.identificador = identificador;
	}

	public ArrayList<Expresion> getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(ArrayList<Expresion> argumentos) {
		this.argumentos = argumentos;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Invocacion");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));

		TreeItem<String> sentencias = new TreeItem<String>("Lista Sentencias");
		raiz.getChildren().add(sentencias);

		for (Expresion arg : argumentos) {
			sentencias.getChildren().add(arg.getArbolVisual());
		}

		return raiz;

	}

}
