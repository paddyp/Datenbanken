package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import userGUI.AlleVorstellungen;
import userGUI.EigeneReservierungen;
import userGUI.KundeBuchen;
import userGUI.UpdateKundenDaten;
import adminGUI.AktuelleSaalbelegung;
import adminGUI.AktuelleVorstellung;
import adminGUI.Kunden;
import adminGUI.Reservieren;
import adminGUI.SaalKategorieBelegung;
import adminGUI.beliebtheitFilm;
import business.DBQuery;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private boolean angemeldet = false;
	private boolean admin = true;

	private JMenuBar menuBar;

	private JPanel[] anzeige;

	private JMenu datei;
	private JMenu hinzufuegen;

	private Reservieren reservierenPanel = new Reservieren();
	private Kunden kunde = new Kunden(reservierenPanel);
	private beliebtheitFilm beliebterFilm = new beliebtheitFilm();
	private SaalKategorieBelegung saalkategoriebelegung = new SaalKategorieBelegung();
	private AktuelleVorstellung akvor = new AktuelleVorstellung(
			saalkategoriebelegung);
	private AktuelleSaalbelegung aktsaalbel = new AktuelleSaalbelegung();

	private JMenuItem einstellungen;
	private JMenuItem aktualisieren;
	private JMenuItem neuerKunde;
	private JMenuItem neuerFilm;
	private JMenuItem neueKategorie;
	private JMenuItem neuerSaal;
	private JMenuItem neuerPlatz;
	private JMenuItem neueVorstellung;
	private JMenuItem neuerPreisaufschlag;
	private JMenuItem neuesGenre;

	private JTextField benutzername;
	private JPasswordField passwort;

	private JLabel benutzerLabel;
	private JLabel passwortLabel;

	private JButton anmelden;
	private JButton abmelden;

	public GUI() {
		setSize(new Dimension(1000, 1000));
		setTitle("Kino Datenbank Benutzungsoberfl�che");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Erzeugen der einzelnen Frames
		final FilmFrame movieFrame = new FilmFrame();
		final KundeFrame clientFrame = new KundeFrame();
		final KategorieFrame kategorieFrame = new KategorieFrame();
		final PlaceFrame placeFrame = new PlaceFrame();
		final PerformanceFrame performanceFrame = new PerformanceFrame();
		final PreisaufschlagFrame preisaufschlagFrame = new PreisaufschlagFrame();

		// Menu Bar Elemente erzeugen
		menuBar = new JMenuBar();

		datei = new JMenu("Datei");
		hinzufuegen = new JMenu("Hinzufuegen");
		aktualisieren = new JMenuItem("Aktualisieren");

		aktualisieren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reservierenPanel.update();
				akvor.update();
				aktsaalbel.update();
				kunde.update();
				beliebterFilm.update();

				revalidate();

			}
		});

		benutzerLabel = new JLabel("Benutzername :");
		benutzername = new JTextField(10);
		passwortLabel = new JLabel("Kennwort :");
		passwort = new JPasswordField(10);
		einstellungen = new JMenuItem("Einstellungen");
		anmelden = new JButton("Anmelden");
		abmelden = new JButton("Abmelden");

		neuerKunde = new JMenuItem("Neuer Kunde");
		neuerFilm = new JMenuItem("Neuer Film");
		neueKategorie = new JMenuItem("Neue Kategorie");
		neuerSaal = new JMenuItem("Neuer Saal");
		neuerPlatz = new JMenuItem("Neuer Platz");
		neueVorstellung = new JMenuItem("Neue Vorstellung");
		neuerPreisaufschlag = new JMenuItem("Neuer Preisaufschlag");
		neuesGenre = new JMenuItem("Neues Genre");

		abmelden.setVisible(false);

		anmelden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				angemeldet = true;
				anmelden.setVisible(false);
				abmelden.setVisible(true);

				benutzerLabel.setVisible(false);
				benutzername.setVisible(false);
				passwortLabel.setVisible(false);
				passwort.setVisible(false);

				getContentPane().repaint();

			}
		});

		abmelden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				angemeldet = false;
				abmelden.setVisible(false);
				anmelden.setVisible(true);

				benutzerLabel.setVisible(true);
				benutzername.setVisible(true);
				passwortLabel.setVisible(true);
				passwort.setVisible(true);

				getContentPane().repaint();

			}
		});

		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBackground(new Color(255, 255, 255));

		anzeige = new JPanel[6];

		for (int i = 0; i < anzeige.length; i++) {
			anzeige[i] = new JPanel();
			anzeige[i].setBackground(new Color(213, 213, 213));
			anzeige[i].setBorder(BorderFactory.createLineBorder(Color.black));

		}

		// adminBereich
		if (admin) {

			JLabel aktuelleVorstellungenLabel = new JLabel(
					"aktuelle Vorstellungen");
			anzeige[0].setLayout(new BorderLayout());
			anzeige[0].add(aktuelleVorstellungenLabel, BorderLayout.NORTH);
			anzeige[0].add(akvor, BorderLayout.CENTER);

			JLabel saalBelegung = new JLabel(
					"Saalbelegung");
			anzeige[1].setLayout(new BorderLayout());
			anzeige[1].add(saalBelegung, BorderLayout.NORTH);
			anzeige[1].add(aktsaalbel, BorderLayout.CENTER);

			JLabel reservieren = new JLabel("reservieren");
			anzeige[3].setLayout(new BorderLayout());
			anzeige[3].add(reservieren, BorderLayout.NORTH);
			anzeige[3].add(reservierenPanel, BorderLayout.CENTER);

			JLabel kundeAnzeigen = new JLabel("Kunde suchen");
			anzeige[2].setLayout(new BorderLayout());
			anzeige[2].add(kundeAnzeigen, BorderLayout.NORTH);
			anzeige[2].add(kunde, BorderLayout.CENTER);

			JLabel beliebtesterFilm = new JLabel("Filme nach Beliebtheit sortiert");
			anzeige[4].setLayout(new BorderLayout());
			anzeige[4].add(beliebtesterFilm, BorderLayout.NORTH);
			anzeige[4].add(beliebterFilm, BorderLayout.CENTER);

			JLabel uhrzeit = new JLabel("Kategoriebelegung (Vorstellung wählen) [belegt|gesamt|kategorie]");
			anzeige[5].setLayout(new BorderLayout());
			anzeige[5].add(uhrzeit, BorderLayout.NORTH);
			anzeige[5].add(saalkategoriebelegung, BorderLayout.CENTER);
		}
		//
		// user
		if (angemeldet) {

			JLabel reservierungen = new JLabel("Eigene Reservierungen");
			anzeige[1].setLayout(new BorderLayout());
			anzeige[1].add(reservierungen, BorderLayout.NORTH);
			EigeneReservierungen eigeneReservierungen = new EigeneReservierungen(
					"hans@peter.de");
			anzeige[1].add(eigeneReservierungen, BorderLayout.CENTER);

			JLabel updateDaten = new JLabel("Eigene angaben ändern");
			anzeige[2].setLayout(new BorderLayout());
			anzeige[2].add(updateDaten, BorderLayout.NORTH);
			UpdateKundenDaten updatekundendaten = new UpdateKundenDaten(
					"hans@peter.de");
			anzeige[2].add(updatekundendaten, BorderLayout.CENTER);

			JLabel buchen = new JLabel("Buchen");
			anzeige[3].setLayout(new BorderLayout());
			anzeige[3].add(buchen, BorderLayout.NORTH);
			KundeBuchen kundeBuchen = new KundeBuchen("hans@peter.de");
			anzeige[3].add(kundeBuchen, BorderLayout.CENTER);

			JLabel alleVorstellungen = new JLabel("Alle Vorstellungen");
			anzeige[0].setLayout(new BorderLayout());
			anzeige[0].add(alleVorstellungen, BorderLayout.NORTH);
			AlleVorstellungen alleVorstellungenPanel = new AlleVorstellungen(
					kundeBuchen);
			anzeige[0].add(alleVorstellungenPanel, BorderLayout.CENTER);

		}

		for (int i = 0; i < anzeige.length; i++) {
			panel.add(anzeige[i]);
		}

		neuerKunde = new JMenuItem("Kunde");
		neuerKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clientFrame.setVisible(true);
			}

		});

		neuerFilm = new JMenuItem("Film");
		neuerFilm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				movieFrame.setVisible(true);

			}
		});

		neueKategorie = new JMenuItem("Kategorie");
		neueKategorie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				kategorieFrame.setVisible(true);

			}
		});

		neuerSaal = new JMenuItem("Saal");
		neuerSaal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = JOptionPane
						.showInputDialog("Saal_Bezeichnung :");
				if (name != null && name != "") {
					try {
						DBQuery.sendInsertIntoQuery("Saal", name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		neuerPlatz = new JMenuItem("Platz");
		neuerPlatz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				placeFrame.setVisible(true);

			}
		});

		neueVorstellung = new JMenuItem("Vorstellung");
		neueVorstellung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				performanceFrame.setVisible(true);

			}
		});

		neuerPreisaufschlag = new JMenuItem("Preisaufschlag");
		neuerPreisaufschlag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				preisaufschlagFrame.setVisible(true);

			}
		});
		
		neuesGenre = new JMenuItem("Genre");
		neuesGenre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = JOptionPane
						.showInputDialog("Genre-Bezeichnung :");
				if (name != null && name != "") {
					try {
						DBQuery.sendInsertIntoQuery("Genre", name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		// Hinzufuegen
		hinzufuegen.add(neuerKunde);
		hinzufuegen.add(neuerFilm);
		hinzufuegen.add(neueKategorie);
		hinzufuegen.add(neuerSaal);
		hinzufuegen.add(neuerPlatz);
		hinzufuegen.add(neueVorstellung);
		hinzufuegen.add(neuerPreisaufschlag);
		hinzufuegen.add(neuesGenre);

		// Einstellungen
		datei.add(einstellungen);
		datei.add(aktualisieren);
		menuBar.add(datei);
		menuBar.add(hinzufuegen);

		// Anmeldezeile
		menuBar.add(benutzerLabel);
		menuBar.add(benutzername);
		menuBar.add(passwortLabel);
		menuBar.add(passwort);
		menuBar.add(anmelden);
		menuBar.add(abmelden);

		add(menuBar, BorderLayout.NORTH);

		add(panel);

		setVisible(true);
	}

}
