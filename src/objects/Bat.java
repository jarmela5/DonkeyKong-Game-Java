package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import pt.iscte.poo.game.Room;

import java.util.List;
import java.util.Random;

public class Bat extends GameObject implements Damager{//leva dano?
    private Point2D position;
    private Random random;
    private int health;

    public Bat(Point2D position) {
        super(position);
        this.position = position;
        this.random = new Random();
        this.health = 1; 
    }

    @Override
    public String getName() {
        return "Bat";
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
    public void dealDamage(Damageable target, int amount) {
        
        target.takeDamage(amount); 
        System.out.println("Morcego causou " + amount + " de dano!");
    }
    

    public void moveBat(Room room, Manel manel) {
        // Movimentação lateral aleatória
        int direction = random.nextBoolean() ? 1 : -1; // 1 = direita, -1 = esquerda
        Point2D newPosition = position.plus(new Vector2D(direction, 0));


        Point2D below = position.plus(Direction.DOWN.asVector());
        if (room.canMoveTo(below) && room.isStairsAt(below)) {
            position = below;
        }
        else if (room.canMoveTo(newPosition)) {
            position = newPosition;
        }

        if (manel.getPosition().equals(position)) {
        	dealDamage(manel,10);
            remove(room); 
        }
    }


    public void remove(Room room) {
        ImageGUI.getInstance().removeImage(this); 
        room.removeObject(this); 
    }
    public boolean isAlive() {
        return this.health > 0;
    }

    public static void moveAllBats(List<GameObject> objects, Room room, Manel manel) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);

            if (obj instanceof Bat) {
                Bat bat = (Bat) obj;
                bat.moveBat(room, manel);

                if (!bat.isAlive()) {
                    objects.remove(i);
                    i--; // 
                }
            }
        }
    }
}
