package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
public class DoorClosed extends GameObject{


	
	
	public DoorClosed(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		return "DoorClosed";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	/*@Override
	public Point2D getPosition() {
		return position;
	}*/
}