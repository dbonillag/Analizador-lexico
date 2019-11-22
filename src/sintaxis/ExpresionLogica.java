package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class ExpresionLogica extends Expresion {

	ExpresionRelacional expresionRelacionalIzq, expresionRelacionalDer;
	Token negacion,operadorLogico;

	public ExpresionLogica(ExpresionRelacional expresionRelacionalIzq, ExpresionRelacional expresionRelacionalDer,
			Token operadorLogico) {
		super();
		this.expresionRelacionalIzq = expresionRelacionalIzq;
		this.expresionRelacionalDer = expresionRelacionalDer;
		this.operadorLogico = operadorLogico;
	}
	
	

	public ExpresionLogica(Token negacion,ExpresionRelacional expresionRelacionalIzq) {
		super();
		this.expresionRelacionalIzq = expresionRelacionalIzq;
		this.negacion = negacion;
	}

	

	public ExpresionLogica(ExpresionRelacional expresionRelacional) {
		super();
		this.expresionRelacionalIzq = expresionRelacional;
	}
	
	

	

	public Token getNegacion() {
		return negacion;
	}



	public void setNegacion(Token negacion) {
		this.negacion = negacion;
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
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		expresionRelacionalIzq.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		if(expresionRelacionalDer!=null)
			
			
			
			expresionRelacionalDer.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		
	}



	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		return "bin";
	}

}
