package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import de.hawhh.informatik.sml.kino.fachwerte.GeldBetrag;
import de.hawhh.informatik.sml.kino.fachwerte.Platz;
import de.hawhh.informatik.sml.kino.materialien.Kinosaal;
import de.hawhh.informatik.sml.kino.materialien.Vorstellung;
import de.hawhh.informatik.sml.kino.werkzeuge.barbezahlung.BarBezahlungsWekzeug;

/**
 * Mit diesem Werkzeug können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Material. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses Werkzeug ist ein eingebettetes Subwerkzeug. Es kann nicht beobachtet
 * werden.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2018
 */
public class PlatzVerkaufsWerkzeug
{
	private int _preisFuerAuswahl;
	// Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
	private Vorstellung _vorstellung;
	Stage dialog = new Stage();
	private PlatzVerkaufsWerkzeugUI _ui;

	/**
	 * Initialisiert das PlatzVerkaufsWerkzeug.
	 */
	public PlatzVerkaufsWerkzeug()
	{
		_ui = new PlatzVerkaufsWerkzeugUI();
		registriereUIAktionen();
		// Am Anfang wird keine Vorstellung angezeigt:
		setVorstellung(null);
	}

	/**
	 * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
	 * Kontextwerkzeug eingebettet werden.
	 * 
	 * @ensure result != null
	 */
	public Pane getUIPane()
	{
		return _ui.getUIPane();
	}

	/**
	 * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
	 */
	private void registriereUIAktionen()
	{
		_ui.getVerkaufenButton().setOnAction(e -> {
			BarBezahlungsWekzeug bbWerkzeug = new BarBezahlungsWekzeug(
					_preisFuerAuswahl);
			boolean result = bbWerkzeug.startBarbezahlung();
			if (result)
			{
				verkaufePlaetze(_vorstellung);
			}
			else
			{
				aktualisierePlatzplan();
			}
		});

		_ui.getStornierenButton()
				.setOnAction(e -> stornierePlaetze(_vorstellung));

		_ui.getPlatzplan().addPlatzSelectionListener(
				(e,b) -> reagiereAufNeuePlatzAuswahl(e.getAusgewaehltePlaetze(),b));

	}

	/**
	 * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert
	 * hat.
	 * 
	 * @param plaetze
	 *            die jetzt ausgewählten Plätze.
	 */
	private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze,boolean result)
	{

		_ui.getVerkaufenButton().setDisable(!istVerkaufenMoeglich(plaetze));
		_ui.getStornierenButton().setDisable(!istStornierenMoeglich(plaetze));

		//TODO: Bessere IF das er nicht reingeht wenn er die Vorstellung lädt! sonder nur wenn geklickt
		if (_vorstellung != null)//TODO:Markieren wenn geklickt.
		{
			if(!result) {
				_vorstellung.entferneAlle();
			}			
			_vorstellung.aktualisiereMarkierungen(plaetze);
		}
		
		

		aktualisierePreisanzeige(plaetze);

	}

	/*
	 * if(_vorstellung != null ) {//Merke in Vorstellung
	 * 
	 * 
	 * for(Platz p : plaetze) {
	 * if(_ui.getPlatzplan().getAusgewaehltePlaetze().contains(p)) {
	 * _vorstellung.markierePlatz(p); } else {
	 * _vorstellung.entferneMarkierung(p); }
	 * 
	 * } }
	 */
	/**
	 * Aktualisiert den anzuzeigenden Gesamtpreis
	 */
	private void aktualisierePreisanzeige(Set<Platz> plaetze)
	{
		// TODO: Geldbetrag anzeigen
		if (istVerkaufenMoeglich(plaetze))
		{
			int preis = _vorstellung.getPreisFuerPlaetze(plaetze);
			GeldBetrag betrag = GeldBetrag.get(preis);

			_ui.getPreisLabel()
					.setText("Gesamtpreis: " + betrag.getFormatiertenString());
			_preisFuerAuswahl = preis;
		}
		else
		{
			_ui.getPreisLabel().setText("Gesamtpreis:");
		}
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle storniert werden können.
	 */
	private boolean istStornierenMoeglich(Set<Platz> plaetze)
	{
		return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
	}

	/**
	 * Prüft, ob die angegebenen Plätze alle verkauft werden können.
	 */
	private boolean istVerkaufenMoeglich(Set<Platz> plaetze)
	{
		return !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
	}

	/**
	 * Setzt die Vorstellung. Sie ist das Material dieses Werkzeugs. Wenn die
	 * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
	 * Vorstellung darf auch null sein.
	 */
	public void setVorstellung(Vorstellung vorstellung)
	{
		_vorstellung = vorstellung;
		aktualisierePlatzplan();
	}

	/**
	 * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
	 */
	// TODO:MARKIERUNG 2 Hier wird der PLatzPLan aktualisiert! Also hier muss
	// auch geprüft werden ob markiert werden soll!
	private void aktualisierePlatzplan()
	{
		if (_vorstellung != null)
		{
			Kinosaal saal = _vorstellung.getKinosaal();
			_ui.getPlatzplan().setAnzahlPlaetze(saal.getAnzahlReihen(),
					saal.getAnzahlSitzeProReihe());

			Set<Platz> ausgewaehltePlaetze = new HashSet<>();

			for (Platz platz : saal.getPlaetze())
			{
				if (_vorstellung.istPlatzVerkauft(platz))
				{
					_ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
				}

				else if (_vorstellung.istMarkiert(platz))
				{
					// gucken ob PLätze markiert wurden
					_ui.getPlatzplan().markierePlatzAlsAusgewaehlt(platz);
					
					// adden um zu überprüfen ob man verkaufen stornieren o.ä
					// kann
					ausgewaehltePlaetze.add(platz);

				}
				else
				{
					_ui.getPlatzplan().markierePlatzAlsFrei(platz);
				}
			}

			if (ausgewaehltePlaetze != null)
			{
				reagiereAufNeuePlatzAuswahl(ausgewaehltePlaetze,true);
			}
		}
		else
		{
			_ui.getPlatzplan().setAnzahlPlaetze(0, 0);
		}
	}

	/**
	 * Verkauft die ausgewählten Plaetze.
	 */
	private void verkaufePlaetze(Vorstellung vorstellung)
	{
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		vorstellung.verkaufePlaetze(plaetze);
		vorstellung.entferneAlle();
		aktualisierePlatzplan();
	}

	/**
	 * Storniert die ausgewählten Plaetze.
	 */
	private void stornierePlaetze(Vorstellung vorstellung)
	{
		Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
		vorstellung.stornierePlaetze(plaetze);
		vorstellung.entferneAlle();
		aktualisierePlatzplan();
	}
}
