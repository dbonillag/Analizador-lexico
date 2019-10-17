package sintaxis;

import javax.swing.tree.DefaultMutableTreeNode;

public class Ciclo extends Sentencia {

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		return new DefaultMutableTreeNode("Ciclo");
	}
}
