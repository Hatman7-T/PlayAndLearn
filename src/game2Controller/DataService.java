package game2Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import controller.UserSession;

/**
 * 	Classe utilizzata per lo scambio di dati tra Controller di Game2
 * 	Viene istanziata come Singleton, per impedire altre istanziazioni il costruttore è privato
 * 
 * @author Ardelean Razvan
 * @project Play And Learn
 */

public class DataService {
	// Unica istanza di DataService (Singleton)
	private static DataService instance = new DataService();

	// Numero di esercizi massimi
	public static final int N_ESERCIZI_PRINCIPIANTE = 4;
	public static final int N_ESERCIZI_INTERMEDIO = 4;
	public static final int N_ESERCIZI_AVANZATO = 5;

	protected final String percorsoScene = "/game2Scenes/";
	protected final String percorsoFile = "./src/exercise2Folder/";
	//protected final String percorsoFilePrincipiante = "./src/exercise2Folder.Easy/";
	//protected final String percorsoFileIntermedio = "./src/exercise2Folder.Medium/";
	//protected final String percorsoFileAvanzato = "./src/exercise2Folder.Hard/";

	protected final String scene0 = "/scenes/UserScene.fxml";
	protected final String scene1 = percorsoScene+"CodeComprehensionSelectDifficultyScene.fxml";
	protected final String scene2 = percorsoScene+"CodeComprehensionGameScene.fxml";

	// Memorizzano nomi file esercizi
	private ArrayList<String> keysPrincipiante = new ArrayList<String>();
	private ArrayList<String> keysIntermedio = new ArrayList<String>();
	private ArrayList<String> keysAvanzato = new ArrayList<String>();
	protected LinkedHashMap<String, Pair> mapEserciziPrincipiante = new LinkedHashMap<>();
	protected LinkedHashMap<String, Pair> mapEserciziIntermedio = new LinkedHashMap<>();
	protected LinkedHashMap<String, Pair> mapEserciziAvanzato = new LinkedHashMap<>();

	// Indicano l'ultimo esercizio a cui l'utente è arrivato
	private int indiceEsercizioPrincipiante = 0;
	private int indiceEsercizioIntermedio = 0;
	private int indiceEsercizioAvanzato = 0;

	// Indica la difficolta attuale
	private static String difficolta;

	
	// Costruttore privato per prevenire l'istanziazione
	private DataService() {}

	// Ottenere l'unica istanza di DataService
	public static DataService getInstance() {
		return instance;
	}

	// Aggiorna gli indici con i progressi caricati dai file
	public void setIndici() {
		int livello = UserSession.currentUser.getLevelGame2();
		if(livello==1)
			livello--;

		// Aggiorno opportunamente gli indici in base alla difficolta raggiunta
		switch(UserSession.currentUser.getDifficultyGame2()) {
		case "Easy":
			indiceEsercizioPrincipiante = livello;
			indiceEsercizioIntermedio = 0;
			indiceEsercizioAvanzato = 0;

			difficolta = "Easy";
			break;
		case "Medium":
			indiceEsercizioIntermedio = livello;
			indiceEsercizioPrincipiante = N_ESERCIZI_PRINCIPIANTE;

			difficolta = "Medium";
			break;
		case "Hard":
			indiceEsercizioAvanzato = livello;
			indiceEsercizioPrincipiante = N_ESERCIZI_PRINCIPIANTE;
			indiceEsercizioIntermedio = N_ESERCIZI_INTERMEDIO;

			difficolta = "Hard";
			break;
		default:
			indiceEsercizioPrincipiante = 0;
			indiceEsercizioIntermedio = 0;
			indiceEsercizioAvanzato = 0;
		}
	}
	// Cambia l'ordine dei file casualmente, in questo modo ogni volta che ha un ordine di presentazione degli esercizi diverso
	public void cambiaOrdine() {
		keysPrincipiante = new ArrayList<>(mapEserciziPrincipiante.keySet());
		Collections.shuffle(keysPrincipiante);
		keysIntermedio = new ArrayList<>(mapEserciziIntermedio.keySet());
		Collections.shuffle(keysIntermedio);
		keysAvanzato = new ArrayList<>(mapEserciziAvanzato.keySet());
		Collections.shuffle(keysAvanzato);
	}

	//------> Funzioni che cambiano il loro comportamento in base alla difficolta attuale
	public String getPercorsoFile() {
		switch(difficolta) {
		case "Easy":
			return percorsoFile+"Easy/";
		case "Medium":
			return percorsoFile+"Medium/";
		case "Hard":
			return percorsoFile+"Hard/";
		}
		return null;
	}

