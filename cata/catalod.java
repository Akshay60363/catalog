import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class catalod{


    private static long decodeValue(int base, String value) {
        return Long.parseLong(value, base);
    }

    private static double lagrangeInterpolation(List<Point> points, int k) {
        double c = 0;

        for (int j = 0; j < k; j++) {
            double xj = points.get(j).x;  
            double yj = points.get(j).y;  

            double term = yj; // This will hold the current term of the sum

            for (int m = 0; m < k; m++) {
                if (m != j) {
                    double xm = points.get(m).x;
                    term *= xm / (xm - xj);
                }
            }

            c += term;
        }

        return c;
    }

    public static void main(String[] args) {
        try {
            // Read the JSON input from a file (example filename)
            String content = new String(Files.readAllBytes(Paths.get("input.json")));
            JSONObject data = new JSONObject(content);

            int n = data.getJSONObject("keys").getInt("n");
            int k = data.getJSONObject("keys").getInt("k");

            List<Point> points = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                JSONObject root = data.getJSONObject(String.valueOf(i));
                int base = root.getInt("base");
                String value = root.getString("value");

                // Key serves as x coordinate
                double x = i;  
                // Decode the y coordinate
                double y = decodeValue(base, value); 
                
                points.add(new Point(x, y));
            }
            double secretC = lagrangeInterpolation(points.subList(0, k), k);
            System.out.printf("The constant term c is: %.0f%n", secretC);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Class to represent a point (x, y)
    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
