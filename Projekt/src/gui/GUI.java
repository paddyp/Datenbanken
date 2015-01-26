package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;


public class GUI extends JFrame {
	
	private JPanel panel;
	private JTable table;
	
	private JButton newClient;
	
	
		public GUI()
		{
			setSize(new Dimension(1000,1000));
			setTitle("DB Projekt");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			String [][] data = {{"A"},{"B"}};
			String [] title = {"titel"};
			
			
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
						ClientFrame clientFrame = new ClientFrame();
					
				
				}
				
			});
			
			table = new JTable(data,title);
			
			table.getModel().setValueAt("Cfasdkljfjadklsfjadsksdfjkdsfaklösdfajklasdlöfdsajklfadsjklfasdjkfasdkfsaadfkls", 0, 0);
			panel.add(table.getTableHeader());
			panel.add(table);
			panel.add(newClient);
			add(panel);
		
			
			setVisible(true);
		}
		
}