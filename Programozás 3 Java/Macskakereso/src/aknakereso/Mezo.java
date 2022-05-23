package aknakereso;

public class Mezo {
	private boolean felsoreteg_e;
	private boolean jelolve_e;
	private int ertek;
	public Mezo(){
		ertek = 0;
		felsoreteg_e = true;
		jelolve_e = false;
	}
	public int getErtek() {
		return ertek;
	}
	public void setErtek(int e) {
		ertek = e;
	}
	public void Novel() {
		ertek = ertek+1;
	}
	public boolean getFelsoreteg_e() {
		return felsoreteg_e;
	}
	public void setFelsoreteg_e(boolean f) {
		felsoreteg_e = f;
	}
	public boolean getJelolve_e() {
		return jelolve_e;
	}
	public void setJelolve_e(boolean j) {
		jelolve_e = j;
	}
	public boolean bomba_e() {
		if(ertek == 10 ) {
			return true;
		}
		else {
			return false;
		}
	}
}