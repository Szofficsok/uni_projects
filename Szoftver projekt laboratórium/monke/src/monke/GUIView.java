package monke;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIView implements Drawable{
	private View view;
	private JButton[] buttons;
	private JLabel[] labels;
	private JLabel[] pictures;
	String thermo;
	private boolean inMove = false;
	private boolean inPlace = false;
	
	/**
	*Konstruktor
	*@param v
	*/
	public GUIView(View v) {
		view = v;
		
		Init();  
	}
	
	/**
	*Inicializalja a kezelofeluletet.
	*/
	public void Init() {
		buttons = new JButton[9];
		buttons[0] = new JButton("Move");
		buttons[1] = new JButton("Drill");
		buttons[2] = new JButton("Mine");
		buttons[3] = new JButton("Replace");
		buttons[4] = new JButton("Build Tpk");
		buttons[5] = new JButton("Skip");
		buttons[6] = new JButton("Give up");
		buttons[7] = new JButton("Build Robot");
		buttons[8] = new JButton("Place tpk");
		
		MoveListener movel = new MoveListener();
		DrillListener drilll = new DrillListener();
		MineListener minel = new MineListener();
		ReplaceListener replacel = new ReplaceListener();
		PlaceTpkListener placetpkl = new PlaceTpkListener();
		BuildTpkListener buildtpkl = new BuildTpkListener();
		BuildRobotListener buildrobotl = new BuildRobotListener();
		SkipListener skipl = new SkipListener();
		GiveUpListener giveupl = new GiveUpListener();
		buttons[0].addActionListener(movel);
		buttons[1].addActionListener(drilll);
		buttons[2].addActionListener(minel);
		buttons[3].addActionListener(replacel);
		buttons[4].addActionListener(buildtpkl);
		buttons[5].addActionListener(skipl);
		buttons[6].addActionListener(giveupl);
		buttons[7].addActionListener(buildrobotl);
		buttons[8].addActionListener(placetpkl);
		
		labels = new JLabel[9];
		labels[0] = new JLabel(": 0");
		labels[1] = new JLabel(": 0");
		labels[2] = new JLabel(": 0");
		labels[3] = new JLabel(": 0");
		labels[4] = new JLabel("No one");
		labels[5] = new JLabel("Asteroid: ");
		labels[6] = new JLabel("Resource: ");
		labels[7] = new JLabel("Other creatures: ");
		labels[8] = new JLabel("Teleports: ");
		
		labels[0].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[1].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[2].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[3].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[4].setFont(new Font("Serif", Font.BOLD, 30));
		labels[5].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[6].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[7].setFont(new Font("Serif", Font.PLAIN, 30));
		labels[8].setFont(new Font("Serif", Font.PLAIN, 30));
		
		labels[0].setForeground(Color.white);
		labels[1].setForeground(Color.white);
		labels[2].setForeground(Color.white);
		labels[3].setForeground(Color.white);
		labels[4].setForeground(Color.white);
		labels[5].setForeground(Color.white);
		labels[6].setForeground(Color.white);
		labels[7].setForeground(Color.white);
		labels[8].setForeground(Color.white);
		
		thermo = "thermometer1.png";
		pictures = new JLabel[5];
		pictures[0] = new JLabel();
		pictures[1] = new JLabel();
		pictures[2] = new JLabel();
		pictures[3] = new JLabel();
		pictures[4] = new JLabel();
        pictures[0].setIcon(new ImageIcon("iron.png"));	
        pictures[1].setIcon(new ImageIcon("coal.png"));	
        pictures[2].setIcon(new ImageIcon("uranium.png"));	
        pictures[3].setIcon(new ImageIcon("waterice.png"));
        pictures[4].setIcon(new ImageIcon(thermo));
        
        MouseListener mousel = new MouseListener();
        MouseListener2 mousel2 = new MouseListener2();
        view.addMouseListener(mousel);
        view.addMouseListener(mousel2);
	}
	
	/**
	*Beallitja a telepes jelenlegi helyzete alapjan a kezelofeluletet.
	*@param s
	*/
	public void SetPlayer(Settler s) {
		labels[4].setText(s.GetName());
		
		Iron ir = new Iron();
		Carbon ca = new Carbon();
		Uranium ur = new Uranium(0);
		Waterice wi = new Waterice();
		int irc = 0;
		int cac = 0;
		int urc = 0;
		int wic = 0;
		for(Resource r : s.GetResources()) {
			if(r.getClass() == ir.getClass()) {
				irc++;
			}
			else if(r.getClass() == ca.getClass()) {
				cac++;
			}
			else if(r.getClass() == ur.getClass()) {
				urc++;
			}
			else if(r.getClass() == wi.getClass()) {
				wic++;
			}
		}
		labels[0].setText(": " + irc);
		labels[1].setText(": " + cac);
		labels[2].setText(": " + urc);
		labels[3].setText(": " + wic);
		
		labels[5].setText("Asteroid: " + s.GetAsteroid().GetId());
		
		String resource = "unknown";
		if(s.GetAsteroid().GetLayers() == 0)
			if(s.GetAsteroid().GetResource() == null)
				resource = "empty";
			else if(s.GetAsteroid().GetResource().getClass() == ir.getClass())
				resource = "iron";
			else if(s.GetAsteroid().GetResource().getClass() == ca.getClass())
				resource = "carbon";
			else if(s.GetAsteroid().GetResource().getClass() == ur.getClass())
				resource = "uranium";
			else if(s.GetAsteroid().GetResource().getClass() == wi.getClass())
				resource = "waterice";
				
			
		String names = "";
		ArrayList<Creature> creatures = s.GetAsteroid().GetCreatures();
		for(Creature c : creatures) {
			if(!c.GetName().equals(s.GetName()))
				names = names + "  " + c.GetName();
		}
		labels[7].setText("Other creatures:" + names);
		labels[6].setText("Resource: " + resource);
		labels[8].setText("Teleports: " + s.GetHasTpk());
		
		if(s.GetAsteroid().GetWeather() == "normal") {
			thermo = "thermometer1.png";
		}
		else if(s.GetAsteroid().GetWeather() == "hot") {
			thermo = "thermometer2.png";
		}
		else if(s.GetAsteroid().GetWeather() == "critical") {
			thermo = "thermometer3.png";
		}
		pictures[4].setIcon(new ImageIcon(thermo));
		
	}
	
	/**
	*Kirajzolja a kezelofeluletet.
	*/
	public void Draw() {
		for(int i=0;i<4;i++) {
			pictures[i].setBounds(300+i*120, 700, 100, 50);
			view.add(pictures[i]);
		}
		for(int i=0;i<4;i++) {
			labels[i].setBounds(352+i*120, 700, 100, 50);
			view.add(labels[i]);
		}
		for(int i=0;i<7;i++) {
			buttons[i].setBounds(50+i*120, 780, 100, 50);
			view.add(buttons[i]);
		}
		buttons[4].setBounds(530, 780, 100, 25);
		buttons[7].setBounds(530, 805, 100, 25);
		view.add(buttons[7]);
		buttons[3].setBounds(410, 780, 100, 25);
		buttons[8].setBounds(410, 805, 100, 25);
		view.add(buttons[8]);
		
		labels[4].setBounds(50, 700, 200, 50);
		view.add(labels[4]);
		pictures[4].setBounds(775, 835, 200, 200);
		view.add(pictures[4]);
		int n = 0;
		for(int i=5;i<9;i++) {
			labels[i].setBounds(50, 830+n*50, 700, 50);
			n++;
			view.add(labels[i]);
		}
	}
	
	/**
	*Befesti szurkere az aszteroidakat
	*/
	public void SetGrayAsteroids() {
		Settler s = view.GetGame().GetOnTurn();
		for(Travel t : s.GetAsteroid().GetNeighbors()) {
			if(t.GetOtherAsteroid() != null)
				t.GetOtherAsteroid().GetView().SetColor(0);
		}
		view.DrawAll();
	}
	
	@Override
	public void SetCoord(Asteroid a) {}
	
	/**
	*Move gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class MoveListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inPlace = false;
				Settler s = view.GetGame().GetOnTurn();
				inMove = true;
				for(Travel t : s.GetAsteroid().GetNeighbors()) {
					if(t.GetOtherAsteroid() != null)
						t.GetOtherAsteroid().GetView().SetColor(1);
				}
				view.DrawAll();
			}	
		}
	}
	
	/**
	*Move gomb lenyomasa utan az eger altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(view.GetGame().GetInGame()) {
				boolean moved = false;
				if(inMove) {
					Settler s = view.GetGame().GetOnTurn();
					SetPlayer(s);
					int x = e.getX();
					int y = e.getY();
					
					for(Travel t : s.GetAsteroid().GetNeighbors()) {
						if(t.GetOtherAsteroid() != null) {
							int asx = t.GetOtherAsteroid().GetView().GetX() + 50;
							int asy = t.GetOtherAsteroid().GetView().GetY() + 50;
							if(x <= asx + 40 && x >= asx -40 && y <= asy + 40 && y >= asy - 40) {
							SetGrayAsteroids();
							moved = s.Move(t);
							break;
							}
						}
						
					}
				}
				inMove = false;
				if(!moved) {
					inMove = true;
				}
				else {
					view.GetGame().NextPlayer();
					SetPlayer(view.GetGame().GetOnTurn());
					if(view.GetGame().CheckWin())
						view.GetGame().EndGame();
				}
				view.DrawAll();
			}
		}
	}
	
	/**
	*Drill gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class DrillListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inPlace = false;
				inMove = false;
				SetGrayAsteroids();
				Settler s = view.GetGame().GetOnTurn();
				if(s.Drill())
					view.GetGame().NextPlayer();
				SetPlayer(view.GetGame().GetOnTurn());
				view.DrawAll();
			}
		}
	}
	
	/**
	*Mine gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class MineListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inPlace = false;
				inMove = false;
				SetGrayAsteroids();
				Settler s = view.GetGame().GetOnTurn();
				if(s.Mine()) {
					view.GetGame().NextPlayer();
					if(view.GetGame().CheckWin())
						view.GetGame().EndGame();
				}
				SetPlayer(view.GetGame().GetOnTurn());
				view.DrawAll();
			}
		}
	}
	
	/**
	*Replace gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class ReplaceListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inMove = false;
				inPlace = true;
				SetGrayAsteroids();
			}
		}
	}
	
	/**
	*Replace gomb lenyomasa utan az eger altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class MouseListener2 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(view.GetGame().GetInGame()) {
				boolean placed = false;
				if(inPlace) {
					Settler s = view.GetGame().GetOnTurn();
					SetPlayer(s);
					int x = e.getX();
					int y = e.getY();
					
					if(x > 280 && x < 400 && y > 720 && y < 810) {
						placed = s.PlaceResource(new Iron());
					}
					else if(x > 400 && x < 520 && y > 720 && y < 810) {
						placed = s.PlaceResource(new Carbon());
					}
					else if(x > 520 && x < 640 && y > 720 && y < 810) {
						Uranium u = new Uranium(0);
						for(Resource r : s.GetResources()) {
							if(r.getClass() == u.getClass()) {
								u = (Uranium)r;
								break;
							}
						}
						placed = s.PlaceResource(u);
					}
					else if(x > 640 && x < 760 && y > 720 && y < 810) {
						placed = s.PlaceResource(new Waterice());
					}
				}
				inPlace = false;
				if(!placed) {
					inPlace = true;
				}
				else {
					view.GetGame().NextPlayer();
					SetPlayer(view.GetGame().GetOnTurn());
				}
				view.DrawAll();
			}
		}
	}
	
	/**
	*Build Tpk gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class BuildTpkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				SetGrayAsteroids();
				inMove = false;
				inPlace = true;
				Settler s = view.GetGame().GetOnTurn();
				SetPlayer(s);
				boolean built = s.BuildTeleport();
				
				if(built) {
					view.GetGame().NextPlayer();
					SetPlayer(view.GetGame().GetOnTurn());
					view.DrawAll();
				}	
			}
		}
	}
	
	/**
	*Place Tpk gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class PlaceTpkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				SetGrayAsteroids();
				inMove = false;
				inPlace = true;
				Settler s = view.GetGame().GetOnTurn();
				SetPlayer(s);
				
				if(s.GetHasTpk() > 0) {
					s.PlaceTeleport(s.GetAsteroid());
					view.GetGame().NextPlayer();
					SetPlayer(view.GetGame().GetOnTurn());
					view.DrawAll();
				}
			}
		}
	}
	
	/**
	*Build Robot gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class BuildRobotListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				SetGrayAsteroids();
				inMove = false;
				inPlace = true;
				Settler s = view.GetGame().GetOnTurn();
				SetPlayer(s);
				boolean built = s.BuildRobot("Robot-" + view.GetGame().GetRobotCount());
				
				if(built) {
					view.GetGame().AddRobotCount();
					view.GetGame().NextPlayer();
					SetPlayer(view.GetGame().GetOnTurn());
					view.DrawAll();
				}	
			}
		}
	}
	
	/**
	*Skip gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class SkipListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inMove = false;
				inPlace = true;
				SetGrayAsteroids();
				view.GetGame().NextPlayer();
				SetPlayer(view.GetGame().GetOnTurn());
				view.DrawAll();
			}
		}
	}
	
	/**
	*Give Up gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class GiveUpListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(view.GetGame().GetInGame()) {
				inMove = false;
				inPlace = true;
				SetGrayAsteroids();
				Settler s = view.GetGame().GetOnTurn();
				s.Die();
				view.GetGame().NextPlayer();
				SetPlayer(view.GetGame().GetOnTurn());
				view.DrawAll();
			}
		}
	}
	
	@Override
	public void SetTomb() {}
}
