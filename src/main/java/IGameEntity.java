
import javafx.scene.canvas.GraphicsContext;

/**
 * Rozhranie pre objekty v hre, ktore sa mozu
 * vykreslovat (draw) a aktualizovat (update).
 */
public interface IGameEntity {
    void update(Level level);
    void draw(GraphicsContext gc);
    boolean intersects(double x, double y, double w, double h);
}
