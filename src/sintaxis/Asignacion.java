package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Categoria;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Asignacion extends Sentencia {

	private Token identificador;
	private Token operadorAsignacion;
	private Expresion expresion;

	public Asignacion(Token indentificador, Token operadorAsignacion, Expresion expresion) {
		super();
		this.identificador = indentificador;
		this.operadorAsignacion = operadorAsignacion;
		this.expresion = expresion;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<String>("Asignación");
		raiz.getChildren().add(new TreeItem<String>("Identificador: " + identificador.getPalabra()));
		raiz.getChildren().add(new TreeItem<String>("Operador: " + operadorAsignacion.getPalabra()));
		raiz.getChildren().add(expresion.getArbolVisual());

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub

	}
	/*
	 * if (tokenActual.getCategoria() == Categoria.RESERVADA &&
	 * (tokenActual.getPalabra().equals("Z") || tokenActual.getPalabra().equals("R")
	 * || tokenActual.getPalabra().equals("bin") ||
	 * tokenActual.getPalabra().equals("text") ||
	 * tokenActual.getPalabra().equals("char"))) { return tokenActual; }
	 * 
	 */
	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		Simbolo simbolo = tablaSimbolos.buscarSimboloVariable(identificador.getPalabra(), ambito,
				identificador.getFila(), identificador.getColumna());

		if (simbolo == null) {
			erroresSemanticos.add("La variable" + identificador.getPalabra() + " no existe");
		} else {
			if (expresion != null) {
				if (!simbolo.getTipo().equals(expresion.obtenerTipo())) {
					erroresSemanticos.add("El tipo de la expresión no es correcto");
				}
			}
		}

		if (expresion != null) {
			expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}

	}

}
