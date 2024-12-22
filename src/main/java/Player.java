
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Hrac, vie sa hybat, zbierat kluce atd.
 */
public class Player {
    private double x, y;
    private double width = 28;
    private double height = 28;
    private double velX, velY;
    private double gravity = 0.28;
    private double maxFallSpeed = 4;

    private Image playerImage;
    private int keysCollected = 0;

    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        playerImage = new Image(getClass().getResourceAsStream("/player.png"));
    }

    public void update(Level level) {
        velY += gravity;
        if (velY > maxFallSpeed) {
            velY = maxFallSpeed;
        }

        x += velX;
        if (level.isColliding(x, y, width, height)) {
            x -= velX;
            velX = 0;
        }

        y += velY;
        if (level.isColliding(x, y, width, height)) {
            y -= velY;
            velY = 0;
        }

        // Check, či sme nezobrali nejaký item
        Item item = level.checkItemCollision(x, y, width, height);
        if (item instanceof KeyItem) {
            keysCollected++;
            level.removeItem(item);
        }
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(playerImage, x, y, width, height);
    }

    public boolean canJump(Level level) {
        double footY = y + height + 1;
        return level.hasSolidTileAt(x, footY, width, height);
    }

    // GET/SET:
    public void setVelX(double vx) {
        this.velX = vx;
    }

    public void setVelY(double vy) {
        this.velY = vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double nx, double ny) {
        this.x = nx;
        this.y = ny;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getKeysCollected() {
        return keysCollected;
    }
}
