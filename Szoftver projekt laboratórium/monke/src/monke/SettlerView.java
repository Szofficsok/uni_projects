package monke;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SettlerView implements Drawable{
	private int x;
	private int y;
	private View v;
	private JLabel[] pictures;
	private JLabel label;
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param v
	 * @param which
	 */
	public SettlerView(int x, int y, View v, String which) {
		this.x=x;
		this.y=y;
		this.v=v;
		
		pictures = new JLabel[5];
		for(int i=0;i<5;i++)
			pictures[i] = new JLabel();
		pictures[0].setIcon(new ImageIcon("bobafeta.png"));
		pictures[1].setIcon(new ImageIcon("darthvader.png"));
		pictures[2].setIcon(new ImageIcon("steve.png"));
		pictures[3].setIcon(new ImageIcon("private.png"));
		pictures[4].setIcon(new ImageIcon("sweetiebelle.png"));
		if(which == "Boba-feta")
			label = pictures[0];
		else if(which == "Darth Vader")
			label = pictures[1];
		else if(which == "Steve")
			label = pictures[2];
		else if(which == "Private")
			label = pictures[3];
		else if(which == "Sweetie Belle")
			label = pictures[4];
	}
	
	/**
	 * Beallitja a halott telepes kepet
	 */
	public void SetTomb() {
		label.setBounds(x + 10, y - 10, 30, 30);
		label.setIcon(new ImageIcon("tombstone.png"));
	}
	
	/**
	 * Atallitja a telepes koordinatait a kapott aszteroidaehoz
	 * @param a
	 */
	@Override
	public void SetCoord(Asteroid a) {
		x = a.GetView().GetX();
		y = a.GetView().GetY();
	}

	/**
	 * Beallitja a telepes koordinatait es hozzaadja a viewhoz
	 */
	@Override
	public void Draw() {
		label.setBounds(x + 10, y - 25, 30, 30);
		v.add(label);
	}
}
