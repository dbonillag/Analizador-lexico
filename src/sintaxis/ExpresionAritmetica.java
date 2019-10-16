package sintaxis;

import lexico.Token;

public class ExpresionAritmetica extends Expresion {

	private ExpresionAritmetica expresionAritmetica1, expresionAritmetica2;
	private Token operadorAritmetico;
	private Token termino;

	public ExpresionAritmetica(Token termino) {
		this.termino = termino;
	}

	public ExpresionAritmetica(Token termino, Token operadorAritmetico, ExpresionAritmetica ea) {
		this.termino = termino;
		this.operadorAritmetico = operadorAritmetico;
		this.expresionAritmetica1 = ea;
	}

	public ExpresionAritmetica(ExpresionAritmetica ea) {
		this.expresionAritmetica1 = ea;
	}

	public ExpresionAritmetica(ExpresionAritmetica ea1, Token operadorAritmetico, ExpresionAritmetica ea2) {
		this.expresionAritmetica1 = ea1;
		this.operadorAritmetico = operadorAritmetico;
		this.expresionAritmetica2 = ea2;
	}

	public ExpresionAritmetica getExpresionAritmetica1() {
		return expresionAritmetica1;
	}

	public void setExpresionAritmetica1(ExpresionAritmetica expresionAritmetica1) {
		this.expresionAritmetica1 = expresionAritmetica1;
	}

	public ExpresionAritmetica getExpresionAritmetica2() {
		return expresionAritmetica2;
	}

	public void setExpresionAritmetica2(ExpresionAritmetica expresionAritmetica2) {
		this.expresionAritmetica2 = expresionAritmetica2;
	}

	public Token getOperadorAritmetico() {
		return operadorAritmetico;
	}

	public void setOperadorAritmetico(Token operadorAritmetico) {
		this.operadorAritmetico = operadorAritmetico;
	}

	public Token getTermino() {
		return termino;
	}

	public void setTermino(Token termino) {
		this.termino = termino;
	}

	@Override
	public String toString() {

		if (termino != null && expresionAritmetica1 == null && expresionAritmetica2 == null
				&& operadorAritmetico == null) {
			return termino.toString();
		} else if (termino != null && expresionAritmetica1 != null && expresionAritmetica2 == null
				&& operadorAritmetico != null) {
			return termino.toString() + " " + operadorAritmetico.toString() + " " + expresionAritmetica1.toString();
		} else if (termino == null && expresionAritmetica1 != null && expresionAritmetica2 == null
				&& operadorAritmetico == null) {
			return expresionAritmetica1.toString();
		} else {
			return expresionAritmetica1.toString() + " " + operadorAritmetico.toString() + " "
					+ expresionAritmetica2.toString();
		}

	}

}
