package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;

public class Condicion extends Sentencia {

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		return new DefaultMutableTreeNode("Condición");
	}
}
