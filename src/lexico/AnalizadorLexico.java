package lexico;

import java.util.ArrayList;

public class AnalizadorLexico {

	private String codigoFuente;
	private ArrayList<Token> listaTokens;
	private ArrayList<ErrorLexico> listaErrores;
	private char caracterActual, finCodigo;
	private int posicionActual, columnaActual, filaActual;

	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente = codigoFuente;
		this.listaTokens = new ArrayList<>();
		this.listaErrores = new ArrayList<>();
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
			if (esCorcheteApertura())
				continue;
			if (esCorcheteCierre())
				continue;
			if (esPunto())
				continue;
			if (esDosPuntos())
				continue;
			if (esTerminal())
				continue;
			if (esSeparador())
				continue;
			if (esComentarioLinea())
				continue;
			if (esComentarioBloque())
				continue;
			if (esCaracter())
				continue;
			if (esCadenaDeCaracteres())
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

			if (caracterActual == '.') {
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

			if (Character.isLetter(caracterActual)) {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				while (Character.isLetter(caracterActual)) {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
				}

			} else {
				listaErrores.add(new ErrorLexico("El identificador debe tener al menos una letra", fila, columna));
			}

			listaTokens.add(new Token(Categoria.IDENTIFICADOR, palabra, fila, columna));
			return true;

		}

