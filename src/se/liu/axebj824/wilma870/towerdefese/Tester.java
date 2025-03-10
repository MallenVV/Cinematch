package se.liu.axebj824.wilma870.towerdefese;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Tester
{
    public static void main(String[] args) {
	Board board = new Board();
	BoardViewer viewer = new BoardViewer();
	Waves waves = new Waves(board);

	final Action doOneStep = new AbstractAction()
	{

	    public void actionPerformed(ActionEvent e) {
		if (board.getBase().getBaseHP() <= 0) {
		    System.exit(0);
		}
		board.tick();
		viewer.repaint(board);
		waves.update();

	    }
	};
//	spawner.spawnDefaultEnemy(board);

	waves.startNextWave();
	viewer.show(board);


	final Timer clockTimer = new Timer(GlobalVariable.UPDATE_INTERVAL, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
//	Varje tick ska adessa köras
//	Tower.shoot()
//	Enemy.move()
    }
}
