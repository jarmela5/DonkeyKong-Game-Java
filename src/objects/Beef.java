package objects;


import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageGUI;
public class Beef extends GameObject implements Interactable,Damager{


	private boolean isRotten=false;
	
	public Beef(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		if(isRotten) {
			return "BadMeat";
		}else
		return "GoodMeat";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
    public void interact(GameObject player) {
		ImageGUI gui = ImageGUI.getInstance();
		
        if (player instanceof Manel) {
        	
            Manel manel = (Manel) player;
            if(isRotten) {
            	dealDamage(manel,20);
            	
        		gui.setStatusMessage("Voce perdeu 20 de vida por comer carne podre!" );
            }else {
            manel.heal(20); 
            gui.setStatusMessage("ganhaste +20 vida");
            }
        }
    }

	@Override
	public void dealDamage(Damageable target, int amount) {
		
		target.takeDamage(amount);
		
	}
	
	public boolean isRotten() {
		return isRotten;
	}
	
	public void setRotten(boolean rotten) {
		this.isRotten=rotten;
	}
	
}
