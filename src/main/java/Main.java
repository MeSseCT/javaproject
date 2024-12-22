
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Spusta JavaFX aplikaciu, zobrazuje uvodnu MenuScene.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MenuSceneController menuController = new MenuSceneController(primaryStage);
        Scene menuScene = menuController.initMenuScene();

        primaryStage.setTitle("My 2D Platformer");
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
