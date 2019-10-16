package sintaxis;

import lexico.Token;

public class Variable {

	private Token identificador;
	private Expresion expresion;

	public Variable(Token identificador) {
		super();
		this.identificador = identificador;
	}

	public Variable(Token identificador, Expresion expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
	}

	public Token getIdentificador() {
		return identificador;
	}

	@Override
	public String toString() {
		return "Variable [identificador=" + identificador + ", expresion=" + expresion + "]";
	}

}
