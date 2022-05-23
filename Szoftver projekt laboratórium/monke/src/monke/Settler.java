package monke;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Settler extends Creature{
	private int hasTpk;
	private ArrayList<Resource> resources;
	private static BillOfResources billOfResources;
	private ArrayList<Teleport> teleports;
	private static int tpid = 1;
	private SettlerView view;
	private View v;
	
	/**
	 * A telepes konstruktora.
	 * @param game
	 * @param name
	 * @throws Exception 
	 * @throws IOException 
	 */
	public Settler(Game game, String name, int x, int y, View view, String which) {
		this.view = new SettlerView(x, y, view, which);
		v = view;
		hasTpk = 0;
		this.game = game;
		resources = new ArrayList<Resource>();
		teleports = new ArrayList<Teleport>();
		billOfResources = new BillOfResources();
		this.SetName(name);
		this.asteroid = null;
		game.AddSettler(this);
	}
	
	public Drawable GetView() {
		return view;
	}
	
	/**
	 * Banyaszik egy aszteroidabol.
	 * Ha az aszteroida kerge nagyobb nullanal, visszater.
	 * Ha a nyersanyaglistajaban van meg hely, hozzaadja az aszteroidaban levo nyersanyagot, es az aszteroidat uresre allitja.
	 * Megnezi tudnak-e nyerni a telepesek.
	 * Ha nincs, visszater.
	 * @throws Exception 
	 */
	public boolean Mine() {
		if(asteroid.GetLayers() > 0 || asteroid.GetResource() == null) {
			return false;
		}
		
		int length = resources.size();
		if(length < 10) {
			AddResource(asteroid.GetResource());
			asteroid.SetResource(null);
			asteroid.SetIsEmpty(true);
			return true;
		}
		return false;
	}
	/**
	 * Meghivja a reducelayerst az aszteroidara.
	 */
	public boolean Drill() {
		return asteroid.ReduceLayers();
	}
	/**
	 * Hozzaadja a parametert a nyersanyaglistajahoz.
	 * @param r
	 * @throws Exception 
	 */
	public void AddResource(Resource r) {
		resources.add(r);
	}
	/**
	 * Visszaadja a nyersanyaglistat.
	 */
	public ArrayList<Resource> GetResources() {
		return resources;
	}
	/**
	 * Visszarak egy parameterul kapott nyersanyagot az aszteroidajaba.
	 * Amennyiben ures es teljesen atfurt az aszteroida,
	 * es a telepesnek van az adott nyersanyagbol, beallitja az aszteroida magjat ra, 
	 * illetve a RemoveResource-al elveszi magatol.
	 * @param r
	 */
	public boolean PlaceResource(Resource r) {
		if(asteroid.GetLayers() == 0 && asteroid.GetIsEmpty()) {
			if(CheckResource(r)) {
				asteroid.SetResource(r);
				ArrayList<Resource> re = new ArrayList<>();
				re.add(r);
				RemoveResource(re);
				return true;
			}
		}
		return false;
	}
	/**
	 * Megnezi, hogy a parameter benne van-e a nyersanyagai kozott.
	 * @param r
	 * @return True-val ter vissza ha benne van, false-al ha nem.
	 */
	public boolean CheckResource(Resource r) {
		for(Resource r2 : resources) {
			if(r.GetName().equals(r2.GetName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * A kapott nyersanyagokat kiveszi a telepes nyersanyagai kozul.
	 * @param rem
	 */
	public void RemoveResource(ArrayList<Resource> rem) {
		for(Resource re : rem) {
			for(Resource r : resources) {
				if(r.getClass().equals(re.getClass())) {
					resources.remove(r);
					break;
				}
			}
		}
	}
	/**
	 * Meghivja a Die-t magara, ezzel feladva a jatekot.
	 */
	public boolean GiveUp() {
		Die();
		return true;
	}
	/**
	 * A jatekos a koret tovabb passzolja.
	 */
	public boolean Skip() {
		return true;
	}
	/**
	 * Leveszi az aszteroidarol es kiveszi a jatekbol a telepest, ezzel megolve azt.
	 */
	@Override
	public void Die() {
		asteroid.Remove(this);
		game.RemoveSettler(this);
		v.RemoveDrawable(this.GetView());
		view.SetTomb();
		if(!game.CheckSettlerLifeLines())
			game.EndGame();
	}
	/**
	 * Megepit egy teleportkapu part, amiket behelyez a listajaba.
	 * Amennyiben tobb mint ketto teleportja van, visszater.
	 * Megnezi van-e eleg nyersanyaga egy tpk parhoz a nyersanyaglista alapjan.
	 * Ha van, kiveszi az elhasznalt nyersanyagokat, megnoveli a teleportszamlalojat.
	 * Letrehoz ket teleportot, melyeket egymas parjanak allit, majd beleteszi a listajaba oket.
	 */
	public boolean BuildTeleport() {
		if(hasTpk >= 2) {
			return false;
		}
		else {
			if(billOfResources.CheckResource(resources, "Teleport")) {
				RemoveResource(billOfResources.GetBillOfTpk());
				hasTpk += 2;
				Teleport t = new Teleport(game, tpid, -30, -30, v);
				tpid++;
				Teleport t2 = new Teleport(game, tpid, -30, -30, v);
				v.AddDrawable(t.GetView());
				v.AddDrawable(t2.GetView());
				tpid++;
				t.SetPair(t2);
				t2.SetPair(t);
				teleports.add(t);
				teleports.add(t2);
				return true;
			}
		}
		return false;
	}
	/**
	 * Megepit egy robotot, amit lehelyez az aszteroidajara.
	 * Megnezi van-e eleg nyersanyaga egy robothoz a nyersanyaglista alapjan.
	 * Ha van, letrehoz egy uj robotot, majd meghivja a RemoveResource-ot, amivel a felhasznalt nyersanyagokat kiveszi.
	 * @param name
	 */
	public boolean BuildRobot(String name) {
		if(billOfResources.CheckResource(resources, "Robot")) {
			int x = asteroid.GetView().GetX();
			int y = asteroid.GetView().GetY();
			Robot r = new Robot(game, asteroid, name, x, y, v);
			v.AddDrawable(r.GetView());
			v.DrawAll();
			RemoveResource(billOfResources.GetBillOfRobot());
			return true;
		}
		return false;
	}
	
	/**
	 * Lerakja a parameter aszteroidara a listaja elso teleportjat.
	 * Amennyiben nincs nala tpk, visszater.
	 * Kulonben a teleports lista elso elemenek beallitja az aszteroidajat a parameterre, kiveszi a listabol es csokkenti a teleportszamlalo erteket.
	 * @param a
	 */
	public boolean PlaceTeleport(Asteroid a) {
		if(hasTpk == 0) {
			return false;
		}
		Teleport t = teleports.get(0);
		t.SetAsteroid(a);
		game.AddTeleport(t);
		asteroid.AddNewNeighbor(t);
		t.GetView().SetCoord(a);
		teleports.remove(0);
		
		hasTpk--;
		return true;
	}
	
	/**
	 * Kilistazza a nyersanyagok neveit.
	 */
	public void ListAllResourceName() {
		int ir = 0;
		int car = 0;
		int ur = 0;
		int wa = 0;
		for(Resource r : resources) {
			if(r.GetName() == "iron")
				ir++;
			else if(r.GetName() == "carbon")
				car++;
			else if(r.GetName() == "uranium")
				ur++;
			else if(r.GetName() == "waterice")
				wa++;
		}
	}
	
	public void SetResources(int sz) {
		for(int i = 0; i < sz; i++) {
			Iron iron = new Iron();
			resources.add(iron);
		}
	}
	
	public int GetHasTpk() {
		return hasTpk;
	}
	
	public void Explode() {
		Die();
	}
}
