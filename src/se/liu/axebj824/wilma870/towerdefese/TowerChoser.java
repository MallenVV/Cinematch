package se.liu.axebj824.wilma870.towerdefese;

import javax.swing.*;
import java.awt.*;

public class TowerChoser
{

    public static void show(Board board, Point pos) {
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(400, 300);

	Object[] towers = {"Tower1", "Tower2", "other"};
	String s = (String)JOptionPane.showInputDialog(
		frame,
		"Choose Tower to place",
		"Place Tower",
		JOptionPane.PLAIN_MESSAGE,
		null,
		towers,
		towers[0]);

//If a string was returned, say so.
	if ("Tower1".equals(s)) {
	    if (board.getMoney() >= GlobalPrices.DEFAULT_TOWER_PRICE){
		board.placeTower(s, pos);
		board.getPlatform(pos.x, pos.y).occupy();
	    }
	} else if ("Tower2".equals(s)) {
	    if (board.getMoney() >= GlobalPrices.DEFAULT_TOWER_PRICE){	// other tower in the future
		board.placeTower(s, pos);
		board.getPlatform(pos.x, pos.y).occupy();
	    }
	}
    }
}