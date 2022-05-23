package monke;

import java.util.ArrayList;
import java.util.Random;

public class Robot extends Creature{
	private RobotView view;
	private View v;
	
	private Random rand = new Random();
	/**
	 * Konstruktor.
	 * @param game
	 * @param a
	 * @param name
	 * @param x
	 * @param gamy
	 * @param view
	 */
	public Robot(Game game, Asteroid a, String name, int x, int y, View view) {
		v = view;
		this.view = new RobotView(x, y, view);
		SetName(name);
		SetAsteroid(a);
		a.Accept(this);
		game.AddRobot(this);
		this.game = game;
	}
	
	/**
	*Visszadja a kirajzolashoz szukseges interfeszt.
	*/
	public Drawable GetView() {
		return view;
	}
	
	/**
	*A robot fur.
	*/
	public void Drill() {
		asteroid.ReduceLayers();
	}
	
	/**
	*A robot lep.
	*Ha az aszteroidanak nincs kopenye akkor a robot atlep egy masik aszteroidara.
	*Ha az aszteroidanak van kopenye akkor a robot fur.
	*/
	public void Step() { 
		if(asteroid.GetLayers() > 0) {
			Drill();
		}
		else if(GetAsteroid().GetLayers() == 0) { 
			ArrayList<Travel> neighbors = asteroid.GetNeighbors();
			Random rand = new Random();
			int r = 0;
			if(neighbors.size() > 0) {
				r = rand.nextInt(neighbors.size()-1);
				Move(neighbors.get(r));
			}
		}
	}
	
	/**
	*A robot meghal.
	*/
	@Override
	public void Die() {
		asteroid.Remove(this);
		game.RemoveRobot(this);
		v.RemoveDrawable(this.GetView());
		view.SetTomb();
	}
	
	/**
	*A robot aszteroidaja felrobban.
	*Ha az aszteroidanak nincs szomszedja, a robot meghal.
	*Ha az aszteroidanak van szomszedja, a robot atlep az egyikre.
	*/
	@Override
	public void Explode() { 
		ArrayList<Travel> neighbors = asteroid.GetNeighbors();
		if(neighbors.size() == 0) {
			Die();
		}
		else {
			Move(neighbors.get(rand.nextInt(neighbors.size()))); 
		}
	}
}
