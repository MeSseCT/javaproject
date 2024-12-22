
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Vytvara scenu s hrou (Canvas).
 */
public class GameSceneController {

    private Stage mainStage;
    private GameController gameController;

    public GameSceneController(Stage stage) {
        this.mainStage = stage;
    }

    public Scene initGameScene() {
        Canvas canvas = new Canvas(1060, 620);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gameController = new GameController();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 1060, 620);

        // Odozva na klavesy
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    gameController.getPlayer().setVelX(-2);
                    break;
                case RIGHT:
                    gameController.getPlayer().setVelX(2);
                    break;
                case UP:
                    if (gameController.getPlayer().canJump(gameController.getCurrentLevel())) {
                        gameController.getPlayer().setVelY(-7);
                    }
                    break;
                default:
                    break;
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                case RIGHT:
                    gameController.getPlayer().setVelX(0);
                    break;
                default:
                    break;
            }
        });

        // Animacna slucka
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameController.update();

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gameController.draw(gc);

                // Text: pocet klucov
                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial", 20));
                gc.fillText("Keys: " + gameController.getPlayer().getKeysCollected(), 10, 20);
            }
        }.start();

        return scene;
    }
}
