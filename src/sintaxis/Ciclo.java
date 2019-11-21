package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Ciclo extends Sentencia {
	private static int serie = 0;
	private int id;

	private Token cicle;
	private ExpresionLogica expLog;

	private ArrayList<Sentencia> bloqueSentencias;

	public Ciclo(Token cicle, ExpresionLogica expLog, ArrayList<Sentencia> bloqueSentencias) {
		this.cicle = cicle;
		id = serie;
		this.expLog = expLog;
		this.bloqueSentencias = bloqueSentencias;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Ciclo");
		raiz.getChildren().add(expLog.getArbolVisual());

		// Sentencias
		TreeItem<String> sentencias = new TreeItem<String>("Lista Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentencias) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}
		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		/*
		 * tablaSimbolos.guardarSimboloVariable("_ciclo_" + id, null, cicle.getFila(),
		 * cicle.getColumna(), ambito);
		 * 
		 * ambito = tablaSimbolos.buscarSimboloVariable("_ciclo_" + id, ambito);
		 */

		for (Sentencia sentencia : bloqueSentencias) {
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito);
		}

	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}


}
