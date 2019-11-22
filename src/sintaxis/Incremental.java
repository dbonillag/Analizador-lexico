package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Incremental extends Sentencia {

	private Token identificador;

	public Incremental(Token identificador) {
		this.identificador = identificador;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Incremental");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador.getPalabra(), ambito, identificador.getFila(),
				identificador.getColumna());

		if (s == null) {
			erroresSemanticos.add("La variable " + identificador.getPalabra() + " no existe");
		} else {
			if (!(s.getTipo().equals("Z") || s.getTipo().equals("R"))) {
				erroresSemanticos.add("La variable " + identificador.getPalabra() + " no es numérica");
			}
		}
		
	}

}
