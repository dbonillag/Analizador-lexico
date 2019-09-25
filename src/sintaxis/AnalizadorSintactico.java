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
	 * ninguna otra categoria puede comenzar por fun para evitar ambiguedades
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("fun")) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSiguienteToken();

			} else {
				reportarError("Falta el nombre de la funcion");
			}

		}
		return null;

	}

	private void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	private void obtenerSiguienteToken() {

		posActual++;

		if (posActual < listaTokens.size()) {
			tokenActual = listaTokens.get(posActual);
		}

	}

}
