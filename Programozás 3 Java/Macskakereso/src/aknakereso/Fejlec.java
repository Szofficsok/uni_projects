package aknakereso;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fejlec extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JButton gomb = new JButton("Ujrakezdes");
	private int ido;
	private boolean idoe;
	private JFrame jatek;
	private Palyabeallitasok beallitasok;
	private JLabel ido_szaml;
	private JLabel bombaszam_szaml;
	private Jatek j;
	
	private Timer idozito;
	
	public Fejlec(Jatek ja, Palyabeallitasok pb, JFrame jateka, String merete, boolean ido_e){
		if(ido_e == true) {
			if(merete.equals("Kicsi")) {
				ido = 300;
			}
			else if(merete.equals("Kozepes")) {
				ido = 600;
			}
			else if(merete.equals("Nagy")) {
				ido = 900;
			}
		}
		else {
			ido = 0;
		}
		setBackground(Color.green);
		j = ja;
		beallitasok = pb;
		jatek = jateka;
		idoe = ido_e;
		megNyom nyom = new megNyom();
		
		gomb.addActionListener(nyom);
		
		idozito = new Timer(1000, this);
		ido_szaml = new JLabel();
		ido_szaml.setText("     Ido: " + Integer.toString(ido));
		
		bombaszam_szaml = new JLabel();
		bombaszam_szaml.setText("     Bombaszam: " + Integer.toString(j.getBomba()));
		
		this.add(bombaszam_szaml);
		this.add(gomb);
		this.add(ido_szaml);
		idozito.start();
	}
	
	public void ujJatek() {
		beallitasok.ujJatek();
		this.jatek.dispose();
	}
	
	public class megNyom implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ujJatek();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(idoe == true) {
			ido_szaml.setText("     Ido: " + Integer.toString(--ido));
			if(ido == 0) {
				idozito.stop();
				j.setVesztett();
			}
		}
		else {
			ido_szaml.setText("     Ido: " + Integer.toString(++ido));
		}
		if(j.getVesztett() == true) {
			idozito.stop();
		}
		if(j.getNyert() == true) {
			idozito.stop();
		}
		bombaszam_szaml.setText("Bombak szama: " + Integer.toString(j.getBomba()) + "     ");
	}
	//Teszt miatt
	public int getIdo(){
		return ido;
	}
}
