package sintaxis;

import javafx.scene.control.TreeItem;
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
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Expresion Aritmetica");
		if (termino != null)
			raiz.getChildren().add(new TreeItem<>("Termino: " + termino.getPalabra()));
		if (operadorAritmetico != null)
			raiz.getChildren().add(new TreeItem<>("Operador: " + operadorAritmetico.getPalabra()));
		if (expresionAritmetica1 != null) {
			raiz.getChildren().add(expresionAritmetica1.getArbolVisual());
		}
		if (expresionAritmetica2 != null) {
			raiz.getChildren().add(expresionAritmetica2.getArbolVisual());
		}

		return raiz;
	}

}
