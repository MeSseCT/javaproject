public enum TileType {
    EMPTY(0), // Prázdne miesto
    STONE(1), // Kameň
    GRASS(2), // Tráva
    SPIKE(3),
    DIRT(4);

    private final int code;

    TileType(int code) {
        this.code = code;
    }

    public static TileType fromCode(int code) {
        for (TileType type : TileType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return EMPTY; // Ak hodnota nie je platná, vráť prázdnu dlaždicu
    }
}
