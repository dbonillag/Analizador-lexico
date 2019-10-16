package lexico;

public class Token {
	
	private Categoria categoria;
	private String palabra;
	private int fila, columna;
	public Token(Categoria categoria, String palabra, int fila, int columna) {
		super();
		this.categoria = categoria;
		this.palabra = palabra;
		this.fila = fila;
		this.columna = columna;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Token [categoria=" + categoria + ", palabra=" + palabra + ", fila=" + fila + ", columna=" + columna
				+ "]";
	}
	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the palabra
	 */
	public String getPalabra() {
		return palabra;
	}
	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}
	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	

}
