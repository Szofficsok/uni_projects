package monke;

public interface Travel {
	
	/**
	 * Creature elfogadasa.
	 * @param c
	 */
	public void Accept(Creature c);
	
	/**
	 * isMoving boolean beallitasa.
	 */
	public void SetIsMoving();
	
	/**
	 * Szomszed eltavolitasa.
	 * @param t
	 */
	public void RemoveNeighbor(Travel t);
	
	/**
	 * Teleport elfogadasa.
	 * @param t
	 */
	public void AcceptTeleport(Teleport t);
	/**
	 * Homersekletet visszaadja.
	 */
	public String GetWeather();
	
	/**
	 * Homerseklet beallitasa.
	 */
	public void SetWeather(String w);
	
	/**
	 * Visszadja a masik aszteroidat, amivel ossze van kotve.
	 */
	public Asteroid GetOtherAsteroid();
}
