package aknakereso;

import java.io.Serializable;

public class Helyezett implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nev;
	private int ido;
	public Helyezett(String n, int i) {
		nev = n;
		ido = i;
	}
	public int getIdo(){
		return ido;
	}
	public String toString() {
		return nev + "      " + ido + " mp";
	}
	//Teszt miatt
	public String getNev(){
		return nev;
	}
}
