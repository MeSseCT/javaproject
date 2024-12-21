import javafx.scene.canvas.GraphicsContext;


public abstract class Item {
    protected double x, y;
    protected double width, height;

    public Item(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(GraphicsContext gc);

    public boolean intersects(double px, double py, double pw, double ph) {
        return px + pw > x && px < x+width && py + ph > y && py < y+height;
    }
}
