package aknakereso;

import java.util.Comparator;

public class Osszehasonlito implements Comparator<Helyezett>{
	@Override
	public int compare(Helyezett h1, Helyezett h2) {
		if(h1.getIdo() < h2.getIdo()) {
			return -1;
		}
		else if(h1.getIdo() > h2.getIdo()) {
			return 1;
		}
		else if(h1.getIdo() == h2.getIdo()) {
			return 0;
		}
		return 2;
	}
}