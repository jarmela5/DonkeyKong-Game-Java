package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.List;
import java.util.*;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Tickable;
import pt.iscte.poo.utils.Vector2D;

public class Banana extends GameObject implements Damager {
	
	
	public Banana(Point2D position) {
		super(position);
		
	}

	@Override
	public String getName() {
		return "Banana";
	}

	@Override
	public int getLayer() {
		return 2;
	}
		

    @Override
    public void dealDamage(Damageable target, int amount) {
        
        target.takeDamage(amount); 
        System.out.println("Banana causou " + amount + " de dano!");
    }
	
    
    public static void moveBananas(List<Banana> bananas, Manel manel) {
        Iterator<Banana> iterator = bananas.iterator(); // Use um iterator
        while (iterator.hasNext()) {
            Banana b = iterator.next();
            Point2D currentPosition = b.getPosition();
            Vector2D v = new Vector2D(0, 1);
            Point2D newPosition = currentPosition.plus(v);

            b.setPosition(newPosition);

            ImageGUI.getInstance().removeImage(b);
            ImageGUI.getInstance().addImage(b);

            if (b.getPosition().equals(manel.getPosition())) {
                b.dealDamage(manel, 15);
                iterator.remove();
                ImageGUI.getInstance().removeImage(b);
                System.out.println("Banana colidiu com Manel!");
            }
        }
    }
}
