
import javafx.scene.canvas.GraphicsContext;

/**
 * Zaklad pre itemy (klice, checkpointy, a pod.).
 */
public abstract class Item {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public Item(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(double px, double py, double pw, double ph) {
        return (px < x + width && px + pw > x && py < y + height && py + ph > y);
    }

    public abstract void draw(GraphicsContext gc);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
