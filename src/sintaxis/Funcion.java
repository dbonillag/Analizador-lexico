package sintaxis;

import java.util.ArrayList;

import lexico.Categoria;
import lexico.Token;

public class Funcion {

	Token nombre;
	ArrayList<Parametro> parametros;
	Token tipoRetorno;
	ArrayList<Sentencia> bloqueSentencias;

	public Funcion(Token nombre, ArrayList<Parametro> parametros, Token tipoRetorno,
			ArrayList<Sentencia> bloqueSentencias) {
		super();
		this.nombre = nombre;
		this.parametros = parametros;
		this.tipoRetorno = tipoRetorno;
		this.bloqueSentencias = bloqueSentencias;
	}

	public Token getNombre() {
		return nombre;
	}

	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(ArrayList<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Token getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(Token tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public ArrayList<Sentencia> getBloqueSentencias() {
		return bloqueSentencias;
	}

	public void setBloqueSentencias(ArrayList<Sentencia> bloqueSentencias) {
		this.bloqueSentencias = bloqueSentencias;
	}

}
