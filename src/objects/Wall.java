package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Wall extends GameObject {


	
	
	public Wall(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		return "Wall";
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
