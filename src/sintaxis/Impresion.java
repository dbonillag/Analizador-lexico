package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Impresion extends Sentencia {

	private ExpresionCadena cadena;

	public Impresion(ExpresionCadena cadena) {
		this.cadena = cadena;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Impresion");
		raiz.getChildren().add(cadena.getArbolVisual());
		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		cadena.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		
	}
}
