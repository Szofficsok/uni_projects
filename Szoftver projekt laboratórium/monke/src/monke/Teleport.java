package monke;

import java.util.ArrayList;
import java.util.Random;

public class Teleport implements Travel{
	private Game game;
	private int id;
	private Asteroid asteroid;
	private Teleport pair;
	private boolean isMoving;
	private TeleportView view;
	
	/**
	 * Konstruktor.
	 * @param GAme
	 * @param id
	 * @param x
	 * @param y
	 * @param view
	 */
	public Teleport(Game game, int id, int x, int y, View view) {
		this.view = new TeleportView(x, y, view);
		this.game = game;
		this.id = id;
		asteroid = null;
		pair = null;
		isMoving = false;
	}
	
	/**
	 * Visszadja a nezete.
	 * @return view
	 */
	public TeleportView GetView() {
		return view;
	}
	
	/**
	 * Konstruktor.
	 * @param id
	 * @param p
	 * @param p
	 */
	public Teleport(Game game, int id, Teleport p){
		this.id = id;
		asteroid = null;
		pair = p;
		isMoving = false;
	}
	
	/**
	 * Visszaadja a teleport aszteroidajat.
	 * @return A teleport aszteroidaja.
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	
	/**
	 * Beallitja a teleport aszteroidajat a megkapottra.
	 * @param a
	 */
	public void SetAsteroid(Asteroid a) {
		asteroid = a;
	}
	
	/**
	 * Visszaadja a teleport teleportparjat.
	 * @return A teleport parja.
	 */
	public Teleport GetPair() {
		return pair;
	}
	
	/**
	 * Beallitja a teleport teleportparjat a megkapottra.
	 * @param t
	 */
	public void SetPair(Teleport t) {
		pair = t;
	}
	
	/**
	 * Atkuldi az entitast a kapun es a maik kapu aszteroidajara rakja.
	 */
	public void Accept(Creature c) {
		if(pair.GetAsteroid() != null) {
			pair.GetAsteroid().Accept(c);
		}
	}
	 
	/**
	 * A teleport mozog.
	 * @param t
	 */
	public void Move(Travel t) {
		if(asteroid != null)
			asteroid.RemoveNeighbor(t);
		t.AcceptTeleport(this);
	}
	 
	/**
	 * A teleport steppel.
	 * Ha a teleport mozog, akkor atlep egy random szomszedra.
	 */
	public void Step() {
		if(isMoving) {
			Random rand = new Random();
			ArrayList<Travel> neighbors = asteroid.GetNeighbors();
			Travel t = neighbors.get(rand.nextInt(neighbors.size()));
			while(t.GetOtherAsteroid() == this.GetOtherAsteroid())
				t = neighbors.get(rand.nextInt(neighbors.size()));
			Move(t);
		}
	}
	
	/**
	 * Teleportkapu elfogadása.
	 */
	@Override
	public void AcceptTeleport(Teleport t) {
		if(pair.GetAsteroid() != null) {
			pair.GetAsteroid().AcceptTeleport(t);
		}
	}
	
	/**
	 * Az isMoving booleant allitja.
	 */
	@Override
	public void SetIsMoving() {
		isMoving = true;
	}
	
	/**
	 * Szomszed eltavolitasa.
	 */
	@Override
	public void RemoveNeighbor(Travel t) {
		pair.SetPair(null);
		game.RemoveTeleport(this);
	}
	
	/**
	 * Visszadja a homersekletet.
	 */
	@Override
	public String GetWeather() {
		return null;
	}
	
	/**
	 * Visszadja az aszteroidat amivel ossze van kotve.
	 * @return ha ossze van kotve teleportkapuval akkor azt adja vissza, ha nem akkor nullt
	 */

	@Override
	public Asteroid GetOtherAsteroid() {
		if(pair != null)
			return pair.GetAsteroid();
		return null;
	}
	
	/**
	 * Beallitja a homersekletet.
	 * @param w
	 */
	@Override
	public void SetWeather(String w) {}
	
}
