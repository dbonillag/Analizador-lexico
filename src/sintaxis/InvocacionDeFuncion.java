package sintaxis;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import lexico.Token;

public class InvocacionDeFuncion extends Sentencia {

	private Token identificador;
	private ArrayList<Expresion> argumentos;
	
	
	public InvocacionDeFuncion(Token identificador, ArrayList<Expresion> argumentos) {
		this.identificador = identificador;
		this.argumentos = argumentos;
	}


	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		// TODO Auto-generated method stub
		return null;
	}

}
