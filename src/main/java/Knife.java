
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Predstavuje akoby "strelu" z blocku, moze
 * mat pohyb do Lava / Doprava, atd.
 * Pre ukazku je zatial staticka.
 */
public class Knife extends Hazard {

    private Image knifeImage;

    public Knife(double x, double y, String imagePath) {
        super(x, y, 32, 32);
        knifeImage = new Image(getClass().getResourceAsStream(imagePath));
    }

    @Override
    public void update(Level level) {
        // Ak by ste chceli, moze sa hybat do Lava / Doprava
        // Napr. x -= 2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(knifeImage, x, y, width, height);
    }
}
