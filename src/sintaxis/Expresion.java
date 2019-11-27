package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public abstract class Expresion {

	public abstract TreeItem<String> getArbolVisual();

	public abstract void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito);
	
	public abstract String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito);

	public abstract String getJavaCode();

}
