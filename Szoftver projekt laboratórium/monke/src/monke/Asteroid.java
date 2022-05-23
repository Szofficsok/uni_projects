package monke;

import java.util.ArrayList;
import java.util.Random;

public class Asteroid implements Travel{
	private Game game;
	private int id;
	private int layers;
	private boolean closeToSun;
	private String weather;
	private boolean isEmpty;
	private ArrayList<Creature> creatures;
	private Resource resource;
	private ArrayList<Travel> neighbors;
	private static BillOfResources bill;
	private AsteroidView aview;
	private View v;
	/**
	 * Konstruktor.
	 * @param g
	 * @param id
	 * @param r
	 */
	public Asteroid(Game g, int id, Resource r, int x, int y, View view) {
		v = view;
		aview = new AsteroidView(x,y,view);
		game = g;
		this.id = id;
		Random rand = new Random();
		layers = rand.nextInt(3) + 3;
		closeToSun = false;
		int ra = rand.nextInt(4);
		if(ra == 0)
			SetWeather("hot");
		else
			SetWeather("normal");
		if(r == null)
			isEmpty = true;
		else
			isEmpty = false;
		creatures = new ArrayList<Creature>();
		resource = r;
		neighbors = new ArrayList<Travel>();
		bill = new BillOfResources();
	}
	
	/**
	 * Megnezi, hogy van-e osszesen minden nyersanyagbol harom az aszteroidan levo telepeseknel.
	 * Ha a telepesek osszegyujtottek eleg nyersanyagot, meghivja az EndGame()-et.
	 */
	public boolean CheckWin() {
		if(creatures.size() > 0) {
			ArrayList<Resource> resources = new ArrayList<>();
			for(Creature c : creatures) {
				if(c.GetResources() != null) {
					resources.addAll(c.GetResources());
				}
			}
			return bill.CheckResource(resources, "Base");
		}
		return false;
	}
	
	public AsteroidView GetView() {
		return aview;
	}

