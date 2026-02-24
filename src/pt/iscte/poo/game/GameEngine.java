package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import java.util.*;

public class GameEngine implements Observer {
	
	
	private int lastTickProcessed = 0;
	
	private Room currentRoom;
	private List<String> levels;
	private int currentLevelIndex = 0; // Índice do nível atual
	
	public GameEngine() {
		ImageGUI.getInstance().update();
        levels = Arrays.asList("rooms/room0.txt", "rooms/room1.txt", "rooms/room2.txt");
        loadLevel(currentLevelIndex);
		
	}

	
	public void loadLevel(int levelIndex) {
		if (levelIndex == 0) {
			currentRoom = new Room(levels.get(levelIndex));
			currentLevelIndex = levelIndex;
			ImageGUI.getInstance().setStatusMessage("Nível " + 1 + " carregado!");
		} else {
			if (levelIndex < levels.size()) {
				currentRoom.clearRoom();
				currentRoom.loadFromFile(levels.get(levelIndex));
				currentLevelIndex = levelIndex;
				ImageGUI.getInstance().setStatusMessage("Nível " + (levelIndex + 1) + " carregado!");
			} else {
				ImageGUI.getInstance().setStatusMessage("Você venceu o jogo!");
			}
		}
		
	}

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				System.out.println("Direction! ");
				currentRoom.moveManel(k);
				
                
			}
			
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}
	private void resetGame() {
		currentRoom.clearRoom();
	    currentLevelIndex = 0; 
	    loadLevel(currentLevelIndex); 
	}

	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
		
		if (currentRoom instanceof Tickable) {
	        ((Tickable) currentRoom).tick();
	    }
		
		 // Verifica se o Manel está na porta
        if (currentRoom.isManelAtDoor()) {
            currentLevelIndex++;
            loadLevel(currentLevelIndex);
        }
        if (currentRoom.ManelLives()==false) {
            resetGame(); // Reinicia o jogo
        }
		
		
	}
	 



}
