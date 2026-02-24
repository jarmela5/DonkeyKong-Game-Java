package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;


public class Trap extends GameObject implements Damager{

	private int damagePerTick=10;
	
	public Trap(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		return "Trap";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	 @Override
	public void dealDamage(Damageable target, int amount) {
        target.takeDamage(amount);
        
    }
	 public int getDamagePerTick() {
		 return damagePerTick;
	 }

	/*@Override
	public Point2D getPosition() {
		return position;
	}*/
}
