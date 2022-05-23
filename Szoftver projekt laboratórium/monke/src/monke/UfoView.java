package monke;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UfoView implements Drawable {
	private int x;
	private int y;
	private View v;
	private JLabel label;
	
	/**
	 * Csinal egy uj ufo nezetet. Beallitja a labeljet az ufo kepecskejere
	 * 
	 * @param x: A kirajzolas x koordinataja
	 * @param y: A kirajzolas y koordinataja
	 * @param v: A view-ja
	 */
	public UfoView(int x, int y, View v) {
		this.x=x;
		this.y=y;
		this.v=v;
		
		label = new JLabel();
		label.setIcon(new ImageIcon("doodlejump.png"));
	}
	/**
	 * Beallitja az ufo koordinatait
	 */
	@Override
	public void SetCoord(Asteroid a) {
		x = a.GetView().GetX();
		y = a.GetView().GetY();
	}
	/**
	 * Kirajzolja az ufot a kepernyore
	 */
	@Override
	public void Draw() {
		label.setBounds(x - 10, y - 15, 30, 30);
		v.add(label);
	}
	/**
	 * Beallitja a labeljet semmire, hogy halal utan megsemmisuljon
	 */
	@Override
	public void SetTomb() {
		label.setIcon(new ImageIcon("semmi.png"));
	}
}
