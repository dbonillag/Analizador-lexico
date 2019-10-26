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

	public void guardarSimboloVariable(String nombre, String tipo, int fila, int columna, String ambito,
			Expresion expresion) {
		Simbolo s = buscarSimboloVariable(nombre, ambito);
		if (s == null) {
			listaSimbolos.add(new Simbolo(nombre, tipo, fila, columna, ambito, expresion));
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

	public Simbolo buscarSimboloVariable(String nombre, String ambito) {
		for (Simbolo simbolo : listaSimbolos) {
			if (ambito != null) {
				if (nombre.equals(simbolo.getNombre()) && ambito.equals(simbolo.getAmbito())) {
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
