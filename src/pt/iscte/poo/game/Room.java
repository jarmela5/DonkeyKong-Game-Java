package pt.iscte.poo.game;

import objects.Banana;
import objects.Gorila;
import objects.Manel;
import objects.Wall;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import pt.iscte.poo.utils.Vector2D;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;


public class Room implements Tickable{
	
	
	private Point2D heroStartingPosition = new Point2D(0, 8) ;
	private Manel manel;
	
	private List<List<Character>> gameMatrix2 = new ArrayList<>();
	private List<objects.GameObject> allObjects = new ArrayList<>();
	private Room nextLevel; // Referência para o próximo nível
	private objects.Bomb currentBomb;

	
	
	public Room(String filename) {
	    manel = new Manel(heroStartingPosition);
		loadFromFile(filename);       
	}
	


	
	public void loadFromFile(String filename) {
		Scanner scanner = null;
		 Scanner inputScanner = new Scanner(System.in); 
		// Passo 1: Verificar existência do ficheiro
		while (scanner == null) {
			try {
				scanner = new Scanner(new File(filename));
			} catch (Exception e) {
				System.out.println("Ficheiro não encontrado. Por favor, insira o nome correto:");
				//filename = System.console().readLine();
				 filename = inputScanner.nextLine();
			}
		}
		inputScanner.close();

		try {
			gameMatrix2 = new ArrayList<>();
			int lineNumber = 0;
			 String primeiraLinha = scanner.nextLine();
			 
			// Passo 2: Processar linhas do ficheiro
			if (!primeiraLinha.startsWith("#")) { 
	            List<Character> row = new ArrayList<>();
	            for (char c : primeiraLinha.toCharArray()) {
	                row.add(c); 
	            }
	            gameMatrix2.add(row);
	            lineNumber++;
	           
			}
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNumber++;
				List<Character> row = new ArrayList<>();
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if (isValidCharacter(c)) {
						row.add(c);
					} else {
						System.err.println("Caractere desconhecido na linha " + lineNumber + ", coluna " + (i + 1)
								+ ". Preenchendo com chão.");
						row.add('.');
						
					}
				}
				// Preenche a linha com chão se for menor que o tamanho esperado
				if (row.size() < 10 && lineNumber<10) {
					row.add(' ');
					System.err.println("Caractere em falta na linha " + lineNumber + ". Preenchendo com chão.");
					
				}
				gameMatrix2.add(row);
				
			}
			if (lineNumber<10) {
				throw new RuntimeException("Falta uma linha inteira no ficheiro na linha " + lineNumber + ".");
			}
			scanner.close();
			
