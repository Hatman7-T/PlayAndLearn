package game2Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controller.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *  Controller usato per la gestione delle pagine degli esercizi.
 *  Utilizzo della stessa scena per tutti gli esercizi.
 *  Prende in input i nomi dei file da gestire e avvia l'esercizio.
 *  Viene mostrato il testo dell'esercizio, le possibili soluzioni / opzioni, 
 *  	una spiegazione dell'esercizio (visibile solo dopo aver scelto la risposta corretta)
 *  Ogni volta che l'utente completa un esercizio viene salvato il progresso su file
 *  
 *  L'utente vince nel caso ha risposta correttamente a tutti gli esercizi
 *  L'utente perde nel caso gli sia scaduto il timer o ha raggiunto il numero di tentativi massimi
 * 
 * @author Ardelean Razvan
 * @project Play And Learn
 */

public class CodeComprehensionGameController extends UserController{

	@FXML
	private Label exerciseTextLabel;
	@FXML
	private VBox optionBox;
	@FXML
	private Label resultLabel;
	@FXML
	private Label nAttemptsLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label explanationLabel;
	@FXML
	private Button confirmButton;
	@FXML
	private ProgressBar progressTot;

	private DataService ds = DataService.getInstance();	// Usato per condividere dati con altri controller

	// Variabili da modificare durante lo svolgimento dell'esercizio
	private int progress = 0;	// Usato per aggiornare ProgressBar (della pagina corrente)
	private int tempo;			// Usato per mostra il tempo trascorso / tempo rimanente
	private int numeroTentativi = 0;	// Usato per contare il numero di volte che l'utente ha scelto una risposta
	private String spiegazione = "";	// Usato per salvare dati letti da file e da mostrare su "explanationLabel"
	private String rispostaCorretta;	// Indica la risposta corretta tra le possibili opzioni

	private final int numeroTentativiMassimi = 3;	// Limitazione per "numeroTentativi", al raggiungimento di questa variabile l'utente ha perso 

	private ToggleGroup group;		// Usato per VBox, contiene le possibili opzioni
	private Timeline timeline;	// Usato con "tempo" per istanziare secondi trascorsi o rimanenti

	// Limite di tempo per le corrispettive difficolta
	private final int timerDifficoltaIntermedio = 50; 
	private final int timerDifficoltaAvanzato = 40;

	// Testi da mostrare su "confirmButton"
	private final String confirmButtonConfirmText = "Conferma";
	private final String confirmButtonLostGame = "Riprova...";
	private final String confirmButtonNextText = "Continua!";

