package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class Arreglo extends Sentencia {

	private Token tipoDato;
	private Token tamanio;
	private Token identificador;

	public Arreglo(Token tipoDato, Token tamanio, Token identificador) {
		this.identificador = identificador;
		this.tamanio = tamanio;
		this.tipoDato = tipoDato;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Arreglo");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("Tipo de dato: " + tipoDato.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("Tamaño: " + tamanio.getPalabra()));

		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos,Simbolo ambito) {
		
		tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(), identificador.getFila(), identificador.getColumna(), ambito);
		
	}
}
