package monke;

import java.util.ArrayList;
import java.util.Random;

public class Sun {
	private ArrayList<Asteroid> asteroids;
	private boolean sunStormNextRound;
	
	/**
	 * Konstruktor.
	 * @param asteroids
	 */
	public Sun() {}
	
	public void Init(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
		this.sunStormNextRound = false;
	}
	
	public void AddAsteroid(Asteroid a) {
		asteroids.add(a);
	}
	
	/**
	 * Visszaallitja az eddigit normal-ra.
	 * Beallitja az uj napkozeli aszteroidat hot-ra.
	 */
	public void GetNewAsteroids() {
		for(Asteroid a : asteroids) {
			SetNormal(a);
		}
		Random rand = new Random();
		int r;
		for(Asteroid a : asteroids) {
			r = rand.nextInt(4);
			if(r == 0)
				SetHot(a);
		}
	}
	
	/**
	 * Minden napkozeli aszteroidara meghivja a napvihart.
	 */
	public void SunStorm() {
		for(Asteroid a : asteroids) {
			if(a.GetWeather().equals("critical"))
			{
				a.SunStorm();
				SetHot(a);
			}
		}
	}
	
	/**
	 * Beallitja a homersekletet critical-ra.
	 * @param a
	 */
	public void SetCritical(Asteroid a) {
		a.SetWeather("critical");
	}
	
	/**
	 * Beallitja a homersekletet normal-ra.
	 * @param a
	 */
	public void SetNormal(Asteroid a) {
		a.SetWeather("normal");
	}
	
	/**
	 * Beallitja a homersekletet hot-ra.
	 * @param a
	 */
	public void SetHot(Asteroid a) {
		a.SetWeather("hot");
	}
	
	/**
	 * Lep a Nap.
	 * Ha napvihar van, akkor mindegyik aszteroidajara meghivja a napvihart.
	 * Visszaallitja hot-ra a napkozeli aszteroidakat.
	 * Veletlenszeruen igazra allitja a vihar ekorejelzot.
	 * Beallitja critical-ra a napkozeli aszteroidakat.
	 * Frissiti a napkozeli aszteroidakat.
	 */
	public void Step() {
		if(sunStormNextRound == true) {
			SunStorm();
			sunStormNextRound = false;
		}
		else {
			Random rand = new Random();
			int sz = rand.nextInt(100);
			if(sz < 10) {
				sunStormNextRound = true;
				for(Asteroid a : asteroids) {
					if(a.GetWeather().equals("hot"))
					{
						SetCritical(a);
					}
				}
			}
			else {
				GetNewAsteroids();
			}
		}
	}
}
