package sintaxis;

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

}
