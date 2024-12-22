
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Klic - ked sa ho hrac dotkne, zvysi sa mu pocet klucov.
 */
public class KeyItem extends Item {

    private Image keyImage;

    public KeyItem(double x, double y) {
        super(x, y, 32, 32);
        keyImage = new Image(getClass().getResourceAsStream("/key.png"));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(keyImage, x, y, width, height);
    }
}
