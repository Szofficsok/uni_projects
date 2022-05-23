package monke;

public class Waterice extends Resource{
	
	/**
	 * Napkozelseg eseten a vizjeg elparolog az aszteroidabol, 
	 * null-ra allitjuk az aszteroida nyersanyagat
	 */
	@Override
	public void CloseToSun(Asteroid a) {
		a.SetResource(null);
	}
	
	String GetName() {
		return "waterice";
	}
}
