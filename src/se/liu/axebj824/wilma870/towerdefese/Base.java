package se.liu.axebj824.wilma870.towerdefese;


/**
 * The Base class is contains all the relevant data and methods for the base that the player protecting with the towers
 */
public class Base
{
    private int baseHP;

    public Base(int baseHP) {
	this.baseHP = baseHP;
    }

    public void baseAttacked(int damage) {
	baseHP -= damage;
    }


    public int getBaseHP() {
	return baseHP;
    }
}
