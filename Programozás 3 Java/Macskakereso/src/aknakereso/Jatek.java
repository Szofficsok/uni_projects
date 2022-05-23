package aknakereso;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Jatek extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private int sorokszama;
	private int oszlopokszama;
	private ArrayList<ArrayList<Mezo>> mezok;
	private int bombasz;
	private int bomba;
	private int bomb;
	private int elsokoord;
	private int masodikkoord;
	private boolean ido_e;
	private String neve;
	private boolean vesztett = false;
	private boolean nyert = false;
	private int jido;
	private Timer jidozito;
	private String meret;
	
	public Jatek(String nev, int ssz, int osz, int bombaszam, boolean ido, String merete) {
		sorokszama = ssz;
		oszlopokszama = osz;
		neve = nev;
		ido_e = ido;
		meret = merete;
		this.setPreferredSize(new Dimension(sorokszama*40, oszlopokszama*40));
		this.setBackground(Color.black);
		this.setVisible(true);
		this.setFocusable(true);
		egerrelKattint katt = new egerrelKattint();
		this.addMouseListener(katt);
		mezok = new ArrayList <ArrayList<Mezo>>();
		bomba = bombaszam;
		bomb = bombaszam;
		bombasz = bombaszam;
		jidozito = new Timer(1000, this);
		Mezo m;
		for(int i = 0; i < sorokszama; i++) {
			mezok.add(new ArrayList());
			for(int j = 0; j < oszlopokszama; j++) {
				m = new Mezo();
				mezok.get(i).add(m);
			}
		}
		szamElhelyezo();
		jidozito.start();
	}
	public void szamElhelyezo() {
		//HA mezõ = 10 AKKOR BOMBA
		while(bomb > 0) {
			Random rand = new Random();
			elsokoord = rand.nextInt(sorokszama);
			masodikkoord = rand.nextInt(oszlopokszama);
			if(!mezok.get(elsokoord).get(masodikkoord).bomba_e()) {
				mezok.get(elsokoord).get(masodikkoord).setErtek(10);
				bomb--;
			}
		}
		for(int i = 0; i < sorokszama; i++) {
			for(int j = 0; j < oszlopokszama; j++) {
				if(mezok.get(i).get(j).bomba_e()) {
					if(i != 0 && j != 0 && i != sorokszama-1 && j != oszlopokszama-1) {
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i+1).get(j+1).bomba_e()) mezok.get(i+1).get(j+1).Novel();
						if(!mezok.get(i+1).get(j-1).bomba_e()) mezok.get(i+1).get(j-1).Novel();
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
						if(!mezok.get(i-1).get(j+1).bomba_e()) mezok.get(i-1).get(j+1).Novel();
						if(!mezok.get(i-1).get(j-1).bomba_e()) mezok.get(i-1).get(j-1).Novel();
					}
					else if(i == 0 && j == 0) {
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i+1).get(j+1).bomba_e()) mezok.get(i+1).get(j+1).Novel();
					}
					else if(i == sorokszama-1 && j == oszlopokszama-1) {
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
						if(!mezok.get(i-1).get(j-1).bomba_e()) mezok.get(i-1).get(j-1).Novel();					
					}
					else if(i == 0 && j != 0 && j != oszlopokszama-1) {
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i+1).get(j+1).bomba_e()) mezok.get(i+1).get(j+1).Novel();
						if(!mezok.get(i+1).get(j-1).bomba_e()) mezok.get(i+1).get(j-1).Novel();
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
					}
					else if(i != 0 && j == 0 && i != sorokszama-1) {
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i+1).get(j+1).bomba_e()) mezok.get(i+1).get(j+1).Novel();
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
						if(!mezok.get(i-1).get(j+1).bomba_e()) mezok.get(i-1).get(j+1).Novel();				
					}
					else if(i == sorokszama-1 && j != oszlopokszama-1 && j != 0) {
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
						if(!mezok.get(i-1).get(j+1).bomba_e()) mezok.get(i-1).get(j+1).Novel();
						if(!mezok.get(i-1).get(j-1).bomba_e()) mezok.get(i-1).get(j-1).Novel();					
					}
					else if(i != sorokszama-1 && j == oszlopokszama-1 && i != 0) {
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i+1).get(j-1).bomba_e()) mezok.get(i+1).get(j-1).Novel();
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
						if(!mezok.get(i-1).get(j-1).bomba_e()) mezok.get(i-1).get(j-1).Novel();					
					}
					else if(i == sorokszama-1 && j == 0) {
						if(!mezok.get(i-1).get(j+1).bomba_e()) mezok.get(i-1).get(j+1).Novel();
						if(!mezok.get(i).get(j+1).bomba_e()) mezok.get(i).get(j+1).Novel();
						if(!mezok.get(i-1).get(j).bomba_e()) mezok.get(i-1).get(j).Novel();
					}
					else if(i == 0 && j == oszlopokszama-1) {
						if(!mezok.get(i+1).get(j-1).bomba_e()) mezok.get(i+1).get(j-1).Novel();
						if(!mezok.get(i+1).get(j).bomba_e()) mezok.get(i+1).get(j).Novel();
						if(!mezok.get(i).get(j-1).bomba_e()) mezok.get(i).get(j-1).Novel();
					}
				}
			}
		}
	}
	public int getIdo(){
		if(ido_e == true) {
			if(meret.equals("Kicsi")) {
				return 300;
			}
			else if(meret.equals("Kozepes")) {
				return 600;
			}
			else if(meret.equals("Nagy")) {
				return 900;
			}
		}
		return 0;
	}
	public int getBomba(){
		return bomba;
	}
	public void setVesztett() {
		vesztett = true;
	}
	public boolean getVesztett() {
		return vesztett;
	}
	public boolean getNyert() {
		return nyert;
	}
	public void jatekosNyert(Graphics g){
		jidozito.stop();
		Image kepzaszlo = new ImageIcon("kepzaszlo.png").getImage();
		String uzenet = "Nyertel!";
		Font alap = g.getFont();
		Font vege = new Font("Helvetica", Font.BOLD|Font.ITALIC, 60);
		FontMetrics m = getFontMetrics(vege);
		for(int i = 0; i < sorokszama; i++) {
			for(int j = 0; j < oszlopokszama; j++) {
				if(mezok.get(i).get(j).bomba_e()) {
					g.fillRect(i*40, j*40, 38, 38);
					g.drawImage(kepzaszlo, i*40, j*40, 38, 38, Color.white, null);
				}
				else if(mezok.get(i).get(j).getErtek() == 0) {
					g.setColor(Color.gray);
					g.fillRect(i*40, j*40, 38, 38);
				}
				else {
					g.setColor(Color.magenta);
					g.fillRect(i*40, j*40, 38, 38);
					g.setColor(Color.black);
					g.drawString(Integer.toString(mezok.get(i).get(j).getErtek()), i*40+18, j*40+22);
				}
			}
		}
		g.setColor(Color.green);
		g.setFont(vege);
		g.drawString(uzenet, (sorokszama*40 - m.stringWidth(uzenet))/2, oszlopokszama*40/2);
		g.setFont(alap);
		int vegido;
		if(ido_e == true) {
			vegido = 1-jido;
		}
		else {
			vegido = jido+1;
		}
		Toplista t = new Toplista();
		t.fajlbolOlvas();
		Helyezett h = new Helyezett(neve, vegido);
		t.getNyertesek().add(h);
		t.fajlbaIr();
	}
	public void jatekosVeszitett(Graphics g) {
		Image kepmacska = new ImageIcon("kepmacska.png").getImage();
		String uzenet = "Vesztettel!";
		Font alap = g.getFont();
		Font vege = new Font("Helvetica", Font.BOLD|Font.ITALIC, 60);
		FontMetrics m = getFontMetrics(vege);
		for(int i = 0; i < sorokszama; i++) {
			for(int j = 0; j < oszlopokszama; j++) {
				if(mezok.get(i).get(j).bomba_e()) {
					g.fillRect(i*40, j*40, 38, 38);
					g.drawImage(kepmacska, i*40, j*40, 38, 38, Color.red, null);
			        repaint();	
				}
				else if(mezok.get(i).get(j).getErtek() == 0) {
					g.setColor(Color.gray);
					g.fillRect(i*40, j*40, 38, 38);
				}
				else {
					g.setColor(Color.magenta);
					g.fillRect(i*40, j*40, 38, 38);
					g.setColor(Color.black);
					g.drawString(Integer.toString(mezok.get(i).get(j).getErtek()), i*40+18, j*40+22);
				}
			}
		}
		g.setColor(Color.green);
		g.setFont(vege);
		g.drawString(uzenet, (sorokszama*40 - m.stringWidth(uzenet))/2, oszlopokszama*40/2);
		g.setFont(alap);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(vesztett == true) {
			jatekosVeszitett(g);
			return;
		}
		if(nyert == true) {
			jatekosNyert(g);
			return;
		}
		Image kepzaszlo = new ImageIcon("kepzaszlo.png").getImage();
        for(int i = 0; i < sorokszama; i++) {
			for(int j = 0; j < oszlopokszama; j++) {
				if(mezok.get(i).get(j).getFelsoreteg_e()) {
					g.setColor(Color.pink);
					g.fillRect(i*40, j*40, 38, 38);
					if(mezok.get(i).get(j).getJelolve_e()) {
						g.fillRect(i*40, j*40, 38, 38);
						g.drawImage(kepzaszlo, i*40, j*40, 38, 38, Color.cyan, null);
					}
				}
				else if(!mezok.get(i).get(j).getFelsoreteg_e()) {
					if(mezok.get(i).get(j).bomba_e()) {
						vesztett = true;
						jatekosVeszitett(g);
					}
					else if(mezok.get(i).get(j).getErtek() == 0) {
						g.setColor(Color.gray);
						g.fillRect(i*40, j*40, 38, 38);
					}
					else{
						g.setColor(Color.magenta);
						g.fillRect(i*40, j*40, 38, 38);
						g.setColor(Color.black);
						g.drawString(Integer.toString(mezok.get(i).get(j).getErtek()), i*40+18, j*40+22);
					}
				}
			}
		}
	}
	public void felderit(int i, int j){
		if (mezok.get(i).get(j).getFelsoreteg_e() && !mezok.get(i).get(j).getJelolve_e()){
			if(mezok.get(i).get(j).getErtek() == 0) {
				mezok.get(i).get(j).setFelsoreteg_e(false);
				if(i != 0 && j != 0 && i != sorokszama-1 && j != oszlopokszama-1 ) {
					felderit(i, j+1);
					felderit(i, j-1);
					felderit(i+1, j);
					felderit(i+1, j+1);
					felderit(i+1, j-1);
					felderit(i-1, j);
					felderit(i-1, j+1);
					felderit(i-1, j-1);
				}
				else if(i == 0 && j == 0) {
					felderit(i, j+1);
					felderit(i+1, j);
					felderit(i+1, j+1);
				}
				else if(i == sorokszama-1 && j == oszlopokszama-1) {
					felderit(i, j-1);
					felderit(i-1, j);
					felderit(i-1, j-1);				
				}
				else if(i == 0 && j != 0 && j != oszlopokszama-1) {
					felderit(i, j+1);
					felderit(i, j-1);
					felderit(i+1, j);
					felderit(i+1, j+1);
					felderit(i+1, j-1);
				}
				else if(i != 0 && i != sorokszama-1 && j == 0) {
					felderit(i, j+1);
					felderit(i+1, j);
					felderit(i+1, j+1);
					felderit(i-1, j);
					felderit(i-1, j+1);			
				}
				else if(i == sorokszama-1 && j != oszlopokszama-1 && j != 0) {
					felderit(i, j+1);
					felderit(i, j-1);
					felderit(i-1, j);
					felderit(i-1, j+1);
					felderit(i-1, j-1);					
				}
				else if(i != 0 && i != sorokszama-1 && j == oszlopokszama-1) {
					felderit(i, j-1);
					felderit(i+1, j);
					felderit(i+1, j-1);
					felderit(i-1, j);
					felderit(i-1, j-1);					
				}
				else if(i == 0 && j == oszlopokszama-1) {
					felderit(i, j-1);
					felderit(i+1, j-1);
					felderit(i+1, j);				
				}
				else if(i == sorokszama-1 && j == 0) {
					felderit(i-1, j);
					felderit(i, j+1);
					felderit(i-1, j+1);					
				}
			}
			else if(mezok.get(i).get(j).getErtek() != 0 && !mezok.get(i).get(j).bomba_e()){
				mezok.get(i).get(j).setFelsoreteg_e(false);
			}
		}
	}
	public class egerrelKattint extends MouseAdapter{
		@Override
		public void mouseClicked (MouseEvent e) {
			super.mouseClicked(e);
			for(int i = 0; i < sorokszama; i++) {
				for(int j = 0; j < oszlopokszama; j++) {
					if(e.getButton() == MouseEvent.BUTTON1 && e.getX() > i*40 && e.getX() < (i+1)*40 && e.getY() > j*40 && e.getY() < (j+1)*40 && !mezok.get(i).get(j).getJelolve_e()) {
						if(mezok.get(i).get(j).getErtek() == 0) {
							felderit(i,j);
						}
						mezok.get(i).get(j).setFelsoreteg_e(false);
					}
					else if(e.getButton() == MouseEvent.BUTTON3 && e.getX() > i*40 && e.getX() < (i+1)*40 && e.getY() > j*40 && e.getY() < (j+1)*40 && mezok.get(i).get(j).getFelsoreteg_e()) {
						if(!mezok.get(i).get(j).getJelolve_e()) {
							mezok.get(i).get(j).setJelolve_e(true);
							bomba--;
						} 
						else if(mezok.get(i).get(j).getJelolve_e()) {
							mezok.get(i).get(j).setJelolve_e(false);
							bomba++;
						}
					}
				}
			}
			int b = 0;
			for(int i = 0; i < sorokszama; i++) {
				for(int j = 0; j < oszlopokszama; j++) {
					if(mezok.get(i).get(j).bomba_e()  && mezok.get(i).get(j).getJelolve_e()) {
						b++;
					}
					else if(!mezok.get(i).get(j).bomba_e()  && mezok.get(i).get(j).getJelolve_e()) {
						b--;
					}
				}
			}
			if(b == bombasz) {
				nyert = true;
			}
			repaint();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(ido_e == true) {
			jido--;
		}
		else {
			jido++;
		}
		if(nyert == true) {
			jidozito.stop();
		}
	}
	//Teszt miatt
	public String getNev() {
		return neve;
	}
}
