package semantica;

import java.util.ArrayList;

import sintaxis.Expresion;

public class Simbolo {

	private String nombre;
	private String tipo;
	private int fila, columna;
	private String ambito;
	private Expresion expresion;
	private ArrayList<String> tipoParametros;

	// Para instanciar variable
	public Simbolo(String nombre, String tipo, int fila, int columna, String ambito, Expresion expresion) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.fila = fila;
		this.columna = columna;
		this.ambito = ambito;
		this.expresion = expresion;
	}

	// Para instanciar Funciones
	public Simbolo(String nombre, String tipo, ArrayList<String> tipoParametros) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.tipoParametros = tipoParametros;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public ArrayList<String> getTipoParametros() {
		return tipoParametros;
	}

	public void setTipoParametros(ArrayList<String> tipoParametros) {
		this.tipoParametros = tipoParametros;
	}

}