		// rechazo inmediato
		return false;
	}

	
	/**
	 * Z : NATURAL
	 * R : REAL 
	 * regret : RETURN
	 * cicle : WHILE
	 * con : IF
	 * start : main
	 * bin : Boolean
	 * void : void
	 * text : String
	 * char : char
	 * list : arreglo
	 * show : imprimir
	 * read : leer
	 * pika : no hace nada
	 */
	
	// <TipoRetorno> ::=Z | R | bin | void | text | char
	public boolean esReservada() {
		
		
		
		
		if (Character.isLetter(caracterActual)) {
			String palabra = "";
			String[] reservadas = { "Z", "R", "regret", "cicle", "con","start",
					"bin","void","text","char","list","show","read","pika"};
			int fila = filaActual;
			int columna = columnaActual;

			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (Character.isLetter(caracterActual)) {

				palabra += caracterActual;
				obtenerSiguienteCaracter();

			}

			for (int i = 0; i < reservadas.length; i++) {
				if (palabra.equals(reservadas[i])) {
					listaTokens.add(new Token(Categoria.RESERVADA, palabra, fila, columna));
					return true;
				}
			}

			return backTracking(palabra, fila, columna);
		}
		// rechazo inmediato
		return false;
	}

	public boolean esAritmetico() {
		if (caracterActual == 'p' || caracterActual == 's' || caracterActual == 'm' || caracterActual == 'd'
				|| caracterActual == 'r') {
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

			if (caracterActual == '=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
			} else if (caracterActual == '<') {
				return backTracking(palabra, fila, columna);
			}

			listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
			return true;
		} else if (caracterActual == '>') {

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual == '=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();
			} else if (caracterActual == '>') {
				return backTracking(palabra, fila, columna);
			}

			listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
			return true;
		} else if (caracterActual == '=' || caracterActual == '~') {

			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == '=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				listaTokens.add(new Token(Categoria.RELACIONAL, palabra, fila, columna));
				return true;
			} else {
				return backTracking(palabra, fila, columna);
			}

		}

		// rechazo inmediato
		return false;
	}

	public boolean esLogico() {
		if (caracterActual == '^' || caracterActual == '¨') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.LOGICO, palabra, fila, columna));
			return true;

		} else if (caracterActual == '~') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();
			if (caracterActual == '=') {
				return backTracking(palabra, fila, columna);
			} else {
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

			if (caracterActual == '=') {
				return backTracking(palabra, fila, columna);
			} else {
				listaTokens.add(new Token(Categoria.ASIGNACION, palabra, fila, columna));
				return true;
			}
		} else if (caracterActual == '-' || caracterActual == '+' || caracterActual == '*' || caracterActual == '/'
				|| caracterActual == '%') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual == '=') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			} else {
				listaErrores.add(
						new ErrorLexico("Operador de asignaci�n necesita un = para estar completo", fila, columna));

			}

			listaTokens.add(new Token(Categoria.ASIGNACION, palabra, fila, columna));
			return true;

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

			if (caracterActual == '<') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				listaTokens.add(new Token(Categoria.INCREMENTO_DECREMENTO, palabra, fila, columna));
				return true;

			} else {
				return backTracking(palabra, fila, columna);
			}
		} else if (caracterActual == '>') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual == '>') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

				listaTokens.add(new Token(Categoria.INCREMENTO_DECREMENTO, palabra, fila, columna));
				return true;

			} else {
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

	public boolean esCorcheteApertura() {
		if (caracterActual == '[') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.CORCHETE_APERTURA, palabra, fila, columna));
			return true;

		}

		// rechazo inmediato
		return false;
	}

	public boolean esCorcheteCierre() {
		if (caracterActual == '}') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.CORCHETE_CIERRE, palabra, fila, columna));
			return true;

		}

		// rechazo inmediato
		return false;
	}

	public boolean esPunto() {
		if (caracterActual == '.') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.PUNTO, palabra, fila, columna));
			return true;

		}

		// rechazo inmediato
		return false;
	}

	public boolean esDosPuntos() {
		if (caracterActual == ':') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			listaTokens.add(new Token(Categoria.DOS_PUNTOS, palabra, fila, columna));
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

	public boolean esComentarioLinea() {
		if (caracterActual == '¿') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual == '¿') {
				backTracking(palabra, fila, columna);
			} else {
				while (caracterActual != '\n' && caracterActual != finCodigo) {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

				}

				listaTokens.add(new Token(Categoria.COMENTARIO_LINEA, palabra, fila, columna));
				return true;
			}
		}
		// rechazo inmediato
		return false;
	}

	public boolean esComentarioBloque() {
		if (caracterActual == '¿') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual == '¿') {

				while (caracterActual != finCodigo) {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == '?') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
						if (caracterActual == '?') {
							palabra += caracterActual;
							obtenerSiguienteCaracter();
							listaTokens.add(new Token(Categoria.COMENTARIO_BLOQUE, palabra, fila, columna));
							return true;
						}

					}

				}

				listaErrores.add(new ErrorLexico("Comentario de bloque no ha sido cerrado", fila, columna));
				listaTokens.add(new Token(Categoria.COMENTARIO_BLOQUE, palabra, fila, columna));
				return true;
			} else {
				return backTracking(palabra, fila, columna);
			}

		}
		// rechazo inmediato
		return false;
	}

	public boolean esCaracter() {
		if (caracterActual == '\'') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			if (caracterActual != '\'' && caracterActual != finCodigo && caracterActual != '\n') {
				if (caracterActual == '\\') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();

					if (caracterActual == '\'' || caracterActual == '\\' || caracterActual == 't'
							|| caracterActual == 'b' || caracterActual == 'r' || caracterActual == 'f'
							|| caracterActual == 'n') {

						palabra += caracterActual;
						obtenerSiguienteCaracter();

					} else {

						listaErrores.add(new ErrorLexico("caracter despues del backslash invalido", fila, columna));

					}
				} else {

					palabra += caracterActual;
					obtenerSiguienteCaracter();
				}
			}
			if (caracterActual == '\'') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			} else {
				// backTracking
				listaErrores.add(new ErrorLexico("Falta ' para finalizar el caracter", fila, columna));
			}

			listaTokens.add(new Token(Categoria.CARACTER, palabra, fila, columna));
			return true;
		}
		// rechazo inmediato
		return false;
	}

	public boolean esCadenaDeCaracteres() {
		if (caracterActual == '"') {
			String palabra = "";
			int fila = filaActual;
			int columna = columnaActual;

			// transicion
			palabra += caracterActual;
			obtenerSiguienteCaracter();

			while (caracterActual != '"' && caracterActual != finCodigo && caracterActual != '\n') {
				if (caracterActual == '\\') {
					palabra += caracterActual;
					obtenerSiguienteCaracter();
					if (caracterActual == '"' || caracterActual == '\\' || caracterActual == 't'
							|| caracterActual == 'b' || caracterActual == 'r' || caracterActual == 'f'
							|| caracterActual == 'n') {
						palabra += caracterActual;
						obtenerSiguienteCaracter();
					} else {

						listaErrores.add(new ErrorLexico("caracter despues del backslash invalido", fila, columna));

					}
				} else {

					palabra += caracterActual;
					obtenerSiguienteCaracter();

				}
			}

			if (caracterActual == '"') {
				palabra += caracterActual;
				obtenerSiguienteCaracter();

			} else {
				// backTracking
				listaErrores.add(new ErrorLexico("Falta \" para finalizar la cadena", fila, columna));
			}
			listaTokens.add(new Token(Categoria.CADENA, palabra, fila, columna));
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

	public ArrayList<ErrorLexico> getListaErrores() {
		return listaErrores;
	}

	public void setListaErrores(ArrayList<ErrorLexico> listaErrores) {
		this.listaErrores = listaErrores;
	}

}
