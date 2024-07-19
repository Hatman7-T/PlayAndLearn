package game2Controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 * 	Controller usato per la gestione della scelta della difficolta.
 * 	I nomi dei file vengono caricati dinamicamente in base ai parametri prestabiliti.
 * 	I progressi precedenti vengono caricati da file.
 * 	Ogni volta che l'utente sceglie una difficolta deve svolgere tutti gli esercizi con quella difficolta.
 * 		Anche se precedentemente aveva interotto una partita, non può riprenderla.
 * 	L'ordine della presentazione degli esercizi è casuale.
 * 	Vengono aggiornate delle barre dei progressi che mostrano gli ultimi progressi.
 * 
 * @author Ardelean Razvan
 * @project Play And Learn
 */

public class CodeComprehensionMainController extends UserController{

	@FXML
	private Button beginnerButton;
	@FXML
	private Button intermediateButton;
	@FXML
	private Button advancedButton;
	@FXML
	private ProgressIndicator beginnerProgress;
	@FXML
	private ProgressIndicator intermediateProgress;
	@FXML
	private ProgressIndicator advancedProgress;

	DataService ds = DataService.getInstance();	// Usato per condividere dati con altri controller

	@FXML
	public void initialize() {
		//----> Creazione dinamica delle maps con i nomi dei file
		ds.mapEserciziPrincipiante = new LinkedHashMap<>();
		ds.mapEserciziIntermedio = new LinkedHashMap<>();
		ds.mapEserciziAvanzato = new LinkedHashMap<>();

		for(int i=1; i<=DataService.N_ESERCIZI_PRINCIPIANTE; i++) {
			ds.mapEserciziPrincipiante.put("e_es"+i+".txt", new Pair("e_esC"+i+".txt", "e_esS"+i+".txt"));
		}
		for(int i=1; i<=DataService.N_ESERCIZI_INTERMEDIO; i++) {
			ds.mapEserciziIntermedio.put("m_es"+i+".txt", new Pair("m_esC"+i+".txt", "m_esS"+i+".txt"));
		}
		for(int i=1; i<=DataService.N_ESERCIZI_AVANZATO; i++) {
			ds.mapEserciziAvanzato.put("h_es"+i+".txt", new Pair("h_esC"+i+".txt", "h_esS"+i+".txt"));
		}
		//<---- Creazione dinamica delle maps con i nomi dei file

		ds.setIndici();	// Aggiorno gli indici correnti con quelli precedentemente salvati su file
		ds.cambiaOrdine();	// Cambia l'ordine dei file casualmente

		// Attivazione / Disattivazione dei pulsanti, che permettono l'utente ad accede alle difficolta, in base ai progressi
		if(ds.indiceEsercizioPrincipiante == (DataService.N_ESERCIZI_PRINCIPIANTE) || ds.indiceEsercizioIntermedio > 0) {	// Medium Button
			intermediateButton.setDisable(false);
		}else {
			intermediateButton.setDisable(true);
		}
		if(ds.indiceEsercizioIntermedio == (DataService.N_ESERCIZI_INTERMEDIO) || ds.indiceEsercizioAvanzato > 0) {	// Hard Button
			advancedButton.setDisable(false);
		}else {
			advancedButton.setDisable(true);
		}

		setAllProgressIndicator();	
	}

	// Aggiorno gli indicatori con i progressi dell'utente
	private void setAllProgressIndicator() {	
		double indice1 = ds.indiceEsercizioPrincipiante;
		double indice2 = ds.indiceEsercizioIntermedio;
		double indice3 = ds.indiceEsercizioAvanzato;

		beginnerProgress.setProgress(indice1 / (DataService.N_ESERCIZI_PRINCIPIANTE));
		intermediateProgress.setProgress(indice2 / (DataService.N_ESERCIZI_INTERMEDIO));
		advancedProgress.setProgress(indice3 / (DataService.N_ESERCIZI_AVANZATO));
	}

	// Funzione chiamata dai pulsante per andare alla pagina degli esercizi
	private void goToExercisePage(ActionEvent event) throws IOException {
		switchToScene(event, ds.scene2);
	}

	@FXML
	private void beginnerAction(ActionEvent event) throws IOException {
		ds.indiceEsercizioPrincipiante = 0;
		DataService.difficolta = "Easy";
		goToExercisePage(event);
	}
	@FXML
	private void intermediateAction(ActionEvent event) throws IOException {
		ds.indiceEsercizioIntermedio = 0;
		DataService.difficolta = "Medium";
		goToExercisePage(event);
	}
	@FXML
	private void advancedAction(ActionEvent event) throws IOException {
		ds.indiceEsercizioAvanzato = 0;
		DataService.difficolta = "Hard";
		goToExercisePage(event);
	}
	@FXML
	private void homeAction(ActionEvent event) throws IOException {
		switchToScene(event, ds.scene0);
	}
}
