import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class KeyItem extends Item {
    private Image keyImage;

    public KeyItem(double x, double y) {
        super(x, y, 32, 32);
        // nahrajte obrázok kľúča, musí byť v resources
        keyImage = new Image(getClass().getResourceAsStream("/key.png"));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(keyImage, x, y, width, height);
    }
}
