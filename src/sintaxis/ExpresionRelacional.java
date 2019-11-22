package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

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

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Expresion Relacional");

		if (expresionIzquierda != null)
			raiz.getChildren().add(expresionIzquierda.getArbolVisual());
		if (operadorAritmetico != null) {
			raiz.getChildren().add(new TreeItem<>("Operador: " + operadorAritmetico.getPalabra()));
		}
		if (expresionDerecha != null) {
			raiz.getChildren().add(expresionDerecha.getArbolVisual());
		}

		return raiz;
	}



	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		
	}

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		return "bin";
	}

}
