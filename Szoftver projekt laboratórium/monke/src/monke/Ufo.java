package monke;

import java.util.ArrayList;
import java.util.Random;

public class Ufo extends Creature{
	private UfoView view;
	private View v;
	
	/**
	*Konstruktor
	*/
	public Ufo(Game game, String name, int x, int y, View view) {
		v = view;
		this.view = new UfoView(x, y, view);
		this.SetName(name);
		game.AddUfo(this);
		this.game = game;
	}
	
	/**
	*Visszadja az ufo kirajzolsahoz szukseges interfeszt
	*@return view
	*/
	public Drawable GetView() {
		return view;
	}
	
	/**
	*Az ufo banyaszik.
	*/
	public void Mine() {
		if(asteroid.GetResource() != null && asteroid.GetLayers() == 0) {
			asteroid.SetResource(null);
		}
	}
	
	
	/**
	*Az ufo meghal.
	*/
	@Override
	public void Die() {
		asteroid.Remove(this);
		game.RemoveUfo(this);
		v.RemoveDrawable(this.GetView());
		view.SetTomb();
	}
	
	/**
	 * Az ufo lep.
	 * Ha az aszteroidanak nincs kopenye akkor az ufo kibanyassza a nyersanyagat.
	 * Ha az aszteroidanak van kopenye akkor az ufo lep.
	 */
	public void Step() {
		if(asteroid.GetLayers() == 0 && asteroid.GetResource() != null) {
			Mine();
		}
		else if(asteroid.GetLayers() > 0 || asteroid.GetResource() == null) {
			ArrayList<Travel> neighbors = asteroid.GetNeighbors();
			Random rand = new Random();
			int r = 0;
			if(neighbors.size() > 0)
				r = rand.nextInt(neighbors.size()-1);
				Move(neighbors.get(r));
		}
	}
	
	/**
	 * Az ufo aszteroidaja felrobban
	 */
	@Override
	public void Explode() {
		Die();
	}
}
