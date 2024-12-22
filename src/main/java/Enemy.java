
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Jednoduchy nepriatel - chodi medzi minX a maxX tam a spat.
 */
public class Enemy extends Hazard {

    private double minX;
    private double maxX;
    private double speed;
    private double direction = 1.0;
    private Image enemyImage;

    public Enemy(double x, double y, double minX, double maxX, double speed, String imagePath) {
        super(x, y, 32, 32);
        this.minX = minX;
        this.maxX = maxX;
        this.speed = speed;
        enemyImage = new Image(getClass().getResourceAsStream(imagePath));
    }

    @Override
    public void update(Level level) {
        x += speed * direction;
        if (x < minX) {
            x = minX;
            direction = 1.0;
        } else if (x > maxX) {
            x = maxX;
            direction = -1.0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(enemyImage, x, y, width, height);
    }
}
