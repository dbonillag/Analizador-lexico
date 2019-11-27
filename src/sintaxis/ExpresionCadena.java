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
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		if (expresion!=null) {
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
		
	}

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		
		return "text";
	}

	@Override
	public String getJavaCode() {
		String codigo = cadenaDeCaracteres.getPalabra();
		if(expresion!=null) {
			codigo+="+ ("+expresion+")";
		}
		
		return codigo;
	}

}
