package sintaxis;

import java.util.ArrayList;
import lexico.Categoria;
import lexico.Token;

public class AnalizadorSintactico {

	private ArrayList<Token> listaTokens;
	private int posActual;
	private Token tokenActual;
	private ArrayList<ErrorSintactico> listaErrores;

	public AnalizadorSintactico(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
	}

	/**
	 * <UnidadDeCompilacion> ::= <ListaFunciones>
	 * 
	 * @return UnidadDeCompilacion
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
	 * 
	 * @return ArrayList<Funcion>
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
	 * 
	 * @return Funcion
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
	 * <ListaParametros> ::= <Parametro>[<ListaParametros>]
	 * 
	 * @return ArrayList<Parametro>
	 */
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

	/**
	 * <Parametro> ::= identificador <TipoDato>
	 * 
	 * @return Parametro
	 */
	public Parametro esParametro() {
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token identificador = tokenActual;
			obtenerSiguienteToken();

			Token tipoDato = esTipoDato();

			if (tipoDato != null) {
				obtenerSiguienteToken();
				return new Parametro(tipoDato, identificador);
			} else {
				reportarError("Falta tipo de dato");
			}
		}
		return null;
	}

	/**
	 * <BloqueSentencias> ::= "{" [<ListaSentencias>] "}"
	 * 
	 * @return ArrayList<Sentencia>
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

	/**
	 * <Sentencia> ::= <Condicion> | <Ciclo> | <Impresion> | <Lectura> |
	 * <Asignacion> | <DeclaracionVariable> | <Retorno> | <invocaFuncion> |
	 * <arreglo>
	 */
	public Sentencia esSentencia() {
		Sentencia expr = null;

		expr = esCondicion();
		if (expr != null) {
			return expr;
		}

		expr = esCiclo();
		if (expr != null) {
			return expr;
		}

		expr = esImpresion();
		if (expr != null) {
			return expr;
		}

		expr = esLectura();
		if (expr != null) {
			return expr;
		}

		expr = esAsignacion();
		if (expr != null) {
			return expr;
		}

		expr = esRetorno();
		if (expr != null) {
			return expr;
		}

		expr = esInvocacionDeFuncion();
		if (expr != null) {
			return expr;
		}

		expr = esArreglo();
		if (expr != null) {
			return expr;
		}

		return null;

	}

	/**
	 * No sé si hay que hacer este metodo teniendo ya el esTipoRetorno()
	 * 
	 * @return
	 */
	private Sentencia esRetorno() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Tampoco estoy seguro de si hay que hacer este
	 * 
	 * @return
	 */
	private Sentencia esInvocacionDeFuncion() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return Lectura
	 */
	private Sentencia esLectura() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return Impresion
	 */
	private Sentencia esImpresion() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return Ciclo
	 */
	private Ciclo esCiclo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return Condicion
	 */
	private Condicion esCondicion() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <Arreglo> ::= list "[" <TipoDato> "," <Entero> "]" identificador
	 * 
	 * @return Arreglo
	 */
	public Arreglo esArreglo() {
		// TODO auto-generated method stub
		return null;
	}

	/**
	 * <Termino> ::= Z | R | identificador
	 * 
	 * @return Token
	 */
	public Token esTermino() {

		if (tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.REAL
				|| tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			return tokenActual;
		}

		return null;
	}

	/**
	 * <ListaVariables> :== <Variable> [","<ListaVariables>]
	 * 
	 * @return ArrayList<Variable>
	 */
	public ArrayList<Variable> esListaVariables() {

		ArrayList<Variable> lista = new ArrayList<>();
		Variable variable = esVariable();

		if (variable != null) {

			lista.add(variable);

			if (tokenActual.getPalabra().equals(",")) {
				obtenerSiguienteToken();
				lista.addAll(esListaVariables());
			}
		}
		return lista;
	}

	/**
	 * <Variable> ::= identificador["="<Expresion>]
	 * 
	 * @return Variable
	 */
	public Variable esVariable() {

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token identificador = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getPalabra().equals("=")) {
				obtenerSiguienteToken();

				Expresion e = esExpresion();

				if (e != null) {
					return new Variable(identificador, e);
				} else {
					reportarError("Falta la expresion");
				}

			} else {
				return new Variable(identificador);
			}
		}

		return null;
	}

	/**
	 * <Expresion> ::= <ExpresionAritmetica> | <ExpresionLogica> |
	 * <ExpresionRelacional> | <ExpresionCadena>
	 * 
	 * @return
	 */
	public Expresion esExpresion() {
		Expresion expr = null;

		expr = esExpresionAritmetica();
		if (expr != null) {
			return expr;
		}

		expr = esExpresionLogica();
		if (expr != null) {
			return expr;
		}

		expr = esExpresionRelacional();
		if (expr != null) {
			return expr;
		}

		expr = esExpresionCadena();
		if (expr != null) {
			return expr;
		}

		return null;

	}

	private Expresion esExpresionCadena() {
		// TODO Auto-generated method stub
		return null;
	}

	private Expresion esExpresionRelacional() {
		// TODO Auto-generated method stub
		return null;
	}

	private Expresion esExpresionLogica() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <ExpresionAritmetica> ::= <Termino>[operacionAritmetica
	 * <ExpresionAritmetica>] | "("<ExpresionAritmetica>")"[operacionAritmetica
	 * <ExpresionAritmetica>]
	 * 
	 * @return ExpresionAritmetica
	 */
	public ExpresionAritmetica esExpresionAritmetica() {

		Token termino = esTermino();

		if (termino != null) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.ARITMETICO) {
				Token operadorAritmetico = tokenActual;
				obtenerSiguienteToken();

				ExpresionAritmetica exp = esExpresionAritmetica();

				if (exp != null) {
					return new ExpresionAritmetica(termino, operadorAritmetico, exp);
				} else {
					reportarError("Falta una expresión aritmética");
				}

			} else {
				return new ExpresionAritmetica(termino);
			}

		}

		if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
			obtenerSiguienteToken();

			ExpresionAritmetica ea = esExpresionAritmetica();

			if (ea != null) {

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.ARITMETICO) {
						Token operadorAritmetico = tokenActual;
						obtenerSiguienteToken();

						ExpresionAritmetica ea2 = esExpresionAritmetica();

						if (ea2 != null) {
							return new ExpresionAritmetica(ea, operadorAritmetico, ea2);
						} else {
							reportarError("Falta una expresión aritmética");
						}

					} else {
						return new ExpresionAritmetica(ea);
					}

				} else {
					reportarError("Falta el paréntesis de cierre");
				}

			} else {
				reportarError("Falta una expresión aritmética");
			}

		}

		return null;

	}

	/**
	 * <Asignacion> ::= identificador operadorAsignacion <Expresion>
	 * 
	 * @return Asignacion
	 */
	public Asignacion esAsignacion() {

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token identificador = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.ASIGNACION) {
				Token operador = tokenActual;
				obtenerSiguienteToken();

				Expresion exp = esExpresion();

				if (exp != null) {

					if (tokenActual.getPalabra().equals("\n")) {
						obtenerSiguienteToken();
						return new Asignacion(identificador, operador, exp);
					} else {
						reportarError("Falta fin de sentencia en la asignación");
					}
				} else {
					reportarError("Falta expresión en la asignación");
				}

			} else {
				reportarError("Falta operador de asignación");
			}

		}

		return null;
	}

	/**
	 * <TipoDato> ::= Z | R | bin | text | char
	 */
	public Token esTipoDato() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && (tokenActual.getPalabra().equals("Z")
				|| tokenActual.getPalabra().equals("R") || tokenActual.getPalabra().equals("bin")
				|| tokenActual.getPalabra().equals("text") || tokenActual.getPalabra().equals("char"))) {
			return tokenActual;
		}

		return null;
	}

	/**
	 * <TipoRetorno> ::= Z | R | bin | text | char | void
	 */
	public Token esTipoRetorno() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA
				&& (tokenActual.getPalabra().equals("Z") || tokenActual.getPalabra().equals("R")
						|| tokenActual.getPalabra().equals("bin") || tokenActual.getPalabra().equals("void")
						|| tokenActual.getPalabra().equals("text") || tokenActual.getPalabra().equals("char"))) {
			return tokenActual;
		}

		return null;
	}

	public void obtenerSiguienteToken() {
		posActual++;
		if (posActual < listaTokens.size()) {
			tokenActual = listaTokens.get(posActual);
		}

	}

	public void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	public void analizar() {
		// TODO auto-generated method stub
	}

}
