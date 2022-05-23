package monke;

import java.util.ArrayList;
import java.util.Scanner;

abstract class Creature {
	protected Game game;
	protected Asteroid asteroid;
	private String name;
	
	/**
	 * A creature mozog.
	 * Ha egy szomszedra akar lepni, akkor atlep ra.
	 * Ha nem szomszedra akar lepni, akkor nem lep.
	 * @param t
	 */
	public boolean Move(Travel t) {
		boolean tf;
		tf = asteroid.CheckNeighbor(t); 
		if(tf) {
			asteroid.Remove(this); 
			t.Accept(this); 
			this.GetView().SetCoord(t.GetOtherAsteroid());
			return true;
		}
		return false;
	} 
	
	public abstract Drawable GetView();
	
	/**
	 * Visszaadja a creature nevet.
	 * @return A creature neve.
	 */
	public String GetName() {
		return name;
	}
	
	/**
	 * Beallitja a creature nevet.
	 * @param name
	 */
	public void SetName(String name) {
		this.name = name;
	}
	
	/**
	 * Visszaadja a creature aszteroidajat.
	 * @return A creature aszteroidaja.
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	
	/**
	 * Beallitja a creature aszteroidajat.
	 * @param a
	 */
	public void SetAsteroid(Asteroid a) {
		asteroid = a;
	}
	
	/**
	 * Visszaad egy ures nyersanyaglistat.
	 * @return null
	 */
	public ArrayList<Resource> GetResources() {
		return null;
	}
	
	/**
	 * A creature meghal.
	 */
	public void Die() {}
	
	/**
	 * A creature aszteroidaja felrobban.
	 */
	abstract void Explode();
}
