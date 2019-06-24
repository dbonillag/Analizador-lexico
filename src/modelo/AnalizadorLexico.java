package modelo;

import java.util.ArrayList;

import com.sun.swing.internal.plaf.basic.resources.basic_ja;

public class AnalizadorLexico {

	private String codigoFuente;
	private ArrayList<Token> listaTokens;
	private char caracterActual, finCodigo;
	private int posicionActual, columnaActual, filaActual;

	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<>();
		this.caracterActual = codigoFuente.charAt(0);
		this.finCodigo = '°';
	}

	public void analizar() {

		while (caracterActual != finCodigo) {
			if (caracterActual == ' ' || caracterActual == '\n' || caracterActual == '\t') {
				obtenerSiguienteCaracter();
				continue;
			}

			if (esEntero())
				continue;
			if (esReal())
				continue;
			if (esIdentificador())
				continue;
			if (esReservada())
				continue;
			if (esAritmetico())
				continue;
			if (esRelacional())
				continue;
			if (esLogico())
				continue;
			if (esAsignacion())
				continue;
			if (esIncremento_Decremento())
				continue;
			if (esParentesisApertura())
				continue;
			if (esParentesisCierre())
				continue;
			if (esLlaveApertura())
				continue;
			if (esLlaveCierre())
				continue;
			if (esTerminal())
				continue;
			if (esSeparador())
				continue;
			if (esHexadecimal())
				continue;
			if (esCadenaDeCaracteres())
				continue;
			if (esComentario())
				continue;

			listaTokens.add(new Token(Categoria.DESCONOCIDO, "" + caracterActual, filaActual, columnaActual));
			obtenerSiguienteCaracter();

		}

	}

	public boolean esEntero() {
		if (Character.isDigit(caracterActual)) {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (Character.isDigit(caracterActual)) {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			}

			if (caracterActual == '.'||caracterActual == 'h') {
				// backTracking
				return backTracking(palabra, fila, columna);
			} else {
				listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
				return true;
			}
		}
		// rechazo inmediato
		return false;
	}

	public boolean esReal() {
		if (Character.isDigit(caracterActual)) {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (Character.isDigit(caracterActual)) {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			}

			if (caracterActual == '.') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				while (Character.isDigit(caracterActual)) {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

				}

				listaTokens.add(new Token(Categoria.REAL, palabra, fila, columna));
				return true;

			} else {
				// backtracking
				return backTracking(palabra, fila, columna);
			}
		} else if (caracterActual == '.') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (Character.isDigit(caracterActual)) {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				while (Character.isDigit(caracterActual)) {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
				}

				listaTokens.add(new Token(Categoria.REAL, palabra, fila, columna));
				return true;

			} else {
				return backTracking(palabra, fila, columna);
			}

		}
		// rechazo inmediato
		return false;
	}

	public boolean esIdentificador() {
		if (caracterActual == '@') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (Character.isLetter(caracterActual) || caracterActual == '_') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				while (Character.isLetter(caracterActual) || caracterActual == '_') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
				}

				listaTokens.add(new Token(Categoria.IDENTIFICADOR, palabra, fila, columna));
				return true;

			} else {
				return backTracking(palabra, fila, columna);
			}

		}

		// rechazo inmediato
		return false;
	}

	public boolean esReservada() {

		String palabra = "";
		int fila = filaActual;
		int columna = columnaActual;

		if (caracterActual == 'Z' || caracterActual == 'R') {
			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
			return true;
		} else if (caracterActual == 'r') {
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == 'e') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'p') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

					listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
					return true;
				} else if (caracterActual == 'g') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'r') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 'e') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();
							if (caracterActual == 't') {
								palabra += caracterActual;
								obtenerSiguienteCaracter();

								listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
								return true;
							}
						}
					}
				}
			}
		} else if (caracterActual == 'c') {
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == 'i') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'c') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'l') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 'e') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();

							listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
							return true;
						}

					}
				}
			} else if (caracterActual == 'a') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'p') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'i') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 't') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();
							if (caracterActual == 'a') {
								palabra += caracterActual;
								obtenerSiguienteCaracter();
								if (caracterActual == 'l') {
									palabra += caracterActual;
									obtenerSiguienteCaracter();
									if (caracterActual == 'i') {
										palabra += caracterActual;
										obtenerSiguienteCaracter();
										if (caracterActual == 's') {
											palabra += caracterActual;
											obtenerSiguienteCaracter();
											if (caracterActual == 'm') {
												palabra += caracterActual;
												obtenerSiguienteCaracter();

												listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
												return true;
											}
										}

									}
								}
							}

						}

					}
				}
			} else if (caracterActual == 'o') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'm') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'm') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 'u') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();
							if (caracterActual == 'n') {
								palabra += caracterActual;
								obtenerSiguienteCaracter();
								if (caracterActual == 'i') {
									palabra += caracterActual;
									obtenerSiguienteCaracter();
									if (caracterActual == 's') {
										palabra += caracterActual;
										obtenerSiguienteCaracter();
										if (caracterActual == 'm') {
											palabra += caracterActual;
											obtenerSiguienteCaracter();

											listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
											return true;

										}

									}
								}
							}

						}

					}
				} else if (caracterActual == 'n') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

					listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
					return true;
				}
			}
		} else if (caracterActual == 'b') {
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == 'o') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'x') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

					listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
					return true;
				}
			}
		} else if (caracterActual == 't') {
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == 'r') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'a') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'd') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 'e') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();

							listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
							return true;
						}
					}

				}
			} else if (caracterActual == 'y') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 'p') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 'e') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();

						listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
						return true;
					}
				}

			}
		} else if (caracterActual == 'd') {
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == 'e') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				if (caracterActual == 's') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == 't') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == 'r') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();
							if (caracterActual == 'o') {
								palabra += caracterActual;
								obtenerSiguienteCaracter();
								if (caracterActual == 'y') {
									palabra += caracterActual;
									obtenerSiguienteCaracter();

									listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
									return true;
								}
							}
						}
					}
				}

			}
		}
		// rechazo inmediato
		return backTracking(palabra, fila, columna);
	}
	
	public boolean esAritmetico() {
		if (caracterActual == 'p'||caracterActual == 's'||caracterActual == 'm'||caracterActual == 'd'||caracterActual == 'r') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;
			
			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.ARITMETICO, palabra, fila, columna));
			return true;

		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esRelacional() {
		String palabra = "";
		int fila = filaActual;
		int columna = columnaActual;
		if (caracterActual == '<') {

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual=='=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
			}else if(caracterActual=='<') {
				return backTracking(palabra, fila, columna);
			}
			
			listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
			return true;
		}else if (caracterActual == '>') {

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual=='=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
			}else if(caracterActual=='>') {
				return backTracking(palabra, fila, columna);
			}
			
			listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
			return true;
		}else if(caracterActual=='='||caracterActual=='~') {
			
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual=='=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
				return true;
			}else {
				return backTracking(palabra, fila, columna);
			}
			
		}

		// rechazo inmediato
		return false;
	}

	public boolean esLogico() {
		if (caracterActual == '^'||caracterActual == '¨') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.LOGICO, palabra, fila, columna));
			return true;

		}else if(caracterActual=='~') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual=='=') {
				return backTracking(palabra, fila, columna);
			}else {
				listaTokens.add(new Token(Categoria.LOGICO, palabra, fila, columna));
				return true;
			}
			
		}

		// rechazo inmediato
		return false;
	}

	public boolean esAsignacion() {
		if (caracterActual == '=') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			if (caracterActual=='=') {
				return backTracking(palabra, fila, columna);
			}else {
				listaTokens.add(new Token(Categoria.ASIGNACION, palabra, fila, columna));
				return true;
			}
		}else if(caracterActual=='-'||caracterActual=='+'||caracterActual=='*'||caracterActual=='/'||caracterActual=='%') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			if (caracterActual=='=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.ASIGNACION, palabra, fila, columna));
				return true;
				
			}else {
				return backTracking(palabra, fila, columna);
			}

			
		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esIncremento_Decremento() {
		if (caracterActual == '<') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			if (caracterActual=='<') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.INCREMENTO_DECREMENTO, palabra, fila, columna));
				return true;
				
			}else {
				return backTracking(palabra, fila, columna);
			}
		}else if (caracterActual == '>') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			if (caracterActual=='>') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.INCREMENTO_DECREMENTO, palabra, fila, columna));
				return true;
				
			}else {
				return backTracking(palabra, fila, columna);
			}
		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esParentesisApertura() {
		if (caracterActual == '(') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.PARENTESIS_APERTURA, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esParentesisCierre() {
		if (caracterActual == ')') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.PARENTESIS_CIERRE, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esLlaveApertura() {
		if (caracterActual == '{') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.LLAVE_APERTURA, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	
	public boolean esLlaveCierre() {
		if (caracterActual == '}') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.LLAVE_CIERRE, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	
	
	public boolean esTerminal() {
		if (caracterActual == '!') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.TERMINAL, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	public boolean esSeparador() {
		if (caracterActual == ',') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			
			listaTokens.add(new Token(Categoria.SEPARADOR, palabra, fila, columna));
			return true;
			
		}

		// rechazo inmediato
		return false;
	}
	
	private boolean backTracking(String palabra, int fila, int columna) {
		this.filaActual = fila;
		this.columnaActual = columna;
		this.posicionActual -= palabra.length();
		this.caracterActual = codigoFuente.charAt(posicionActual);
		return false;
	}

	public void obtenerSiguienteCaracter() {
		posicionActual++;
		if (posicionActual < codigoFuente.length()) {
			if (caracterActual == '\n') {
				filaActual++;
				columnaActual = 0;
			} else {
				columnaActual++;
			}
			caracterActual = codigoFuente.charAt(posicionActual);
		} else {
			caracterActual = finCodigo;
		}

	}
	
	public boolean esHexadecimal() {
		if (Character.isDigit(caracterActual)||(caracterActual>='A'&&caracterActual<='F')) {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (Character.isDigit(caracterActual)||(caracterActual>='A'&&caracterActual<='F')) {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			}

			if (caracterActual == 'h') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.HEXADECIMAL, palabra, fila, columna));
				return true;
				
			} else {
				// backTracking
				return backTracking(palabra, fila, columna);
			}
		}
		// rechazo inmediato
		return false;
	}

	public boolean esCadenaDeCaracteres() {
		if (caracterActual=='"') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			
			
			while (caracterActual!='"'||caracterActual!='\''||caracterActual!='\n') {
				if (caracterActual=='\\') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual=='\''||caracterActual=='"'||caracterActual=='\\'||caracterActual=='t'||caracterActual=='b'||caracterActual=='r'||caracterActual=='f'||caracterActual=='n') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
					}else {
						return backTracking(palabra, fila, columna);
					}
				}else {
					
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					
				}		
			}
			
			if (caracterActual == '"') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.CADENA, palabra, fila, columna));
				return true;
				
			} else {
				// backTracking
				return backTracking(palabra, fila, columna);
			}
		}
		// rechazo inmediato
		return false;
	}
	
	
	public boolean esComentario() {
		if (caracterActual=='¿') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (caracterActual!='?'||caracterActual!='°') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			}
			if (caracterActual=='?') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
				
				listaTokens.add(new Token(Categoria.HEXADECIMAL, palabra, fila, columna));
				return true;
			}else {
				return backTracking(palabra, fila, columna);
			}
			
		}
		// rechazo inmediato
		return false;
	}
	
	/**
	 * @return the listaTokens
	 */
	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

	/**
	 * @param listaTokens the listaTokens to set
	 */
	public void setListaTokens(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
	}

}
