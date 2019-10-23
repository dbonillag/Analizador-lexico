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

	@Override
	public TreeItem<String> getArbolVisual() {
		// TODO Auto-generated method stub
		return null;
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

}
