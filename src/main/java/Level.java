
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavuje jednu mapu (level).
 */
public class Level {

    private int[][] mapData;
    private int tileSize = 32;

    private Image grassImg;
    private Image stoneImg;
    private Image spikeImg;
    private Image dirtImg;
    private Image woodImg;

    private List<Item> items = new ArrayList<>();
    private List<Hazard> hazards = new ArrayList<>();

    private double startX;
    private double startY;
    private int index;
    private int width;
    private int height;

    public Level(int index, int[][] mapData, double startX, double startY) {
        this.index = index;
        this.mapData = mapData;
        this.startX = startX;
        this.startY = startY;

        this.height = mapData.length;
        this.width = mapData[0].length;

        grassImg = loadImage("/grass.png");
        stoneImg = loadImage("/stone.png");
        spikeImg = loadImage("/spike.png");
        dirtImg  = loadImage("/dirt.png");
        woodImg  = loadImage("/wood.png");
    }

    private Image loadImage(String path) {
        Image img = new Image(getClass().getResourceAsStream(path));
        if (img.isError()) {
            throw new RuntimeException("Image not found: " + path);
        }
        return img;
    }

    public void draw(GraphicsContext gc) {
        // Pozadie
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Kreslenie dlaždíc
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                TileType tile = TileType.fromCode(mapData[row][col]);
                double x = col * tileSize;
                double y = row * tileSize;

                switch (tile) {
                    case STONE:
                        gc.drawImage(stoneImg, x, y, tileSize, tileSize);
                        break;
                    case GRASS:
                        gc.drawImage(grassImg, x, y, tileSize, tileSize);
                        break;
                    case SPIKE:
                        gc.drawImage(spikeImg, x, y, tileSize, tileSize);
                        break;
                    case DIRT:
                        gc.drawImage(dirtImg, x, y, tileSize, tileSize);
                        break;
                    case WOOD:
                        gc.drawImage(woodImg, x, y, tileSize, tileSize);
                        break;
                    default:
                        // NO-OP (prázdne)
                        break;
                }
            }
        }

        // Kreslenie itemov
        for (Item it : items) {
            it.draw(gc);
        }

        // Kreslenie hazardov
        for (Hazard h : hazards) {
            h.draw(gc);
        }
    }

    public boolean isColliding(double px, double py, double pw, double ph) {
        int leftTile   = (int)(px / tileSize);
        int rightTile  = (int)((px + pw) / tileSize);
        int topTile    = (int)(py / tileSize);
        int bottomTile = (int)((py + ph) / tileSize);

        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                if (row < 0 || row >= height || col < 0 || col >= width) {
                    return true; // out-of-bounds => kolizia
                }
                TileType tile = TileType.fromCode(mapData[row][col]);
                if (tile == TileType.STONE || tile == TileType.DIRT || tile == TileType.SPIKE || tile == TileType.GRASS) {
                    return true;
                }
                if (tile == TileType.WOOD) {
                    double tileTop = row * tileSize;
                    double tileBottom = tileTop + tileSize / 4.0;
                    if ((py + ph > tileTop) && (py < tileBottom)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasSolidTileAt(double px, double py, double pw, double ph) {
        int leftTile = (int)(px / tileSize);
        int rightTile = (int)((px + pw) / tileSize);
        int footTileY = (int)(py / tileSize);

        for (int col = leftTile; col <= rightTile; col++) {
            if (footTileY < 0 || footTileY >= height || col < 0 || col >= width) {
                continue;
            }

            TileType tile = TileType.fromCode(mapData[footTileY][col]);
            if (tile == TileType.STONE || tile == TileType.DIRT || tile == TileType.GRASS) {
                return true;
            }
            if (tile == TileType.WOOD) {
                double tileTop = footTileY * tileSize;
                double tileBottom = tileTop + tileSize / 4.0;
                if (py + ph > tileTop && py < tileBottom) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onSpike(double px, double py, double pw, double ph) {
        int leftTile   = (int)(px / tileSize);
        int rightTile  = (int)((px + pw) / tileSize);
        int topTile    = (int)(py / tileSize);
        int bottomTile = (int)((py + ph) / tileSize);

        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                if (row < 0 || row >= height || col < 0 || col >= width) {
                    continue;
                }
                TileType tile = TileType.fromCode(mapData[row][col]);
                if (tile == TileType.SPIKE) {
                    return true;
                }
            }
        }
        return false;
    }

    public Item checkItemCollision(double px, double py, double pw, double ph) {
        for (Item it : items) {
            if (it.intersects(px, py, pw, ph)) {
                return it;
            }
        }
        return null;
    }

    public boolean onDoor(double px, double py, double pw, double ph) {
        int leftTile   = (int)(px / tileSize);
        int rightTile  = (int)((px + pw) / tileSize);
        int topTile    = (int)(py / tileSize);
        int bottomTile = (int)((py + ph) / tileSize);

        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                if (row < 0 || row >= height || col < 0 || col >= width) {
                    continue;
                }
                TileType tile = TileType.fromCode(mapData[row][col]);
                if (tile == TileType.DOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldGoNextLevel(double px, double py) {
        return (px + 28) > (width * tileSize);
    }

    public boolean shouldGoPrevLevel(double px, double py) {
        return px < 0;
    }

    public void addItem(Item it) {
        items.add(it);
    }

    public List<Hazard> getHazards() {
        return hazards;
    }

    public void addHazard(Hazard h) {
        hazards.add(h);
    }

    public double getStartX() {
        return startX;
    }
    public double getStartY() {
        return startY;
    }

    public int getIndex() {
        return index;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getTileSize() {
        return tileSize;
    }
    public void removeItem(Item item) {
        items.remove(item);
    }

}
