package sintaxis;

import java.util.ArrayList;


import javafx.scene.control.TreeItem;

public class Condicion extends Sentencia {

	private ExpresionLogica expLogica;
	private ArrayList<Sentencia> bloqueSentencias;

	public Condicion(ExpresionLogica expLog, ArrayList<Sentencia> bloqueSentencias) {
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
}
