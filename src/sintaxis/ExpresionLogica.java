package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class ExpresionLogica extends Expresion {

	ExpresionRelacional expresionRelacionalIzq, expresionRelacionalDer;
	Token operadorLogicoIzq,operadorLogico;

	public ExpresionLogica(ExpresionRelacional expresionRelacionalIzq, ExpresionRelacional expresionRelacionalDer,
			Token operadorLogico) {
		super();
		this.expresionRelacionalIzq = expresionRelacionalIzq;
		this.expresionRelacionalDer = expresionRelacionalDer;
		this.operadorLogico = operadorLogico;
	}
	
	

	public ExpresionLogica(Token operadorLogicoIzq,ExpresionRelacional expresionRelacionalIzq) {
		super();
		this.expresionRelacionalIzq = expresionRelacionalIzq;
		this.operadorLogicoIzq = operadorLogicoIzq;
	}

	

	public ExpresionLogica(ExpresionRelacional expresionRelacional) {
		super();
		this.expresionRelacionalIzq = expresionRelacional;
	}
	
	

	public Token getOperadorLogicoIzq() {
		return operadorLogicoIzq;
	}



	public void setOperadorLogicoIzq(Token operadorLogicoIzq) {
		this.operadorLogicoIzq = operadorLogicoIzq;
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

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Expresion Logica");

		if (expresionRelacionalIzq != null)
			raiz.getChildren().add(expresionRelacionalIzq.getArbolVisual());
		if (operadorLogico != null) {
			raiz.getChildren().add(new TreeItem<>("Operador: " + operadorLogico.getPalabra()));
		}
		if (expresionRelacionalDer != null) {
			raiz.getChildren().add(expresionRelacionalDer.getArbolVisual());
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
