package aknakereso;

import java.awt.GridLayout;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Toplista extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JPanel[] paneltomb;
	private JLabel rekord;
	private JLabel szoveg;
	private ArrayList <Helyezett> nyertesek = new ArrayList<>();
	
	public Toplista(){
		super("Toplista");
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(400, 1000);
		this.setResizable(true);

		fajlbolOlvas();
		int meddig;
		if(nyertesek == null) {
			meddig = 0;
		}
		else if(50 > nyertesek.size()) {
			meddig = nyertesek.size();
		}
		else {
			meddig = 50;
		}
		this.setLayout(new GridLayout(meddig+1,1));
		if(meddig == 0) {
			 szoveg = new JLabel ("Nincs meg helyezet");
		}
		else {
			szoveg = new JLabel ("Elso " + meddig + " helyezet");
		}
		paneltomb = new JPanel[meddig+1];
		paneltomb[0] = new JPanel();
		paneltomb[0].add(szoveg);
		this.add(paneltomb[0]);
		Osszehasonlito oh = new Osszehasonlito();
		nyertesek.sort(oh);
		for(int i = 1; i < meddig+1; i++) {
			paneltomb[i] = new JPanel();
			rekord = new JLabel (i + ".   " + nyertesek.get(i-1));
			paneltomb[i].add(rekord);
			this.add(paneltomb[i]);		
		}
		pack();
		setLocationRelativeTo(null);
	}
	public ArrayList <Helyezett> getNyertesek(){
		return nyertesek;
	}
	public void fajlbolOlvas(){
		try {
			FileInputStream fajl = new FileInputStream("toplista.ser");
			ObjectInputStream be = new ObjectInputStream(fajl);
			nyertesek = (ArrayList<Helyezett>)be.readObject();
			be.close();
		}
		catch(Exception hiba) {
			hiba.getMessage();
		}
    }
	public void fajlbaIr(){
		try {
			FileOutputStream fajl = new FileOutputStream("toplista.ser");
			ObjectOutputStream ki = new ObjectOutputStream(fajl);
			ki.writeObject(nyertesek);
			ki.close();
		}
		catch(Exception hiba) {
			hiba.getMessage();
		}
	}
}


