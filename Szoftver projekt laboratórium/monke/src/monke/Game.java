package monke;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Game {
	private Sun sun;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Settler> settlers;
	private ArrayList<Ufo> ufos;
	private ArrayList<Robot> robots;
	private ArrayList<Teleport> teleports;
	private ArrayList<String> players;
	private String playerimg;
	private View view;
	private Settler onTurn;
	private int whichPlayer;
	private int robotCount = 1;
	private boolean inGame;
	/**
	 * Konstruktor.
	 */
	public Game(String img, ArrayList<String> players){
		inGame = true;
		whichPlayer = 0;
		sun = new Sun();
		asteroids = new ArrayList<>();
		settlers = new ArrayList<>();
		ufos = new ArrayList<>();
		robots = new ArrayList<>();
		teleports = new ArrayList<>();
		this.players = players;
		playerimg = img;
		view = new View(this);
		Init();
	}
	
	/**
	 * Inicializalja a jatekot.
	 */
	public void Init(){
		int asteroidCount = players.size() * 6;
		double inc = (double)(240 / (double)((double)players.size() * 1.5 * 0.2));
		if(inc < 60) inc = 60;
		double incy = 190;
		Random rand = new Random();
		int x=0;
		int y=40;
		for(int i=1;i<=asteroidCount;i++) {
			x += inc ;
			if(x > 1850) {
				x-=1850;
				y += incy;
			}
			int r = rand.nextInt(5);
			if(r == 0) {
				Asteroid temp = new Asteroid(this, i, new Iron(), x, y, view);
				asteroids.add(temp);
				view.AddDrawable(temp.GetView());
			}
				
			else if(r == 1) {
				Asteroid temp = new Asteroid(this, i, new Carbon(), x, y, view);
				asteroids.add(temp);
				view.AddDrawable(temp.GetView());
			}
				
			else if(r == 2) {
				Asteroid temp = new Asteroid(this, i, new Uranium(0), x, y, view);
				asteroids.add(temp);
				view.AddDrawable(temp.GetView());
			}
				
			else if(r == 3) {
				Asteroid temp = new Asteroid(this, i, new Waterice(), x, y, view);
				asteroids.add(temp);
				view.AddDrawable(temp.GetView());
			}
				
			else if(r == 4) {
				Asteroid temp = new Asteroid(this, i, null, x, y, view);
				asteroids.add(temp);
				view.AddDrawable(temp.GetView());
			}
			view.DrawAll();	
		}
		
		sun.Init(asteroids);
		boolean b;
		while(true) {
			b = true;
			for(int i=0;i<asteroidCount;i++) {
				Asteroid a = asteroids.get(i);
				if(a.GetNeighbors().size() < 3) {
					b = false;
				}
			}
			if(!b) {
				for(int i=0;i<asteroidCount;i++) {
					Asteroid a = asteroids.get(i);
					Asteroid a2 = asteroids.get(rand.nextInt(asteroidCount));
					if(a != a2 && !a.CheckNeighbor(a2)) {
						a.AddNewNeighbor(a2);
						a2.AddNewNeighbor(a);
					}
				}
			}
			else {
				break;
			}
		}
		
		int x0 = asteroids.get(0).GetView().GetX();
		int y0 = asteroids.get(0).GetView().GetY();
		
		int x1 = asteroids.get(asteroidCount-1).GetView().GetX();
		int y1 = asteroids.get(asteroidCount-1).GetView().GetY();
		
		for(int i=0;i<players.size();i++) {
			Settler temps = new Settler(this, players.get(i), x0, y0, view, playerimg);
			view.AddDrawable(temps.GetView());
			
			int j = i + 1;
			String ufoname = "Ufo-" + j;
			Ufo tempu = new Ufo(this, ufoname, x1, y1, view);
			view.AddDrawable(tempu.GetView());
		}
		onTurn = settlers.get(0);
		whichPlayer = 0;
		
		for(int i=0;i<settlers.size();i++) {
			asteroids.get(0).Accept(settlers.get(i));
			asteroids.get(asteroidCount-1).Accept(ufos.get(i));
		}
		

		for(Asteroid a : asteroids) {
			int r = rand.nextInt(4);
			if(r == 0)
				a.SetWeather("hot");
		}
		view.SetPlayer(settlers.get(0));
		view.DrawAll();	
	}
	/**
	 * Visszadja, hogy jatekban vagyunk-e
	 * @return inGame
	 */
	public boolean GetInGame() {
		return inGame;
	}
	
	/**
	 * Visszadja a robotok szamat.
	 * @return robotCount
	 */
	public int GetRobotCount() {
		return robotCount;
	}
	
	/**
	 * Eggyel egnöveli a robotok szamat.
	 */
	public void AddRobotCount() {
		robotCount++;
	}
	
	/**
	 * Következõ jatekost allitja be.
	 */
	public void NextPlayer() { 
		if(settlers.size() > 0) {
			if(whichPlayer >= settlers.size()-1) {
				whichPlayer = -1;
				onTurn = settlers.get(++whichPlayer);
				view.GetGUI().SetPlayer(onTurn);
				Step();
			}
			else {
				onTurn = settlers.get(++whichPlayer);
				view.GetGUI().SetPlayer(onTurn);
			}
		}
	}
	
	/**
	 * Visszadja, hogy a telepes van-e soron.
	 * @return onTurn
	 */
	public Settler GetOnTurn() {
		return onTurn;
	}
	
	/**
	 * Visszadja az ufok listajat.
	 * @return ufos
	 */
	public ArrayList<Ufo> GetUfos() {
		return ufos;
	}
	
	/**
	 * Visszadja a Napot.
	 * @return sun
	 */
	public Sun GetSun() {
		return sun;
	}
	
	/**
	 * Hozzad egy aszteroidat a jatekhoz.
	 * @param a
	 */
	public void AddAsteroid(Asteroid a) {
		asteroids.add(a);
	}
	
	/**
	 * Meghivja a napvihart.
	 */
	public void SunStorm() {
		sun.SunStorm();
	}
	
	/**
	 * Lepteti az osszes leptetheto dolgot.
	 */
	public void Step() {
		view.GetGUI().SetPlayer(onTurn);
		if(robots.size() > 0)
			for(Robot r : robots)
				r.Step();
		if(ufos.size() > 0)
			for(Ufo u : ufos)
				u.Step();
		if(teleports.size() > 0)
			for(Teleport t : teleports)
				t.Step();
		boolean stillinside = true;
		int j = 0;
		int p;
		while(stillinside) {
			p = j;
			for(int i = p; i < asteroids.size(); i++) {
				if(i == asteroids.size()-1) {
					stillinside=false;
				}
					int temp = asteroids.size();
					asteroids.get(i).Step();
					if(temp != asteroids.size()) {
						break;
					}
				
				j++;
			}
		}
		sun.Step();
	}
	
	/**
	 * Hozzaad a jatekhoz egy teleportot.
	 * @param t
	 */
	public void AddTeleport(Teleport t) {
		teleports.add(t);
	}
	
	/**
	 * Hozzaad a jatekhoz egy robotot.
	 * @param r
	 */
	public void AddRobot(Robot r) {
		robots.add(r);
	}
	
	/**
	 * Hozzaad a jatekhoz egy ufot.
	 * @param u
	 */
	public void AddUfo(Ufo u) {
		ufos.add(u);
	}
	
	/**
	 * Hozzaad a jatekhoz egy settlert.
	 * @param s
	 */
	public void AddSettler(Settler s) {
		settlers.add(s);
	}
	
	/**
	 * Visszaadja a jatek settlereinek listajat.
	 * @return Jatek settler listaja.
	 */
	public ArrayList<Settler> GetSettlers() {
		return this.settlers;
	}
	
	/**
	 * Visszaadja a jatek aszteroidainak listajat.
	 * @return Jatek aszteroida listaja.
	 */
	public ArrayList<Asteroid> GetAsteroid() {
		return this.asteroids;
	}
	
	/**
	 * Eltavolitja a kapott aszteroidat a jatekbol.
	 * @param a
	 */
	public void RemoveAsteroid(Asteroid a) {
		asteroids.remove(a);
	}
	
	/**
	 * Eltavolitja a kapott teleportot a jatekbol.
	 * @param t
	 */
	public void RemoveTeleport(Teleport t) {
		teleports.remove(t);
	}
	
	/**
	 * Eltavolitja a kapott settler-t a jatekbol.
	 * @param s
	 */
	public void RemoveSettler(Settler s) {
		settlers.remove(s);
	}
	
	/**
	 * Eltavolitja a kapott robotot a jatekbol.
	 * @param r
	 */
	public void RemoveRobot(Robot r) {
		robots.remove(r);
	}
	
	/**
	 * Eltavolitja a kapott ufot a jatekbol.
	 * @param u
	 */
	public void RemoveUfo(Ufo u) {
		ufos.remove(u);
	}
	
	/**
	 * Ellenorzi, hogy el-e meg settler.
	 * @return Ha nem el tobb settler, akkor false-t ad, ha meg el valaki akkor true.
	 */
	public boolean CheckSettlerLifeLines() {
		if(settlers.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Ellenorzi, hogy nyertunk-e.
	 */
	public boolean CheckWin() {
		if(asteroids.size() > 0) {
			for(Asteroid a : asteroids) {
				if(a.CheckWin()) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Jatek befejezese.
	 * Meghivodott ez a fuggveny es meg elnek telepesek, igy nyertek.
	 * Meghivodott ez a fuggveny es nem elnek mar telepesek, igy vesztettek.
	 */
	public void EndGame() {
		inGame = false;
		if(CheckSettlerLifeLines() == true) {
			view.Win();
		}
		else {
			view.Lose();
		}
	}
}
