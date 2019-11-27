package sintaxis;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	
	
	public void analizarSemantica(TablaSimbolos tablaSimbolos,ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador.getPalabra(), ambito, identificador.getFila(),
				identificador.getColumna());

		if (s == null) {
			erroresSemanticos.add("La variable " + identificador.getPalabra() + " no existe");
		} else {
			if (!(s.getTipo().equals("text"))) {
				erroresSemanticos.add("La variable " + identificador.getPalabra() + " no es texto");
			}
		}
		
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJavaCode() {
		return identificador.getPalabra().replace("@", "$")+"=JOptionPane.showInputDialog(\"\"); ";
	}
	
	
	
	
}
