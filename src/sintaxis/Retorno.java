package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;

import lexico.Token;

public class Retorno extends Sentencia {

	private Token tipoDato;
	
	
	public Retorno(Token tipoDato) {
	
		this.tipoDato = tipoDato;
	}



	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		// TODO Auto-generated method stub
		return null;
	}

}
