package co.edu.uniquindio.compilador.sintaxis;

import java.util.ArrayList;

import co.edu.uniquindio.compilador.lexico.Token;
import co.edu.uniquindio.compilador.semantica.Simbolo;
import co.edu.uniquindio.compilador.semantica.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class Asignacion extends Sentencia{

	private Token identificador;
	private Token operadorAsignacion;
	private Expresion expresion;
	private InvocacionFuncion invocacion;
	
	public Asignacion(Token identificador, Token operadorAsignacion, Expresion expresion) {
		this.identificador = identificador;
		this.operadorAsignacion = operadorAsignacion;
		this.expresion = expresion;
	}
	
	public Asignacion(Token identificador, Token operadorAsignacion, InvocacionFuncion invocacion) {
		this.identificador = identificador;
		this.operadorAsignacion = operadorAsignacion;
		this.invocacion = invocacion;
	}

	@Override
	public String toString() {
		return "Asignacion [identificador=" + identificador + ", operadorAsignacion=" + operadorAsignacion
				+ ", expresion=" + expresion + ", invocacion=" + invocacion + "]";
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> nodo = new TreeItem<>("Asignación");
		
		nodo.getChildren().add(new TreeItem<>(identificador.getLexema()));
		nodo.getChildren().add(new TreeItem<>(operadorAsignacion.getLexema()));
		
		if(expresion!=null) {
			nodo.getChildren().add(expresion.getArbolVisual());	
		}
		
		if(invocacion!=null) {
			nodo.getChildren().add(invocacion.getArbolVisual());
		}		
		
		return nodo;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador.getLexema(), ambito, identificador.getFila(), identificador.getColumna());
		
		if(s==null) {
			erroresSemanticos.add("La variable no existe");
		}else {
			
			if(expresion!=null) {
				
				if( !s.getTipo().equals( expresion.obtenerTipo() )) {
					erroresSemanticos.add("El tipo de la expresión no es correcto");
				}
			}
		}
		
		
		if(expresion!=null) {
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
		
	}
	
}