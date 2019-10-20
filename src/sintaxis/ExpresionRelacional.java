package sintaxis;

import lexico.Token;

public class ExpresionRelacional extends Expresion {
	
	ExpresionAritmetica expresionIzquierda;
	Token operadorAritmetico;
	ExpresionAritmetica expresionDerecha;
	public ExpresionRelacional(ExpresionAritmetica expresionIzquierda, Token operadorAritmetico,
			ExpresionAritmetica expresionDerecha) {
		super();
		this.expresionIzquierda = expresionIzquierda;
		this.operadorAritmetico = operadorAritmetico;
		this.expresionDerecha = expresionDerecha;
	}
	public ExpresionAritmetica getExpresionIzquierda() {
		return expresionIzquierda;
	}
	public void setExpresionIzquierda(ExpresionAritmetica expresionIzquierda) {
		this.expresionIzquierda = expresionIzquierda;
	}
	public Token getOperadorAritmetico() {
		return operadorAritmetico;
	}
	public void setOperadorAritmetico(Token operadorAritmetico) {
		this.operadorAritmetico = operadorAritmetico;
	}
	public ExpresionAritmetica getExpresionDerecha() {
		return expresionDerecha;
	}
	public void setExpresionDerecha(ExpresionAritmetica expresionDerecha) {
		this.expresionDerecha = expresionDerecha;
	}
	
	

}
