package monke;

public interface Drawable {
	/**
	 * Kirajzolja az adott elemet.
	 */
	public void Draw();
	
	/**
	 * Beallitja az adott elem kordinatait.
	 * @param a
	 */
	void SetCoord(Asteroid a);
	
	/**
	 * Beallitja a tombot.
	 */
	public void SetTomb();
}