			heroStartingPosition = getPositionManel();
			manel.setPosition(heroStartingPosition);
			loadRoom();

		} catch (RuntimeException e) {
			System.err.println("Erro crítico: " + e.getMessage());
			ImageGUI.getInstance().dispose(); 
			System.exit(1); 
		} catch (Exception e) {
			System.err.println("Erro ao inicializar a sala: " + e.getMessage());
			//e.printStackTrace();
			ImageGUI.getInstance().dispose();
			System.exit(1);
		}
	}

	
	private boolean isValidCharacter(char c) {
		
		return c == 'W' || c == ' ' || c == 'b'|| c == 'S' || c == 'H'|| c == 's' || c == 't' || c == 'P' || c == 'B'|| c == 'M' || c == '0'|| c == 'G' || c=='T' ; 
	}




	    public void setNextLevel(Room nextLevel) {
	        this.nextLevel = nextLevel;
	    }

	    public Room getNextLevel() {
	        return nextLevel;
	    }

	    public void clearRoom() {
	        ImageGUI.getInstance().clearImages();  
	        allObjects.clear();  
	        objects.Gorila.clearBananas();
	        gameMatrix2.clear();
	        
	    }
	    

	    public Point2D getPositionManel() {
	        for (int y = 0; y < gameMatrix2.size(); y++) {
	            for (int x = 0; x < gameMatrix2.get(y).size(); x++) {
	                char element = gameMatrix2.get(y).get(x);
	                if(element == 'H') {
	                    return new Point2D(x, y);
	                }
	            }
	        }
	        return new Point2D(0, 8);  
	    }

    
	public void loadRoom() {

            	 for (int y = 0; y < gameMatrix2.size(); y++) {
            	        for (int x = 0; x < gameMatrix2.get(y).size(); x++) {
            	            char element = gameMatrix2.get(y).get(x);
            	          
                Point2D position = new Point2D(x, y);
                ImageGUI.getInstance().addImage(new objects.Floor(position));
                switch (element) {
                    
                case 'W':
                		objects.Wall w= new objects.Wall(position);
                        ImageGUI.getInstance().addImage(w);
                        allObjects.add(w);
                        break;
                    case 'b':
                    	objects.Beef bf= new objects.Beef(position);
                    	ImageGUI.getInstance().addImage(bf);
                    	allObjects.add(bf);
                        break;
                    case 'S':
                    	objects.Stairs s= new objects.Stairs(position);
                        ImageGUI.getInstance().addImage(s);
                        allObjects.add(s);
                        break;
                    case 'H':
                    	  //heroStartingPosition = position;
                          
            	        ImageGUI.getInstance().addImage(manel);
                        break;
                    case 's':
                    	objects.Sword sw= new objects.Sword(position);
                        ImageGUI.getInstance().addImage(sw);
                        allObjects.add(sw);
                        break;
                    case 'T': 
                        objects.HiddenTrap rd = new objects.HiddenTrap(position);
                        ImageGUI.getInstance().addImage(rd);
                        allObjects.add(rd);
                        break;
                    case 't':
                    	objects.Trap t=new objects.Trap(position);
                        ImageGUI.getInstance().addImage(t);
                        allObjects.add(t);
                        break;
                    case 'P':
                    	objects.Princess pr=new objects.Princess(position);
                        ImageGUI.getInstance().addImage(pr);
                        allObjects.add(pr);
                        break;
                    case 'B': 
                    	objects.Bat bu=new objects.Bat(position);
                        allObjects.add(bu);
                        ImageGUI.getInstance().addImage(bu);
                        break;
                    case 'M': 
                    	objects.Bomb bom=new objects.Bomb(position);
                        allObjects.add(bom);
                        currentBomb=bom;
                        ImageGUI.getInstance().addImage(bom);
                        break;
                    case '0':
                    	objects.DoorClosed dr=new objects.DoorClosed(position); 
                        ImageGUI.getInstance().addImage(dr);
                        allObjects.add(dr);
                        break;
                    case 'G'://fazer que 2 gorilas funcionem
                    	objects.Gorila gorila = new objects.Gorila(position);
                    	allObjects.add(gorila);
                        ImageGUI.getInstance().addImage(gorila);
                        //moveGorila();
                        break;
                }
            
            }
        }
	}
	
	public Point2D getInitialPosition() {
		return heroStartingPosition;
	}

	public boolean isAtDoor(Point2D position) {
		return position.equals(new Point2D(0, 0));
	}
	  

	public boolean isManelAtDoor() {
		if (isAtDoor(manel.getPosition()) == true) {

			ImageGUI.getInstance().clearImages();
			objects.Gorila.clearBananas();
			return true;
		} else
			return false;
	}
	
	
	public boolean canMoveTo(Point2D position) {

		int x = (int) position.getX();
		int y = (int) position.getY();

		if (y < 0 || y >= 10 || x < 0 || x >= 10) {
			return false;
		}

		char element = gameMatrix2.get(y).get(x);
		if(element=='W' || element=='P' || element=='t')
			return false;
		else
			return true;
	}

    public  List<List<Character>> getGameMatrix() {
        return gameMatrix2;
    }
	
    public boolean ManelLives() {
    	if(manel.getLives()<=0) {
    		return false;
    	}else
    		return true;
    }
	
    public void moveManel(int k) {
        Vector2D dir = Direction.directionFor(k).asVector();
        Point2D newPosition = manel.getPosition().plus(dir);

        int newX = (int) newPosition.getX();
        int newY = (int) newPosition.getY();

       
        if (dir.getY() > 0 && !(gameMatrix2.get(newY).get(newX) == 'S')) {
            return;
        }
        if (dir.getY() < 0 && !(gameMatrix2.get(newY + 1).get(newX) == 'S')) {
            return;
        }
		if (isGorilaAtPosition(newPosition)) {
			manel.damage(GorilaReturn(newPosition));
		} else {

			if (canMoveTo(newPosition)) {
				manel.move(k);
				checkInteraction(newPosition);
			}
		}
    }

	public boolean isGorilaAtPosition(Point2D position) {
		for (objects.GameObject g : allObjects) {
			if (g instanceof Gorila ) 
				if (g.getPosition().equals(position)) {
					return true; 
				}
			
		}

		return false;
	}
	public Gorila GorilaReturn(Point2D position) {
		for (objects.GameObject g : allObjects)
			if (g instanceof Gorila)
				if (g.getPosition().equals(position)) {
				return (Gorila)(g);
				}
		return null;
	}

	
	
	
	private void checkInteraction(Point2D position) {
	    for (Iterator<objects.GameObject> iterator = allObjects.iterator(); iterator.hasNext();) {
	        objects.GameObject obj = iterator.next();

	        
	        if (obj instanceof objects.Interactable && obj.getPosition().equals(position)) {
	          
	        	objects.Interactable interactiveObj = (objects.Interactable) obj;
	            interactiveObj.interact(manel);

	            iterator.remove(); 
	            ImageGUI.getInstance().removeImage(obj); 
	            ImageGUI.getInstance().addImage(new objects.Floor(position)); 
	            break; 
	        }
	    }
	}
	
	public boolean isStairsAt(Point2D position) {
	    int x = (int) position.getX();
	    int y = (int) position.getY();

	    // Verifica se a posição está dentro dos limites
	    if (y >= 0 && y < gameMatrix2.size() && x >= 0 && x < gameMatrix2.get(y).size()) {
	        return gameMatrix2.get(y).get(x) == 'S'; // Verifica se o caractere na posição é 'S'
	    }
	    return false;
	}

	public void removeObject(objects.GameObject obj) {
	    allObjects.remove(obj); 
	    ImageGUI.getInstance().removeImage(obj);
	    
	}
	
	public void Adicionar(objects.GameObject obj) {
	    allObjects.add(obj);
	    ImageGUI.getInstance().addImage(obj);
	    
	}

	
    public void fall() {
        Point2D below = manel.getPosition().plus(new Vector2D(0, 1));
        int belowX = (int) below.getX();
        int belowY = (int) below.getY();

        
        if (canMoveTo(below)) {

            // Verifica se o espaço abaixo está vazio (' ')
            if (gameMatrix2.get(belowY).get(belowX) == ' ') {
                manel.move(KeyEvent.VK_DOWN);
            }
        }
    }
    
    public void dropBomb() {
        if (currentBomb == null || !currentBomb.isActive()) {
            Point2D heroPosition = manel.getPosition();

            currentBomb.activate(manel.getPosition());

            ImageGUI.getInstance().addImage(currentBomb);
            System.out.println("Bomba largada na posição: " + heroPosition);
        } else {
            System.out.println("Uma bomba já está ativa!");
        }
    }
    
    public objects.GameObject getObjectAt(Point2D position) {
        for (objects.GameObject obj : allObjects) {
            if (obj.getPosition().equals(position)) {
                return obj;
            }
        }
        return null;
    }
    
    public void ManelBomb() {
    	if(currentBomb != null && currentBomb.getPosition().equals(manel.getPosition()) && currentBomb.isActive()== false ) {//currentBomb.bombTem() == false
    		ImageGUI.getInstance().removeImage(currentBomb);
    		currentBomb.setTem();
    		
    	}
    }
    private void removeBombAfterExplosion() {
        if (currentBomb != null && !currentBomb.isActive()) {
            ImageGUI.getInstance().removeImage(currentBomb);  
            removeObject(currentBomb);  
            currentBomb = null;  
        }
    }
    
    private void checkEndGame() {
		Point2D manelPosition = manel.getPosition();
		Vector2D v = new Vector2D(-1, 0);
		manelPosition=manelPosition.plus(v);

		if (isPrincessAtPosition(manelPosition)) {
			endGame();
		}

    }
    private boolean isPrincessAtPosition(Point2D position) {
        for (objects.GameObject object : allObjects) {
            if (object.getPosition().equals(position) && object instanceof objects.Princess) {
                return true;
            }
        }
        return false;
    }
    
 
	private void endGame() {
		  ImageGUI.getInstance().setStatusMessage("Chegou à princesa e venceu o jogo com " +ImageGUI.getInstance().getTicks()+ " ticks!!!!");
		clearRoom();

		// Atualiza as classificações
		Score scoreHandler = new Score("", 0); 
		scoreHandler.updateTop10Scores();

		
		String s= "=== Top 10 Classificações ===";
		
		for (Score score : scoreHandler.showTop10Scores()) {
			s=s+ "\n"+ score.toString();
		}
		ImageGUI.getInstance().showMessage("classificações", s);
		
		 ImageGUI.getInstance().showMessage("end","Fim do Jogo. Obrigado por jogar!");
		System.exit(0);
	}
	
	public Point2D ManelPosition() {
		return manel.getPosition();
	}

	
	@Override
	public void tick() {
		objects.Gorila.moveGorilas(allObjects,this, manel);
	    fall();
	    manel.checkTrapManel(allObjects);
	    manel.checkBeef(allObjects);
	    objects.Bat.moveAllBats(allObjects, this, manel);
	    checkEndGame();
	    manel.cheeckHiddenTrap(allObjects);

	    if(currentBomb != null && currentBomb.isActive()  ) {
	    	currentBomb.explod(allObjects, this, manel);
	    	 removeBombAfterExplosion();
	    }
	    ManelBomb();
	    int k = ImageGUI.getInstance().keyPressed();
	    if (currentBomb != null && k ==66 && (currentBomb.isActive()==true || currentBomb.bombTem()==true)) {
	    	dropBomb();
        }
	    
	    
	}
	
	
}