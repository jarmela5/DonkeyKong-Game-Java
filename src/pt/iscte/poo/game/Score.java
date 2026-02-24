package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import java.util.List;
import java.util.*;
import java.nio.file.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;



public class Score implements Comparable<Score> {
	String playerName;
	int score;
	private static final String SCORES_FILE = "top10_scores.txt"; // Ficheiro para armazenar as classificações
	private List<Score> top10Scores = new ArrayList<>();

	public Score(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}

	@Override
	public int compareTo(Score other) {
	    return Integer.compare(this.score, other.score); 
	}

	@Override
	public String toString() {
		return playerName + ": " + score;
	}




	public void updateTop10Scores() {
		loadTop10Scores();

	
		/*String playerName = System.console() != null ? System.console().readLine("Insira seu nome: ")
				: new Scanner(System.in).nextLine();*/

		// Adicionar o novo score
		int currentScore = calculateCurrentScore();
		top10Scores.add(new Score(playerName, currentScore));

		Collections.sort(top10Scores);

		if (top10Scores.size() > 10) {
			top10Scores = top10Scores.subList(0, 10);
		}

		saveTop10Scores();
	}


	private void loadTop10Scores() {
	    top10Scores.clear();
	    File file = new File(SCORES_FILE);
	    try {
	        if (!file.exists()) {
	            // Cria o arquivo se ele não existir
	            file.createNewFile();
	           
	        }
	        try (Scanner scanner = new Scanner(file)) {
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                String[] parts = line.split(":", 2);
	                if (parts.length == 2) {
	                    top10Scores.add(new Score(parts[0].trim(), Integer.parseInt(parts[1].trim())));
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.err.println("Erro ao carregar o Top 10: " + e.getMessage());
	    }
	}


	private void saveTop10Scores() {
		try (PrintWriter writer = new PrintWriter(new File(SCORES_FILE))) {
			for (Score score : top10Scores) {
				writer.println(score.playerName + ": " + score.score);
			}
		} catch (IOException e) {
			System.err.println("Erro ao salvar o Top 10: " + e.getMessage());
		}
	}

	public List<Score> showTop10Scores() {
		return top10Scores;
	}

	private int calculateCurrentScore() {
		return ImageGUI.getInstance().getTicks();
	}
}
