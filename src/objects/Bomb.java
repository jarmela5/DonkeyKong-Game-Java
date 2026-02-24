package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import pt.iscte.poo.game.Room;
import java.util.*;
import java.util.List;
import java.util.Random;

public class Bomb extends GameObject  implements  Damager{
    private static boolean active;  
    private static int ticksToExplode;  
    private static Point2D bombPosition; 
    private  Point2D Position;
    private boolean tem;

    public Bomb(Point2D position) {
        super(position);
        active = false;
        ticksToExplode = 5;
        this.Position=position;
    }

    public void activate(Point2D position) {
        active = true;
        ticksToExplode = 5; 
        bombPosition = position;
        tem=false;
        this.Position=position;
       
    }
    @Override
    public void dealDamage(Damageable target, int amount) {
        
        target.takeDamage(amount); 
        System.out.println("Bomba causou " + amount + " de dano!");
    }
    @Override
    public Point2D getPosition() {
        return Position;
    }

    public boolean isActive() {
        return active;
    }
    public boolean bombPositionIsNull() {
        if(bombPosition==null)
        	return true;
        else
        	return false;
    }
    
    public boolean bombTem() {
        return tem;
    }

    public boolean setTem() {
        return tem=true;
    }
    
    @Override
    public String getName() {
        return "Bomb";
    }

    @Override
    public int getLayer() {
        return 2;
    }

 
    
    public void explod(List<GameObject> objects, Room room, Manel manel) {
    	if (active) {
            ticksToExplode--;
            if(manel.getPosition().equals(bombPosition) && ticksToExplode < 4) {
        		dealDamage(manel,100);
        		remove(room);
                System.out.println("Bomba explodiu!");
        	}else if(room.isGorilaAtPosition(bombPosition)) {
        		room.GorilaReturn(bombPosition).takeDamage(100);
        		 remove(room);
                 System.out.println("Bomba explodiu!");
        	}else if(room.getObjectAt(bombPosition) instanceof Bat) {
        		room.removeObject(room.getObjectAt(bombPosition));
        		remove(room);
        	}
           
            if (ticksToExplode <= 0) {
                explode(objects,room, manel);
                active = false; 
            }
        }
    }

    
    private void explode(List<GameObject> objects, Room room, Manel manel) {

    	
    	
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
            	Vector2D v = new Vector2D(dx, dy);
                Point2D targetPosition = bombPosition.plus(v);
            	
                List<GameObject> objectsToRemove = new ArrayList<>();
                for (GameObject obj : objects) {
                    if (obj.getPosition().equals(targetPosition) 
                        && !(obj instanceof Wall) 
                        && !(obj instanceof Stairs)
                        && !(obj instanceof DoorClosed)) {
                        objectsToRemove.add(obj); 
                    }
                }

                // Remove objetos marcados
                for (GameObject obj : objectsToRemove) {
                    room.removeObject(obj); 
                }

                // Aplica dano ao Manel se ele estiver na área de explosão
                if (manel.getPosition().equals(targetPosition)) {
                    manel.takeDamage(100);
                }
            }
        }
    	

        
        remove(room);
        bombPosition = null;
        System.out.println("Bomba explodiu!");
    }
    
    public void remove(Room room) {
    	ImageGUI.getInstance().removeImage(this);
        room.removeObject(this); 
    }

}
