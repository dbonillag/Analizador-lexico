package sintaxis;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import javafx.scene.control.TreeItem;
import lexico.Token;

public class Funcion {

	private Token nombre;
	private ArrayList<Parametro> parametros;
	private Token tipoRetorno;
	private ArrayList<Sentencia> bloqueSentencias;

	public Funcion(Token nombre, ArrayList<Parametro> parametros, Token tipoRetorno,
			ArrayList<Sentencia> bloqueSentencias) {
		super();
		this.nombre = nombre;
		this.parametros = parametros;
		this.tipoRetorno = tipoRetorno;
		this.bloqueSentencias = bloqueSentencias;
	}

	public Token getNombre() {
		return nombre;
	}

	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(ArrayList<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Token getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(Token tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public ArrayList<Sentencia> getBloqueSentencias() {
		return bloqueSentencias;
	}

	public void setBloqueSentencias(ArrayList<Sentencia> bloqueSentencias) {
		this.bloqueSentencias = bloqueSentencias;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Función");
		raiz.getChildren().add(new TreeItem<>("Nombre: " + nombre.getPalabra()));
		raiz.getChildren().add(new TreeItem<>("Tipo de retorno: " + tipoRetorno.getPalabra()));

		TreeItem<String> params = new TreeItem();
		raiz.getChildren().add(params);

		for (Parametro parametro : parametros) {
			params.getChildren().add(parametro.getArbolVisual());
		}
		return raiz;
	}

}
