package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Interrupcion extends Sentencia {

	public Interrupcion() {
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Interrupcion");
		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		
		
	}

}
