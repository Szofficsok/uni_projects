package monke;

public class Uranium extends Resource{
	
	private int exposedCount = 0;
	
	/**
	 * Napkozelseg eseten az uran miatt felrobban az aszteroida.
	 * Meghivodik az aszteroidara a felrobban fuggveny.
	 */
	public Uranium(int c) {
		exposedCount = c;
	}
	
	public int GetExposedCount() {
		return exposedCount;
	}
	/**
	 * Ha eleri a szamlalo a 3-at, akkor felrobban
	 */
	@Override
	public void CloseToSun(Asteroid a) {
		exposedCount++;
		if(exposedCount == 3) {
			a.Explode();
		}
	}
	/**
	 * Visszaadja az uranium stringjet
	 */
	String GetName() {
		return "uranium";
	}
}
