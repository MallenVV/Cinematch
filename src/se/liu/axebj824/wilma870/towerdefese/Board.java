package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The board class is containing al of the relevant measures for the map htat is displayed when the game is played. Furthermore, it also
 * holds all entities(i.e Enemy and Tower) and towerplatforms that is currently on the board. It is a centerpiece for the game and is the
 * "board" where our game is played.
 */

public class Board
{
    //    private ArrayList<BoardListener> listeners = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Tower> towers = new ArrayList<>();
    private List<TowerPlatform> platforms = new ArrayList<>();
    private float money = 10;
    private Map map;
    private int width;
    private int height;
    private Base base;

    /**
     * Creates a board with height and width according to the global variables MAP_WIDTH and MAP_HEIGHT. The board also gets a Map with the
     * same width and height, and a base with 250 HP.
     *
     * @param width  The width of the board, same as the width of the map, since the board should be the same size as the map
     * @param height The height of the board, same as the height of the map, since the board should be the same size as the map
     * @param map    The map that specifies how the map should be rendered
     * @param base   The players base that the towers will protect.
     */
    public Board() {
	this.width = GlobalVariable.MAP_WIDTH;
	this.height = GlobalVariable.MAP_HEIGHT;
	this.map = new Map(width, height);
	this.base = new Base(250);
	createTowerPlatforms();
    }

    public void tick() {

	for (Tower t : towers) {
	    t.shoot();
	}

	for (Enemy e : enemies) {
	    e.move();
	    e.atBase();
	}
    }

    public void placeTower(String name, Point pos) {
	Spawner.spawnDefaultTower(this, pos);
    }

    public void createTowerPlatforms() {
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		boolean painted = map.createPlatforms(x, y);
		if (painted) {
		    addPlatform(new TowerPlatform(new Point(x, y)));
		}
	    }
	}
    }

    public boolean isEntityPos(int x, int y) {
	for (Entity e : entities) {

	    if (e.pos.x >= x - e.size / 2) {
		if (e.pos.y >= y - e.size / 2) {
		    if (e.pos.x <= x + e.size / 2) {
			if (e.pos.y <= y + e.size / 2) {
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }

    public void addPlatform(TowerPlatform p) {
	platforms.add(p);
    }

    public void addEntity(Entity entity) {
	entity.addToBoard(this);
    }

    public void addTower(Tower t) {
	if (getMoney() >= t.getValue()) {
	    spendMoney(t.getValue());
	    entities.add(t);
	    towers.add(t);
	}
    }

    public void addEnemy(Enemy e) {
	entities.add(e);
	enemies.add(e);
    }

    public void damageEntity(Enemy e, int damage) {
	e.takeDamage(damage);
	if (e.getHp() <= 0) {
	    removeEntity(e);
	}
    }

    public void removeEntity(Entity entity) {
	entities.remove(entity);
	entity.removeFromBoard(this);
    }

    public void removeTower(Tower t) {
	addMoney(t.getValue() * GlobalPrices.CASH_BACK);
	towers.remove(t);
    }

    public void removeEnemy(Enemy e) {
	addMoney(e.getValue());
	enemies.remove(e);
    }


    public void addMoney(float earned) {
	money += earned;
	System.out.println("Current money: " + money);
    }

    public void spendMoney(float cost) {
	money -= cost;
	System.out.println("Current money: " + money);
    }

    public float getMoney() {
	return money;
    }


    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public Map getMap() {
	return map;
    }


    public List<Enemy> getEnemies() {
	return enemies;
    }

    public Base getBase() {
	return base;
    }


    public SquareType getVisibleSquareAt(int x, int y) {
	return map.getMapSquare(x, y);
    }

    public TowerPlatform getPlatform(int x, int y) {
	for (TowerPlatform p : platforms) {
	    if (p.atPosition(x, y)) {
		return p;
	    }
	}
	return null;
    }

    public Entity getEntity(int x, int y) {
	for (Entity e : entities) {
	    if (e.pos.x >= x - e.size / 2 && e.pos.y >= y - e.size / 2 && e.pos.x <= x + e.size / 2 && e.pos.y <= y + e.size / 2) {
		return e;
	    }
	}
	return null;
    }

    public Tower getTower(int x, int y) {
	for (Tower e : towers) {
	    if (e.pos.x >= x - e.size / 2 && e.pos.y >= y - e.size / 2 && e.pos.x <= x + e.size / 2 && e.pos.y <= y + e.size / 2) {
		return e;
	    }
	}
	return null;
    }

}
