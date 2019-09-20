package sintaxis;

import java.util.ArrayList;

public class UnidadDeCompilacion {

	private ArrayList<Funcion> listaFunciones;

	public UnidadDeCompilacion(ArrayList<Funcion> listaDeFunciones) {
		this.listaFunciones = listaDeFunciones;
	}

	public ArrayList<Funcion> getListaFunciones() {
		return listaFunciones;
	}

	public void setListaFunciones(ArrayList<Funcion> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}

}
