package sintaxis;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import lexico.Token;
import semantica.Simbolo;
import semantica.TablaSimbolos;

public class InvocacionDeFuncion extends Sentencia {

	private Token identificador;
	private ArrayList<Expresion> argumentos;

	public InvocacionDeFuncion(Token identificador, ArrayList<Expresion> argumentos) {
		this.identificador = identificador;
		this.argumentos = argumentos;
	}

	public Token getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Token identificador) {
		this.identificador = identificador;
	}

	public ArrayList<Expresion> getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(ArrayList<Expresion> argumentos) {
		this.argumentos = argumentos;
	}

	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<>("Invocacion");
		raiz.getChildren().add(new TreeItem<>("Identificador: " + identificador.getPalabra()));

		TreeItem<String> sentencias = new TreeItem<String>("Lista Sentencias");
		raiz.getChildren().add(sentencias);

		for (Expresion arg : argumentos) {
			sentencias.getChildren().add(arg.getArbolVisual());
		}

		return raiz;

	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		ArrayList<String> tipoArgumentos = new ArrayList<>();
		for (Expresion arg : argumentos) {
			tipoArgumentos.add(arg.obtenerTipo(tablaSimbolos, erroresSemanticos, ambito));
		}

		Simbolo funcion = tablaSimbolos.buscarSimboloFuncion(identificador.getPalabra(), tipoArgumentos);
		
		if(funcion==null) {
			erroresSemanticos.add("La función "+identificador.getPalabra()+""+tipoArgumentos+" no ha sido declarada");
		}
			
		
		
		
		
	}

	@Override
	public String getJavaCode() {
		String codigo=identificador.getPalabra().replace("@", "$")+"(";
		for (Expresion expresion : argumentos) {
			codigo+=expresion.getJavaCode()+",";
		}
		
		if (!argumentos.isEmpty()) {
			codigo=codigo.substring(0, codigo.length()-1);
		}
		
		codigo+="); ";
		
		return codigo;
	}

}
