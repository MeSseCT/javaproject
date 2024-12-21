import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

public class Level {
    private int[][] mapData;
    private int tileSize = 32; // Nastavíme správnu veľkosť dlaždíc
    private Image grassImg, stoneImg, spikeImg, dirtImg;

    private List<Item> items = new ArrayList<>();
    private double startX = 100;
    private double startY = 100;

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
        dirtImg = loadImage("/dirt.png");

    }

    private Image loadImage(String path) {
        Image img = new Image(getClass().getResourceAsStream(path));
        if (img.isError()) {
            throw new RuntimeException("Image not found: " + path);
        }
        return img;
    }

    public void draw(GraphicsContext gc) {
        // Čierne pozadie
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
                    default:
                        break; // Prázdna dlaždica
                }
            }
        }



    // Kreslenie predmetov
        for (Item it : items) {
            it.draw(gc);
        }

        gc.restore();


    // Vykresli itemy
        for (Item it : items) {
            it.draw(gc);
        }
    }

    public boolean isColliding(double px, double py, double pw, double ph) {
        int leftTile = Math.max(0, (int)(px / tileSize));
        int rightTile = Math.min(width - 1, (int)((px + pw) / tileSize));
        int topTile = Math.max(0, (int)(py / tileSize));
        int bottomTile = Math.min(height - 1, (int)((py + ph) / tileSize));

        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                TileType tile = TileType.fromCode(mapData[row][col]);
                if (tile == TileType.STONE || tile == TileType.GRASS || tile == TileType.DIRT) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean hasSolidTileAt(double px, double py, double pw, double ph) {
        // Rovnaké ako isColliding, ale vráti true ak aspoň 1 pevná dlaždica dole
        int leftTile = (int)(px / tileSize);
        int rightTile = (int)((px + pw) / tileSize);
        int footTileY = (int)(py / tileSize);

        for (int col = leftTile; col <= rightTile; col++) {
            if (footTileY < 0 || footTileY >= height || col < 0 || col >= width) continue;
            TileType tile = TileType.fromCode(mapData[footTileY][col]);
            if (tile == TileType.STONE || tile == TileType.GRASS || tile == TileType.DIRT) {
                return true;
            }
        }
        return false;
    }

    public boolean onSpike(double px, double py, double pw, double ph) {
        int leftTile = (int)(px / tileSize);
        int rightTile = (int)((px + pw) / tileSize);
        int topTile = (int)(py / tileSize);
        int bottomTile = (int)((py + ph) / tileSize);

        for (int row = topTile; row <= bottomTile; row++) {
            for (int col = leftTile; col <= rightTile; col++) {
                if (row < 0 || row >= height || col < 0 || col >= width) continue;
                TileType tile = TileType.fromCode(mapData[row][col]);
                if (tile == TileType.SPIKE) return true;
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

    public void removeItem(Item it) {
        items.remove(it);
    }

    public double getStartX() { return startX; }
    public double getStartY() { return startY; }

    public int getIndex() { return index; }

    // Tu skontrolujeme, či je hráč na nejakom výstupe - napr. ak stojí na pravom okraji
    // Alebo použijeme špeciálny tile na prechod do ďalšieho levelu
    public boolean shouldGoNextLevel(double px, double py) {
        // Napr. ak hráčova x koordinácia > šírka mapy * tileSize - 50, prejde na ďalší level
        return (px > (width*tileSize - 50));
    }

    public boolean shouldGoPrevLevel(double px, double py) {
        // napr. ak px < 0 + nejaká hranica
        return (px < 10);
    }

    public void addItem(Item it) {
        items.add(it);
    }
}
