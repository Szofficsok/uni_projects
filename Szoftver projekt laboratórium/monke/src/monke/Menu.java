package monke;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame{
private static final long serialVersionUID = 1L;
	
	private JButton play;
	private JButton options;
	private JButton exit;
	
	private Options op = new Options();
	
	/**
	*Konstruktor
	*/
	public Menu() {
		super("Menu");
		setSize(400, 280);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JPanel p = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		play = new JButton("Play!");
		play.setPreferredSize(new Dimension(190, 70));
		PlayButtonActionListener playlistener = new PlayButtonActionListener();
		play.addActionListener(playlistener);
		p.add(play);
		
		options = new JButton("Options");
		options.setPreferredSize(new Dimension(190, 70));
		OptionsButtonActionListener optionslistener = new OptionsButtonActionListener();
		options.addActionListener(optionslistener);
		p2.add(options);
		
		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(190, 70));
		ExitButtonActionListener exitlistener = new ExitButtonActionListener();
		exit.addActionListener(exitlistener);
		p3.add(exit);
		
		add(p);
		add(p2);
		add(p3);
	}
	
	/**
	*Play gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class PlayButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Game g;
			if(op.GetPlayers().size() > 0)
				g = new Game(op.GetSelectedImage(), op.GetPlayers());
		}
	}
	
	/**
	*Options gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class OptionsButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			op.SetVis();
		}
	}
	
	/**
	*Exit gomb lenyomasa altal bekovetkezett esemenyert felelos osztaly.
	*/
	private class ExitButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
