package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class ExpresionRelacional extends Expresion {

	ExpresionAritmetica expresionIzquierda;
	Token operadorRelacional;
	ExpresionAritmetica expresionDerecha;

	public ExpresionRelacional(ExpresionAritmetica expresionIzquierda, Token operadorAritmetico,
			ExpresionAritmetica expresionDerecha) {
		super();
		this.expresionIzquierda = expresionIzquierda;
		this.operadorRelacional = operadorAritmetico;
		this.expresionDerecha = expresionDerecha;
	}

	public ExpresionRelacional(ExpresionAritmetica expresionIzquierda) {
		super();
		this.expresionIzquierda = expresionIzquierda;
		
	}
	
	public ExpresionAritmetica getExpresionIzquierda() {
		return expresionIzquierda;
	}

	public void setExpresionIzquierda(ExpresionAritmetica expresionIzquierda) {
		this.expresionIzquierda = expresionIzquierda;
	}

	

	public Token getOperadorRelacional() {
		return operadorRelacional;
	}

	public void setOperadorRelacional(Token operadorRelacional) {
		this.operadorRelacional = operadorRelacional;
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
		if (operadorRelacional != null) {
			raiz.getChildren().add(new TreeItem<>("Operador: " + operadorRelacional.getPalabra()));
		}
		if (expresionDerecha != null) {
			raiz.getChildren().add(expresionDerecha.getArbolVisual());
		}

		return raiz;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		String tipo = "";
		if (expresionDerecha != null) {
			expresionDerecha.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
			tipo = expresionDerecha.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito);
			if (!(tipo.equals("R") || tipo.equals("Z"))) {
				
				erroresSemanticos.add("El tipo de la expresión aritmetica es invalido");
			}
		}

		if (expresionIzquierda != null) {
			expresionIzquierda.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
			tipo = expresionIzquierda.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito);
			if (!(tipo.equals("R") || tipo.equals("Z"))) {
				
				
				if(!(tipo.equals("bin")&&operadorRelacional==null)) {
					erroresSemanticos.add("El tipo de la expresión aritmetica es invalido");
				}
			}
		}
	}

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		return "bin";
	}

	@Override
	public String getJavaCode() {

		String codigo = "(" + expresionIzquierda.getJavaCode() + ") ";
		if(operadorRelacional!=null) {
			if (operadorRelacional.getPalabra().equals(">=")) {
				codigo+=">=";
			}else if (operadorRelacional.getPalabra().equals("<=")) {
				codigo+="<=";
			}else if (operadorRelacional.getPalabra().equals(">")) {
				codigo+=">";
			}else if (operadorRelacional.getPalabra().equals("<")) {
				codigo+="<";
			}else if (operadorRelacional.getPalabra().equals("==")) {
				codigo+="==";
			}else if (operadorRelacional.getPalabra().equals("~=")) {
				codigo+="!=";
			}
			
			codigo+="("+expresionDerecha.getJavaCode()+") ";
		}
		

		
		return codigo;
	}

}
