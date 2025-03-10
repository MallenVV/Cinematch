package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;


/**
 * A class for spawning entities on the board
 */

public class Spawner
{
    public Spawner() {
    }

    public static void spawnDefaultTower(Board board, Point pos) {
	int size = 7;
	int damage = 5;
	int cooldown = 500;
	Color color = Color.BLUE;
	int radius = 35;
	float price = GlobalPrices.DEFAULT_TOWER_PRICE;
	Tower tow = new Tower(board, pos, size, damage, cooldown, color, price,radius);
	board.addEntity(tow);
    }

    public static void spawnDefaultEnemy(Board board) {
	int size = 5;
	int damage = 5;
	int cooldown = 1000;
	Color color = Color.RED;
	int hp = 10;
	int speed = 7;
	float value = GlobalPrices.DEFAULT_ENEMY_VALUE;
	Point pos = board.getMap().getStart();

	Enemy en = new Enemy(board, pos, size, damage, cooldown, color, value, hp, speed);
	board.addEntity(en);
    }

    public static void spawnSpeedyEnemy(Board board) {
	int size = 4;
	int damage = 10;
	int cooldown = 1000;
	Color color = Color.GREEN;
	int hp = 5;
	int speed = 15;
	float value = GlobalPrices.SPEEDY_ENEMY_VALUE;
	Point pos = board.getMap().getStart();

	Enemy en = new Enemy(board, pos, size, damage, cooldown, color, value, hp, speed);
	board.addEntity(en);
    }

}
