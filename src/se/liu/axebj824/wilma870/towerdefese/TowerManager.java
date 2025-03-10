package se.liu.axebj824.wilma870.towerdefese;

import javax.swing.*;
import java.awt.*;

public class TowerManager
{
    public static void show(Board board, Point pos) {
	int width = 400;
	int height = 300;
	JFrame frame = new JFrame();
	frame.setSize(width, height);
	frame.setLocation(pos.x, pos.y + width);
//	Panel

	JPanel panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	JLabel panelLabel = new JLabel("Upgrade Tower");
	JButton button = new JButton("Speed");
	button.addActionListener(e -> {
	    Tower tower = board.getTower(pos.x, pos.y);
	    tower.upgradeSpeed();
	});
	JButton button2 = new JButton("Damage");
	JButton button3 = new JButton("Radius");
	c.gridx = 0;
	c.gridy = 0;
	c.gridwidth = 2;
	c.fill = GridBagConstraints.VERTICAL;
	panel.add(panelLabel, c);
	c.gridwidth = 1;
	c.gridy = 1;
	panel.add(button, c);
	c.gridx = 1;
	panel.add(button2, c);
	c.gridx = 2;
	panel.add(button3, c);
//	Frame
	JLabel label = new JLabel("Manage selected tower");
	label.setHorizontalAlignment(SwingConstants.CENTER);
	label.setFont(label.getFont().deriveFont(Font.BOLD, 25));
	frame.setLayout(new GridLayout(3, 1));
	frame.add(label);
	frame.add(panel);
	JButton sellButton = new JButton("Sell Tower");
	sellButton.addActionListener(e -> {
	    Entity tower = board.getEntity(pos.x, pos.y);
	    board.removeEntity(tower);
	    board.getPlatform(pos.x, pos.y).unoccupy();
	    frame.dispose();
	});
	frame.add(sellButton);
	frame.setVisible(true);

//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	Object[] options = { "Yes", "Cancel" };
//	int choice = JOptionPane.showOptionDialog(frame, "Do you want to delete this tower?", "Delete Tower", JOptionPane.YES_NO_OPTION,
//						  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//
////If a string was returned, say so.
//	if (choice == JOptionPane.YES_OPTION) {
//	    Entity tower = board.getEntity(pos.x, pos.y);
//	    board.removeEntity(tower);
//	    board.getPlatform(pos.x, pos.y).unoccupy();
//	}
    }
}
