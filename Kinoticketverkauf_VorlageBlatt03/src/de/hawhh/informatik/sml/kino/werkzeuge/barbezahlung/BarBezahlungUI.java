package de.hawhh.informatik.sml.kino.werkzeuge.barbezahlung;



import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Modality;
import javafx.stage.Stage;

class BarBezahlungUI
{

	Button _bestaetigenButton;
	Button _beendenButton;
	
	TextField _txtGeldBekommen;
	Label _lblZuBezahlenderPreis;
	Label _txtRueckgeld;
	Dialog<Boolean> _dialog;
	
  	public BarBezahlungUI() {
  		 _dialog = getDialog();
  	}
  	/**
  	 * creates a new Dialog with TextFields Labels and Buttons
  	 * @return new Dialog
  	 */
  	public Dialog<Boolean> getDialog(){
  		//Create Button
  		_bestaetigenButton = new Button("Ok");
  		_beendenButton = new Button("Abbrechen"); 	
  		
  		Dialog<Boolean> dialog = new Dialog<>();  		
  		
  		// Create the Grid
  		GridPane grid = new GridPane();
  		grid.setHgap(10);
  		grid.setVgap(10);
  		grid.setPadding(new Insets(20, 150, 10, 10));

  		//Create TextFields&Labels
  		_lblZuBezahlenderPreis = new Label();
  		_txtGeldBekommen = new TextField();  		
  		_txtRueckgeld = new Label();

  		//Add TextFields and Labels to Grid
  		grid.add(new Label("Zu Bezahlender Betrag:"), 0, 0);
  		grid.add(_lblZuBezahlenderPreis, 1, 0);
  		grid.add(new Label("Bekommen:"), 0, 1);
  		grid.add(_txtGeldBekommen, 1, 1);
  		grid.add(new Label("Rückgeld:"), 0, 2);
  		grid.add(_txtRueckgeld, 1, 2);
  		grid.add(_bestaetigenButton, 1,4);
  		grid.add(_beendenButton, 2,4);
  		
  		//Modaler Dialog
  		dialog.initModality(Modality.APPLICATION_MODAL); 
  		dialog.getDialogPane().setContent(grid);  		
  		
  		return dialog;  		
  	}
  	/**
  	 * 
  	 * @return bestaetigenButton
  	 */
  	public Button getBestaetigenButton() {
  		return _bestaetigenButton;
  	}
  	/**
  	 * 
  	 * @return beendenButton
  	 */
  	public Button getBeendenButton() {
  		return _beendenButton;
  	}
  	/**
  	 * 
  	 * @return txtGeldBekommen
  	 */
  	public TextField getTxtGeldBekommen() {
  		return _txtGeldBekommen;
  	}
  	/**
  	 * 
  	 * @return txtRueckgeld
  	 */
  	public Label getTxtRueckgeld() {
  		return _txtRueckgeld;
  	}
  	/**
  	 * 
  	 * @return txtZuBezahlenderPreis
  	 */
  	public Label getLblZuBezahlenderPreis() {
  		return _lblZuBezahlenderPreis;
  	}
  	/**
  	 * 
  	 * @return this dialog(UI)
  	 */
  	public Dialog<Boolean> getUIPane(){
  		return _dialog;
  	}
	
}
	