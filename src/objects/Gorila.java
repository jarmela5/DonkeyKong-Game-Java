package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.game.Tickable;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import java.util.*;

public class Gorila extends GameObject implements Damageable, Damager{
	private Point2D position;
	private static List<Banana> bananas = new ArrayList<>();
	private int health;

	public Gorila(Point2D position) {
		super(position);
		this.position = position;
		health=100;

	}

	@Override
	public String getName() {
		return "DonkeyKong";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	
	@Override
	public void takeDamage(int amount) {
		ImageGUI gui = ImageGUI.getInstance();
		int resto=amount-health;
		this.health -= amount;
		if(health>0)
		gui.setStatusMessage("Gorila perdeu " + amount + " de vida. Vida restante: " + this.health + "/100");
		else {
			gui.setStatusMessage("Gorila perdeu " + resto + " de vida. Vida restante: " + 0 + "/100");
		}
	}
	@Override
	public  void dealDamage(Damageable target, int amount) {

		target.takeDamage(amount);
		System.out.println("causou " + amount + " de dano!");
	}

    

    public boolean isAlive() {
        return this.health > 0;
    }
    

	public void move(int k) {

		if (k == 0) {
			position = position.plus(Direction.directionFor(39).asVector());
		} else {
			position = position.plus(Direction.directionFor(37).asVector());
		}

	}
	
	public static void clearBananas() {
		bananas.clear(); 
	}

	
	public static void moveGorilas(List<GameObject> gorilas, Room room, Manel manel) {
		ImageGUI gui = ImageGUI.getInstance();
		for (Iterator<GameObject> iterator = gorilas.iterator(); iterator.hasNext();) {
			GameObject obj = iterator.next();
			if (obj instanceof Gorila) {
				Gorila gorila = (Gorila) obj;
				
				int randomDirection = (int) (Math.random() * 2);
				if (!gorila.isAlive()) {
					iterator.remove();
					ImageGUI.getInstance().removeImage(gorila);
					gui.setStatusMessage("Gorila morreu!!!");
				}

				Point2D newPosition = null;
				Point2D oldPosition = gorila.getPosition();
				if (randomDirection == 0) {
					newPosition = gorila.getPosition().plus(Direction.directionFor(39).asVector());
				} else {
					newPosition = gorila.getPosition().plus(Direction.directionFor(37).asVector());
				}
				if (manel.getPosition().equals(newPosition)) {

					gorila.dealDamage(manel, 10);
					newPosition = oldPosition;

				} else if (!room.isGorilaAtPosition(newPosition) && room.canMoveTo(newPosition)) {
					gorila.move(randomDirection);
				} else {
					newPosition = oldPosition; // Mantém a posição original
				}

				// criar bananas
				int randomBanana = (int) (Math.random() * 2);
				if (randomBanana == 1) {
					Banana bn = new Banana(newPosition);
					bananas.add(bn);
				}
			}
		}
		
		Banana.moveBananas(bananas, manel);

	}
	
	
	public static boolean isAdjacent(Point2D manelPosition, Point2D gorilaPosition) {
	    // Verifica se as posições são adjacentes (distância de 1 em x ou y)
	    int dx = Math.abs(manelPosition.getX() - gorilaPosition.getX());
	    int dy = Math.abs(manelPosition.getY() - gorilaPosition.getY());

	    return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);  
	}

}
