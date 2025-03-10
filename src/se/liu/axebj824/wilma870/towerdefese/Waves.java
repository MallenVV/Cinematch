//package se.liu.liuid123.towerdefese;
//
//import java.util.HashMap;
//
//
//public class Wave
//{
//    //    		    wave   	type of enemie, how many
//    private static HashMap<Integer, HashMap<String, Integer>> waves = new HashMap<>();
//    private Board board;
//    private Spawner spawner = new Spawner();
//
//    public Wave(Board board) {
//	this.board = board;
//
//	HashMap<String, Integer> wave1 = new HashMap<>();
//	wave1.put("def", 20);
//	HashMap<String, Integer> wave2 = new HashMap<>();
//	wave2.put("def", 50);
//	HashMap<String, Integer> wave3 = new HashMap<>();
//	wave3.put("def", 20);
//	wave3.put("speed", 15);
//
//	waves.put(1, wave1);
//	waves.put(2, wave2);
//	waves.put(3, wave3);
//    }
//
//
//    public void start() {
//	int waveNum = 1;
//	waves.get(waveNum).forEach((type, times) -> {
//	    long lastSpawn = System.nanoTime() - 1000;
//	    int num = 0;
//	    while (num < times) {
//		if (Math.abs(System.nanoTime() - lastSpawn) / 1_000_000 > 1000) {
//		    spawn(type);
//		    lastSpawn = System.nanoTime();
//		    num++;
//		}
//
//	    }
//	});
//	waveNum++;
//
//
//    }
//
//
//    public void spawn(String type) {
//	System.out.println("spawning enemy in Wave");
//	switch (type) {
//	    case "def" -> spawner.spawnDefaultEnemy(board);
//	    case "speed" -> spawner.spawnSpeedyEnemy(board);
//	}
//    }
//
//    public HashMap<Integer, HashMap<String, Integer>> getWaves() {
//	return waves;
//    }
//}
package se.liu.axebj824.wilma870.towerdefese;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

public class Waves
{
    private Map<Integer, Map<String, Integer>> waves = new HashMap<>();
    private Board board;
    private int currentWave = 1;
    private int enemiesSpawned = 0;
    private int waveGroupSpawned = 0;
    private long lastSpawnTime = System.nanoTime();
    private boolean waveActive = false;
    private String currentEnemyType = null;
    private long lastWaveTime = System.nanoTime();

    public Waves(Board board) {
	this.board = board;

	Map<String, Integer> wave1 = new HashMap<>();
	wave1.put("def", 5);
	Map<String, Integer> wave2 = new HashMap<>();
	wave2.put("def", 15);
	Map<String, Integer> wave3 = new HashMap<>();
	wave3.put("def", 20);
	wave3.put("speed", 15);

	waves.put(1, wave1);
	waves.put(2, wave2);
	waves.put(3, wave3);
    }

    /**
     * Körs varje tick från huvud-loopen
     */
    public void update() {
	if (waveActive) {
	    long currentTime = System.nanoTime();
	    if (currentEnemyType != null && enemiesSpawned < waves.get(currentWave).get(currentEnemyType)) {
		if ((currentTime - lastSpawnTime) / 1_000_000 > 500) { // Spawn var 1000 ms
		    spawn(currentEnemyType);
		    lastSpawnTime = currentTime;
		    enemiesSpawned++;
		}
	    } else {
		// Kolla om det finns fler fiendetyper i vågen
		nextEnemyType();
	    }
	}
	else if (GlobalVariable.autoStartWaves) {
	    startNextWave();
	}
	else {
	    waitForStart();
	}
    }

    /**
     * Startar nästa våg
     */
    public void startNextWave() {
	if (waves.containsKey(currentWave) && (System.nanoTime() - lastWaveTime) / 1_000_000 > 1500) {
	    System.out.println("Starting wave " + currentWave);
	    waveActive = true;
	    enemiesSpawned = 0;
	    waveGroupSpawned = 0;
	    lastSpawnTime = System.nanoTime();
	    currentEnemyType = null;
	    nextEnemyType();
	} else if (!waves.containsKey(currentWave) && board.getEnemies().isEmpty()){
	    System.out.println("No more waves!");
	}
    }

    /**
     * Väljer nästa fiendetyp i vågen
     */
    private void nextEnemyType() {
	if (waves.get(currentWave) != null) {
	    int loops = 0;
	    for (String type : waves.get(currentWave).keySet()) {
		if (waveGroupSpawned > loops) {
		    loops++;
		}
		else {
		    currentEnemyType = type;
		    waveGroupSpawned++;
		    enemiesSpawned = 0;
		    return;
		}

	    }
	    // Om alla fiender spawnats, avsluta vågen
	    finishWave();
	}
    }

    /**
     * Markerar vågen som klar och förbereder för nästa
     */
    private void finishWave() {
	System.out.println("Wave " + currentWave + " finished!");
	waveActive = false;
	currentWave++;
	lastWaveTime = System.nanoTime();
    }

    /**
     * Spawnar en fiende
     */
    public void spawn(String type) {
//	System.out.println("Spawning " + type + " enemy");
	switch (type) {
	    case "def" -> Spawner.spawnDefaultEnemy(board);
	    case "speed" -> Spawner.spawnSpeedyEnemy(board);
	}
    }

    public void waitForStart() {
	if (GlobalVariable.startWave) {
	    startNextWave();
	    GlobalVariable.startWave = false;
	}
    }

    public Map<Integer, Map<String, Integer>> getWaves() {
	return waves;
    }
}
