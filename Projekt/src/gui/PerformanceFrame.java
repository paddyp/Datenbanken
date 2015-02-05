package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.DBQuery;

public class PerformanceFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel performancePanel;
	private JLabel performanceLabel;
	
	private JComboBox<String> dayCB;
	private JComboBox<String> monthCB;
	private JComboBox<String> yearCB;
	
	private JComboBox<String> hourCB;
	private JComboBox<String> minutesCB;
	
	private JComboBox<String> filmCB;
	private JComboBox<String> saalCB;
	
	private JButton performance;
	
	
	public PerformanceFrame(){
		setSize(500,500);
		setTitle("Neue Vorstellung hinzufügen");
		
		performancePanel = new JPanel();
		performanceLabel = new JLabel("Vorstellung Datum und zeit");
		
		createDateComboBox();
		createTimeComboBox();
		try {
			createMovie();
			createSaal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		performance = new JButton("neue Vorstellung");
		performance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("send performance");
				
			}
		});
		
		
		
		performancePanel.add(performanceLabel);
		performancePanel.add(dayCB);
		performancePanel.add(monthCB);
		performancePanel.add(yearCB);
		
		performancePanel.add(hourCB);
		performancePanel.add(minutesCB);
		
		performancePanel.add(filmCB);
		performancePanel.add(saalCB);
		
		performancePanel.add(performance);
		
		add(performancePanel);
		
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	

	private void createMovie() throws SQLException{
		filmCB = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM film");
		while(rs.next()){
			filmCB.addItem(rs.getString("titel"));
		}
	}
	
	private void createSaal() throws SQLException{
		saalCB = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM saal");
		while(rs.next())
		{
			saalCB.addItem(rs.getString("bezeichnung"));
		}
	}
	
	private void createDateComboBox(){
		dayCB = new JComboBox<String>();
		for(int i = 1; i < 32; i++)
		{
			if(i < 10)
			{
				
				dayCB.addItem("0" + i);
			}else{
				dayCB.addItem("" + i);
			}
			
		}
		monthCB = new JComboBox<String>();
		for(int i = 1; i <= 12;i++)
		{
			monthCB.addItem("" + i);
		}
		
		Calendar cal = Calendar.getInstance();
		yearCB = new JComboBox<String>();
		for(int i = cal.get(Calendar.YEAR); i < cal.get(Calendar.YEAR) +100; i++)
		{
			yearCB.addItem("" + i);
		}
	}
	
	private void createTimeComboBox(){
		hourCB = new JComboBox<String>();
		for(int i = 0; i < 25; i++)
		{
			if(i < 10){
				hourCB.addItem("0" + i);
			}else{
				hourCB.addItem("" + i);
			}
			
		}
		
		minutesCB = new JComboBox<String>();
		for(int i = 0; i < 60; i++)
		{
			if(i < 10)
			{
				minutesCB.addItem("0" + i);
			}else{
				minutesCB.addItem("" + i);
			}
		}
	}
}
