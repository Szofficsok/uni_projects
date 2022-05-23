package monke;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Options extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private String[] images = new String[5];
	private ArrayList<String> players;
	private String selectedimg;
	JComboBox<String> imgs;
	private JTextField name;
	private JLabel l2;
	
	/**
	*Visszadja a jatekosok listajat
	*/
	public ArrayList<String> GetPlayers() {
		return players;
	}
	
	/**
	*Play gomb lenyomasa utan az eger altal bekovetkezett esemenyert felelos osztaly.
	*/
	public String GetSelectedImage() {
		return selectedimg;
	}
	
	/**
	*Konstruktor
	*/
	public Options(){
		images[0] = "Boba-feta";
		images[1] = "Darth Vader";
		images[2] = "Steve";
		images[3] = "Private";
		images[4] = "Sweetie Belle";
		
		players = new ArrayList<String>();
		imgs = new JComboBox<String>(images);
		setSize(600, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
		setLayout(new GridLayout(4,1));
		JPanel p1 = new JPanel();
		p1.add(imgs);
		this.add(p1);
		
		JLabel l1 = new JLabel("Enter player name: ");
		name = new JTextField(15);
		JButton add = new JButton("Add");
		JPanel p2 = new JPanel();
		p2.add(l1);
		p2.add(name);
		p2.add(add);
		AddButtonActionListener addl = new AddButtonActionListener();
		add.addActionListener(addl);
		this.add(p2);
		
		l2 = new JLabel("Players: " + players.size());
		JPanel p3 = new JPanel();
		p3.add(l2);
		this.add(p3);
		
		JButton ok = new JButton("Ok");
		JPanel p4 = new JPanel();
		p4.add(ok);
		OkButtonActionListener okl = new OkButtonActionListener();
		ok.addActionListener(okl);
		this.add(p4);
	}
	
	public void SetInvis() {
		this.setVisible(false);
	}
	
	public void SetVis() {
		this.setVisible(true);
	}
	
	/**
	*Ok gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class OkButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			selectedimg = (String)imgs.getSelectedItem();
			SetInvis();
		}
	}
	
	/**
	*Add gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class AddButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!name.getText().equals("")) {
				players.add(name.getText());
				name.setText("");
				l2.setText("Players: " + players.size());
			}
		}
	}
}
