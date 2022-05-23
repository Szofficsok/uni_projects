package monke;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AsteroidView implements Drawable{
	private int x;
	private int y;
	private View v;
	private JLabel[] pictures;
	private JLabel label;
	
	public int GetX() {
		return x;
	}
	
	public int GetY() {
		return y;
	}
	
	AsteroidView(int x, int y, View v) {
		this.x=x;
		this.y=y;
		this.v=v;
		pictures = new JLabel[2];
		label = new JLabel();
		label.setIcon(new ImageIcon("asteroid.png"));	
	}
	
	/**
	 * Be�ll�tja az aszeroida k�p�t (sz�rke vagy s�rga k�r)
	 * @param id
	 */
	public void SetColor(int id) {
		if(id == 0)
			label.setIcon(new ImageIcon("asteroid.png"));
		else
			label.setIcon(new ImageIcon("asteroid2.png"));
	}
	
	/**
	 * Be�ll�tja az aszeroida k�p�t egy robban�sra
	 */
	public void SetExploded() {
		label.setIcon(new ImageIcon("explosion.png"));
	}

	/**
	 * Kirajzolja az aszeroid�t
	 */
	@Override
	public void Draw() {
		v.remove(label);
		label.setBounds(x, y, 50, 50);
		v.add(label);
	}
	
	@Override
	public void SetCoord(Asteroid a) {}
	
	@Override
	public void SetTomb() {}
	
}
