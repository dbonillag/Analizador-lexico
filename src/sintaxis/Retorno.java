package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Retorno extends Sentencia {

	private Expresion expresion;

	public Retorno(Expresion expresion) {

		this.expresion = expresion;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Retorno");
		raiz.getChildren().add(expresion.getArbolVisual());

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		if(!ambito.getTipo().equals("void")) {
			if (!(expresion.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito).equals(ambito.getTipo()))) {
				erroresSemanticos.add("El tipo de retorno no corresponde al valor retornado");
			}else {
				ambito.setRetorno(true);
			}
		}
		
		
	}

	@Override
	public String getJavaCode() {
		
		return "return "+expresion.getJavaCode()+"; ";
	}

}
