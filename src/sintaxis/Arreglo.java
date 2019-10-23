package sintaxis;

import javafx.scene.control.TreeItem;

public class Arreglo extends Sentencia {

	@Override
	public TreeItem<String> getArbolVisual() {
		return new TreeItem<String>("Arreglo");
	}
}
