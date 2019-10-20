package sintaxis;

import java.util.ArrayList;

import lexico.Token;

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
	
	

	
	
	

}
