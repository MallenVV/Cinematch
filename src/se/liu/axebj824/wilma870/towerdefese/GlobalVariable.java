package se.liu.axebj824.wilma870.towerdefese;

/**
 Constant variables used in manný parts of the code
 */
public class GlobalVariable
{
    /** Decides the size of the window/map */
    public final static int MAP_SIZE = 250;
    /** Decides the width of the window/map */
    public final static int MAP_WIDTH = MAP_SIZE;
    /** Decides the height of the window/map */
    public final static int MAP_HEIGHT = (MAP_WIDTH / 16) * 9;
    /** Decides the amount of pixels in one square that we draw */
    public final static int SQUARE_SIZE = 5;
    /** how often we run gametick*/
    public final static int UPDATE_INTERVAL = 16; //in ms
    /** how big the road should be*/
    public final static int ROAD_SIZE = 8;
    /** how big the towerplatforms should be */
    public final static int PLATFORM_SIZE = 10;
    /** Says if waves will autostart or not*/
    public static boolean autoStartWaves = false;
    /** If auto start waves not true, this decides if waves start or not*/
    public static boolean startWave = false;
}

