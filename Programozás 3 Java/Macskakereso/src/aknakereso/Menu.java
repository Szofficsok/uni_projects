package aknakereso;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JButton gomb_a = new JButton("Jatek!");
	private JButton gomb_b = new JButton("Toplista");
	private JButton gomb_c = new JButton("Hattertortenet");
	
	public Menu() {
		super("Aknakereso");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(350, 150);
		this.setResizable(true);
		setLocationRelativeTo(null);
		
		this.setLayout(new GridLayout(3,1));
		JPanel panel1= new JPanel();
		JPanel panel2= new JPanel();
		JPanel panel3= new JPanel();
		JatekGomb jatek = new JatekGomb();
		ToplistaGomb toplista = new ToplistaGomb();
		FeladatleirasGomb feladatleiras = new FeladatleirasGomb();
		
		JLabel szoveg = new JLabel ("Valassza ki mit szeretne tenni:");
		panel1.add(szoveg);
		panel2.add(gomb_a);
		panel2.add(gomb_b);
		panel3.add(gomb_c);
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		gomb_a.addActionListener(jatek);
		gomb_b.addActionListener(toplista);
		gomb_c.addActionListener(feladatleiras);
	}
	public class JatekGomb implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Palyabeallitasok p = new Palyabeallitasok();
		}
	}
	public class ToplistaGomb implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Toplista t = new Toplista();
			t.setVisible(true);
		}
	}
	public class FeladatleirasGomb implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Feladatleiras f = new Feladatleiras();
			f.setVisible(true);
		}
	}
}