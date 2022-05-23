package aknakereso;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Palyabeallitasok extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JButton gomb = new JButton("Mehet a menet!");
	private JComboBox meret;
	private JTextField nev = new JTextField("", 20);
	private JTextField bomba = new JTextField("", 5);
	private JCheckBoxMenuItem ido = new JCheckBoxMenuItem();
	
	public Palyabeallitasok() {
		super("Jatek beallitasok");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(500, 200);
		this.setResizable(true);
		setLocationRelativeTo(null);
		
		this.setLayout(new GridLayout(5,1));
		JPanel panel1= new JPanel();
		JPanel panel2= new JPanel();
		JPanel panel3= new JPanel();
		JPanel panel4= new JPanel();
		JPanel panel5= new JPanel();
		JLabel szovnev = new JLabel ("Nev:");
		JLabel szovmeret = new JLabel ("Palya meret:");
		JLabel szovbomba = new JLabel ("Macskaszam (ajanlott~10-100):");
		JLabel szovido = new JLabel ("Szeretne idokorlatot?");
		IndulJatek indul = new IndulJatek();
		Object[] palyameret = new Object[3];
		palyameret[0] = (String) ("Kicsi");
		palyameret[1] = (String) ("Kozepes");
		palyameret[2] = (String) ("Nagy");
		meret = new JComboBox(palyameret);
		
		panel1.add(szovnev);
		panel2.add(szovmeret);
		panel3.add(szovbomba);
		panel4.add(szovido);
		panel1.add(nev);
		panel2.add(meret);
		panel3.add(bomba);
		panel4.add(ido);
		panel5.add(gomb);
		gomb.addActionListener(indul);
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.add(panel5);
		setVisible(true);
	}

	public String getBomb() {
		return bomba.getText();
	}
	
	public int mezoSorokSzama() {
		if(((String) meret.getSelectedItem()).equals("Kicsi")) {
			return 9;
		}
		if(((String) meret.getSelectedItem()).equals("Kozepes")) {
			return 16;
		}
		if(((String) meret.getSelectedItem()).equals("Nagy")) {
			return 30;
		}
		else{
			return 0;
		}
	}
	public int mezoOszlopokSzama() {
		if(((String) meret.getSelectedItem()).equals("Kicsi")) {
			return 9;
		}
		if(((String) meret.getSelectedItem()).equals("Kozepes") || ((String) meret.getSelectedItem()).equals("Nagy")) {
			return 16;
		}
		else{
			return 0;
		}
	}
	
	public void ujJatek() {
		JFrame jatek = new JFrame("Aknakereso");
		jatek.setLayout(new BorderLayout());
		jatek.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(bomba.getText() == null || bomba.getText().trim().isEmpty()) {
			bomba.setText("10");
		}
		else if(((String) meret.getSelectedItem()).equals("Kicsi") && Integer.valueOf(bomba.getText()) > 81) {
			bomba.setText("81");
		}
		else if(((String) meret.getSelectedItem()).equals("Kozepes") && Integer.valueOf(bomba.getText()) > 256) {
			bomba.setText("256");
		}
		else if(((String) meret.getSelectedItem()).equals("Nagy") && Integer.valueOf(bomba.getText()) > 480) {
			bomba.setText("480");
		}
		else if((((String) meret.getSelectedItem()).equals("Kicsi") || ((String) meret.getSelectedItem()).equals("Kozepes") ||  ((String) meret.getSelectedItem()).equals("Nagy"))&& Integer.valueOf(bomba.getText()) < 0) {
			bomba.setText("10");
		}
		int sorokszama = mezoSorokSzama();
		int oszlopokszama = mezoOszlopokSzama();
		Jatek j = new Jatek(nev.getText(), sorokszama, oszlopokszama, Integer.valueOf(bomba.getText()), ido.isSelected(),(String) meret.getSelectedItem());
		Fejlec f = new Fejlec(j, this, jatek, (String) meret.getSelectedItem(), ido.isSelected());
	
		jatek.add(f, BorderLayout.NORTH);
		jatek.add(j, BorderLayout.SOUTH);
		jatek.pack();
		jatek.setLocationRelativeTo(null);
		jatek.setVisible(true);
		dispose();
	}
	
	public class IndulJatek implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ujJatek();
		}
	}
}