package se.liu.axebj824.wilma870.towerdefese;


import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The tower subclass of Entity that contains logic for the towers
 *
 * @param radius the radius that the towers can shoot enemies in
 * @param lastShot the nanoTime when the latest shot was shot
 */
public class Tower extends Entity
{
    private int radius;
    private long lastShot;

    public Tower(Board board, Point pos, int size, int damage, int cooldown, Color color, float value, int radius) {
	super(board, pos, size, damage, cooldown, color, value);
	this.radius = radius;
	this.lastShot = System.nanoTime() - (cooldown * 1_000_000L);
    }


    @Override public void addToBoard(Board board) {
	board.addTower(this);
    }

    @Override public void removeFromBoard(Board board) {
	board.removeTower(this);
    }

    public void shoot() {

//	System.out.println("since last shot: " + ((System.nanoTime() - lastShot) / 1_000_000));
	Enemy removed = null;

	List<Enemy> enemies = new ArrayList<>(board.getEnemies());
	enemies.sort(Comparator.comparingInt(Enemy::getVisitedSize).reversed());

	for (Enemy e : enemies) {
	    Point enemyPos = e.getPos();
	    double distance = Math.abs(Math.hypot(pos.x - enemyPos.x, pos.y - enemyPos.y));
	    if (distance < radius && Math.abs(System.nanoTime() - lastShot) / 1_000_000 > cooldown) {
		lastShot = System.nanoTime();
		board.damageEntity(e, damage);
//		System.out.println("torn sköt på fiende");
		break;
	    }
	}
    }

    public float getValue() {
	return value;
    }


    public void upgradeSpeed(){
//	TODO mer logik för hur pengar ska gå åt och så, men konceptet fungerar samt att cooldown inte ändras för alla torn
	this.cooldown -= 200;
	System.out.println(cooldown);
    }
}
