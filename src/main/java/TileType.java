
/**
 * Jednoduche enum pre typy dlazdic.
 */
public enum TileType {
    EMPTY(0),
    STONE(1),
    GRASS(2),
    SPIKE(3),
    DIRT(4),
    WOOD(5),
    DOOR(6);

    private final int code;

    TileType(int code) {
        this.code = code;
    }

    public static TileType fromCode(int code) {
        for (TileType tt : values()) {
            if (tt.code == code) {
                return tt;
            }
        }
        return EMPTY;
    }
}