	public void Step() {
		if(closeToSun && layers == 0 && resource != null) {
			resource.CloseToSun(this);
		}
	}
	/**
	 * Visszaadja az aszteroida id-jet.
	 * @return Visszater az id-vel.
	 */
	public int GetId() {
		return id;
	}
	/**
	 * Visszaadja, hogy ures-e az aszteroida.
	 * @return  Visszater az isEmpty-vel.
	 */
	public boolean GetIsEmpty() {
		return isEmpty;
	}
	/**
	 * Beallitja az isEmpty booleant.
	 * @param b
	 */
	public void SetIsEmpty(boolean b) {
		isEmpty = b;
	}
	/**
	 * Visszaadja, hogy mekkora az aszteroida kopenye.
	 * @return Visszaadja a layers valtozot.
	 */
	public int GetLayers() {
		return layers;
	}
	/**
	 * Beallitja a layers valtozot.
	 * @param l
	 */
	public void SetLayers(int l) {
		layers = l;
	}
	/**
	 * Visszaadja, hogy milyen nyersanyag van az aszteroidaban.
	 * @return Visszaadja a resource-t.
	 */
	public Resource GetResource() {
		return resource;
	}
	/**
	 * Beallitja a a resource-t.
	 * @param r
	 */
	public void SetResource(Resource r) {
		resource = r;
		if(resource == null) {
			isEmpty = true;
		}
	}
	/**
	 * Visszaadja, hogy az aszteroida napkozelben van-e.
	 * @return Visszaadja a closeToSun booleant.
	 */
	public boolean GetCloseToSun() {
		return closeToSun;
	}
	/**
	 * Beallitja a closeToSun booleant.
	 * @param b
	 */
	public void SetCloseToSun(boolean b) {
		closeToSun = b;
	}
	/**
	 * Visszaadja az aszteroidan levo idojarast.
	 * @return Visszaadja a weather-t.
	 */
	public String GetWeather() {
		return weather;
	}
	/**
	 * Beallitja az idojarast.
	 * @param w
	 */
	public void SetWeather(String w) {
		weather = w;
		if(w.equals("normal"))
			closeToSun = false;
		else 
			closeToSun = true;
	}
	/**
	 * Visszaadja a szomszedos aszteroidak es teleportok listajat
	 * @return Visszaadja a neighbors-t.
	 */
	public ArrayList<Travel> GetNeighbors(){
		return neighbors;
	}
	/**
	 * A megkapott aszteroida vagy teleport hozzaadodik az aszteroida szomszedaihoz.
	 * @param t
	 */
	public void AddNewNeighbor(Travel t) {
		neighbors.add(t);
	}
	/**
	 * A megkapott aszteroida vagy teleport torlodik az aszteroida szomszedai kozul.
	 */
	public void RemoveNeighbor(Travel t) {
		neighbors.remove(t);
	}
	/**
	 * Visszaadja az aszteroidan tartozkodo entitasokat.
	 * @return Visszaadja a creature-ok listajat.
	 */
	public ArrayList<Creature> GetCreatures() {
		return creatures;
	}
	/**
	 * Hozzaadja az entitast az aszteroidahoz.
	 * @param c
	 */
	public void AddCreature(Creature c){
		creatures.add(c);
	}
	/**
	 * Torli az aszteroidarol a megkapott entitast.
	 * @param c
	 */
	public void Remove(Creature c) { 
		creatures.remove(c);
	}
	/**
	 * Csokkenti az aszteroida kopenyet.
	 * Ha epp napkozelben van, akkor meghivja a resource CloseToSun fuggvenyet.
	 */
	public boolean ReduceLayers() {
		if(layers > 0){
			layers--;
			return true;
		}
		return false;
	}
	/**
	 * Megnezi, hogy a kapott travel szomszedja-e annak a travelnek amire ez hivva van.
	 * @param t
	 * @return Ha szomszedja, akkor true-val, ha nem, akkor false-al ter vissza.
	 */
	public boolean CheckNeighbor(Travel t) {
		for(Travel tr : neighbors) {
			if(tr.GetOtherAsteroid() == t.GetOtherAsteroid())
				return true;
		}
		return false;
	}
	/**
	 * Napviharnal ha el tudnak bujni az entitasok nem csinal semmit, egyebkent pedig megoli az osszes entitast ami rajta van.
	 */
	public void SunStorm() {
		int size = creatures.size();
		if(layers == 0 && isEmpty);
		else {
			if(creatures != null)
				while(creatures.size() > 0)
					creatures.get(0).Die();
		}
		if(neighbors != null)
			for(Travel t : neighbors)
				t.SetIsMoving();
	}
	/**
	 * Felrobban az aszteroida.
	 */
	public void Explode() {
		if(creatures != null)
			while(creatures.size() > 0) {
				creatures.get(0).Explode();
			}
		if(neighbors != null)
			for(Travel t : neighbors)
				t.RemoveNeighbor(this);
		int count = 0;
		for(Asteroid a : game.GetAsteroid()) {
			count++;
			if(a.GetId() == this.GetId()) {
				game.GetAsteroid().remove(count-1);
				break;
			}
		}
		v.RemoveDrawable(aview);
		aview.SetExploded();
	}
	
	/**
	 * Teleport elfogadasa.
	 */
	@Override
	public void AcceptTeleport(Teleport t) {
		t.SetAsteroid(this);
		AddNewNeighbor(t);
		t.GetView().SetCoord(this);
	}
	/**
	 * Elfogadja a ralepo entitast.
	 */
	@Override
	public void Accept(Creature c) {
		creatures.add(c);
		c.SetAsteroid(this);
	}
	/**
	 * Az isMoving boolean beallitasa.
	 */
	@Override
	public void SetIsMoving() {}
	
	@Override
	public Asteroid GetOtherAsteroid() {
		return this;
	}
}
