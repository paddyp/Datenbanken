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
import adminGUI.AktuelleSaalbelegung;
import adminGUI.AktuelleVorstellung;
import adminGUI.Kunden;
import adminGUI.Reservieren;
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
	private AktuelleVorstellung akvor = new AktuelleVorstellung();
	private AktuelleSaalbelegung aktsaalbel = new AktuelleSaalbelegung();
	private Kunden kunde = new Kunden(reservierenPanel);
	private beliebtheitFilm beliebterFilm = new beliebtheitFilm();
	
	
	private JMenuItem einstellungen;
	private JMenuItem aktualisieren;
	private JMenuItem neuerKunde;
	private JMenuItem neuerFilm;
	private JMenuItem neueKategorie;
	private JMenuItem neuerSaal;
	private JMenuItem neuerPlatz;
	private JMenuItem neueVorstellung;

	private JTextField benutzername;
	private JPasswordField passwort;

	private JLabel benutzerLabel;
	private JLabel passwortLabel;

	private JButton anmelden;
	private JButton abmelden;
	
	public GUI() {
		setSize(new Dimension(1000, 1000));
		setTitle("DB Projekt");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Erzeugen der einzelnen Frames
		final MovieFrame movieFrame = new MovieFrame();
		final ClientFrame clientFrame = new ClientFrame();
		final KategorieFrame kategorieFrame = new KategorieFrame();
		final PlaceFrame placeFrame = new PlaceFrame();
		final PerformanceFrame performanceFrame = new PerformanceFrame();

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

		
		// IN RUHE LASSEN PATRIKS
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		panel.setBackground(new Color(255,255,255));
		
		anzeige = new JPanel[6];
		
		for(int i = 0; i < anzeige.length; i++)
		{
			anzeige[i] = new JPanel();
			anzeige[i].setBackground(new Color(213,213,213));
			anzeige[i].setBorder(BorderFactory.createLineBorder(Color.black));
			
		}
		
		
		// adminBereich
		if(admin){
			
		
		JLabel aktuelleVorstellungenLabel = new JLabel("aktuelle Vorstellungen");
		anzeige[0].setLayout(new BorderLayout());
		anzeige[0].add(aktuelleVorstellungenLabel,BorderLayout.NORTH);
		anzeige[0].add(akvor,BorderLayout.CENTER);
		
		JLabel saalBelegung = new JLabel("aktuelle Saalbelegung basierend auf der Vorstellung");
		anzeige[1].setLayout(new BorderLayout());
		anzeige[1].add(saalBelegung, BorderLayout.NORTH);
		anzeige[1].add(aktsaalbel,BorderLayout.CENTER);
		
		JLabel reservieren = new JLabel("reservieren");
		anzeige[3].setLayout(new BorderLayout());
		anzeige[3].add(reservieren, BorderLayout.NORTH);
		anzeige[3].add(reservierenPanel,BorderLayout.CENTER);
		
		JLabel kundeAnzeigen = new JLabel("Kunde suchen");
		anzeige[2].setLayout(new BorderLayout());
		anzeige[2].add(kundeAnzeigen, BorderLayout.NORTH);
		anzeige[2].add(kunde,BorderLayout.CENTER);
		
		
		
		JLabel beliebtesterFilm = new JLabel("beliebtheitsskala Film");
		anzeige[4].setLayout(new BorderLayout());
		anzeige[4].add(beliebtesterFilm,BorderLayout.NORTH);
	anzeige[4].add(beliebterFilm, BorderLayout.CENTER);
		
		JLabel uhrzeit = new JLabel("Kategoriebelegung pro Saal");
		anzeige[5].add(uhrzeit);
		}
		//
		//user
		if(angemeldet)
		{
			JLabel alleVorstellungen = new JLabel("Alle Vorstellungen");
			anzeige[0].setLayout(new BorderLayout());
			anzeige[0].add(alleVorstellungen, BorderLayout.NORTH);
			AlleVorstellungen alleVorstellungenPanel = new AlleVorstellungen();
			anzeige[0].add(alleVorstellungenPanel,BorderLayout.CENTER);
			
			JLabel reservierungen = new JLabel("Eigene Reservierungen");
			anzeige[1].setLayout(new BorderLayout());
			anzeige[1].add(reservierungen,BorderLayout.NORTH);
			EigeneReservierungen eigeneReservierungen = new EigeneReservierungen("hans@peter.de");
			anzeige[1].add(eigeneReservierungen,BorderLayout.CENTER);
			
			JLabel updateDaten = new JLabel("Eigene angaben ändern");
			anzeige[2].setLayout(new BorderLayout());
			anzeige[2].add(updateDaten,BorderLayout.NORTH);
			
			
		}
		
		
		

		
		
	
		for(int i = 0; i < anzeige.length; i++)
		{
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
						.showInputDialog("Geben sie einen Bezeichnung fuer den neuen Saal ein: ");
				if (name != null && name != "") {
					try {
						DBQuery.sendInsertIntoQuery("saal", name);
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

		

		// Hinzufuegen
		hinzufuegen.add(neuerKunde);
		hinzufuegen.add(neuerFilm);
		hinzufuegen.add(neueKategorie);
		hinzufuegen.add(neuerSaal);
		hinzufuegen.add(neuerPlatz);
		hinzufuegen.add(neueVorstellung);
		
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
