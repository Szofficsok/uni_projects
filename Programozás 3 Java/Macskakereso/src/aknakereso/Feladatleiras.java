package aknakereso;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Feladatleiras extends JFrame{
	private static final long serialVersionUID = 1L;

	Feladatleiras(){
		super("Feladat tortenete");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(1350, 100);
		this.setResizable(true);
		
		this.setLayout(new BorderLayout());
		dispose();
		JLabel szov1 = new JLabel ("A tortenetunk egy uj kiseger erkezesevel kezdodik egy kis varoskaba, ahol a piciny lakosnak fel kell deritenie, merre nincsenek vermes cicak. Keszitett egy alagut rendszert, amivel minden hazat el tud erni kozvetlenul.");
		JLabel szov2 = new JLabel ("Minden haznal van egy ott lakó kutya, aki elmondja hogy van-e a hazban cica, illetve jo szaglasa miatt azt is, hogy a kornyezo hazakban van-e. Ha a kiseger veletlen egy olyan hazban bukkan fel, ahol van cica,");
		JLabel szov3 = new JLabel ("akkor sajnos a kuldetese nem sikerult, es az orok sajtok mezejen szaladgalhat tovabb. A feladat tehat a szomszedsag teljes felderitese, hogy fohosunk nyugodtan tudjon hazrol hazra jarni es eldegelni. Sok sikert es jo szorakozast!");
		this.add(szov1,BorderLayout.PAGE_START);
		this.add(szov2,BorderLayout.LINE_START);
		this.add(szov3,BorderLayout.PAGE_END);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
