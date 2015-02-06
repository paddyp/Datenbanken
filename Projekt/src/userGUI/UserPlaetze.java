package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objekte.PlatzObjekt;
import Objekte.VorstellungObjekt;
import business.DBQuery;

public class UserPlaetze extends JPanel {

	private JLabel hinweis;
	private JPanel buchunspanel;
	private JButton buchenbtn;
	private JButton randombtn;
	private JTextField reihe;
	private JTextField nummer;
	private JLabel reihelbl;
	private JLabel nummerlbl;
	private VorstellungObjekt vorstellung;
	private UserBuchen userBuchen;

	public UserPlaetze(UserBuchen userBuchen) {
		this.userBuchen = userBuchen;

		setLayout(new GridLayout(2, 1));

		hinweis = new JLabel("Bitte eine Vorstellung links oben auswählen",
				JLabel.CENTER);
		add(hinweis);

		buchunspanel = new JPanel();
		buchunspanel.setLayout(new GridLayout(3, 2));
		add(buchunspanel);

		reihelbl = new JLabel("Reihe: ");
		buchunspanel.add(reihelbl);
		reihe = new JTextField(6);
		buchunspanel.add(reihe);
		nummerlbl = new JLabel("Nummer: ");
		buchunspanel.add(nummerlbl);
		nummer = new JTextField(6);
		buchunspanel.add(nummer);

		buchenbtn = new JButton("Reservieren");
		buchenbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (vorstellung != null && !reihe.getText().isEmpty()
						&& !nummer.getText().isEmpty()) {
					System.out.println(UserPlaetze.this.userBuchen);
					UserPlaetze.this.userBuchen.add(new PlatzObjekt(reihe
							.getText(), nummer.getText()));
				}
			}
		});

		randombtn = new JButton("freier Platz");
		randombtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (vorstellung != null) {
					String kat = JOptionPane
							.showInputDialog("Bitte geben sie die Kategorie ein");
					ResultSet rs;

					try {
						rs = DBQuery.sendQuery(DBQuery
								.fillPlaceholders(
										"select reihe,nummer from vorstellung v "
												+ "join platz p on v.saal_bezeichnung = p.saal_bezeichnung "
												+ "WHERE v.id = %1% AND p.kategorie_bezeichnung = '%2%' EXCEPT select platz_reihe,platz_nummer "
												+ "from platz_reservierung pr join reservierung r "
												+ "ON pr.reservierung_id  = r.id join vorstellung v "
												+ "on v.id = r.vorstellung_id WHERE vorstellung_id = %1% LIMIT 1;",
										vorstellung.getId(), kat));
						
						rs.next();
						if(rs != null)
						{
							UserPlaetze.this.reihe.setText(rs.getString("reihe"));
							UserPlaetze.this.nummer.setText(rs.getString("nummer"));
						}else{
							JOptionPane.showMessageDialog(null, "Es gibt keinen freien Platz für diese Kategorie");
						}
					
						System.out.println("button ist durch ");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Bitte wählen sie links eine Vorstellung");
				}
			}
		});
		
		buchunspanel.add(randombtn);
		buchunspanel.add(buchenbtn);
	}

	public void update(VorstellungObjekt vorstellung) {
		this.vorstellung = vorstellung;
		hinweis.setText("Datum: " + vorstellung.getZeit() + ", Saal: "
				+ vorstellung.getSaal() + ", Film: " + vorstellung.getTitel());
	}
}
