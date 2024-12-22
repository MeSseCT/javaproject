
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Checkpoint - hrac si ho moze aktivovat, 
 * a pri umreti ho to vrati sem.
 */
public class CheckpointItem extends Item {

    private Image checkpointImage;

    public CheckpointItem(double x, double y) {
        super(x, y, 32, 32);
        checkpointImage = new Image(getClass().getResourceAsStream("/checkpoint.png"));
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(checkpointImage, x, y, width, height);
    }
}
