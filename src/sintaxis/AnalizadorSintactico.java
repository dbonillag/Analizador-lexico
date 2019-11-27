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
	 * ":" <BloqueSentencias> ":"
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

								ArrayList<Sentencia> bloqueSentencias = esBloqueSentencias();

								if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
									obtenerSiguienteToken();

									return new Funcion(nombre, parametros, tipoRetorno, bloqueSentencias);

								} else {
									reportarError("Falta dos puntos de cierre");
								}
							} else {
								reportarError("Falta dos puntos de apertura");
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

			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
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
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 * 
	 * @return
	 */
	public ArrayList<Sentencia> esBloqueSentencias() {
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

		expr = esDeclaracionDeVariable();
		if (expr != null) {
			return expr;
		}

		expr = esAsignacion();
		if (expr != null) {
			return expr;
		}

		expr = esInvocacionDeFuncion();
		if (expr != null) {
			return expr;
		}

		expr = esRetorno();
		if (expr != null) {
			return expr;
		}

		expr = esArreglo();
		if (expr != null) {
			return expr;
		}

		expr = esIncremental();
		if (expr != null) {
			return expr;
		}

		expr = esInterrupcion();
		if (expr != null) {
			return expr;
		}

		expr = esMedida();
		if (expr != null) {
			return expr;
		}

		return null;

	}

	/**
	 * <Incremental> ::= pacman identificador !
	 * 
	 * @return Incremental
	 */

	public Incremental esIncremental() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("pacman")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerSiguienteToken();
					return new Incremental(identificador);
				} else {
					reportarError("Falta el fin de linea ");
				}
			} else {
				reportarError("Falta el identficador");
			}
		}
		return null;
	}

	/**
	 * <Incremental> ::= interrupt!
	 * 
	 * @return Interrupcion
	 */

	public Interrupcion esInterrupcion() {

		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("interrupt")) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.TERMINAL) {
				obtenerSiguienteToken();
				return new Interrupcion();
			} else {
				reportarError("Falta el fin de linea");
			}
		}
		return null;
	}

	/**
	 * <Retorno>::= regret <Expresion>"!"
	 * 
	 * @return Retorno
	 */
	public Retorno esRetorno() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("regret")) {
			obtenerSiguienteToken();
			Expresion expresion = esExpresion();
			if (expresion != null) {
				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerSiguienteToken();
					return new Retorno(expresion);
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
	 * <InvocacionDeFuncion>::= call identificador "(" [<ListaArgumentos>] ")" "!"
	 * 
	 * @return InvocacionDeFuncion
	 */

	public InvocacionDeFuncion esInvocacionDeFuncion() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("call")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
					obtenerSiguienteToken();
					ArrayList<Expresion> argumentos = esListaArgumentos();
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.TERMINAL) {
							obtenerSiguienteToken();
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
			} else {
				reportarError("Falta el identificador de la funcion a llamar");
			}
		}

		return null;

	}

	/**
	 * <Lectura> read "(" <identificador> ")""!"
	 * 
	 * @return Lectura
	 */
	public Lectura esLectura() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("read")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token identificador = tokenActual;
					obtenerSiguienteToken();
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.TERMINAL) {
							obtenerSiguienteToken();
							return new Lectura(identificador);
						} else {
							reportarError("Falta el fin de linea");
						}
					} else {
						reportarError("Falta paréntesis derecho");
					}
				} else {
					reportarError("Falta el identificador");
				}

			} else {
				reportarError("Falta paréntesis izquierdo");

			}

		}
		return null;
	}

	/**
	 * 
	 * <Medida>::= measure "(" identificadorArreglo "," identificadorVariable ")" !
	 * 
	 * @return Impresion
	 */
	public Medida esMedida() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("measure")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token identificadorArreglo = tokenActual;
					obtenerSiguienteToken();
					if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
							Token identificadorVariable = tokenActual;
							obtenerSiguienteToken();
							if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
								obtenerSiguienteToken();
								if (tokenActual.getCategoria() == Categoria.TERMINAL) {
									obtenerSiguienteToken();
									return new Medida(identificadorArreglo, identificadorVariable);
								} else {
									reportarError("Falta el fin de linea");
								}
							} else {
								reportarError("Falta paréntesis derecho");
							}
						} else {
							reportarError("Falta el identificador de la variable");
						}
					} else {
						reportarError("Falta la coma");
					}

				} else {
					reportarError("Falta el identificador del arreglo");
				}
			} else {
				reportarError("Falta paréntesis izquierdo");
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
	public Impresion esImpresion() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("show")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				ExpresionCadena cadena = esExpresionCadena();
				if (cadena != null) {
					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.TERMINAL) {
							obtenerSiguienteToken();
							return new Impresion(cadena);
						} else {
							reportarError("Falta el fin de linea");
						}
					} else {
						reportarError("Falta paréntesis derecho");
					}
				} else {
					reportarError("Falta expresion cadena");
				}
			} else {
				reportarError("Falta paréntesis izquierdo");
			}

		}

		return null;

	}

	/**
	 * 
	 * * <Ciclo> ::= cicle "("<ExpresionLogica>")" "{" <BloqueSentencias> "}"
	 * 
	 * @return Ciclo
	 */
	public Ciclo esCiclo() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("cicle")) {
			Token cicle = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				ExpresionLogica expLog = esExpresionLogica();
				if (expLog != null) {

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();

						if (tokenActual.getCategoria() == Categoria.LLAVE_APERTURA) {
							obtenerSiguienteToken();

							ArrayList<Sentencia> bloqueSentencias = esBloqueSentencias();

							if (bloqueSentencias != null) {

								if (tokenActual.getCategoria() == Categoria.LLAVE_CIERRE) {
									obtenerSiguienteToken();
									return new Ciclo(cicle, expLog, bloqueSentencias);
								} else {

									reportarError("Falta cerrar llaves");
								}

							} else {
								reportarError("Faltó el bloque de sentencias en la función");
							}

						} else {

							reportarError("Falta abrir llaves");
						}

					} else {
						reportarError("Falta paréntesis derecho");
					}
				} else {
					reportarError("Falta la condicion");
				}
			} else {
				reportarError("Falta paréntesis izquierdo");
			}

		}
		return null;
	}

	/**
	 * 
	 * * <Condicion> ::= con "("<ExpresionLogica>")" "{" <BloqueSentencias> "}"
	 * 
	 * @return Condicion
	 */
	public Condicion esCondicion() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("con")) {
			Token con = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.PARENTESIS_APERTURA) {
				obtenerSiguienteToken();
				ExpresionLogica expLog = esExpresionLogica();
				if (expLog != null) {

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRE) {
						obtenerSiguienteToken();

						if (tokenActual.getCategoria() == Categoria.LLAVE_APERTURA) {
							obtenerSiguienteToken();

							ArrayList<Sentencia> bloqueSentencias = esBloqueSentencias();

							if (bloqueSentencias != null) {

								if (tokenActual.getCategoria() == Categoria.LLAVE_CIERRE) {
									obtenerSiguienteToken();
									return new Condicion(con, expLog, bloqueSentencias);
								} else {

									reportarError("Falta cerrar llaves");
								}

							} else {
								reportarError("Faltó el bloque de sentencias en la función");
							}

						} else {

							reportarError("Falta abrir llaves");
						}

					} else {
						reportarError("Falta paréntesis derecho");
					}
				} else {
					reportarError("Falta la condicion");
				}
			} else {
				reportarError("Falta paréntesis izquierdo");
			}

		}
		return null;
	}

	/**
	 * <Arreglo> ::= list "[" <TipoDato> "," <Entero> "]" identificador
	 * 
	 * @return Arreglo
	 */
	public Arreglo esArreglo() {
		if (tokenActual.getCategoria() == Categoria.RESERVADA && tokenActual.getPalabra().equals("list")) {
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.CORCHETE_APERTURA) {
				obtenerSiguienteToken();
				Token tipoDato = esTipoDato();
				if (tipoDato != null) {
					obtenerSiguienteToken();
					if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
						obtenerSiguienteToken();
						if (tokenActual.getCategoria() == Categoria.ENTERO) {
							Token tamanio = tokenActual;
							obtenerSiguienteToken();
							if (tokenActual.getCategoria() == Categoria.CORCHETE_CIERRE) {
								obtenerSiguienteToken();
								if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
									Token identificador = tokenActual;
									obtenerSiguienteToken();
									if (tokenActual.getCategoria() == Categoria.TERMINAL) {
										obtenerSiguienteToken();
										return new Arreglo(tipoDato, tamanio, identificador);
									} else {
										reportarError("Falta el fin de linea");
									}
								} else {
									reportarError("Falta el identificador");
								}
							} else {
								reportarError("Falta el corchete de cierre");
							}
						} else {
							reportarError("Falta el tamaño del arreglo");
						}
					} else {
						reportarError("Falta la coma");
					}
				} else {
					reportarError("Falta el tipo de dato del arreglo");
				}

			} else {
				reportarError("Falta abrir corchetes");
			}
		}

		return null;
	}

	/**
	 * <DeclaracionCampo> ::= <tipoDato> identificador "!"
	 * 
	 * @return
	 */
	public DeclaracionDeVariable esDeclaracionDeVariable() {

		Token tipoDato = esTipoDato();
		if (tipoDato != null) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerSiguienteToken();
					return new DeclaracionDeVariable(tipoDato, identificador);
				} else {
					reportarError("Falta fin de sentencia");
				}
			} else {
				reportarError("Debe escribir al menos un identificador");
			}
		}
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
	 * <ListaArgumentos> :== <Expresion> [","<ListaArgumentos>]
	 * 
	 * @return
	 */
	public ArrayList<Expresion> esListaArgumentos() {

		ArrayList<Expresion> lista = new ArrayList<>();
		Expresion arg = esExpresion();

		if (arg != null) {
			lista.add(arg);
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				lista.addAll(esListaArgumentos());
			}
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
	public ExpresionCadena esExpresionCadena() {

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
	 * <ExpresionRelacional>::=
	 * <ExpresionAritmetica>[operadorRelacional<ExpresionAritmetica>]
	 * 
	 * @return
	 */
	public ExpresionRelacional esExpresionRelacional() {
		ExpresionAritmetica expresionAritIzq = esExpresionAritmetica();
		if (expresionAritIzq != null) {
			if (tokenActual.getCategoria() == Categoria.RELACIONAL) {
				Token opRelacional = tokenActual;
				obtenerSiguienteToken();
				ExpresionAritmetica expresionAritDer = esExpresionAritmetica();
				if (expresionAritDer != null) {
					return new ExpresionRelacional(expresionAritIzq, opRelacional, expresionAritDer);
				} else {
					reportarError("Falta la expresión relacional izquierda");
				}
			} else {
				
			}
			return new ExpresionRelacional(expresionAritIzq);
		}
		return null;
	}

	/**
	 * <ExpresionLogica> ::=
	 * <ExpresionRelacional>|<ExpresionRelacional>OperadorLogico<ExpresionRelacional>|"~"<ExpresionRelacional>
	 * 
	 * @return
	 */
	public ExpresionLogica esExpresionLogica() {

		if (tokenActual.getCategoria() == Categoria.LOGICO && tokenActual.getPalabra().equals("~")) {
			Token negacion = tokenActual;
			obtenerSiguienteToken();

			ExpresionRelacional ExpRelIzq = (ExpresionRelacional) esExpresionRelacional();

			if (ExpRelIzq != null) {

				return new ExpresionLogica(negacion, ExpRelIzq);
			} else {

				reportarError("Falta una expresión logica");
			}

		}

		ExpresionRelacional expRelIzq = (ExpresionRelacional) esExpresionRelacional();

		if (expRelIzq != null) {
			// obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.LOGICO) {
				Token opLogico = tokenActual;

				obtenerSiguienteToken();

				ExpresionRelacional ExpRelDer = (ExpresionRelacional) esExpresionRelacional();

				if (ExpRelDer != null) {

					return new ExpresionLogica(expRelIzq, ExpRelDer, opLogico);
				}

			}

			return new ExpresionLogica(expRelIzq);
		}
		return null;
	}

	/**
	 * <EA> ::= <Termino>[op.Arit<EA>] | "("<EA>")"[op.Arit<EA>]
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
							reportarError("Falta expresión aritmética");
						}
					} else {
						return new ExpresionAritmetica(ea);
					}
				} else {
					reportarError("Falta paréntesis derecho");
				}
			} else {
				reportarError("Falta expresión aritmética");
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
		System.out.println(mensaje + " en " + tokenActual.getFila() + " " + tokenActual.getColumna());
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

	public void setListaTokens(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
	}

	public int getPosActual() {
		return posActual;
	}

	public void setPosActual(int posActual) {
		this.posActual = posActual;
	}

	public Token getTokenActual() {
		return tokenActual;
	}

	public void setTokenActual(Token tokenActual) {
		this.tokenActual = tokenActual;
	}

	public ArrayList<ErrorSintactico> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(ArrayList<ErrorSintactico> listaErrores) {
		this.listaErrores = listaErrores;
	}

}
