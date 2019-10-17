package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;

public class Arreglo extends Sentencia {

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		return new DefaultMutableTreeNode("Asignación");
	}
}
