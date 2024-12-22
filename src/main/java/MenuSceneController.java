
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Riesenie menu obrazovky, obsahuje tlacidlo Start,
 * pri ktorom sa prepneme na GameScene.
 */
public class MenuSceneController {

    private Stage mainStage;

    public MenuSceneController(Stage stage) {
        this.mainStage = stage;
    }

    public Scene initMenuScene() {
        VBox root = new VBox();
        root.setSpacing(10);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            // Po kliknuti vytvorime GameScene
            GameSceneController gameController = new GameSceneController(mainStage);
            Scene gameScene = gameController.initGameScene();
            mainStage.setScene(gameScene);
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            mainStage.close();
        });

        root.getChildren().addAll(startButton, exitButton);

        Scene scene = new Scene(root, 400, 300);
        return scene;
    }
}
