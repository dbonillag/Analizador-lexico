package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;

public class Ciclo extends Sentencia {

	private ExpresionLogica expLog;
	private ArrayList<Sentencia> bloqueSentencias;

	public Ciclo(ExpresionLogica expLog, ArrayList<Sentencia> bloqueSentencias) {
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
}
