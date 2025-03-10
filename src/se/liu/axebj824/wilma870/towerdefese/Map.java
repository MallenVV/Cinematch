package se.liu.axebj824.wilma870.towerdefese;

import java.awt.*;

public class Map
{
    private final static int HALF_ROAD = GlobalVariable.ROAD_SIZE/2;
    private SquareType[][] map;
    private int width;
    private int height;
    private Point start;

    public Map(int width, int height) {
	this.width = width;
	this.height = height;
	this.map = createTrail(width, height);
//	this.map = createMap(width, height);
	this.start = new Point(0, height / 2);
    }

//    private SquareType[][] createMap(float width, float height) {
//	SquareType[][] newMap = new SquareType[(int)width][(int)height];
//	for (int x = 0; x < width; x++) {
//	    for (int y = 0; y < height; y++) {
//
//		if (map[x][y] != SquareType.TRAIL) {
//		    newMap[x][y] = SquareType.GRASS;
//		    if (y > height / 2 - RSIZE / 2 && y < height / 2 + RSIZE / 2) {
//			newMap[x][y] = SquareType.ROAD;
//			if (x > width - 1 - RSIZE) {
//			    newMap[x][y] = SquareType.BASE;
//			}
//		    }
//		}
//	    }
//	}
//	return newMap;
//    }

    private SquareType[][] createTrail(int width, int height) {
	SquareType[][] newMap = new SquareType[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {


		if (y == height / 2 && x <= width / 2) {
		    newMap[x][y] = SquareType.TRAIL;
		    paintRoadH(newMap,x,y);
		    paintFillH(newMap,x,y);

		} else if (x == width / 2 && y >= height / 2 && y <= height / 2 + 40) {
		    newMap[x][y] = SquareType.TRAIL;
		    paintRoadV(newMap,x,y);
		    paintFillV(newMap,x,y);

		} else if (y == height / 2 + 40 && x >= width / 2) {
		    newMap[x][y] = SquareType.TRAIL;
		    paintRoadH(newMap,x,y);
		    if (x >= width - GlobalVariable.ROAD_SIZE) {
			newMap[x][y] = SquareType.BASE;

			for (int i = 1; i <= HALF_ROAD; i++) {
			    if (newMap[x][y+i] != SquareType.TRAIL) {
				newMap[x][y+i] = SquareType.BASE;
			    }

			    if (newMap[x][y-i] != SquareType.TRAIL) {
				newMap[x][y-i] = SquareType.BASE;
			    }
			}

		    }

		} else if (newMap[x][y] == null) {
		    newMap[x][y] = SquareType.GRASS;
		}
	    }
	}
	return newMap;
    }


    private void paintRoadH(SquareType[][] newMap, int x, int y) {
	for (int i = 1; i <= HALF_ROAD; i++) {
	    if (newMap[x][y+i] != SquareType.TRAIL) {
		newMap[x][y+i] = SquareType.ROAD;
	    }

	    if (newMap[x][y-i] != SquareType.TRAIL) {
		newMap[x][y-i] = SquareType.ROAD;
	    }
	}
    }

    private void paintFillH(SquareType[][] newMap, int x, int y) {
	for (int i = 1; i < HALF_ROAD + 1; i++) {
	    newMap[x+i][y] = SquareType.ROAD;
	    paintRoadH(newMap,x+i, y);
	}
    }

    private void paintRoadV(SquareType[][] newMap, int x, int y) {
	for (int i = 1; i <= HALF_ROAD; i++) {
	    if (newMap[x+i][y] != SquareType.TRAIL) {
		newMap[x+i][y] = SquareType.ROAD;
	    }

	    if (newMap[x-i][y] != SquareType.TRAIL) {
		newMap[x-i][y] = SquareType.ROAD;
	    }
	}
    }

    private void paintFillV(SquareType[][] newMap, int x, int y) {
	for (int i = 1; i < HALF_ROAD + 1; i++) {
	    newMap[x][y+i] = SquareType.ROAD;
	    paintRoadV(newMap,x, y+i);
	}
    }


    public boolean createPlatforms(int x, int y) {

	if (x % 20 == 0) {
	    if (x <= width / 2) {

		if (y == height/2 - HALF_ROAD + 18 && x != 0) {
		    paintPlatform(x,y);
		    return true;
		}

		if (y == height/2 + HALF_ROAD - 18 && x != 0) {
		    paintPlatform(x,y);
		    return true;
		}

	    }
	}

	if (x % 20 == 10) {
	    if (x > width / 2) {

		if (y == height/2+40 - HALF_ROAD + 18) {
		    paintPlatform(x,y);
		    return true;
		}

		if (y == height/2+40 + HALF_ROAD - 18) {
		    paintPlatform(x,y);
		    return true;
		}

	    }
	}

	if (x == width/2- HALF_ROAD + 18 && y % 20 == 0) {
	    if (y > height / 2 - 15 && y < height / 2 + 40){
		paintPlatform(x,y);
		return true;
	    }
	}

	if (x == width/2+ HALF_ROAD - 18 && y % 20 == 8) {
	    if (y > height / 2 + 15 && y < height / 2 + 60) {
		paintPlatform(x, y);
		return true;
	    }
	}
	return false;
    }

    private void paintPlatform(int x, int y) {
	boolean invalidPlacement = false;
	int size = GlobalVariable.PLATFORM_SIZE;
	for (int i = -size / 2; i <= size / 2; i++) {
	    for (int j = 0; j <= size / 2; j++) {
		if (map[x+i][y+j] != SquareType.GRASS) {
		    invalidPlacement = true;
		    break;
		}
	    }
	}
	if (!invalidPlacement) {
	    for (int i = -size / 2; i <= size / 2; i++) {
		for (int j = 0; j <= size / 2; j++) {
		    map[x+i][y+j] = SquareType.PLATFORM;
		    map[x+i][y-j] = SquareType.PLATFORM;
		}
	    }
	}
    }

    public Point getStart() {
	return start;
    }

    public SquareType getMapSquare(int x, int y) {
	return map[x][y];
    }
}
