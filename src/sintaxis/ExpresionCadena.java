package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class ExpresionCadena extends Expresion {

	Token cadenaDeCaracteres;

	Expresion expresion;

	public ExpresionCadena(Token cadenaDeCaracteres, Expresion expresion) {
		super();
		this.cadenaDeCaracteres = cadenaDeCaracteres;
		this.expresion = expresion;
	}

	public Token getCadenaDeCaracteres() {
		return cadenaDeCaracteres;
	}

	public void setCadenaDeCaracteres(Token cadenaDeCaracteres) {
		this.cadenaDeCaracteres = cadenaDeCaracteres;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<String>("Expresion Cadena");
		raiz.getChildren().add(new TreeItem<>("Cadena: " + cadenaDeCaracteres.getPalabra()));
		if (expresion != null) {
			raiz.getChildren().add(expresion.getArbolVisual());
		}

		return raiz;

	}

	@Override
	public String obtenerTipo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

}
