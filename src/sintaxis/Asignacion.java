package sintaxis;

import javafx.scene.control.TreeItem;
import lexico.Token;

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

}
