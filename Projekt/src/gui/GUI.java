package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;


public class GUI extends JFrame {
	
	private JPanel panel;
	private JTable table;
		public GUI()
		{
			setSize(new Dimension(1000,1000));
			setTitle("DB Projekt");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			String [][] data = {{"A"},{"B"}};
			String [] title = {"titel"};
			panel = new JPanel();
			table = new JTable(data,title);
			
			table.getModel().setValueAt("Cfasdkljfjadklsfjadsksdfjkdsfaklösdfajklasdlöfdsajklfadsjklfasdjkfasdkfsaadfkls", 0, 0);
			panel.add(table.getTableHeader());
			panel.add(table);
			add(panel);
		
			
			setVisible(true);
		}
}
