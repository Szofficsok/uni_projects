package aknakereso;

import static org.junit.Assert.*;
import javax.swing.JFrame;
import org.junit.Test;
import junit.framework.Assert;

public class MacskakeresoTest {

	Menu m;
	Jatek j;
	Palyabeallitasok p;
	Fejlec f;
	JFrame fr;
	Helyezett h;
	Osszehasonlito o;
	Mezo me;
	
	@Test
	public void testFejlecIdo1() {
		j = new Jatek("", 0, 0, 0, true,"Kicsi");
		p = new Palyabeallitasok();
		fr = new JFrame();
		f = new Fejlec(j, p, fr, "Kicsi", true);
		Assert.assertEquals(300, f.getIdo());
		Assert.assertEquals(300, j.getIdo());
	}
	@Test
	public void testFejlecIdo2() {
		j = new Jatek("", 0, 0, 0, true, "Kozepes");
		p = new Palyabeallitasok();
		fr = new JFrame();
		f = new Fejlec(j, p, fr, "Kozepes", true);
		Assert.assertEquals(600, f.getIdo());
		Assert.assertEquals(600, j.getIdo());
		
	}
	@Test
	public void testFejlecIdo3() {
		j = new Jatek("", 0, 0, 0, true, "Nagy");
		p = new Palyabeallitasok();
		fr = new JFrame();
		f = new Fejlec(j, p, fr, "Nagy", true);
		Assert.assertEquals(900, f.getIdo());
		Assert.assertEquals(900, j.getIdo());
	}
	@Test
	public void testHelyezett() {
		h = new Helyezett("Teszt", 1);
		Assert.assertEquals(1,h.getIdo());
		Assert.assertEquals("Teszt",h.getNev());
	}
	@Test
	public void testOsszehasonlito1() {
		o = new Osszehasonlito();
		Helyezett h1 = new Helyezett("Teszt1", 1);
		Helyezett h2 = new Helyezett("Teszt2", 2);
		Assert.assertEquals(-1, o.compare(h1,h2));
	}
	@Test
	public void testOsszehasonlito2() {
		o = new Osszehasonlito();
		Helyezett h1 = new Helyezett("Teszt1", 2);
		Helyezett h2 = new Helyezett("Teszt2", 1);
		Assert.assertEquals(1, o.compare(h1,h2));
	}
	@Test
	public void testOsszehasonlito3() {
		o = new Osszehasonlito();
		Helyezett h1 = new Helyezett("Teszt1", 1);
		Helyezett h2 = new Helyezett("Teszt2", 1);
		Assert.assertEquals(0, o.compare(h1,h2));
	}
	@Test
	public void testJatekKonstruktor1() {
		j = new Jatek("Teszt", 9, 9, 10, true, "Kicsi");
		Assert.assertEquals("Teszt", j.getNev());
		Assert.assertEquals(10, j.getBomba());
		Assert.assertEquals(300,j.getIdo());
	}
	@Test
	public void testJatekKonstruktor2() {
		j = new Jatek("Teszt", 16, 16, 10, true, "Kozepes");
		Assert.assertEquals("Teszt", j.getNev());
		Assert.assertEquals(10, j.getBomba());
		Assert.assertEquals(600,j.getIdo());
	}
	@Test
	public void testJatekKonstruktor3() {
		j = new Jatek("Teszt", 30, 16, 10, false, "Nagy");
		Assert.assertEquals("Teszt", j.getNev());
		Assert.assertEquals(10, j.getBomba());
		Assert.assertEquals(0,j.getIdo());
	}
	@Test
	public void testMezoKonstruktor() {
		me = new Mezo();
		Assert.assertEquals(true, me.getFelsoreteg_e());
		Assert.assertEquals(false, me.getJelolve_e());
		Assert.assertEquals(0, me.getErtek());
		Assert.assertEquals(false, me.bomba_e());
	}

}