	public void setIndice0() {
		switch(difficolta) {
		case "Easy":
			indiceEsercizioPrincipiante = 0;
			break;
		case "Medium":
			indiceEsercizioIntermedio = 0;
			break;
		case "Hard":
			indiceEsercizioAvanzato = 0;
			break;
		}
	}
	public void incrementaIndice() {
		switch(difficolta) {
		case "Easy":
			indiceEsercizioPrincipiante++;
			break;
		case "Medium":
			indiceEsercizioIntermedio++;
			break;
		case "Hard":
			indiceEsercizioAvanzato++;
			break;
		}
	}
	public int getIndice() {
		switch(difficolta) {
		case "Easy":
			return indiceEsercizioPrincipiante;
		case "Medium":
			return indiceEsercizioIntermedio;
		case "Hard":
			return indiceEsercizioAvanzato;
		}
		return -1;
	}

	public LinkedHashMap<String, Pair> getMapEsercizi(){
		switch(difficolta) {
		case "Easy":
			return mapEserciziPrincipiante;
		case "Medium":
			return mapEserciziIntermedio;
		case "Hard":
			return mapEserciziAvanzato;
		}
		return null;
	}

	public String[] getNomiFile() {
		String[] f = new String[3];
		switch(difficolta) {
		case "Easy":
			String key1 = keysPrincipiante.get(indiceEsercizioPrincipiante);
			Pair value1 = mapEserciziPrincipiante.get(key1);

			f[0] = key1; 
			f[1] = value1.getFirst(); 
			f[2] = value1.getSecond();

			return f;
		case "Medium":
			String key2 = keysIntermedio.get(indiceEsercizioIntermedio);
			Pair value2 = mapEserciziIntermedio.get(key2);

			f[0] = key2; 
			f[1] = value2.getFirst(); 
			f[2] = value2.getSecond();

			return f;
		case "Hard":
			String key3 = keysAvanzato.get(indiceEsercizioAvanzato);
			Pair value3 = mapEserciziAvanzato.get(key3);

			f[0] = key3; 
			f[1] = value3.getFirst(); 
			f[2] = value3.getSecond();

			return f;
		}
		return null;
	}
	public void sbloccaProssimaSerie() {
		switch(difficolta) {
		case "Easy":
			UserSession.currentUser.setDifficultyGame2("Medium");
			UserSession.currentUser.setLevelGame2(1);
			break;
		case "Medium":
			UserSession.currentUser.setDifficultyGame2("Hard");
			UserSession.currentUser.setLevelGame2(1);
			break;
		}
	}
	public void updateDbData(int livello) {
		switch(difficolta) {
		case "Easy":
			UserSession.currentUser.setDifficultyGame2("Easy");
			UserSession.currentUser.setLevelGame2(livello);
			break;
		case "Medium":
			UserSession.currentUser.setDifficultyGame2("Medium");
			UserSession.currentUser.setLevelGame2(livello);
			break;
		case "Hard":
			UserSession.currentUser.setDifficultyGame2("Hard");
			UserSession.currentUser.setLevelGame2(livello);
			break;
		}
	}
	//<------ Funzioni che cambiano il loro comportamento in base alla difficolta attuale
	
	//------> Metodi set/get delle variabili
	public String getDifficolta() {
		return difficolta;
	}
	public void setDifficolta(String nuovaDifficolta) {
		difficolta = nuovaDifficolta;
	}
	public int getIndicePrincipiante() {
		return indiceEsercizioPrincipiante;
	}
	public void resetIndicePrincipiante() {
		indiceEsercizioPrincipiante = 0;
	}
	public int getIndiceIntermedio() {
		return indiceEsercizioIntermedio;
	}
	public void resetIndiceIntermedio() {
		indiceEsercizioIntermedio = 0;
	}
	public int getIndiceAvanzato() {
		return indiceEsercizioAvanzato;
	}
	public void resetIndiceAvanzato() {
		indiceEsercizioAvanzato = 0;
	}
	//<------ Metodi set/get delle variabili
}

// Classe utilizzata da LinkedHashMap per memorizzare i nomi dei file. Struttura (key: value1, value2)
class Pair {
	private String first;
	private String second;

	public Pair(String first, String second) {
		this.first = first;
		this.second = second;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "(" + first + ", " + second + ")";
	}
}

