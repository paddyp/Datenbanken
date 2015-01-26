package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

public class MovieFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private JButton commit;
	private JButton abourt;
	
	
	public MovieFrame(){
		setSize(500,500);
		setLayout(new GridLayout(3,2));
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
		
		darstellerPanel.add(darstellerLabel);
		darstellerPanel.add(darstellerTF);
		
		add(darstellerPanel);
		
		commit = new JButton("commit");
		commit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
			}
		});
		add(commit);
		abourt = new JButton("abourt");
		abourt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					dispose();
				
			}
		});
		add(abourt);
		
		

	}


}