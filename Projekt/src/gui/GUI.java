package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import business.DBQuery;


public class GUI extends JFrame {
	
	private JPanel panel;
	
	private boolean angemeldet = false;
	private boolean admin = false;
	
	private JMenuBar menuBar;
	private JMenu datei;
	private JMenu hinzufuegen;
	private JMenuItem einstellungen;
	
	private JTextField benutzername;
	private JPasswordField kennwort;
	
	private JLabel benutzerLabel;
	private JLabel kennLabel;
	
	private JButton anmelden;
	
	private JButton newClient;
	private JButton newMovie;
	private JButton newKategorie;
	private JButton newRoom;
	private JButton newPlace;
	private JButton newPerformance;
	
		public GUI()
		{
			setSize(new Dimension(1000,1000));
			setTitle("DB Projekt");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			final MovieFrame movieFrame = new MovieFrame();
			final ClientFrame clientFrame = new ClientFrame();
			final KategorieFrame kategorieFrame = new KategorieFrame();
			final PlaceFrame placeFrame = new PlaceFrame();
			final PerformanceFrame performanceFrame = new PerformanceFrame();
			
			
			// Menu Bar
			menuBar = new JMenuBar();
			datei = new JMenu("Datei");
			hinzufuegen = new JMenu("Hinzufügen");
			
			benutzerLabel = new JLabel("Benutzername :");
			benutzername = new JTextField(10);
			kennLabel = new JLabel("Kennwort :");
			kennwort = new JPasswordField(10);
			einstellungen = new JMenuItem("Einstellungen");
			anmelden = new JButton("anmelden");
			
			anmelden.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					angemeldet = true;
					anmelden.setVisible(!angemeldet);
					System.out.println(angemeldet);
					
					getContentPane().repaint();
					
				}
			});
			
			datei.add(einstellungen);
			menuBar.add(datei);
			menuBar.add(hinzufuegen);
			

			menuBar.add(benutzerLabel);
			menuBar.add(benutzername);
			menuBar.add(kennLabel);
			menuBar.add(kennwort);
			menuBar.add(anmelden);
			
			//
			
			
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			panel.setBackground(new Color(255,255,255));
			GridBagConstraints c = new GridBagConstraints();
			
			newClient = new JButton();
			newClient.setText("neuer Kunde");
			newClient.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
						clientFrame.setVisible(true);
					
				
				}
				
			});
			newMovie = new JButton("new Movie");
			newMovie.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					movieFrame.setVisible(true);
					
				}
			});
			newKategorie = new JButton("neue Kategorie");
			newKategorie.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					kategorieFrame.setVisible(true);
					
				}
			});
			
			newRoom = new JButton("neuer Saal");
			newRoom.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
						String name =  JOptionPane.showInputDialog("Geben sie einen Bezeichnung für den neuen Saal ein");
						if(name != null && name != "")
						{
							try {
								DBQuery.sendInsertIntoQuery("saal", name);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					
				}
			});
			
			panel.add(newRoom);
			
			newPlace = new JButton("Platz");
			newPlace.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
						placeFrame.setVisible(true);
					
				}
			});
			panel.add(newPlace);
			
			newPerformance = new JButton("neue Vorstellung");
			newPerformance.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					performanceFrame.setVisible(true);
					
					
				}
			});
			
			panel.add(newPerformance);
		
			
			
			add(menuBar, BorderLayout.NORTH);
			panel.add(newClient);
			panel.add(newMovie);
			panel.add(newKategorie);
			
			add(panel);
			
		
			
			setVisible(true);
		}
		
}
