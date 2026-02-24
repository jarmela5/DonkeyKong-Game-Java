package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable {

	public Sword(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Sword";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void interact(GameObject player) {
		ImageGUI gui = ImageGUI.getInstance();
		if (player instanceof Manel) { // Verifica se o jogador é do tipo Manel
			Manel manel = (Manel) player;
			manel.pickSword(); 
			gui.setStatusMessage("Você pegou uma espada! +20 Damage");
		}
	}
	
}
