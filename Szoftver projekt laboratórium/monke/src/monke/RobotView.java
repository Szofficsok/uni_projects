package monke;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RobotView implements Drawable{
	private int x;
	private int y;
	private View v;
	private JLabel label;
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param v
	 */
	public RobotView(int x, int y, View v) {
		this.x=x;
		this.y=y;
		this.v=v;
		
		label = new JLabel();
		label.setIcon(new ImageIcon("bb8.png"));
	}
	
	/**
	 * Beállítja a robot koordinátáit a kapott aszeroidához
	 * @param a
	 */
	@Override
	public void SetCoord(Asteroid a) {
		x = a.GetView().GetX();
		y = a.GetView().GetY();
	}

	/**
	 * Kirajzolja a robotot
	 */
	@Override
	public void Draw() {
		label.setBounds(x + 20, y - 25, 30, 30);
		v.add(label);
	}
	
	/**
	 * Halal utan atallitja a robot kepet uresre
	 */
	@Override
	public void SetTomb() {
		label.setIcon(new ImageIcon("semmi.png"));
	}
}
