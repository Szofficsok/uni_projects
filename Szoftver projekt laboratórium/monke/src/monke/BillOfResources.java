package monke;


import java.util.ArrayList;
import java.util.List;


public class BillOfResources {
	private ArrayList<Resource> billOfTpk;
	private ArrayList<Resource> billOfRobot;
	private ArrayList<Resource> billOfBase;
	/**
	 * Konstruktor.
	 */
	public BillOfResources() {
		this.billOfTpk = new ArrayList<Resource>();
		this.billOfRobot = new ArrayList<Resource>();
		this.billOfBase = new ArrayList<Resource>();
		Iron i = new Iron();
		Carbon c = new Carbon();
		Waterice w = new Waterice();
		Uranium u = new Uranium(0);
		this.billOfTpk.add(i);
		this.billOfTpk.add(i);
		this.billOfTpk.add(w);
		this.billOfTpk.add(u);
		this.billOfRobot.add(i);
		this.billOfRobot.add(c);
		this.billOfRobot.add(u);
		for (int j=0; j<3; j++) {
			this.billOfBase.add(i);
			this.billOfBase.add(c);
			this.billOfBase.add(w);
			this.billOfBase.add(u);
		}
	}
	/**
	 * Visszaadja a teleport receptjet.
	 * @return Visszaadja a billOfTpk
	 */
	public ArrayList<Resource> GetBillOfTpk() {
		return this.billOfTpk;
	}
	/**
	 * Visszaadja a robot receptjet.
	 * @return Visszaadja a billOfRobot
	 */
	public ArrayList<Resource> GetBillOfRobot() {
		return this.billOfRobot;
	}
	/**
	 * Visszaadja a bazis receptjet.
	 * @return Visszaadja a billOfBase
	 */
	public ArrayList<Resource> GetBillOfBase() {
		return this.billOfBase;
	}
	/**
	 * A sztringkent megkapott objektumnak a receptjeben megnézi, hogy van-e hozza eleg nyersanyag es ettol fuggoen ad vissza booleant.
	 * @param resources
	 * @param valami
	 * @return Ha van nala, akkor true-t ad vissza, ha nincs, akkor false-t.
	 */
	public boolean CheckResource(ArrayList<Resource> resources, String valami) {
		List<Resource> check = new ArrayList<Resource>();
		if(valami == "Robot") {
			check = this.billOfRobot;
		}
		else if(valami == "Teleport") {
			check = this.billOfTpk;
		}
		else if(valami == "Base") {
			check = this.billOfBase;
		}
		List<Resource> temporary = new ArrayList<Resource>();
		boolean talalt = false;
		for (Resource robot : check) {
			talalt=false;
			for (Resource name : resources) {
				talalt=false;
				if(robot.getClass().equals(name.getClass())==true) {
					for (Resource temp : temporary) {
						if(temp.equals(name)==true) {
							talalt = true;
							break;
						}
					}
					if (talalt==false) {
						temporary.add(name);
						break;
					}
				}
			}
		}
		if(check.size()==temporary.size()) {
			return true;
		}
		else {
			return false;
		}
	}
}