	@FXML
	public void initialize() {
		// Quando si inizia la partita, i progressi precedenti vengono azzerati e si ricomincia dall'inizio
		resetTentativi();
		progress = 0;
		progressTot.setProgress(progress);
		ds.setIndice0();
		mostraSpiegazione(false);

		// Modifica label per GUI
		exerciseTextLabel.setMaxWidth(600);
		exerciseTextLabel.setWrapText(true);
		explanationLabel.setMaxWidth(600);
		explanationLabel.setWrapText(true);

		// Avvio esercizio
		try {
			startExercise();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////

	// Avvia il tempo / timer e prende i nomi dei file da usare nell'esercizio e li passa a "doExercises"
	public void startExercise() throws IOException {
		mostraRisultatoConfirmText();
		switch(ds.getDifficolta()) {	// In base alla difficolta avvia il tempo o il timer
		case "Easy":
			avviaTempo();
			break;
		case "Medium":
			avviaTimer(timerDifficoltaIntermedio);
			break;
		case "Hard":
			avviaTimer(timerDifficoltaAvanzato);
			break;
		}

		// Inizia l'esercizio
		String[] f = ds.getNomiFile();
		doExercises(f[0], f[1], f[2]);
	}
	// Legge i dati degli esercizi da file e li stampa sulla GUI
	private void doExercises(String fileNameEs, String fileNameEsC, String fileNamesEsS) {
		try(Scanner scanEs = new Scanner(new File(ds.getPercorsoFile()+fileNameEs));
				Scanner scanC = new Scanner(new File(ds.getPercorsoFile()+fileNameEsC));
				Scanner scanS = new Scanner(new File(ds.getPercorsoFile()+fileNamesEsS));) {

			// Lettura testo esercizio
			StringBuilder testoEs = new StringBuilder();
			while (scanEs.hasNextLine()) {
				testoEs.append(scanEs.nextLine()).append("\n");
			}
			scanEs.close();


			// Lettura testo opzioni e salvo soluzione (prima riga)
			ArrayList<String> opzioni = new ArrayList<>();
			rispostaCorretta = scanC.nextLine();
			opzioni.add(rispostaCorretta);

			while (scanC.hasNextLine()) {
				opzioni.add(scanC.nextLine());
			}
			Collections.shuffle(opzioni);	// Mischia l'ordine delle opzioni
			scanC.close();


			// Lettura spiegazione risposta corretta
			StringBuilder testoS = new StringBuilder();
			while (scanS.hasNextLine()) {
				testoS.append(scanS.nextLine());
			}
			spiegazione = testoS.toString();
			scanS.close();


			// Stampa testo esercizio e soluzioni
			mostraTestoEsercizio(testoEs.toString());

			// Creare i RadioButton dinamicamente
			group = new ToggleGroup();
			optionBox.getChildren().clear();	// Elimina le opzioni precedenti (se esistenti)
			for (String opzione : opzioni) {
				RadioButton radioButton = new RadioButton(opzione);
				radioButton.setWrapText(true);
				radioButton.setMaxWidth(650);
				radioButton.setToggleGroup(group);

				VBox radioButtonContainer = new VBox(radioButton);
				radioButtonContainer.setPadding(new Insets(10, 0, 10, 0)); // Aggiungi padding sopra e sotto il RadioButton
				optionBox.getChildren().add(radioButtonContainer);

				Separator separator = new Separator();
				optionBox.getChildren().add(separator);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	//------> Funzioni per cambiare il testo dei Label sulla GUI
	private void mostraTestoEsercizio(String testoEs) {
		exerciseTextLabel.setText(testoEs);
	}
	private void mostraRisultatoConfirmText() {
		resultLabel.setTextFill(Color.WHITE);
		resultLabel.setText("Seleziona una risposta.");
	}
	private void mostraRisultatoTryAgainText() {
		resultLabel.setTextFill(Color.YELLOW);
		resultLabel.setText("Sbagliato, riprova!");
	}
	private void mostraRisultatoTimeOut() {
		resultLabel.setTextFill(Color.RED);
		resultLabel.setText("Tempo scaduto!");
	}
	private void mostraRisultatoTooAttempts() {
		resultLabel.setTextFill(Color.RED);
		resultLabel.setText("Troppi tentativi!");
	}
	private void mostraRisultatoNextText() {
		resultLabel.setTextFill(Color.GREENYELLOW);
		resultLabel.setText("Corretto!");
	}
	private void mostraRisultatoWin() {
		resultLabel.setTextFill(Color.GREEN);
		resultLabel.setText("Serie Completata!");
	}
	private void mostraTentativi() {
		String text = "Numero tentativi: " + numeroTentativi;
		if(!ds.getDifficolta().equals("Easy"))
			text += "/" + numeroTentativiMassimi;
		nAttemptsLabel.setText(text);
	}
	private void mostraSpiegazione(boolean tastoConferma) {
		if(tastoConferma)
			explanationLabel.setText(spiegazione);
		else
			explanationLabel.setText("...");
	}
	private void mostraTimer() {
		timeLabel.setText("Tempo rimanente: " + tempo + "s");
	}
	private void mostraTempo() {
		timeLabel.setText("Tempo trascorso: " + tempo + "s");
	}
	private void mostraTextConferma(String text) {
		confirmButton.setText(text);
	}
	//<------ Funzioni per cambiare il testo dei Label sulla GUI

	//------> Funzioni per gestire il tempo / timer
	private void avviaTempo() {
		tempo = 0;
		mostraTempo();

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	// Aumenta il tempo all'infinito
				tempo++;
				mostraTempo();
			}
		};

		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), eventHandler);
		timeline = new Timeline(keyFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	private void avviaTimer(int secondi) {
		tempo = secondi;
		mostraTimer();

		EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	// Diminuisce il tempo finche non arriva a 0
				tempo--;
				mostraTimer();

				if (tempo <= 0) {	// Tempo scaduto! Partita persa -> Deve ricominciare dall'inizio
					timeline.stop();
					disabilitaOpzioni();

					mostraTextConferma(confirmButtonLostGame);
					mostraRisultatoTimeOut();
				}
			}
		};

		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), eventHandler);
		timeline = new Timeline(keyFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	// Rende le opzioni (radioButton) non cliccabili
	private void disabilitaOpzioni() {
		for (Node node : optionBox.getChildren()) {
			node.setDisable(true);
		}
	}
	//<------ Funzioni per gestire il tempo / timer

	//------> Funzioni per la gestione dei bottoni e sottofunzioni utilizzate
	@FXML
	private void confirmAction(ActionEvent event) throws IOException {
		// L'utente precedentemente ha risposto correttamente e va alla prossima scena
		if(getTextConferma().equals(confirmButtonNextText)) {	
			timeline.stop();
			mostraSpiegazione(false);

			if(ds.getIndice() >= ds.getMapEsercizi().size()) {	// Serie completata, esce da questa scena
				switchToScene(event, ds.scene1);	// Va alla scena della scelta della difficolta
			}else {	// Va al prossimo esercizio
				resetTentativi();
				mostraTextConferma(confirmButtonConfirmText);
				startExercise();	// Inizio il prossimo esercizio
			}
		}
		// L'utente precedentemente ha perso la partita, riinizia 
		else if(getTextConferma().equals(confirmButtonLostGame)) {
			mostraTextConferma(confirmButtonConfirmText);
			ds.cambiaOrdine();	// Cambia l'ordine dei file casualmente
			initialize();
		}
		// L'utente ha effettuato una scelta
		else if(getTextConferma().equals(confirmButtonConfirmText)){
			
			RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
			boolean win = false;	// Indica se l'utente ha risposto correttamente, viene utilizzata per il controllo dei numeri di tentativi
			
			if (selectedRadioButton != null) {
				if (selectedRadioButton.getText().equals(rispostaCorretta)) {	// Risposta corretta
					timeline.stop();
					disabilitaOpzioni();
					
					progress+=1;
					progressTot.setProgress((double) (progress) / ds.getMapEsercizi().size());
					ds.incrementaIndice();
					
					// Modifiche ai Label
					mostraSpiegazione(true);
					mostraRisultatoNextText();
					mostraTextConferma(confirmButtonNextText);

					if(ds.updateDbData(ds.getIndice()))	// Aggiorno i dati per il file 
						updateCSV();						// Salvo le modifiche su file
					
					if(ds.getIndice() >= ds.getMapEsercizi().size()) {
						mostraRisultatoWin();
					}
					win = true;
				} else {	// Risposta sbagliata
					mostraRisultatoTryAgainText();
				}

				numeroTentativi++;
				mostraTentativi();

				// L'utente ha superato i numeri di tentativi disponibili e all'ultimo tentativo non ha risposto correttamente
				if(numeroTentativi>=numeroTentativiMassimi && !ds.getDifficolta().equals("Easy") && !win) {	
					timeline.stop();

					mostraTextConferma(confirmButtonLostGame);
					mostraRisultatoTooAttempts();
				}
			}
		}
	}
	private String getTextConferma() {
		return confirmButton.getText();
	}
	private void resetTentativi() {
		numeroTentativi = 0;
		mostraTentativi();
	}

	@FXML
	private void homeAction(ActionEvent event) throws IOException {
		switchToScene(event, ds.scene0);
	}
	@FXML
	private void exitAction(ActionEvent event) throws IOException {
		switchToScene(event, ds.scene1);
	}
	//<------ Funzioni per la gestione dei bottoni e sottofunzioni utilizzate
}
