package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;

/**
 * An abstract class for the entities that are going to interact in the game (towers and enemies)
 * The class contains getters for necessary fields and and abstract functions to remove and add oneself on the board
 *
 * @param board the board the entity is placed or remoed from, the board the game is played on.
 * @param pos the entity's position
 * @param size the entity's size
 * @param damage the entity's damage done when attacking
 * @param cooldown the entity's cooldown for attacks
 * @param color the entity's color
 */
public abstract class Entity
{
    protected Board board;
    protected Point pos;
    protected int size;
    protected int damage;
    protected int cooldown;
    protected Color color; //utvecklas senare sprite
    protected float value;


    protected Entity(Board board, Point pos, int size, int damage, int cooldown, Color color, float value) {
	this.board = board;
	this.pos = pos;
	this.damage = damage;
	this.size = size;
	this.cooldown = cooldown;
	this.color = color;
	this.value = value;
    }

    protected abstract void addToBoard(Board board);

    protected abstract void removeFromBoard(Board board);

    public Point getPos() {
	return pos;
    }

    public int getSize() {
	return size;
    }

    public int getDamage() {
	return damage;
    }

    public int getCooldown() {
	return cooldown;
    }

    public Color getColor() {
	return color;
    }
}
