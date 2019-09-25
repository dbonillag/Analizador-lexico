package sintaxis;

import java.util.ArrayList;

import modelo.Categoria;
import modelo.Token;

public class AnalizadorSintactico {

	private ArrayList<Token> listaTokens;
	private int posActual;
	private Token tokenActual;
	private ArrayList<ErrorSintactico> listaErrores;

	public void analizadorSintactico(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
		this.tokenActual = tokenActual;
		this.listaErrores = listaErrores;
	}

	/**
	 * <UnidadDeCompilacion>::=<ListaDeFunciones>
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {

		ArrayList<Funcion> listaDeFunciones = esListaDeFunciones();
		return new UnidadDeCompilacion(listaDeFunciones);
	}

	/**
	 * <ListaDeFunciones>::=<Funcion>[<ListaDeFunciones>]
	 */
	public ArrayList<Funcion> esListaDeFunciones() {
		return null;
	}

	/**
	 * Funcion::= fun identificador "("[<ListaParametros>]")" [":"<TipoRetorno>"]
	 * <BloqueSentencias>
	 * 
	 * Ninguna otra categoria puede comenzar por fun para evitar ambiguedades
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("fun")) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSiguienteToken();
				return new Funcion(nombre);

			} else {
				reportarError("Falta el nombre de la funcion");
				return null;
			}

		} else {

			return null;
		}
	}

	
	//TODO Parte recursiva está mal
	public ListaDeParametros esListaDeParametros() {
		
		Parametro parametro = esParametro();
		if (parametro != null) {
			obtenerSiguienteToken();
			if (tokenActual.getPalabra().equals(",")) {
				obtenerSiguienteToken();
				
				return new ListaDeParametros(parametro,esListaDeParametros());
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// TODO Definir los tipos de dato int float doble boolean void char en el
	// lexico, y cambiar los de acá
	public Parametro esParametro() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && (tokenActual.getPalabra().equals("entero")
				|| tokenActual.getPalabra().equals("decimal") || tokenActual.getPalabra().equals("texto"))) {
			Token tipoDato = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;

				return new Parametro(tipoDato, nombre);

			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	public void obtenerSiguienteToken() {

		posActual++;

		if (posActual < listaTokens.size()) {
			tokenActual = listaTokens.get(posActual);
		}

	}

}
