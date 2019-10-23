package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Retorno extends Sentencia {

	private Token tipoDato;
	
	
	public Retorno(Token tipoDato) {
	
		this.tipoDato = tipoDato;
	}



	@Override
	public TreeItem<String> getArbolVisual() {
		// TODO Auto-generated method stub
		return null;
	}



	public Token getTipoDato() {
		return tipoDato;
	}



	public void setTipoDato(Token tipoDato) {
		this.tipoDato = tipoDato;
	}

}
