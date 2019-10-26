package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import semantica.TablaSimbolos;

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

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Unidad de Compilación");

		for (Funcion funcion : listaFunciones) {
			raiz.getChildren().add(funcion.getArbolVisual());
		}

		return raiz;
	}

	public void llenarTablaDeSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> listaErrores) {
		// TODO aqui quedamos en clase
	}

}
