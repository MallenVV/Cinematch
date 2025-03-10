package se.liu.axebj824.wilma870.towerdefese;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TDAction
{
    private Board board;
    private JFrame frame;

    public TDAction(JFrame frame, Board board) {
	this.board = board;
	this.frame = frame;
	JComponent pane = frame.getRootPane();

	final InputMap in = pane.getInputMap(JComponent.WHEN_FOCUSED);

	pane.addMouseListener(new MouseAdapter()
	{
	    @Override public void mouseClicked(MouseEvent e) {
		// Handle mouse click event
		mouseClickedInstructions(e);
	    }
	});
    }


    private void mouseClickedInstructions(MouseEvent e) {
	int menuBarHeight = frame.getJMenuBar().getHeight();
	Point pos = e.getPoint();
	Point newPos = new Point(pos.x / GlobalVariable.SQUARE_SIZE, (pos.y - menuBarHeight) / GlobalVariable.SQUARE_SIZE);
	SquareType square = board.getVisibleSquareAt(newPos.x, newPos.y);
	System.out.println("square at " + newPos + ": " + square);
	if (square == SquareType.PLATFORM) {
//	    System.out.println("placable");

	    if (board.getPlatform(newPos.x, newPos.y) != null) {
		Point middelpos = board.getPlatform(newPos.x, newPos.y).getPosition();

		if (!board.getPlatform(newPos.x, newPos.y).isOccupied()) {
		    TowerChoser.show(board, middelpos);
		} else {
		    TowerManager.show(board, middelpos);
		}
	    }
	}
    }
}
