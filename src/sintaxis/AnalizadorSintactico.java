package sintaxis;

import java.util.ArrayList;

import modelo.Categoria;
import modelo.Token;

public class AnalizadorSintactico {

	private ArrayList<Token> listaTokens;
	private int posActual;
	private Token tokenActual;
	private ArrayList<ErrorSintactico> listaErrores;

<<<<<<< HEAD
	public AnalizadorSintactico(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
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

	/**
	 * <UnidadDeCompilacion> ::= <ListaFunciones>
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {
		ArrayList<Funcion> funcion = esListaDeFunciones();

		if (funcion != null) {
			return new UnidadDeCompilacion(funcion);
		}

		return null;
	}

	/**
	 * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
	 */
	public ArrayList<Funcion> esListaDeFunciones() {

		ArrayList<Funcion> lista = new ArrayList<>();
		Funcion funcion = esFuncion();

		while (funcion != null) {
			lista.add(funcion);
			funcion = esFuncion();
		}

		return lista;
	}

	/**
	 * <Funcion> ::= method identificador <TipoRetorno> "("[<ListaParametros>]")"
	 * ":" <BloqueSentencias>
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("method")) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSiguienteToken();

				Token tipoRetorno = esTipoRetorno();
				if (tipoRetorno != null) {
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
						obtenerSiguienteToken();

						ArrayList<Parametro> parametros = esListaParametros();

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
							obtenerSiguienteToken();

							if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
								obtenerSiguienteToken();
							}

							ArrayList<Sentencia> bloqueSentencias = esBloqueDeSentencias();

							if (bloqueSentencias != null) {
								return new Funcion(nombre, parametros, tipoRetorno, bloqueSentencias);
							} else {
								reportarError("Faltó el bloque de sentancias en la función");
							}

						} else {
							reportarError("Falta paréntesis derecho");
						}
					} else {
						reportarError("Falta paréntesis izquierdo");
					}
				} else {
					reportarError("Falta el tipo de retorno de la función");
				}
			} else {
				reportarError("Falta el nombre de la función");
			}

		}

		return null;
	}

	/**
	 * <BloqueSentencias> ::= "{" [<ListaSentencias>] "}"
	 */
	public ArrayList<Sentencia> esBloqueDeSentencias() {

		if (tokenActual.getCategoria() == Categoria.LLAVE_APERTURA) {
			obtenerSiguienteToken();

			ArrayList<Sentencia> sentencias = esBloqueDeSentencias();

			if (tokenActual.getCategoria() == Categoria.LLAVE_CIERRE) {
				obtenerSiguienteToken();
				return sentencias;
			} else {
				reportarError("Falta llave derecha");
			}

		}

		return null;

	}

	// TODO Terminar este. No sé si sea así
	private Sentencia esSentencia() {
		if (esCondicion != null || esDeclaracionDeVariable() != null || esAsignacion() != null || esImpresion() != null
				|| tokenActual.getCategoria() == Categoria.IDENTIFICADOR || tokenActual.getPalabra().equals("rep")
				|| tokenActual.getPalabra().equals("cicle") || tokenActual.getPalabra().equals("regret")) {
			return new Sentencia();
		}
		return null;
	}

	/**
	 * <TipoRetorno> ::=Z | R | bin | void | text | char
	 */
	public Token esTipoRetorno() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA
				&& (tokenActual.getPalabra().equals("int") || tokenActual.getPalabra().equals("decimal")
						|| tokenActual.getPalabra().equals("bool") || tokenActual.getPalabra().equals("string")
						|| tokenActual.getPalabra().equals("char") || tokenActual.getPalabra().equals("void"))) {
			return tokenActual;
		}

		return null;
	}

	/**
	 * <TipoDato> ::= Z | R | bin | text | char
	 */
	// TODO cuadrar los tipos de dato bien
	public Token esTipoDato() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && (tokenActual.getPalabra().equals("int")
				|| tokenActual.getPalabra().equals("decimal") || tokenActual.getPalabra().equals("bool")
				|| tokenActual.getPalabra().equals("string") || tokenActual.getPalabra().equals("char"))) {
			return tokenActual;
		}

		return null;
	}

	public ArrayList<Parametro> esListaParametros() {

		if (esParametro() == null) {

			ArrayList<Parametro> lista = new ArrayList<>();
			Parametro parametro = esParametro();
			lista.add(parametro);
			obtenerSiguienteToken();

			while (tokenActual.getPalabra().equals(",")) {
				obtenerSiguienteToken();

				parametro = esParametro();
				lista.add(parametro);

				obtenerSiguienteToken();
			}

			return lista;
		}

		return null;
	}

	public void obtenerSiguienteToken() {

		posActual++;

		if (posActual < listaTokens.size()) {
			tokenActual = listaTokens.get(posActual);
		}

	}

	public void analizar() {

		// TODO Como sería esta mierda? xD
	}

=======
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
	 * Funcion::= fun identificador "("[<ListaParametros>]")" [":"<TipoRetorno>"] <BloqueSentencias>
	 * 
	 *  ninguna otra categoria puede comenzar por fun para evitar ambiguedades
	 */
	public Funcion esFuncion() {
		
		if(tokenActual.getCategoria()==Categoria.RESERVADA && tokenActual.getPalabra().equals("fun")) {
			obtenerSiguienteToken();
			
			if (tokenActual.getCategoria()==Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerSiguienteToken();
				
			}else {
				reportarError("Falta el nombre de la funcion");
			}
			
		}
		
	}

	private void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje,tokenActual.getFila(),tokenActual.getColumna()));
	}

	private void obtenerSiguienteToken() {
		
		posActual++;
		
		if (posActual<listaTokens.size()) {
			tokenActual=listaTokens.get(posActual);
		}
		
	}

	
>>>>>>> parent of 79a2af7... Falto meter estos archivos al commit anterior GG
}
