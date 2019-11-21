package co.edu.uniquindio.compilador.sintaxis;

import java.util.ArrayList;

import co.edu.uniquindio.compilador.lexico.Categoria;
import co.edu.uniquindio.compilador.semantica.Simbolo;
import co.edu.uniquindio.compilador.semantica.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class ExpresionAritmetica extends Expresion{

	private ExpresionAritmetica expAritmetica;
	private ExpresionAuxiliar expAuxiliar;
	private ValorNumerico valorNumerico;
	
	public ExpresionAritmetica(ExpresionAritmetica ea, ExpresionAuxiliar eAux) {
		super();
		this.expAritmetica = ea;
		this.expAuxiliar = eAux;
	}

	public ExpresionAritmetica(ValorNumerico valorNumerico, ExpresionAuxiliar eAux) {
		super();
		this.expAuxiliar = eAux;
		this.valorNumerico = valorNumerico;
	}

	@Override
	public String toString() {
		if( expAritmetica!=null && expAuxiliar==null && valorNumerico == null ) {
			return "ExpresionAritmetica [expAritmetica=" + expAritmetica + "]";
		}else if( expAritmetica!=null && expAuxiliar!=null && valorNumerico == null ) {
			return "ExpresionAritmetica [expAritmetica=" + expAritmetica + ", expAuxiliar=" + expAuxiliar + "]";
		}else if( expAritmetica==null && expAuxiliar==null && valorNumerico != null ) {
			return "ExpresionAritmetica [valorNumerico=" + valorNumerico + "]";
		}else {
			return "ExpresionAritmetica [valorNumerico=" + valorNumerico + ", expAuxiliar=" + expAuxiliar + "]";
		}
	}
	
	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> nodo = new TreeItem<>("Expresión Aritmética");
		
		if(valorNumerico!=null) {
			nodo.getChildren().add( valorNumerico.getArbolVisual() );
		}
		
		if(expAuxiliar!=null) {
			nodo.getChildren().add( expAuxiliar.getArbolVisual() );
		}
		
		if(expAritmetica!=null) {
			nodo.getChildren().add( expAritmetica.getArbolVisual() );
		}
		
		return nodo;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		
		if(valorNumerico!=null) {
			
			if( valorNumerico.getTermino().getCategoria() == Categoria.IDENTIFICADOR ) {
				
				Simbolo s = tablaSimbolos.buscarSimboloVariable(valorNumerico.getTermino().getLexema(), ambito, valorNumerico.getTermino().getFila(), valorNumerico.getTermino().getColumna());
				
				if(s==null) {
					errores.add("La variable "+valorNumerico.getTermino().getLexema()+" no existe");
				}else {
					if( ! (s.getTipo().equals("int") || s.getTipo().equals("decimal")) ) {
						errores.add("La variable "+valorNumerico.getTermino().getLexema()+" no es numérica");
					}
				}
				
			}
			
		}
		
		if(expAritmetica!=null) {
			expAritmetica.analizarSemantica(tablaSimbolos, errores, ambito);
		}
		
		if(expAuxiliar!=null) {
			expAuxiliar.analizarSemantica(tablaSimbolos, errores, ambito);
		}
		
	}

	@Override
	public String obtenerTipo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
