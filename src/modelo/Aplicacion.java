package modelo;

public class Aplicacion {

	public static void main(String[] args) {
		String codigoFuente = "1234hjjhk464";
		
		AnalizadorLexico analizador = new AnalizadorLexico(codigoFuente);
		analizador.analizar();
		System.out.println(analizador.getListaTokens().toString());
	}
}
