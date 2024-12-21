import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class GameController {
    private List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = 0;
    private Player player;

    public GameController() {
        // Vytvorenie levelov s mapami
        int[][] map1 = {
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 2, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 4, 0, 0, 0, 2, 4, 0, 0, 0, 4, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 4, 0, 2, 4, 0, 0, 0, 4, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 4, 0, 0, 0, 2, 2, 2, 2, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}
        };



        int[][] map2 = {
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 2, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}
        };



        Level l1 = new Level(0, map1, 64, 64); // Počiatočná pozícia hráča na prázdnom mieste
        Level l2 = new Level(1, map2, 50, 100);

        l2.addItem(new KeyItem(8 * 32, 3 * 32));

        levels.add(l1);
        levels.add(l2);

        player = new Player(l1.getStartX(), l1.getStartY());
    }

    public void update() {
        Level current = getCurrentLevel();
        player.update(current);

        if (current.onSpike(player.getX(), player.getY(), 32, 32)) {
            player.setPosition(current.getStartX(), current.getStartY());
        }

        if (current.shouldGoNextLevel(player.getX(), player.getY())) {
            goNextLevel();
        }

        if (current.shouldGoPrevLevel(player.getX(), player.getY())) {
            goPrevLevel();
        }
    }

    public void draw(GraphicsContext gc) {
        getCurrentLevel().draw(gc);
        player.draw(gc);
    }

    public void goNextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
            Level lvl = getCurrentLevel();
            player.setPosition(lvl.getStartX(), lvl.getStartY());
        }
    }

    public void goPrevLevel() {
        if (currentLevelIndex > 0) {
            currentLevelIndex--;
            Level lvl = getCurrentLevel();
            player.setPosition(lvl.getStartX(), lvl.getStartY());
        }
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public Player getPlayer() {
        return player;
    }
}
