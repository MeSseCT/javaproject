// Game.java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Game extends Application {
    private GameController gameController;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(1280, 720); // Väčšia veľkosť
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gameController = new GameController();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Platformer");
        primaryStage.setResizable(false);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:  gameController.getPlayer().setVelX(-3); break;
                case RIGHT: gameController.getPlayer().setVelX(3); break;
                case UP:
                    if (gameController.getPlayer().canJump(gameController.getCurrentLevel())) {
                        gameController.getPlayer().setVelY(-10);
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT: case RIGHT: gameController.getPlayer().setVelX(0); break;
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameController.update();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gameController.draw(gc);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
