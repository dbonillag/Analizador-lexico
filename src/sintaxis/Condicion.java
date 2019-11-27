package sintaxis;

import java.util.ArrayList;


import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Condicion extends Sentencia {
	
	private static int serie = 0;
	private int id;
	
	private Token con;
	private ExpresionLogica expLogica;
	private ArrayList<Sentencia> bloqueSentencias;

	public Condicion(Token con, ExpresionLogica expLog, ArrayList<Sentencia> bloqueSentencias) {
		serie++;
		id=serie;
		this.con=con;
		
		this.expLogica = expLog;
		this.bloqueSentencias = bloqueSentencias;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<>("Condición");
		raiz.getChildren().add(expLogica.getArbolVisual());

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
		tablaSimbolos.guardarSimboloVariable("_con_" + id, null, con.getFila(), con.getColumna(), ambito);

		ambito = tablaSimbolos.buscarSimboloVariable("_con_" + id, ambito);
		*/

		for (Sentencia sentencia : bloqueSentencias) {
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito);
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		expLogica.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		for (Sentencia sentencia : bloqueSentencias) {
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
	}

	@Override
	public String getJavaCode() {
		String codigo="";
		codigo+="if("+expLogica.getJavaCode()+"){";
		for (Sentencia sentencia : bloqueSentencias) {
			codigo+=sentencia.getJavaCode();
		}
		
		codigo+="} ";
		return codigo;
	}
}
