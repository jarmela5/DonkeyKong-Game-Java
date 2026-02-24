package objects;

import pt.iscte.poo.gui.ImageGUI;

import java.util.Iterator;
import java.util.List;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Damageable, Damager{

	private Point2D position;
	private Point2D inicial;
	private boolean hasSword;
	private int health;
	private int maxHealth;
	private int tickCount=0;
	private int lives;
	
	public Manel(Point2D initialPosition){
		super(initialPosition);
		position = initialPosition;
		inicial=initialPosition;
		this.health = 100; 
	    this.maxHealth = 100;
	    this.hasSword=false;
	    this.lives=3;
	}
	
	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D p) {
		inicial=p;
		position=p;
		tickCount=0;
	}

	@Override
	public int getLayer() {
		
		return 2;
	}
	
	public void move(int k) {
		
		position = position.plus(Direction.directionFor(k).asVector());
		
	}
	
	public void health() {
		ImageGUI gui = ImageGUI.getInstance();
		gui.setStatusMessage(getHealth()+ "/100" );
	}
	
	public boolean hasSword() {
		return hasSword;
	}
	
	public void pickSword() {
		hasSword=true;
	}
	
	public void heal(int amount) {
	    this.health += amount;
	    if (this.health > this.maxHealth) { 
	        this.health = this.maxHealth;
	    }
	    System.out.println("Manel recuperou " + amount + " de vida! Vida atual: " + health);
	}
	public int getHealth() {
	    return health;
	}

	public void setHealth(int health) {
	   
	    this.health=health;
	}
	public void interact(GameObject obj) {
	    if (obj.getClass()== Sword.class) {
	        pickSword(); // Chama o método de pegar a espada
	    } else if (obj.getClass()== Beef.class) {
	        heal(20); // Cura 20 de vida ao pegar um bife
	    } 
	}
	
	@Override
    public void takeDamage(int amount) {
		ImageGUI gui = ImageGUI.getInstance();
		
        this.health -= amount;  

		gui.setStatusMessage("Manel perdeu " + amount + " de vida. Tens " + lives+ " vidas. Vida restante: " + this.health+ "/100" );
        if (this.health <= 0) {
        	gui.setStatusMessage("Manel morreu!");
            resetManel(); 
        }
    }

	public int getLives() {
		return lives;
	}


	@Override
	public void dealDamage(Damageable target, int amount) {
		
		target.takeDamage(amount);
		System.out.println("Banana causou " + amount + " de dano!");
	}
	
	public void damage(Gorila g) {
		if (hasSword)
			dealDamage((Gorila) (g), 40);
		else
			dealDamage((Gorila) (g), 20);
	}

	public void checkTrapManel(List<objects.GameObject> interactables) {
		Point2D manelPosition = this.getPosition();
		for (objects.GameObject obj : interactables) {
			if (obj instanceof Trap) {
				Point2D trapPosition = obj.getPosition();
				if (manelPosition.equals(new Point2D(trapPosition.getX(), trapPosition.getY() - 1))) {
					Trap trap = (Trap) obj;
					trap.dealDamage(this, trap.getDamagePerTick());
					break; 
				}
			}
		}
	}
	
	public void cheeckHiddenTrap(List<objects.GameObject> allObjects) {
		 for (GameObject obj : allObjects) {
			    if (obj instanceof HiddenTrap) {
			        HiddenTrap hiddenTrap = (HiddenTrap) obj;
			        if (hiddenTrap.isHidden() && this.getPosition().equals(hiddenTrap.getPosition().plus(Direction.UP.asVector()))) {
			            hiddenTrap.activate(); 
			        }
			    }
	 }
	}
	
	 private void resetManel() {
	        this.position =inicial; 
	        this.health = maxHealth; 
	        this.lives--;
	        
	        ImageGUI.getInstance().setStatusMessage("Manel voltou ao início! Tens " + lives + " vidas restante.Vida restaurada para " + this.health + "/100");
	    }
	 
	 public void checkBeef(List<objects.GameObject> allObjects) {
	        // A cada tick, verifica os bifes na sala e aplica a lógica de apodrecimento
	        tickCount++;

	        if (tickCount >= 5) {
	            for (objects.GameObject obj : allObjects) {
	                if (obj instanceof Beef) {
	                    Beef beef = (Beef) obj;

	                    if (!beef.isRotten()) {
	                        beef.setRotten(true);
	                        ImageGUI.getInstance().setStatusMessage("O bife apodreceu!");

	                        // Causa dano ao jogador se ele estiver na mesma posição do bife podre
	                        if (this.getPosition().equals(beef.getPosition())) {
	                            this.takeDamage(20);
	                            ImageGUI.getInstance().setStatusMessage("Você perdeu 20 de vida por comer carne podre!");
	                        }
	                    }
	                }
	            }
	        }
	    }
	
	
}
