
import javafx.scene.canvas.GraphicsContext;

/**
 * Zaklad pre nebezpecne objekty (Enemy, Knife...).
 * Maju update a draw, koliziu, atd.
 */
public abstract class Hazard {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    public Hazard(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(double px, double py, double pw, double ph) {
        return (px < x + width && px + pw > x && py < y + height && py + ph > y);
    }

    public abstract void update(Level level);
    public abstract void draw(GraphicsContext gc);
}
