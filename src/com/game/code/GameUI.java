package com.game.code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameUI extends JFrame	{

	private static final long serialVersionUID = 1L;
	JButton[] buttons = new JButton[9];
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new GameUI();

			}
		});
	}

	public GameUI() {
		setLayout(new GridLayout(3, 3));
		for(int i=0; i<9; i++)
		{
			final JButton button = createButton();
			buttons[i] = button;
		}
		pack();
		setVisible(true);
	}

	private JButton createButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(100,100));
		button.setFont(new Font(null, Font.PLAIN, 50));
		add(button);
		return button;
	}

}