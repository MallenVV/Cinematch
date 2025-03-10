package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The enemy subclass of entity that contains logic for the enemies
 *
 * @param hp the enemy's hitpoints
 * @param speed the enemy's speed
 * @param visited the visited coordinates of the enemy
 * @param speedOffset the remaining decimals of the speed calculations stored for next calculation
 * @param lastAttack the nanoTime when the latest attack was hit
 */

public class Enemy extends Entity
{
    private int hp;
    private float speed;
    private List<Point> visited = new ArrayList<>();
    private float speedOffset = 0;
    private long lastAttack = 0;

    public Enemy(final Board board, Point pos, int size, int damage, int cooldown, Color color, float value, final int hp, final float speed) {
	super(board, pos, size, damage, cooldown, color, value);
	this.hp = hp;
	this.speed = speed / 4;
	this.lastAttack = System.nanoTime() - (cooldown * 1_000_000L);
    }

    public void move() {
	if (!visited.contains(pos)) {
	    visited.add(pos);
	}

	this.speedOffset += speed - (int) speed;
	int intSpeed = (int) speed;

	if (speedOffset >= 1) {
	    intSpeed += (int)speedOffset;
	    speedOffset -= (int)speedOffset;
	}

	if (intSpeed > 1) {

	    for (int i = intSpeed; i > 1; i--) {
		if (checkMove(i)) {
		    moveSequens(i);
		    break;
		}
	    }
	}
	else {
	    moveSequens(intSpeed);
	}
    }

    private boolean checkMove(int distance) {
	Point rightMove = new Point(this.pos.x + distance, this.pos.y);
	if (board.getMap().getMapSquare(rightMove.x, rightMove.y) == SquareType.TRAIL && !visited.contains(rightMove)) {
	    return true;
	}

	Point downMove = new Point(this.pos.x, this.pos.y + distance);
	if (board.getMap().getMapSquare(downMove.x, downMove.y) == SquareType.TRAIL && !visited.contains(downMove)) {
	    return true;
	}

	Point upMove = new Point(this.pos.x, this.pos.y - distance);
	if (board.getMap().getMapSquare(upMove.x, upMove.y) == SquareType.TRAIL && !visited.contains(upMove)) {
	    return true;
	}

	Point leftMove = new Point(this.pos.x - distance, this.pos.y);
	if (board.getMap().getMapSquare(leftMove.x, leftMove.y) == SquareType.TRAIL && !visited.contains(leftMove)) {
	    return true;
	}
	return false;
    }

    private void moveSequens(int distance) {
	Point rightMove = new Point(this.pos.x + distance, this.pos.y);
	if (board.getMap().getMapSquare(rightMove.x, rightMove.y) == SquareType.TRAIL && !visited.contains(rightMove)) {

	    for (int i = 1; i < distance; i++) {
		visited.add(new Point(pos.x + i, pos.y));
	    }
	    this.pos = rightMove;
	}

	Point downMove = new Point(this.pos.x, this.pos.y + distance);
	if (board.getMap().getMapSquare(downMove.x, downMove.y) == SquareType.TRAIL && !visited.contains(downMove)) {

	    for (int i = 1; i < distance; i++) {
		visited.add(new Point(pos.x, pos.y + i));
	    }
	    this.pos = downMove;
	}

	Point upMove = new Point(this.pos.x, this.pos.y - distance);
	if (board.getMap().getMapSquare(upMove.x, upMove.y) == SquareType.TRAIL && !visited.contains(upMove)) {

	    for (int i = 1; i < distance; i++) {
		visited.add(new Point(pos.x, pos.y - i));
	    }
	    this.pos = upMove;
	}

	Point leftMove = new Point(this.pos.x - distance, this.pos.y);
	if (board.getMap().getMapSquare(leftMove.x, leftMove.y) == SquareType.TRAIL && !visited.contains(leftMove)) {

	    for (int i = 1; i < distance; i++) {
		visited.add(new Point(pos.x - i, pos.y));
	    }
	    this.pos = leftMove;
	}
    }

    public void atBase() {
//	System.out.println(board.getVisableSquareAt(pos.x + (int)speed, pos.y));
	if (board.getVisibleSquareAt(pos.x + 1, pos.y) == SquareType.BASE) {
	    attackBase();
	}
    }

    public void attackBase() {
	if (Math.abs(System.nanoTime() - lastAttack) / 1_000_000 > cooldown) {
	    lastAttack = System.nanoTime();
	    board.getBase().baseAttacked(damage);
	    System.out.println(board.getBase().getBaseHP());
//	    System.out.println("Fiende attackerade basen");
	}
    }

    public void takeDamage(int damage){
	hp -= damage;
    }

    @Override public void addToBoard(Board board) {
	board.addEnemy(this);
    }

    @Override public void removeFromBoard(Board board) {
	board.removeEnemy(this);
    }

    public int getVisitedSize() {
	return visited.size();
    }

    public int getHp(){
	return hp;
    }

    public float getValue() {
	return value;
    }
}
