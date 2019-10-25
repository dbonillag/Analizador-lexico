package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

	@FXML // fx:id="campoErrores"
	private TextArea campoErrores; // Value injected by FXMLLoader

	@FXML // fx:id="tablaPalabras"
	private TableView<TokenObservable> tablaPalabras; // Value injected by FXMLLoader

	@FXML
	private TreeView<String> arbolVisual;

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
				"@a=5!\r\n" + 
				"@a=4p(5p3)!\r\n" + 
				"read(@a)!\r\n" + 
				"show(\"cadena\")!\r\n" + 
				"cicle(5>4){\r\n" + 
				"@a=6!\r\n" + 
				"}\r\n" + 
				"con(5==4){\r\n" + 
				"@a=8!\r\n" + 
				"}\r\n" + 
				"call @metodoAInvocar(@a,5p4)!\r\n" + 
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


		// Analisis Lexico
		AnalizadorLexico analizadorLexico = new AnalizadorLexico(campoTexto.getText());
		analizadorLexico.analizar();
		actualizarTabla(analizadorLexico.getListaTokens());
		for (ErrorLexico error : analizadorLexico.getListaErrores()) {
			campoErrores.appendText(error.toString() + "\n");
		}

		// Analisis Sintactico
		AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.getListaTokens());
		UnidadDeCompilacion uc = analizadorSintactico.esUnidadDeCompilacion();
		arbolVisual.setRoot(uc.getArbolVisual());
		for (ErrorSintactico error : analizadorSintactico.getListaErrores()) {
			campoErrores.appendText(error.toString() + "\n");
		}

	}

}
