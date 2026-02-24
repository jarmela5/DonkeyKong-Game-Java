package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class HiddenTrap extends Trap {
    private boolean hidden = true; // Inicialmente escondida

    public HiddenTrap(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return hidden ? "Wall" : "Trap"; // Se escondida, aparece como "Wall"
    }

    public void activate() {
        if (hidden) {
            hidden = false; // Torna visível
          
            ImageGUI gui = ImageGUI.getInstance();
            gui.removeImage(this); 
            gui.addImage(this);    
        }
    }

    public boolean isHidden() {
        return hidden;
    }
}
