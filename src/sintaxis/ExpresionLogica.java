package sintaxis;

import lexico.Token;

public class ExpresionLogica extends Expresion {
	
	ExpresionRelacional expresionRelacionalIzq,expresionRelacionalDer;
	Token operadorLogico;
	public ExpresionLogica(ExpresionRelacional expresionRelacionalIzq, ExpresionRelacional expresionRelacionalDer,
			Token operadorLogico) {
		super();
		this.expresionRelacionalIzq = expresionRelacionalIzq;
		this.expresionRelacionalDer = expresionRelacionalDer;
		this.operadorLogico = operadorLogico;
	}
	
	
	
	public ExpresionLogica(ExpresionRelacional expresionRelacional) {
		super();
		this.expresionRelacionalIzq = expresionRelacional;
	}



	public ExpresionRelacional getExpresionRelacionalIzq() {
		return expresionRelacionalIzq;
	}
	public void setExpresionRelacionalIzq(ExpresionRelacional expresionRelacionalIzq) {
		this.expresionRelacionalIzq = expresionRelacionalIzq;
	}
	public ExpresionRelacional getExpresionRelacionalDer() {
		return expresionRelacionalDer;
	}
	public void setExpresionRelacionalDer(ExpresionRelacional expresionRelacionalDer) {
		this.expresionRelacionalDer = expresionRelacionalDer;
	}
	public Token getOperadorLogico() {
		return operadorLogico;
	}
	public void setOperadorLogico(Token operadorLogico) {
		this.operadorLogico = operadorLogico;
	}
	
	
	

}
