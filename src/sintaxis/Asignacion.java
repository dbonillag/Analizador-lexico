package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;
import lexico.Token;

public class Asignacion extends Sentencia {

	private Token indentificador;
	private Token operadorAsignacion;
	private Expresion expresion;

	public Asignacion(Token indentificador, Token operadorAsignacion, Expresion expresion) {
		super();
		this.indentificador = indentificador;
		this.operadorAsignacion = operadorAsignacion;
		this.expresion = expresion;
	}

	@Override
	public String toString() {
		return "Asignacion [indentificador=" + indentificador + ", operadorAsignacion=" + operadorAsignacion
				+ ", expresion=" + expresion + "]";
	}

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		return new DefaultMutableTreeNode("Asignación");
	}

}
