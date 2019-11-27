package semantica;

import java.util.ArrayList;

import semantica.TablaSimbolos;
import sintaxis.UnidadDeCompilacion;

public class AnalizadorSemantico {

	private TablaSimbolos tablaSimbolos;
	private ArrayList<String> erroresSemanticos;
	private UnidadDeCompilacion uc;

	public AnalizadorSemantico() {
		this.erroresSemanticos = new ArrayList<String>();
		this.tablaSimbolos = new TablaSimbolos(erroresSemanticos);
		uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos);
	}

	public AnalizadorSemantico(UnidadDeCompilacion uc) {
		this.erroresSemanticos = new ArrayList<String>();
		this.tablaSimbolos = new TablaSimbolos(erroresSemanticos);
		this.uc = uc;
	}

	public void llenarTablaSimbolos() {
		uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos);
	}

	public void analizarSemantica() {
		uc.analizarSemantica(tablaSimbolos, erroresSemanticos);
	}

	public TablaSimbolos getTablaSimbolos() {
		return tablaSimbolos;
	}

	public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
		this.tablaSimbolos = tablaSimbolos;
	}

	public ArrayList<String> getErroresSemanticos() {
		return erroresSemanticos;
	}

	public void setErroresSemanticos(ArrayList<String> erroresSemanticos) {
		this.erroresSemanticos = erroresSemanticos;
	}

	public UnidadDeCompilacion getUc() {
		return uc;
	}

	public void setUc(UnidadDeCompilacion uc) {
		this.uc = uc;
	}
	
	

}
