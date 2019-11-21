package semantica;

import java.util.ArrayList;

import sintaxis.Expresion;

public class TablaSimbolos {

	private ArrayList<Simbolo> listaSimbolos;
	private ArrayList<String> listaErrores;

	public TablaSimbolos(ArrayList<String> listaErrores) {
		this.listaSimbolos = new ArrayList<Simbolo>();
		this.listaErrores = listaErrores;
	}

	public void guardarSimboloVariable(String nombre, String tipo, int fila, int columna, Simbolo ambito) {
		Simbolo s = buscarSimboloVariable(nombre, ambito,fila,columna);
		if (s == null) {
			listaSimbolos.add(new Simbolo(nombre, tipo, fila, columna, ambito));
		} else {
			listaErrores.add("La variable " + nombre + " ya existe en el ambito " + ambito);
		}

	}

	public void guardarSimboloFuncion(String nombre, String tipo, ArrayList<String> tipoParametros) {
		Simbolo s = buscarSimboloFuncion(nombre, tipoParametros);

		if (s == null) {
			listaSimbolos.add(new Simbolo(nombre, tipo, tipoParametros));
		} else {
			listaErrores.add("Ya existe una Funcion " + nombre + " con los tipos de parametro " + tipoParametros);
		}

	}

	public Simbolo buscarSimboloVariable(String nombre, Simbolo ambito,int fila, int columna) {
		for (Simbolo simbolo : listaSimbolos) {
			if (ambito != null) {
				if (nombre.equals(simbolo.getNombre()) && ambito.equals(simbolo.getAmbito())&&((fila==simbolo.getFila()&&columna>=simbolo.getColumna())||(fila>simbolo.getFila()))) {
					return simbolo;
				}
			}
		}
		return null;
	}

	public Simbolo buscarSimboloFuncion(String nombre, ArrayList<String> tipoParametros) {
		for (Simbolo simbolo : listaSimbolos) {
			if (tipoParametros != null) {
				if (nombre.equals(simbolo.getNombre()) && tipoParametros.equals(simbolo.getTipoParametros())) {
					return simbolo;
				}
			}
		}
		return null;
	}
}
