package sintaxis;

public class ErrorSintactico {

	private String mensaje;
	private int fila;
	private int columna;
	
	
	
	
	public ErrorSintactico(String mensaje, int fila, int columna) {
		this.mensaje = mensaje;
		this.fila = fila;
		this.columna = columna;
	}
	
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	
	
}
