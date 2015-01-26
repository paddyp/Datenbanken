package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MovieFrame extends JFrame{
	
	private JPanel titelPanel;
	private JLabel titelLabel;
	private JTextField titelTF;
	
	private JPanel fskPanel;
	private JLabel fskLabel;
	private JTextField fskTF;
	
	private JPanel bewertungPanel;
	private JLabel bewertungLabel;
	private JTextField bewertungTF;
	
	private JPanel darstellerPanel;
	private JLabel darstellerLabel;
	private JTextField darstellerTF;
	
	
	public MovieFrame(){
		setSize(500,500);
		setLayout(new GridLayout());
		
		titelPanel = new JPanel();
		titelLabel = new JLabel("Titel :");
		titelTF = new JTextField(10);
		
		titelPanel.add(titelLabel);
		titelPanel.add(titelTF);
		
		add(titelPanel);
		
		fskPanel = new JPanel();
		fskLabel = new JLabel("FSK :");
		fskTF = new JTextField(2);
		
		fskPanel.add(fskLabel);
		fskPanel.add(fskTF);
		
		add(fskPanel);
		
		bewertungPanel = new JPanel();
		bewertungLabel = new JLabel("Bewertung :");
		bewertungTF = new JTextField(2);
		
		bewertungPanel.add(bewertungLabel);
		bewertungPanel.add(bewertungTF);
		
		add(bewertungPanel);
		
		darstellerPanel = new JPanel();
		darstellerLabel = new JLabel("Hauptarsteller :");
		darstellerTF = new JTextField(10);
		
		
		
		setVisible(true);
	}

}
