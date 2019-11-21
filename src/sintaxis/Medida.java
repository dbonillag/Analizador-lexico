package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Medida extends Sentencia {

	private Token identificadorArreglo;
	private Token identificadorVariable;

	public Medida(Token identificadorArreglo, Token identificadorVariable) {
		super();
		this.identificadorArreglo = identificadorArreglo;
		this.identificadorVariable = identificadorVariable;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Medida");
		raiz.getChildren().add(new TreeItem<>("Id Arreglo: " + identificadorArreglo.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("id Variable: " + identificadorVariable.getPalabra()));

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}
}
