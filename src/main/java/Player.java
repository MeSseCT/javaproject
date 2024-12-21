import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    private double x, y;
    private double width = 30; // Zvýšená veľkosť hráča
    private double height = 30;
    private double velX, velY;
    private double gravity = 0.5;
    private double maxFallSpeed = 10;
    private Image playerImage;

    private int keysCollected = 0;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        playerImage = new Image(getClass().getResourceAsStream("/player.png"));
    }

    public void update(Level level) {
        velY += gravity;
        if (velY > maxFallSpeed) velY = maxFallSpeed;

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
        // Stojí na pevnom povrchu?
        double footY = y + height + 1;
        return level.hasSolidTileAt(x, footY, width, height);
    }

    public void setVelX(double vx) { this.velX = vx; }
    public void setVelY(double vy) { this.velY = vy; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void setPosition(double x, double y) { this.x = x; this.y = y; }

    public int getKeysCollected() {
        return keysCollected;
    }
}
