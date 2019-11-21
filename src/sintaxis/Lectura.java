package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Lectura extends Sentencia {

	private Token identificador;

	public Lectura(Token identificador) {
		this.identificador = identificador;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Lectura");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));
		return raiz;

	}
	
	
	public void analizarSemantica(TablaSimbolos tablaSimbolos,ArrayList<String> errores, Simbolo ambito) {
		
		
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}
}
