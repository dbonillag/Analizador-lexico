package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public abstract class Sentencia {

	public abstract TreeItem<String> getArbolVisual();

	public abstract void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito);

	public abstract void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito);

	public abstract String getJavaCode();
}
