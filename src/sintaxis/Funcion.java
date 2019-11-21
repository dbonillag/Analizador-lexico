package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

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

		// Parametros
		TreeItem<String> params = new TreeItem<String>("Lista Parametros");
		raiz.getChildren().add(params);

		for (Parametro parametro : parametros) {
			params.getChildren().add(parametro.getArbolVisual());
		}

		// Sentencias
		TreeItem<String> sentencias = new TreeItem<String>("Lista Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentencias) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}

		return raiz;
	}

	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {
		ArrayList<String> tipoParametros = new ArrayList<>();

		for (Parametro parametro : parametros) {
			tipoParametros.add(parametro.getTipoDato().getPalabra());
		}

		if (tipoRetorno != null) {
			tablaSimbolos.guardarSimboloFuncion(nombre.getPalabra(), tipoRetorno.getPalabra(), tipoParametros);
		} else {
			tablaSimbolos.guardarSimboloFuncion(nombre.getPalabra(), "void", tipoParametros);
		}

		Simbolo ambito = tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), tipoParametros);

		for (Parametro parametro : parametros) {
			tablaSimbolos.guardarSimboloVariable(parametro.getNombre().getPalabra(),
					parametro.getTipoDato().getPalabra(), parametro.getNombre().getFila(),
					parametro.getNombre().getColumna(), ambito);
		}

		for (Sentencia sentencia : bloqueSentencias) {
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito);
		}
	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {

		ArrayList<String> tipoParametros = new ArrayList<>();
		for (Parametro parametro : parametros) {
			tipoParametros.add(parametro.getTipoDato().getPalabra());
		}

		Simbolo ambito = tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), tipoParametros);

		for (Sentencia sentencia : bloqueSentencias) {
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}

	}

}
