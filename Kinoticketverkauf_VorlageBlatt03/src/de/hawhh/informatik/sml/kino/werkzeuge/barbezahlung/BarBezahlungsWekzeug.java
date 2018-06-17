package de.hawhh.informatik.sml.kino.werkzeuge.barbezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.GeldBetrag;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class BarBezahlungsWekzeug
{
	private GeldBetrag _zuBezahlenderPreis;
	private GeldBetrag _bekommendesGeld;
	private GeldBetrag _rueckGeld;
	private BarBezahlungUI _ui;

	/**
	 * Konstruktor zur Barbezahlung
	 * 
	 * @require preisFuerAuswahl > 0
	 * @param preisFuerAuswahl
	 */
	public BarBezahlungsWekzeug(int preisFuerAuswahl)
	{
		assert preisFuerAuswahl > 0 : "Vorbedingung verletzt: preis > 0";
		_zuBezahlenderPreis = GeldBetrag.get(preisFuerAuswahl);
	}

	/**
	 * startet die UI gibt True zurück, falls Ok gedrückt wurde False falls
	 * Abbrechen
	 * 
	 * @return boolean result
	 */
	public boolean startBarbezahlung()
	{
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
	private void setZuBezahlendenrPreis()
	{
		// TODO: Geldbetrag anzeigen
		_ui.getLblZuBezahlenderPreis()
				.setText(_zuBezahlenderPreis.getFormatiertenString());

	}

	/**
	 * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
	 */
	private void registriereUIAktionen()
	{
		// Alert mit Rückgeld und result true
		_ui.getBestaetigenButton().setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Tickets verkauft! Bitte geben Sie "
							+ _rueckGeld.getFormatiertenString() + " zurück!",
					ButtonType.OK);

			_ui.getUIPane().setResult(true);
			alert.show();
			closeDialog();
		});
		// result false und dialog schließen
		_ui.getBeendenButton().setOnAction(e -> {
			_ui.getUIPane().setResult(false);
			closeDialog();
		});

		// Bei Eingabe wird das Rückgeld errechnet
		_ui.getTxtGeldBekommen().setOnKeyReleased(e -> {

			_ui.getBestaetigenButton().setDisable(true);

			if (_ui.getTxtGeldBekommen().getText().length() > 0)
			{
				String helperBekommendesGeld = _ui.getTxtGeldBekommen()
						.getText();
				if (!helperBekommendesGeld.contains("-"))
				{//kein Minus

					String[] parts = helperBekommendesGeld.split(",");
					int euroCent;

					// const parts
					final int euro = 0;
					final int cent = 1;

					// Gib Cent und Euro
					if (parts.length > 1)
					{
						if (parts[cent].length() == 1)
						{
							parts[cent] = parts[cent] + "0";
						}
						if (parts[cent].length() >= 2)
						{
							parts[cent] = parts[cent].substring(0, 2);
						}

						euroCent = Integer.valueOf(parts[euro] + parts[cent]);
						_bekommendesGeld = GeldBetrag.get(euroCent);
					}//end if nicht nur Euro
					else
					{ 
						euroCent = Integer.valueOf(parts[euro]) * 100;
					}
					_bekommendesGeld = GeldBetrag.get(euroCent);

					if ((_bekommendesGeld.getEurocent()
							- _zuBezahlenderPreis.getEurocent()) >= 0)
					{
						_rueckGeld = _bekommendesGeld
								.subtrahiere(_zuBezahlenderPreis);
						_ui.getBestaetigenButton().setDisable(false);
						// TODO: Geldbetrag anzeigen
						_ui.getTxtRueckgeld()
								.setText(_rueckGeld.getFormatiertenString());
					}//end if >=0
				}//end if minus
				else
				{
					Alert alert = new Alert(AlertType.CONFIRMATION,
							"Bitte nur positive Beträge!", ButtonType.OK);
					alert.show();
				}

			}
			else
			{
				_ui.getTxtRueckgeld().setText("0");
			}
		});
	}

	/**
	 * Close the Dialog
	 */
	private void closeDialog()
	{
		_ui.getUIPane().close();
	}

	/**
	 * Rechnet Cent zu Euro
	 * 
	 * @ensure cent > 0
	 * @param cent
	 * @return Euro als String
	 */
	private String centZuEuro(int cent)
	{
		assert cent > 0 : "Vorbedingung verletzt: cent > 0";
		double betrag = Double.valueOf(cent);
		betrag /= 100.00;

		return betrag + " Euro ";

	}
}
