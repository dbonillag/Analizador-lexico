package lexico;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TokenObservable {

	private IntegerProperty fila;

	private IntegerProperty columna;

	private StringProperty palabra;

	private Categoria categoria;

	public TokenObservable(Token token) {

		this.fila = new SimpleIntegerProperty(token.getFila());
		this.columna = new SimpleIntegerProperty(token.getColumna());
		this.palabra = new SimpleStringProperty(token.getPalabra());
		this.categoria = token.getCategoria();

	}

	/**
	 * @return the fila
	 */
	public IntegerProperty getFila() {
		return fila;
	}

	/**
	 * @param fila the fila to set
	 */
	public void setFila(IntegerProperty fila) {
		this.fila = fila;
	}

	/**
	 * @return the columna
	 */
	public IntegerProperty getColumna() {
		return columna;
	}

	/**
	 * @param columna the columna to set
	 */
	public void setColumna(IntegerProperty columna) {
		this.columna = columna;
	}

	/**
	 * @return the palabra
	 */
	public StringProperty getPalabra() {
		return palabra;
	}

	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(StringProperty palabra) {
		this.palabra = palabra;
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

}
