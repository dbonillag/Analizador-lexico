package semantica;

import java.util.ArrayList;

import sintaxis.UnidadDeCompilacion;

public class AnalizadorSemantico {

	private TablaSimbolos tablaSimbolos;
	private ArrayList<String> listaErrores;
	private UnidadDeCompilacion uc;

	public AnalizadorSemantico() {
		this.listaErrores = new ArrayList<String>();
		this.tablaSimbolos = new TablaSimbolos(listaErrores);
		uc.llenarTablaDeSimbolos(tablaSimbolos, listaErrores);

	}

}
