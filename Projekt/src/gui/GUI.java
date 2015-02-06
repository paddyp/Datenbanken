package gui;

import guestGUI.AlleVorstell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
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

import org.postgresql.util.PSQLException;

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
	private boolean admin = false;

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
	private EigeneReservierungen eigeneReservierungen;
	
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
	private JLabel willkommenLabel;

	private JButton anmelden;
	private JButton abmelden;
	
	private String email;

	public GUI() {
		setSize(new Dimension(1000, 1000));
		setTitle("Kino Datenbank Benutzungsoberfläche");
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
		hinzufuegen.setVisible(false);
		aktualisieren = new JMenuItem("Aktualisieren");

		aktualisieren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reservierenPanel.update();
				akvor.update();
				aktsaalbel.update();
				kunde.update();
				beliebterFilm.update();
				

				revalidate();

			}
		});

		willkommenLabel = new JLabel("");
		willkommenLabel.setVisible(false);
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
				if(benutzername.getText().equalsIgnoreCase("admin") && new String(passwort.getPassword()).equalsIgnoreCase("admin")){
					admin = true;
					angemeldet = false;
					createView();
					hinzufuegen.setVisible(true);
				}else{
					ResultSet rs;
					try {
						System.out.println(benutzername.getText());
						rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("SELECT passwort FROM kunde WHERE email='%1%'", benutzername.getText()));
						rs.next();
						try{
							String passwort  = rs.getString("passwort");
							System.out.println("GUI" + new String(GUI.this.passwort.getPassword()));
							System.out.println("passwort" + passwort);
							if(passwort.equals(new String(GUI.this.passwort.getPassword()))){
								System.out.println("angemeldet");
								angemeldet = true;
								admin = false;
								createView();
								
								
							}else{
								JOptionPane.showMessageDialog(null, "Das Passwort ist falsch");
							}
						}catch(PSQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Benutzername ist falsch");
							return;
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				getContentPane().repaint();
			}
		});

		abmelden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				angemeldet = false;
				admin = false;
				abmelden.setVisible(false);
				anmelden.setVisible(true);
				
				
				
				createView();

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
		
		createView();

		
		for (int i = 0; i < anzeige.length; i++) {
			panel.add(anzeige[i]);
		}

		neuerKunde = new JMenuItem("Kunde");
		neuerKunde.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clientFrame.setVisible(true);
			}

		});

		neuerFilm = new JMenuItem("Film");
		neuerFilm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				movieFrame.setVisible(true);

			}
		});

		neueKategorie = new JMenuItem("Kategorie");
		neueKategorie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				kategorieFrame.setVisible(true);

			}
		});

		neuerSaal = new JMenuItem("Saal");
		neuerSaal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane
						.showInputDialog("Saal_Bezeichnung :");
				if (name != null && name != "") {
					try {
						DBQuery.sendInsertIntoQuery("Saal", name);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		neuerPlatz = new JMenuItem("Platz");
		neuerPlatz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				placeFrame.setVisible(true);

			}
		});

		neueVorstellung = new JMenuItem("Vorstellung");
		neueVorstellung.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performanceFrame.setVisible(true);

			}
		});

		neuerPreisaufschlag = new JMenuItem("Preisaufschlag");
		neuerPreisaufschlag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
		menuBar.add(willkommenLabel);
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
	
	private void createAdminView(){


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
	
	private void createGuestView(){
		JLabel alleVorstellungen = new JLabel("Alle Vorstellungen");
		anzeige[0].setLayout(new BorderLayout());
		anzeige[0].add(alleVorstellungen, BorderLayout.NORTH);
		anzeige[0].add(akvor, BorderLayout.CENTER);
		
		JLabel uhrzeit = new JLabel("Kategoriebelegung (Vorstellung wählen) [belegt|gesamt|kategorie]");
		anzeige[1].setLayout(new BorderLayout());
		anzeige[1].add(uhrzeit, BorderLayout.NORTH);
		anzeige[1].add(saalkategoriebelegung, BorderLayout.CENTER);
	}
	
	private void createUserView(String email){
		willkommenLabel.setText("Willkommen "+email+"! ");
		willkommenLabel.setVisible(true);
		
		
		JLabel reservierungen = new JLabel("Eigene Reservierungen");
		anzeige[1].setLayout(new BorderLayout());
		anzeige[1].add(reservierungen, BorderLayout.NORTH);
		eigeneReservierungen = new EigeneReservierungen(
				email);
		anzeige[1].add(eigeneReservierungen, BorderLayout.CENTER);

		JLabel updateDaten = new JLabel("Eigene Daten ändern");
		anzeige[2].setLayout(new BorderLayout());
		anzeige[2].add(updateDaten, BorderLayout.NORTH);
		UpdateKundenDaten updatekundendaten = new UpdateKundenDaten(
				email);
		anzeige[2].add(updatekundendaten, BorderLayout.CENTER);

		JLabel buchen = new JLabel("Buchen");
		anzeige[3].setLayout(new BorderLayout());
		anzeige[3].add(buchen, BorderLayout.NORTH);
		KundeBuchen kundeBuchen = new KundeBuchen(email);
		anzeige[3].add(kundeBuchen, BorderLayout.CENTER);

		JLabel alleVorstellungen = new JLabel("Alle Vorstellungen");
		anzeige[0].setLayout(new BorderLayout());
		anzeige[0].add(alleVorstellungen, BorderLayout.NORTH);
		AlleVorstellungen alleVorstellungenPanel = new AlleVorstellungen(
				kundeBuchen);
		anzeige[0].add(alleVorstellungenPanel, BorderLayout.CENTER);
	}
	
	private void createView(){
		// adminBereich

		for(int i = 0; i < anzeige.length; i++){
			anzeige[i].removeAll();
		}
		
		if (admin) {
			createAdminView();
		
		}else if (angemeldet) {
			createUserView(benutzername.getText());
		}else{
			createGuestView();
		}
		
		if(admin || angemeldet){
			anmelden.setVisible(false);
			abmelden.setVisible(true);
			benutzerLabel.setVisible(false);
			benutzername.setVisible(false);
			passwortLabel.setVisible(false);
			GUI.this.passwort.setVisible(false);
		}else{
			willkommenLabel.setText("");
			willkommenLabel.setVisible(false);
			hinzufuegen.setVisible(false);
			passwort.setText("");
			benutzername.setText("");

			benutzerLabel.setVisible(true);
			benutzername.setVisible(true);
			passwortLabel.setVisible(true);
			passwort.setVisible(true);
		}

	}

}
