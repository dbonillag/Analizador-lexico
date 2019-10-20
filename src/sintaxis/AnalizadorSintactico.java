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
		this.tokenActual = listaTokens.get(posActual);
		listaErrores = new ArrayList<ErrorSintactico>();
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
								reportarError("Faltó el bloque de sentencias en la función");
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
	 * <ListaParametros> :== <Parametro> [","<ListaParametros>]
	 * 
	 * @return
	 */
	public ArrayList<Parametro> esListaParametros() {

		ArrayList<Parametro> lista = new ArrayList<>();
		Parametro param = esParametro();

		if (param != null) {

			lista.add(param);

			if (tokenActual.getPalabra().equals(",")) {
				obtenerSiguienteToken();
				lista.addAll(esListaParametros());
			}

		}

		return lista;
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
		System.out.println("entra a esBloqueSentencias()");
		if (tokenActual.getCategoria() == Categoria.LLAVE_APERTURA) {
			obtenerSiguienteToken();
			ArrayList<Sentencia> listaSentencias = new ArrayList<>();

			Sentencia sentencia = esSentencia();

			while (sentencia != null) {
				listaSentencias.add(sentencia);
				sentencia = esSentencia();
			}
			if (tokenActual.getCategoria() == Categoria.LLAVE_CIERRE) {
				obtenerSiguienteToken();
				return listaSentencias;
			} else {
				reportarError("Falta llave derecha");
			}
		}
		return null;
	}

	/**
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 * 
	 * @return
	 */
	public ArrayList<Sentencia> esListaSentencias() {
		ArrayList<Sentencia> lista = new ArrayList<>();

		Sentencia sentencia = esSentencia();

		while (sentencia != null) {
			lista.add(sentencia);
			sentencia = esSentencia();
		}

		return lista;
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
	 * <Retorno>::= regret <Expresion>"!"
	 * 
	 * @return
	 */
	public Sentencia esRetorno() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("regret")) {
			obtenerSiguienteToken();

			Expresion expresion = esExpresion();

			if (expresion != null) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() != Categoria.TERMINAL) {
					return new Retorno(identificador);
				} else {
					reportarError("Falta el fin de linea");
				}
			} else {
				reportarError("Falta la expresion a retornar");
			}
		}
		return null;
	}

	/**
	 * 
	 * <InvocacionDeFuncion>::= identificador "(" [<ListaArgumentos>] ")" "!"
	 * 
	 * @return
	 */
	public Sentencia esInvocacionDeFuncion() {
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token identificador = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				ArrayList<Expresion> argumentos = esListaArgumentos();
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
					obtenerSiguienteToken();
					if (tokenActual.getCategoria() == Categoria.TERMINAL) {
						return new InvocacionDeFuncion(identificador, argumentos);
					} else {
						reportarError("Falta el fin de linea");
					}
				} else {
					reportarError("Falta paréntesis derecho");
				}
			} else {
				reportarError("Falta paréntesis izquierdo");
			}
		}

		return null;

	}

	/**
	 * 
	 * <Lectura> read "!"
	 * 
	 * @return Lectura
	 */
	public Sentencia esLectura() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("read")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.TERMINAL) {
				return new Lectura();
			} else {
				reportarError("Falta el fin de linea");
			}
		}
		return null;
	}

	/**
	 * 
	 * <Impresion> show "(" <ExpresionCadena> ")" "!"
	 * 
	 * @return Impresion
	 */
	public Sentencia esImpresion() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("show")) {
			Token identificador = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				ExpresionCadena cadena = esExpresionCadena();
				if (cadena != null) {
//TODO terminar
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.TERMINAL) {
							return new InvocacionDeFuncion(identificador, argumentos);
						} else {
							reportarError("Falta el fin de linea");
						}
					} else {
						reportarError("Falta paréntesis derecho");
					}
				} else {
					reportarError("Falta paréntesis izquierdo");
				}

			}
		}

		return null;
	}

	/**
	 * 
	 * @return Ciclo
	 */
	public Ciclo esCiclo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return Condicion
	 */
	public Condicion esCondicion() {
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

	public ArrayList<Expresion> esListaArgumentos() {
		ArrayList<Expresion> lista = new ArrayList<>();

		Expresion expresion = esExpresion();

		while (expresion != null) {
			lista.add(expresion);
			expresion = esExpresion();
		}

		return lista;
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

	/**
	 * <ExpresionCadena> ::= CadenaDeCaracteres[","<Expresión>]
	 * 
	 * @return
	 */
	public Expresion esExpresionCadena() {

		if (tokenActual.getCategoria() == Categoria.CADENA) {
			Token cadenaDeCaracteres = tokenActual;
			obtenerSiguienteToken();

			Expresion expresion = null;
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				expresion = esExpresion();
				if (expresion == null) {
					reportarError("Falta la expresion");
				}
			}
			return new ExpresionCadena(cadenaDeCaracteres, expresion);

		}
		return null;
	}

	/**
	 *<ExpresionRelacional>::= <ExpresionAritmetica>operadorRelacional<ExpresionAritmetica>
	 * @return
	 */
	public Expresion esExpresionRelacional() {
		ExpresionAritmetica expresionAritIzq=esExpresionAritmetica();
		if(expresionAritIzq!=null) {
			
			obtenerSiguienteToken();
			
			
			
			if (tokenActual.getCategoria()==Categoria.RELACIONAL) {
				Token opRelacional = tokenActual;
				obtenerSiguienteToken();
				ExpresionAritmetica expresionAritDer=esExpresionAritmetica();
				if (expresionAritDer!=null) {
					return new ExpresionRelacional(expresionAritIzq, opRelacional, expresionAritDer);
				}else {
					reportarError("Falta la expresión relacional izquierda");
				}
				
				
			}else {
				reportarError("Duda:Que pasa si enrealidad es una expresion aritmetica sola?");
			}
			
			
		}
		return null;
	}

	/**
	 * <ExpresionLogica> ::= <ExpresionRelacional>|<ExpresionRelacional>OperadorLogico<ExpresionRelacional>|OperadorLogico<ExpresionRelacional>                                                   
	 * @return
	 */
	public Expresion esExpresionLogica() {
		ExpresionRelacional expRelIzq=(ExpresionRelacional) esExpresionRelacional();
		if (expRelIzq!=null) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria()==Categoria.LOGICO) {
				Token opLogico=tokenActual;
				
				obtenerSiguienteToken();
				
				ExpresionRelacional ExpRelDer=(ExpresionRelacional) esExpresionRelacional();
				
				if (ExpRelDer!=null) {
					
					
					return new ExpresionLogica(expRelIzq, ExpRelDer, opLogico);
				}
				
						
			}
			
			
			return new ExpresionLogica(expRelIzq);
		}
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
	 * <Asignacion> ::= identificador operadorAsignacion <Expresion> "!"
	 * 
	 * @return
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

					if (tokenActual.getCategoria() == Categoria.TERMINAL) {
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
		System.out.println(mensaje);
		System.out.println(tokenActual.getFila() + " " + tokenActual.getColumna());
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

}
