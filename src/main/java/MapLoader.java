import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
    public static int[][] loadMapFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                MapLoader.class.getResourceAsStream(filePath)))) {

            List<int[]> rows = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int[] row = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Integer.parseInt(values[i].trim());
                }
                rows.add(row);
            }

            int[][] mapData = new int[rows.size()][];
            for (int i = 0; i < rows.size(); i++) {
                mapData[i] = rows.get(i);
            }

            return mapData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Chyba pri načítaní mapy zo súboru: " + filePath);
        }
    }
}
