package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Medida extends Sentencia {

	private Token identificadorArreglo;
	private Token identificadorVariable;

	public Medida(Token identificadorArreglo, Token identificadorVariable) {
		super();
		this.identificadorArreglo = identificadorArreglo;
		this.identificadorVariable = identificadorVariable;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Medida");
		raiz.getChildren().add(new TreeItem<>("Id Arreglo: " + identificadorArreglo.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("id Variable: " + identificadorVariable.getPalabra()));

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(identificadorArreglo.getPalabra(), ambito, identificadorArreglo.getFila(),
				identificadorArreglo.getColumna());

		if (s == null) {
			erroresSemanticos.add("La variable " + identificadorArreglo.getPalabra() + " no existe, fila: "+identificadorArreglo.getFila());
		} else {
			if (!(s.getTipo().endsWith("[]"))) {
				erroresSemanticos.add("La variable " + identificadorArreglo.getPalabra() + " no es un arreglo,, fila: "+identificadorArreglo.getFila());
			}
		}
		
		s = tablaSimbolos.buscarSimboloVariable(identificadorVariable.getPalabra(), ambito, identificadorVariable.getFila(),
				identificadorVariable.getColumna());

		if (s == null) {
			erroresSemanticos.add("La variable " + identificadorVariable.getPalabra() + " no existe, fila: "+identificadorVariable.getFila());
		} else {
			if (!(s.getTipo().equals("Z"))) {
				erroresSemanticos.add("La variable " + identificadorVariable.getPalabra() + " no es entero, fila: "+identificadorVariable.getFila());
			}
		}
		
		
		
		
	}

	@Override
	public String getJavaCode() {
		int[]arreglo= {3,4,5,6};
		
		
		String codigo=identificadorVariable.getPalabra().replace("@", "$")+"="+
	identificadorArreglo.getPalabra().replace("@", "$")+".length; ";
		return codigo;
	}
}
