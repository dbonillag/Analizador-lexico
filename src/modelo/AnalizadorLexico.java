package modelo;

import java.util.ArrayList;

public class AnalizadorLexico {
	
	private String codigoFuente;
	private ArrayList<Token> listaTokens;
	private char caracterActual,finCodigo;
	private int posicionActual, columnaActual, filaActual;
	
	public AnalizadorLexico(String codigoFuente) {
		this.codigoFuente=codigoFuente;
		this.listaTokens=new ArrayList<>();
		this.caracterActual=codigoFuente.charAt(0);
		this.finCodigo='¿';
	}
	
	public void analizar() {
		
		while(caracterActual != finCodigo) {
			if (caracterActual==' '||caracterActual=='\n'||caracterActual=='\t') {
				obtenerSiguienteCaracter();
				continue;
			}
			
			if (esEntero()) continue;
			if(esIdentificador()) continue;
			
			listaTokens.add(new Token(Categoria.DESCONOCIDO,""+caracterActual,filaActual,columnaActual));
			obtenerSiguienteCaracter();
				
			
			
		}
		
	}
	
	public boolean esEntero() {
		if(Character.isDigit(caracterActual)) {
			String palabra="";
			int fila=filaActual;
			int columna=columnaActual;
			
			//transicion
			palabra+=caracterActual;
			obtenerSiguienteCaracter();
			
			while(Character.isDigit(caracterActual)) {
				palabra+=caracterActual;
				obtenerSiguienteCaracter();
			}
			
			listaTokens.add(new Token(Categoria.ENTERO, palabra, fila, columna));
			return true;
		}
		
		//rechazo inmediato
		return false;
	}
	
	public boolean esIdentificador() {
		if(Character.isLetter(caracterActual)||caracterActual=='_'||caracterActual=='$') {
			String palabra="";
			int fila=filaActual;
			int columna=columnaActual;
			
			//transicion
			palabra+=caracterActual;
			obtenerSiguienteCaracter();
			
			while(Character.isLetter(caracterActual)||caracterActual=='_'||caracterActual=='$'||Character.isDigit(caracterActual)) {
				palabra+=caracterActual;
				obtenerSiguienteCaracter();
			}
			
			listaTokens.add(new Token(Categoria.IDENTIFICADOR, palabra, fila, columna));
			return true;
		}
		
		//rechazo inmediato
		return false;
	}
	
	public void obtenerSiguienteCaracter() {
		posicionActual++;
		if(posicionActual<codigoFuente.length()) {
			if(caracterActual=='\n') {
				filaActual++;
				columnaActual=0;
			}else {
				columnaActual++;
			}
			caracterActual= codigoFuente.charAt(posicionActual);
		}else {
			caracterActual=finCodigo;
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
	
	

}
