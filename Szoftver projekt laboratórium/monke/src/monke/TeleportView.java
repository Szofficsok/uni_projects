package monke;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TeleportView implements Drawable {

	private int x;
	private int y;
	private View v;
	private JLabel label;
	/**
	 * Konstruktor.
	 * @param x
	 * @param y
	 * @param v
	 */
	public TeleportView(int x, int y, View v) {
		this.x=x;
		this.y=y;
		this.v=v;
		label = new JLabel();
		label.setIcon(new ImageIcon("teleport.png"));
	}
	/**
	 * Kirajzolja a helyere az aszteroidahoz kepest.
	 */
	@Override
	public void Draw() {
		label.setBounds(x + 10, y + 40, 30, 30);
		v.add(label);
	}
	/**
	 * Beallitja a helyet a parameterkent kapott aszteroidhoz.
	 */
	@Override
	public void SetCoord(Asteroid a) {
		x = a.GetView().GetX();
		y = a.GetView().GetY();
	}
	/**
	 * Ures fv. ami megvalositja a drawable interface fv-et
	 */
	@Override
	public void SetTomb() {}
}
