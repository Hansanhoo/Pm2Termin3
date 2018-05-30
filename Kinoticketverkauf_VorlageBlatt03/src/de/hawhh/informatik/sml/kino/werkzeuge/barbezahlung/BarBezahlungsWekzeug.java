package de.hawhh.informatik.sml.kino.werkzeuge.barbezahlung;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class BarBezahlungsWekzeug
{	
	private int _zuBezahlenderPreis;
	private int _bekommendesGeld;
	private int _rueckGeld;
	private BarBezahlungUI _ui;
	
	
	/**
	 * Konstruktor zur Barbezahlung
	 * @require preisFuerAuswahl > 0
	 * @param preisFuerAuswahl
	 */
	public BarBezahlungsWekzeug(int preisFuerAuswahl) {
		 assert preisFuerAuswahl > 0 : "Vorbedingung verletzt: preis > 0";
		_zuBezahlenderPreis = preisFuerAuswahl;		
	}
	/**
	 * startet die UI
	 * gibt True zurück, falls Ok gedrückt wurde
	 * False falls Abbrechen
	 * @return boolean result
	 */
	public boolean startBarbezahlung() {
		_ui = new BarBezahlungUI();
		_ui.getBestaetigenButton().setDisable(true);
		setZuBezahlendenrPreis();		
		registriereUIAktionen();
		_ui.getUIPane().showAndWait();

		return _ui.getUIPane().resultProperty().get();
	}

	/**
	 * Setzt den zu bezahlnden Preis auf TextField txtZuBezahlenderPreis
	 */
	private void setZuBezahlendenrPreis() {		
		_ui.getLblZuBezahlenderPreis().setText(String.valueOf(_zuBezahlenderPreis));				
	}
	 /**
     * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
     */
    private void registriereUIAktionen()
    {
    	//Alert mit Rückgeld und result true
    	_ui.getBestaetigenButton().setOnAction(e -> {
    		Alert alert = new Alert(AlertType.CONFIRMATION,"Tickets verkauft! Bitte geben Sie " + centZuEuro(_rueckGeld) +" zurück!", ButtonType.OK);
    		
    		_ui.getUIPane().setResult(true);    		
    		alert.show();
    		closeDialog();    
    	});
    	//result false und dialog schließen
     	_ui.getBeendenButton().setOnAction(e -> {
    		_ui.getUIPane().setResult(false);    		
    		closeDialog();    
    	});
     	
     	//Bei Eingabe wird das Rückgeld errechnet 
    	_ui.getTxtGeldBekommen().setOnKeyReleased( e -> {
    		_rueckGeld = 0;
    		_ui.getBestaetigenButton().setDisable(true);
    		
    		if(_ui.getTxtGeldBekommen().getText().length() > 0) {
    			_bekommendesGeld = Integer.valueOf(_ui.getTxtGeldBekommen().getText());    	
    			_rueckGeld = _bekommendesGeld -_zuBezahlenderPreis;    			
    		 	if((_bekommendesGeld -_zuBezahlenderPreis) >=0){
    		 		_ui.getBestaetigenButton().setDisable(false);
    		 	}
    		
    			_ui.getTxtRueckgeld().setText(String.valueOf(_rueckGeld));
    		}
    		else {		
    			_ui.getTxtRueckgeld().setText("0");
    		}
    	});     
    }
    /**
     * Close the Dialog
     */
    private void closeDialog() {
    	_ui.getUIPane().close();
    }
   /**
    * Rechnet Cent zu Euro
    * @ensure cent > 0
    * @param cent
    * @return Euro als String
    */
    private String centZuEuro(int cent) {
    	 assert cent > 0 : "Vorbedingung verletzt: cent > 0";
    	double betrag = Double.valueOf(cent);    	
    	betrag /= 100.00;  
    	
    	return betrag + " Euro ";
    	
    }
}
