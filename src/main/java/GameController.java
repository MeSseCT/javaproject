
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Riadime logiku hry, drzime si zoznam Levelov, aktualny index,
 * hraca, nacitavame mapy, atd.
 */
public class GameController {

    private List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = 0;
    private Player player;

    private boolean hasCheckpoint = false;
    private double checkpointX, checkpointY;

    public GameController() {
        // Načítanie/konštrukcia máp
        loadLevels();

        // Vytvorenie hráča
        Level startLevel = levels.get(0);
        player = new Player(startLevel.getStartX(), startLevel.getStartY());
    }

    private void loadLevels() {
        // Ukážka, ako by sme mohli načítať mapy z textových súborov:
        // (samozrejme, môžete to robiť aj priamo v kóde)

        int[][] map1 = MapLoader.loadMapFromFile("/maps/map1.txt");
        Level l1 = new Level(0, map1, 32, 450);

        int[][] map2 = MapLoader.loadMapFromFile("/maps/map2.txt");
        Level l2 = new Level(1, map2, 50, 100);

        int[][] map3 = MapLoader.loadMapFromFile("/maps/map3.txt");
        Level l3 = new Level(2, map3, 50, 100);

      //  int[][] map4 = MapLoader.loadMapFromFile("/maps/map4.txt");
      //  Level l4 = new Level(3, map4, 50, 100); //

        levels.add(l1);
        levels.add(l2);
        levels.add(l3);
      //  levels.add(l4);

        // Môžete pridať itemy / hazardy do jednotlivých Levelov:
        // Príklad: kľúč + checkpoint do l2
        l2.addItem(new KeyItem(8 * 32, 3 * 32));
        l2.addItem(new CheckpointItem(10 * 32, 3 * 32));

        // Príklad: nepriateľ + nôž do l2
        l2.addHazard(new Enemy(6 * 32, 9 * 32, 6 * 32, 12 * 32, 1.0, "/enemy.png"));
        l2.addHazard(new Knife(15 * 32, 5 * 32, "/knife.png"));
    }

    public void update() {
        Level current = getCurrentLevel();

        // 1) Update hráča
        player.update(current);

        // 2) Update hazardov (nepriatelia, nože, atď.)
        for (Hazard h : current.getHazards()) {
            h.update(current);
        }

        // 3) Kolízia so spike
        if (current.onSpike(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            killPlayer();
        }

        // 4) Kolízia s hazardmi
        for (Hazard h : current.getHazards()) {
            if (h.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                killPlayer();
            }
        }

        // 5) Prechod okrajmi
        if (current.shouldGoNextLevel(player.getX(), player.getY())) {
            goNextLevel();
        }
        if (current.shouldGoPrevLevel(player.getX(), player.getY())) {
            goPrevLevel();
        }

        // 6) Prechod dverami
        if (current.onDoor(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
            goNextLevel();
        }

        // 7) Checkpoint?
        Item item = current.checkItemCollision(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        if (item instanceof CheckpointItem) {
            hasCheckpoint = true;
            checkpointX = item.getX();
            checkpointY = item.getY();
            // Môžeme ho buď ponechať, alebo z mapy odstrániť
            // current.removeItem(item);
        }
    }

    public void draw(GraphicsContext gc) {
        getCurrentLevel().draw(gc);
        player.draw(gc);
    }

    private void killPlayer() {
        if (hasCheckpoint) {
            player.setPosition(checkpointX, checkpointY);
        } else {
            Level current = getCurrentLevel();
            player.setPosition(current.getStartX(), current.getStartY());
        }
    }

    public void goNextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
            // Zachováme Y, ak chcete, alebo reset na startY
            Level newLevel = getCurrentLevel();

            // TRIK: Ponecháme „X offset“, aby sme „vyšli“ z ľavého kraja
            player.setPosition(1, player.getY());

            // Reset checkpointu
            hasCheckpoint = false;
        }
    }

    public void goPrevLevel() {
        if (currentLevelIndex > 0) {
            currentLevelIndex--;
            Level newLevel = getCurrentLevel();

            // Ponecháme Y, aby sme tam prišli z pravej strany
            double newX = (newLevel.getWidth() * newLevel.getTileSize()) - player.getWidth() - 1;
            player.setPosition(newX, player.getY());

            // Reset checkpointu
            hasCheckpoint = false;
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public Player getPlayer() {
        return player;
    }
}
