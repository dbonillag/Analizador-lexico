package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import semantica.AnalizadorSemantico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import lexico.AnalizadorLexico;
import lexico.ErrorLexico;
import lexico.Token;
import lexico.TokenObservable;
import sintaxis.AnalizadorSintactico;
import sintaxis.ErrorSintactico;
import sintaxis.UnidadDeCompilacion;

public class ControladorPrincipal {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="columnaFila"
	private TableColumn<TokenObservable, Number> columnaFila; // Value injected by FXMLLoader

	@FXML // fx:id="columnaColumna"
	private TableColumn<TokenObservable, Number> columnaColumna; // Value injected by FXMLLoader

	@FXML // fx:id="columnaPalabra"
	private TableColumn<TokenObservable, String> columnaPalabra; // Value injected by FXMLLoader

	@FXML // fx:id="columnaCategoria"
	private TableColumn<TokenObservable, String> columnaCategoria; // Value injected by FXMLLoader

	@FXML // fx:id="btnIngresar"
	private Button btnIngresar; // Value injected by FXMLLoader

	@FXML // fx:id="campoTexto"
	private TextArea campoTexto; // Value injected by FXMLLoader
	
	 @FXML //fx:id="campoTexto"
	 private Button btnEjecutar;

	@FXML // fx:id="campoErrores"
	private TextArea campoErrores; // Value injected by FXMLLoader

	@FXML // fx:id="tablaPalabras"
	private TableView<TokenObservable> tablaPalabras; // Value injected by FXMLLoader

	@FXML
	private TreeView<String> arbolVisual;

	private AnalizadorSemantico analizadorSemantico;

	private AnalizadorLexico analizadorLexico;

	private AnalizadorSintactico analizadorSintactico;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert columnaFila != null : "fx:id=\"columnaFila\" was not injected: check your FXML file 'VentanaPrincipal.fxml'.";
		assert columnaColumna != null : "fx:id=\"columnaColumna\" was not injected: check your FXML file 'VentanaPrincipal.fxml'.";
		assert columnaPalabra != null : "fx:id=\"columnaPalabra\" was not injected: check your FXML file 'VentanaPrincipal.fxml'.";
		assert columnaCategoria != null : "fx:id=\"columnaCategoria\" was not injected: check your FXML file 'VentanaPrincipal.fxml'.";
		assert btnIngresar != null : "fx:id=\"btnIngresar\" was not injected: check your FXML file 'VentanaPrincipal.fxml'.";

		columnaFila.setCellValueFactory(claseCelda -> claseCelda.getValue().getFila());
		columnaColumna.setCellValueFactory(claseCelda -> claseCelda.getValue().getColumna());
		columnaPalabra.setCellValueFactory(claseCelda -> claseCelda.getValue().getPalabra());
		columnaCategoria.setCellValueFactory(
				claseCelda -> new SimpleStringProperty(claseCelda.getValue().getCategoria().toString()));

		campoTexto.setText("method @metodo bin (@a bin,@b text):\r\n" + 
				"Z @c!\r\n" + 
				"@c=5!\r\n" + 
				"pacman @c!\r\n" + 
				"@c=4p(5p3)!\r\n" + 
				"read(@b)!\r\n" + 
				"show(\"cadena\")!\r\n" + 
				"cicle(5>4){\r\n" + 
				"@c=6!\r\n" + 
				"interrupt!\r\n" + 
				"}\r\n" + 
				"list [text,5] @arreglo!\r\n" + 
				"measure(@arreglo,@c)!\r\n" + 
				"con(5==4){\r\n" + 
				"@c=8!\r\n" + 
				"}\r\n" + 
				"call @metodo(@a,\"saad\")!\r\n" + 
				"text @miCadena!\r\n" + 
				"regret @a!\r\n" + 
				":\r\n" + 
				"¿Is this the real life?\r\n" + 
				"¿¿\r\n" + 
				"Or is\r\n" + 
				"This just\r\n" + 
				"FANTASY\r\n" + 
				"??");

	}

	void actualizarTabla(ArrayList<Token> tokens) {
		int indice = tablaPalabras.getSelectionModel().getSelectedIndex();
		ObservableList<TokenObservable> tokensObservables = FXCollections.observableArrayList();
		for (Token token : tokens) {
			tokensObservables.add(new TokenObservable(token));
		}
		tablaPalabras.setItems(tokensObservables);
		tablaPalabras.getSelectionModel().clearSelection();
		tablaPalabras.getSelectionModel().select(indice);
	}

	@FXML
	void ingresar(ActionEvent event) {
		campoErrores.setText("");

		// Analisis Lexico
		analizadorLexico = new AnalizadorLexico(campoTexto.getText());
		analizadorLexico.analizar();
		actualizarTabla(analizadorLexico.getListaTokens());
		for (ErrorLexico error : analizadorLexico.getListaErrores()) {
			campoErrores.appendText(error.toString() + "\n");
		}

		// Analisis Sintactico
		analizadorSintactico = new AnalizadorSintactico(analizadorLexico.getListaTokens());
		UnidadDeCompilacion uc = analizadorSintactico.esUnidadDeCompilacion();
		arbolVisual.setRoot(uc.getArbolVisual());
		for (ErrorSintactico error : analizadorSintactico.getListaErrores()) {
			campoErrores.appendText(error.toString() + "\n");
		}
		
		analizadorSemantico = new AnalizadorSemantico(uc);
		analizadorSemantico.llenarTablaSimbolos();
		analizadorSemantico.analizarSemantica();
		for(String error : analizadorSemantico.getErroresSemanticos()) {
			campoErrores.appendText("Error semantico: "+error.toString()+"\n");
		}
		
		btnEjecutar.setDisable(false);
		btnEjecutar.setVisible(true);
	}
	
	@FXML
	void ejecutar(ActionEvent event) {
		
		if(analizadorSemantico.getErroresSemanticos().isEmpty()&&
				analizadorSintactico.getListaErrores().isEmpty()&&
				analizadorLexico.getListaErrores().isEmpty()) {
			String codigo=analizadorSemantico.getUc().getJavaCode();
			escribirArchivo(codigo);
			
			try {
				Process r = Runtime.getRuntime().exec("javac src/Principal.java");
				r.waitFor();
				
				Runtime.getRuntime().exec("java bin/Principal.class");
				System.out.println("Exito!");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}
	
	public void escribirArchivo(String codigo) {
		
		
		String ruta = "src/Principal.java";
		
		try {
			FileWriter fis = new FileWriter(ruta);
			BufferedWriter bw = new BufferedWriter(fis);
			
			bw.write(codigo);
			bw.close();
		}catch(Exception e){
			
			e.printStackTrace();
			
			
		}
	}
	
	
	public void traducirCodigo() {
		
	}

}
