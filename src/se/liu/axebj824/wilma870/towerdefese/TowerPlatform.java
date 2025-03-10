package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;

public class TowerPlatform
{
    private boolean occupied = false;
    private Point position;
    private int size = GlobalVariable.PLATFORM_SIZE;
//    private Tower tower;

    public TowerPlatform(final Point position) {
//	this.tower = tower;
	this.position = position;
    }

    public void occupy() {
	occupied = true;
    }

    public void unoccupy() {
	occupied = false;
    }


    public boolean atPosition(int x, int y) {
	for (int i = -size / 2; i <= (size / 2) + 0.5; i++) {
	    for (int j = -size / 2; j <= (size / 2) + 0.5; j++) {
		if (new Point(position.x + i, position.y + j).equals(new Point(x, y))) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean isOccupied() {
	return occupied;
    }

    public Point getPosition() {
	return position;
    }

    public int getSize() {
	return size;
    }
}
